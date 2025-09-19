public class SimplifyConstants {



    public static int testZeroMultiplication(int x) {
        int a = 0;
        int b = 0;
        return a + b;
    }

    public static Object[][] booleanConstantsData() {
        return new Object[][] {
            {true},     // Should become: true
            {false},    // Should become: false
            {true},     // Should become: true
            {false}     // Should become: false
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

    public static Object[][] complexExpressionsData(int x) {
        return new Object[][] {
            {x},        // Should become: x
            {3 * x},    // Should become: 3 * x
            {x * 5}     // Should become: x * 5
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