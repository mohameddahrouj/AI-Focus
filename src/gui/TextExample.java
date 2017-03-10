package gui;

/** Example of a text-based program, demonstrating the methods in the Program class. */
public class TextExample extends Program {

	public static void main(String[] args) {
		String name = readLine("What is your name?");
		printLine("Hello, " + name + "!");
		int n = readInt("Please enter an integer:");
		double x = readDouble("Please enter another number (not necessarily an integer):");
		if (readBoolean("Would you like me to multiply those two numbers (y/n)?")) {
			printLine("The product is " + (n * x));
		}
	}

}
