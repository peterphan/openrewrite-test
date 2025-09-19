import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;

/**
 * Simplifies constant arithmetic expressions and other constant values.
 * Examples:
 * - 1 + 2 → 3
 * - 5 * 0 → 0
 * - x * 1 → x
 * - x + 0 → x
 * - true && true → true
 * - false || false → false
 */
public class SimplifyConstants extends Recipe {

    @Override
    public String getDisplayName() {
        return "Simplify constant expressions";
    }

    @Override
    public String getDescription() {
        return "Simplifies arithmetic expressions with constants, identity operations, and boolean constants.";
    }

    @Override
    protected TreeVisitor<?, ExecutionContext> getVisitor() {
        return new JavaIsoVisitor<ExecutionContext>() {
            
            @Override
            public J.Binary visitBinary(J.Binary binary, ExecutionContext executionContext) {
                binary = super.visitBinary(binary, executionContext);
                
                // Handle arithmetic operations
                if (binary.getOperator() == J.Binary.Type.Addition) {
                    return simplifyAddition(binary);
                } else if (binary.getOperator() == J.Binary.Type.Subtraction) {
                    return simplifySubtraction(binary);
                } else if (binary.getOperator() == J.Binary.Type.Multiplication) {
                    return simplifyMultiplication(binary);
                } else if (binary.getOperator() == J.Binary.Type.Division) {
                    return simplifyDivision(binary);
                }
                
                // Handle boolean operations
                if (binary.getOperator() == J.Binary.Type.And) {
                    return simplifyBooleanAnd(binary);
                } else if (binary.getOperator() == J.Binary.Type.Or) {
                    return simplifyBooleanOr(binary);
                }
                
                return binary;
            }
            
            private J.Binary simplifyAddition(J.Binary binary) {
                // x + 0 → x
                if (isIntegerLiteral(binary.getRight(), 0)) {
                    return (J.Binary) binary.getLeft().withPrefix(binary.getPrefix());
                }
                // 0 + x → x
                if (isIntegerLiteral(binary.getLeft(), 0)) {
                    return (J.Binary) binary.getRight().withPrefix(binary.getPrefix());
                }
                // Constant folding: 1 + 2 → 3
                if (isIntegerLiteral(binary.getLeft()) && isIntegerLiteral(binary.getRight())) {
                    int left = getIntegerValue(binary.getLeft());
                    int right = getIntegerValue(binary.getRight());
                    return createIntegerLiteral(left + right, binary);
                }
                return binary;
            }
            
            private J.Binary simplifySubtraction(J.Binary binary) {
                // x - 0 → x
                if (isIntegerLiteral(binary.getRight(), 0)) {
                    return (J.Binary) binary.getLeft().withPrefix(binary.getPrefix());
                }
                // Constant folding: 5 - 2 → 3
                if (isIntegerLiteral(binary.getLeft()) && isIntegerLiteral(binary.getRight())) {
                    int left = getIntegerValue(binary.getLeft());
                    int right = getIntegerValue(binary.getRight());
                    return createIntegerLiteral(left - right, binary);
                }
                return binary;
            }
            
            private J.Binary simplifyMultiplication(J.Binary binary) {
                // x * 0 → 0
                if (isIntegerLiteral(binary.getRight(), 0)) {
                    return createIntegerLiteral(0, binary);
                }
                // 0 * x → 0
                if (isIntegerLiteral(binary.getLeft(), 0)) {
                    return createIntegerLiteral(0, binary);
                }
                // x * 1 → x
                if (isIntegerLiteral(binary.getRight(), 1)) {
                    return (J.Binary) binary.getLeft().withPrefix(binary.getPrefix());
                }
                // 1 * x → x
                if (isIntegerLiteral(binary.getLeft(), 1)) {
                    return (J.Binary) binary.getRight().withPrefix(binary.getPrefix());
                }
                // Constant folding: 3 * 4 → 12
                if (isIntegerLiteral(binary.getLeft()) && isIntegerLiteral(binary.getRight())) {
                    int left = getIntegerValue(binary.getLeft());
                    int right = getIntegerValue(binary.getRight());
                    return createIntegerLiteral(left * right, binary);
                }
                return binary;
            }
            
            private J.Binary simplifyDivision(J.Binary binary) {
                // x / 1 → x
                if (isIntegerLiteral(binary.getRight(), 1)) {
                    return (J.Binary) binary.getLeft().withPrefix(binary.getPrefix());
                }
                // Constant folding: 8 / 2 → 4 (only if divisible)
                if (isIntegerLiteral(binary.getLeft()) && isIntegerLiteral(binary.getRight())) {
                    int left = getIntegerValue(binary.getLeft());
                    int right = getIntegerValue(binary.getRight());
                    if (right != 0 && left % right == 0) {
                        return createIntegerLiteral(left / right, binary);
                    }
                }
                return binary;
            }
            
            private J.Binary simplifyBooleanAnd(J.Binary binary) {
                // true && x → x
                if (isBooleanLiteral(binary.getLeft(), true)) {
                    return (J.Binary) binary.getRight().withPrefix(binary.getPrefix());
                }
                // x && true → x
                if (isBooleanLiteral(binary.getRight(), true)) {
                    return (J.Binary) binary.getLeft().withPrefix(binary.getPrefix());
                }
                // false && x → false
                if (isBooleanLiteral(binary.getLeft(), false)) {
                    return createBooleanLiteral(false, binary);
                }
                // x && false → false
                if (isBooleanLiteral(binary.getRight(), false)) {
                    return createBooleanLiteral(false, binary);
                }
                return binary;
            }
            
            private J.Binary simplifyBooleanOr(J.Binary binary) {
                // true || x → true
                if (isBooleanLiteral(binary.getLeft(), true)) {
                    return createBooleanLiteral(true, binary);
                }
                // x || true → true
                if (isBooleanLiteral(binary.getRight(), true)) {
                    return createBooleanLiteral(true, binary);
                }
                // false || x → x
                if (isBooleanLiteral(binary.getLeft(), false)) {
                    return (J.Binary) binary.getRight().withPrefix(binary.getPrefix());
                }
                // x || false → x
                if (isBooleanLiteral(binary.getRight(), false)) {
                    return (J.Binary) binary.getLeft().withPrefix(binary.getPrefix());
                }
                return binary;
            }
            
            private boolean isIntegerLiteral(J expression) {
                return expression instanceof J.Literal && ((J.Literal) expression).getValue() instanceof Integer;
            }
            
            private boolean isIntegerLiteral(J expression, int value) {
                return expression instanceof J.Literal && 
                       ((J.Literal) expression).getValue() instanceof Integer &&
                       ((Integer) ((J.Literal) expression).getValue()) == value;
            }
            
            private boolean isBooleanLiteral(J expression, boolean value) {
                return expression instanceof J.Literal && 
                       ((J.Literal) expression).getValue() instanceof Boolean &&
                       ((Boolean) ((J.Literal) expression).getValue()) == value;
            }
            
            private int getIntegerValue(J expression) {
                if (expression instanceof J.Literal) {
                    Object value = ((J.Literal) expression).getValue();
                    if (value instanceof Integer) {
                        return (Integer) value;
                    }
                }
                throw new IllegalArgumentException("Expression is not an integer literal");
            }
            
            private J.Binary createIntegerLiteral(int value, J.Binary original) {
                J.Literal literal = new J.Literal(
                    original.getId(),
                    original.getPrefix(),
                    original.getMarkers(),
                    value,
                    String.valueOf(value),
                    null,
                    null
                );
                return (J.Binary) literal;
            }
            
            private J.Binary createBooleanLiteral(boolean value, J.Binary original) {
                J.Literal literal = new J.Literal(
                    original.getId(),
                    original.getPrefix(),
                    original.getMarkers(),
                    value,
                    String.valueOf(value),
                    null,
                    null
                );
                return (J.Binary) literal;
            }
        };
    }
}