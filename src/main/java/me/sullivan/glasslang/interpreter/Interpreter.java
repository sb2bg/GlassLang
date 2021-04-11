package me.sullivan.glasslang.interpreter;

import me.sullivan.glasslang.interpreter.errors.RuntimeError;
import me.sullivan.glasslang.interpreter.primitives.*;
import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.lexer.token.Token;
import me.sullivan.glasslang.lexer.token.TokenType;
import me.sullivan.glasslang.parser.nodes.*;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

public record Interpreter(Context context)
{
    public Context currentContext()
    {
        return context;
    }

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

    public Primitive<?> visitAssignmentNode(AssignmentNode node)
    {
        return context.getTable().set(node.getToken().getValue(), visitNode(node.getValue()), context);
    }

    public Primitive<?> visitIfNode(IfNode node)
    {
        Pair<Node, Node> ifCase = node.getValue();
        Node valueNode = ifCase.getValue1();
        Primitive<?> condition = visitNode(ifCase.getValue0());

        if (condition.isTrue())
        {
            return visitNode(valueNode);
        }
        else if (node.getElseCase() != null)
        {
            return visitNode(node.getElseCase());
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
        Primitive<?> start = visitNode(node.getStartValue());
        Primitive<?> end = visitNode(node.getEndValue());

        if (start.getType() != Type.NUMBER)
        {
            throw new RuntimeError("Start value expected to be number", context);
        }

        if (end.getType() != Type.NUMBER)
        {
            throw new RuntimeError("End value expected to be number", context);
        }

        NumberPrimitive startVal = start.getValue(Type.NUMBER);
        NumberPrimitive endVal = end.getValue(Type.NUMBER);
        NumberPrimitive stepVal = new NumberPrimitive(1, context);

        if (node.getStep() != null)
        {
            Primitive<?> step = visitNode(node.getStep());

            if (step.getType() != Type.NUMBER)
            {
                throw new RuntimeError("Step value expected to be number", context);
            }

            stepVal = visitNode(node.getStep()).getValue(Type.NUMBER);
        }

        double i = startVal.getValue();

        Condition condition = i >= 0 ? val -> val < endVal.getValue() : val -> val > endVal.getValue();

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
        return visitNode(node.nodeToCall()).call(node.getArgNodes());
    }

    public Primitive<?> visitStringNode(StringNode stringNode)
    {
        return new StringPrimitive(stringNode.getValue(), context);
    }

    public Primitive<?> visitListNode(ListNode node)
    {
        List<Primitive<?>> list = new ArrayList<>();

        for (Node valNode : node.getValue())
        {
            list.add(visitNode(valNode));
        }

        return new ListPrimitive(list, context);
    }

    public Primitive<?> visitImportNode(ImportNode node)
    {
        return new VoidPrimitive();
    }
}
