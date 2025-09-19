public class SimplifyConstants {



    public static int testZeroMultiplication(int x) {
        int a = 0;
        int b = 0;
        return a + b;
    }

    public static boolean testBooleanConstants() {
        boolean a = true;
        boolean b = false;
        boolean c = true;
        boolean d = false;
        return a && b && c && d;
    }

    public static boolean testBooleanIdentity(boolean x) {
        boolean a = x;
        boolean b = x;
        boolean c = x;
        boolean d = x;
        return a && b && c && d;
    }

    public static boolean testBooleanShortCircuit(boolean x) {
        boolean a = false;
        boolean b = false;
        boolean c = true;
        boolean d = true;
        return a && b && c && d;
    }

    public static int testComplexExpressions(int x) {
        int a = x;
        int b = 3 * x;
        int c = x * 5;
        return a + b + c;
    }
}