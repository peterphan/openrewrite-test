public class SimplifyConstants {



    public static int testZeroMultiplication(int x) {
        int a = x * 0;           // Should become: 0
        int b = 0 * x;           // Should become: 0
        return a + b;
    }

    public static Object[][] booleanConstantsData() {
        return new Object[][] {
            {true && true},     // Should become: true
            {false || false},   // Should become: false
            {true || false},    // Should become: true
            {false && true}     // Should become: false
        };
    }

    public static boolean testBooleanConstants() {
        Object[][] data = booleanConstantsData();
        boolean result = true;
        for (Object[] testCase : data) {
            result = result && (Boolean) testCase[0];
        }
        return result;
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

    public static Object[][] complexExpressionsData(int x) {
        return new Object[][] {
            {(x + 0) * 1},      // Should become: x
            {(1 + 2) * x},      // Should become: 3 * x
            {x * (2 + 3)}       // Should become: x * 5
        };
    }

    public static int testComplexExpressions(int x) {
        Object[][] data = complexExpressionsData(x);
        int result = 0;
        for (Object[] testCase : data) {
            result += (Integer) testCase[0];
        }
        return result;
    }
}