package ExceptionProblems;

import java.util.*;

public class hackerrankPR1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            int x = sc.nextInt();
            int y = sc.nextInt();
            int div = x / y;
            System.out.println(div);

        } catch (InputMismatchException e) {
            // HackerRank usually wants the class name for input errors
            System.out.println(e.getClass().getName());
        } catch (ArithmeticException e) {
            System.out.println(e);
        }
    }
}
