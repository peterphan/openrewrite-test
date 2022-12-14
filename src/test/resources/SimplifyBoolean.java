import java.util.Random;


public class SimplifyBoolean {


  public static boolean test1() {
    boolean a = randomBoolean() && true;
    boolean b = true && randomBoolean();
    boolean c = randomBoolean() || true;
    boolean d = true || randomBoolean();
    boolean e = randomBoolean() && !true;
    boolean f = randomBoolean() && !false;
    return false;
  }

  public static boolean test2() {
    return randomBoolean() || true;
  }

  public static boolean test3() {
    return true || randomBoolean();
  }

  public static boolean test4() {
    return randomBoolean() && !true;
  }

  public static boolean test5() {
    return randomBoolean() && !false;
  }

  public static boolean test6() {
    if (randomBoolean()) {
      return randomBoolean() || true;
    } else {
      return false;
    }
  }

  public static boolean test7() {
    if (randomBoolean()) {
      return randomBoolean() && true;
    } else {
      return false;
    }
  }

  public static boolean test8() {
    if (randomBoolean()) {
      return true;
    } else {
      return false;
    }
  }

  private static boolean randomBoolean() {
    Random rd = new Random();
    return rd.nextBoolean();
  }
}