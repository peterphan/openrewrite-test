import java.util.Random;


public class SimplifyBoolean {


  public static boolean test1() {
    boolean a = randomBoolean();
    boolean b = randomBoolean();
    boolean c = true;
    boolean d = true;
    boolean e = false;
    boolean f = randomBoolean();
    return false;
  }

  public static boolean test2() {
    return true;
  }

  public static boolean test3() {
    return true;
  }

  public static boolean test4() {
    return false;
  }

  public static boolean test5() {
    return randomBoolean();
  }

  private static boolean randomBoolean() {
    Random rd = new Random();
    return rd.nextBoolean();
  }
}