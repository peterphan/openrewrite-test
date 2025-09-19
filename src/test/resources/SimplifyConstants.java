public class SimplifyConstants {

    public static int testArithmeticConstants() {
        int a = 1 + 2;           // Should become: 3
        int b = 5 - 3;           // Should become: 2
        int c = 4 * 3;           // Should become: 12
        int d = 8 / 2;           // Should become: 4
        return a + b + c + d;
    }

    public static int testIdentityOperations(int x) {
        int a = x + 0;           // Should become: x
        int b = 0 + x;           // Should become: x
        int c = x - 0;           // Should become: x
        int d = x * 1;           // Should become: x
        int e = 1 * x;           // Should become: x
        int f = x / 1;           // Should become: x
        return a + b + c + d + e + f;
    }

    public static int testZeroMultiplication(int x) {
        int a = x * 0;           // Should become: 0
        int b = 0 * x;           // Should become: 0
        return a + b;
    }

    public static boolean testBooleanConstants() {
        boolean a = true && true;   // Should become: true
        boolean b = false || false; // Should become: false
        boolean c = true || false;  // Should become: true
        boolean d = false && true;  // Should become: false
        return a && b && c && d;
    }

    public static boolean testBooleanIdentity(boolean x) {
        boolean a = x && true;      // Should become: x
        boolean b = true && x;      // Should become: x
        boolean c = x || false;     // Should become: x
        boolean d = false || x;     // Should become: x
        return a && b && c && d;
    }

    public static boolean testBooleanShortCircuit(boolean x) {
        boolean a = x && false;     // Should become: false
        boolean b = false && x;     // Should become: false
        boolean c = x || true;      // Should become: true
        boolean d = true || x;      // Should become: true
        return a && b && c && d;
    }

    public static int testComplexExpressions(int x) {
        int a = (x + 0) * 1;        // Should become: x
        int b = (1 + 2) * x;        // Should become: 3 * x
        int c = x * (2 + 3);        // Should become: x * 5
        return a + b + c;
    }
}