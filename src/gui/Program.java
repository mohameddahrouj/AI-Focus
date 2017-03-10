package gui;

import java.util.Scanner;

/**
 * This class contains a few static method to make console interaction less
 * arcane than it is in "raw" Java. Programs may extend this class, call the
 * methods as, e.g., ljing.Program.printLine(), or statically import these
 * methods.
 */
public class Program {

	/** Scanner attached to console input. */
	public static final Scanner IN = new Scanner(System.in);

	/**
	 * Equivalent to System.out.print, but easier to type and more consistent
	 * with Java method naming conventions.
	 */
	public static void print(Object output) {
		System.out.print(output);
	}

	/**
	 * Equivalent to System.out.println, but easier to type and more consistent
	 * with Java method naming conventions.
	 */
	public static void printLine() {
		System.out.println();
	}

	/**
	 * Equivalent to System.out.println, but easier to type and more consistent
	 * with Java method naming conventions.
	 */
	public static void printLine(Object output) {
		System.out.println(output);
	}

	/**
	 * Prints prompt (plus a space) to the console, reads an answer, and returns
	 * it as a boolean. Any answer starting with 'y' or 't' (case-insensitive)
	 * is treated as true. Any answer starting with 'n' or 'f' is treated as
	 * false. The user is prompted to supply another answer in any other case.
	 */
	public static boolean readBoolean(String prompt) {
		while (true) {
			System.out.print(prompt + " ");
			String line = IN.nextLine().trim().toLowerCase();
			if (line.startsWith("y") || line.startsWith("t")) {
				return true;
			}
			if (line.startsWith("n") || line.startsWith("f")) {
				return false;
			}
			System.out.println("Please enter y or n.");
		}
	}

	/**
	 * Prints prompt (plus a space) to the console, reads an answer, and returns
	 * it as a double. If a non-double is given, the user is prompted to supply
	 * another answer.
	 */
	public static double readDouble(String prompt) {
		while (true) {
			try {
				System.out.print(prompt + " ");
				String line = IN.nextLine().trim();
				return Double.parseDouble(line);
			} catch (NumberFormatException e) {
				System.out.println("Please enter a number.");
			}
		}
	}

	/**
	 * Prints prompt (plus a space) to the console, reads an answer, and returns
	 * it as an int. If a non-integer is given, the user is prompted to supply
	 * another answer.
	 */
	public static int readInt(String prompt) {
		while (true) {
			try {
				System.out.print(prompt + " ");
				String line = IN.nextLine().trim();
				return Integer.parseInt(line);
			} catch (NumberFormatException e) {
				System.out.println("Please enter an integer.");
			}
		}
	}

	/**
	 * Prints prompt (plus a space) to the console, reads the next line, and
	 * returns it.
	 */
	public static String readLine(String prompt) {
		System.out.print(prompt + " ");
		return IN.nextLine();
	}

}
