package me.sullivan.glasslang.interpreter;

import me.sullivan.glasslang.interpreter.errors.RuntimeError;
import me.sullivan.glasslang.interpreter.primitives.types.*;
import me.sullivan.glasslang.interpreter.primitives.types.functions.FunctionPrimitive;
import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.lexer.token.Token;
import me.sullivan.glasslang.lexer.token.TokenType;
import me.sullivan.glasslang.parser.nodes.*;
import me.sullivan.glasslang.parser.nodes.ifnodes.IfNode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public record Interpreter(Context context)
{
    public Primitive<?> visitNode(Node node)
    {
        return node.visitor(this);
    }

    public Primitive<?> visitBinOp(BinOpNode node)
    {
        Primitive<?> left = visitNode(node.getLeft());
        Primitive<?> right = visitNode(node.getRight());

        return switch (node.getValue().getType())
                {
                    case PLUS -> left.add(right);
                    case MINUS -> left.min(right);
                    case DIVIDE -> left.div(right);
                    case TIMES -> left.mul(right);
                    case POWER -> left.pow(right);
                    case GREATER -> left.greater(right);
                    case GREATER_EQUAL -> left.greaterEqual(right);
                    case LESS -> left.less(right);
                    case LESS_EQUAL -> left.lessEqual(right);
                    case EQUAL_OP -> left.equal(right);
                    case NOT_EQUAL -> left.notEqual(right);
                    case AND -> left.and(right);
                    case OR -> left.or(right);
                    case MOD -> left.mod(right);
                    default -> null;
                };
    }

    public Primitive<?> visitNumberNode(NumberNode node)
    {
        return new NumberPrimitive(node.getValue(), context);
    }

    public Primitive<?> visitUnaryOpNode(UnaryOpNode node)
    {
        Primitive<?> number = visitNode(node.getNode());

        if (node.getToken().getType() == TokenType.MINUS)
        {
            number = number.mul(new NumberPrimitive(-1, context));
        }
        else if (node.getToken().getType() == TokenType.NOT)
        {
            number = number.not();
        }

        return number;
    }

    public Primitive<?> visitVariableNode(VariableNode node)
    {
        return context.getTable().get(node.getToken().getValue(), context);
    }

    // TODO fix: if you do,
    // i = 3
    // println(i += 4)
    // it will print 7, but i still equals 3 in the top context. i only equals 7 in function context. To fix, update it at top level context?

    public Primitive<?> visitAssignmentNode(AssignmentNode node)
    {
        return switch (node.getOperator().getType())
                {
                    case EQUALS -> context.getTable().set(node.getToken().getValue(), visitNode(node.getValue()), context);
                    case PLUS_EQUALS -> context.getTable().set(node.getToken().getValue(), context.getTable().get(node.getToken().getValue(), context).add(visitNode(node.getValue())), context);
                    case MINUS_EQUALS -> context.getTable().set(node.getToken().getValue(), context.getTable().get(node.getToken().getValue(), context).min(visitNode(node.getValue())), context);
                    case DIVIDED_EQUALS -> context.getTable().set(node.getToken().getValue(), context.getTable().get(node.getToken().getValue(), context).div(visitNode(node.getValue())), context);
                    case TIMES_EQUALS -> context.getTable().set(node.getToken().getValue(), context.getTable().get(node.getToken().getValue(), context).mul(visitNode(node.getValue())), context);
                    default -> throw new RuntimeError("Operator " + node.getOperator() + " undefined for assignment", context);
                };
    }


    public Primitive<?> visitIfNode(IfNode node)
    {
        Primitive<?> condition = visitNode(node.getCondition());

        if (condition.isTrue())
        {
            Primitive<?> value = visitNode(node.getValue());

            if (!node.isStatement())
            {
                return value;
            }
        }
        else if (node.getElseCase() != null)
        {
            Primitive<?> value = visitNode(node.getElseCase());

            if (!node.isStatement())
            {
                return value;
            }
        }

        return new VoidPrimitive();
    }

    private interface Condition
    {
        boolean condition(double i);
    }

    public Primitive<?> visitForNode(ForNode node)
    {
        List<Primitive<?>> values = new ArrayList<>();
        boolean pre = context.getTable().contains(node.getValue());

        NumberPrimitive start = visitNode(node.getStartValue()).getValue(Type.NUMBER);
        NumberPrimitive end = visitNode(node.getEndValue()).getValue(Type.NUMBER);
        NumberPrimitive stepVal = node.getStep() == null ? new NumberPrimitive(1, context) : visitNode(node.getStep()).getValue(Type.NUMBER);

        double i = start.getValue();
        Condition condition = i >= 0 ? val -> val < end.getValue() : val -> val > end.getValue();

        while (condition.condition(i))
        {
            context.getTable().set(node.getValue(), new NumberPrimitive(i, context), context);
            i += stepVal.getValue();
            values.add(visitNode(node.getEval()));
        }

        if (!pre)
        {
            context.getTable().delete(node.getValue());
        }

        return new ListPrimitive(values, context);
    }

    public Primitive<?> visitWhileNode(WhileNode node)
    {
        List<Primitive<?>> values = new ArrayList<>();
        Primitive<?> condition = visitNode(node.getCondition());

        while (condition.isTrue())
        {
            values.add(visitNode(node.getValue()));
            condition = visitNode(node.getCondition());
        }

        return new ListPrimitive(values, context);
    }

    public Primitive<?> visitFuncDefinitionNode(FunctionDefinitionNode node)
    {
        String name = node.getToken().getValue();
        Node bodyNode = node.getBody();
        List<Token> args = node.getArgs();

        FunctionPrimitive function = new FunctionPrimitive(name, bodyNode, args, context);

        if (name != null)
        {
            context.getTable().set(name, function, context);
        }

        return function;
    }

    public Primitive<?> visitCallNode(CallNode node)
    {
        return visitNode(node.nodeToCall()).call(node.getArgNodes(), context);
    }

    public Primitive<?> visitStringNode(StringNode stringNode)
    {
        return new StringPrimitive(stringNode.getValue(), context);
    }

    public Primitive<?> visitListNode(ListNode node)
    {
        return new ListPrimitive(node.getValue().stream().map(this::visitNode).collect(Collectors.toList()), context);
    }

    public Primitive<?> visitMapNode(DictNode node)
    {
        return new DictionaryPrimitive(new LinkedHashMap<>()
        {{
            node.getValue().forEach((key, value) -> put(visitNode(key), visitNode(value)));
        }}, context);
    }

    public Primitive<?> visitImportNode(ImportNode node)
    {
        return new VoidPrimitive();
    }
}
