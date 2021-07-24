# GlassLang
## Note: this is the old repo, there is a new version of this project in java, and the one in rust that you can follow through https://github.com/sb2bg/RustGlass
## About Glass
Glass is a flexible and dynamic in addition to be turning complete. The language is constantly being
updated with new features. 

## Features
TODO

## Usage (multi line if and function statements are currently in the works)
Since Glass is turing complete, there's a lot you can do with it. A simple functional based approach
to FizzBuzz would look like this:
``` glass
func fizzBuzz(number) => 
    if number % 15 == 0 => 
        return "FizzBuzz"
    else if number % 3 == 0 =>
        return "Fizz"
    else if number % 5 == 0 =>
        return "Buzz"
    else 
        return number
    end
end

println(for i = 1 to 16 => fizzBuzz(i))
```
which would print `[1, 2, Fizz, 4, Buzz, Fizz, 7, 8, Fizz, Buzz, 11, Fizz, 13, 14, FizzBuzz]` to console.

