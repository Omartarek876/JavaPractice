package ExceptionProblems;

public class exception {

    // This is the "Entry Point" - IntelliJ needs this to show the Play button
    public static void main(String[] args) {

        try {
        int x = 5/0;
        } catch (ArithmeticException e) {
            System.out.println("created expection " + e);
        }
    }

}