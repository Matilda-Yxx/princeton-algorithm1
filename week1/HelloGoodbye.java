/******************************************************************************
 *  Compilation:  javac HelloGoodbye.java
 *  Execution:    java HelloGoodbye
 *
 *  Prints "HelloGoodbye with input names".
 *
 *  % java HelloGoodbye
 *  Matilda Moo
 *
 *
 ******************************************************************************/

public class HelloGoodbye {

    public static void main(String[] args) {
        // Prints "Hello, World" to the terminal window.
        System.out.println("Hello " + args[0] + " and " + args[1] + ".");
        System.out.println("Goodbye " + args[1] + " and " + args[0] + ".");
    }
}
