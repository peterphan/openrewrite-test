import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.openrewrite.InMemoryExecutionContext;
import org.openrewrite.RecipeRun;
import org.openrewrite.Result;
import org.openrewrite.java.JavaParser;
import org.openrewrite.java.cleanup.SimplifyBooleanExpression;
import org.openrewrite.java.cleanup.SimplifyBooleanReturn;
import org.openrewrite.java.tree.J;

import static org.junit.jupiter.api.Assertions.*;


public class TestSimplifyBoolean {

  @Test
  public void testSimplifyBoolean() throws Exception {
    Path sourceFilePath = getResourcePath("SimplifyBoolean.java");
    String expected = getResourceContents("Target-SimplifyBoolean.java");

    SimplifyBooleanExpression recipe = new SimplifyBooleanExpression();
    recipe.doNext(new SimplifyBooleanReturn());

    JavaParser javaParser = JavaParser.fromJavaVersion().classpath(Collections.emptyList()).build();

    InMemoryExecutionContext ctx = new InMemoryExecutionContext();
    List<J.CompilationUnit> cus = javaParser.parse(List.of(sourceFilePath), getResourcePath("."), ctx);
    RecipeRun recipeRun = recipe.run(cus);

    String actual = recipeRun.getResults().get(0).getAfter().printAll();
    assertEquals(expected, actual);
  }

  private static String getResourceContents(String resource) throws Exception {
    URL url = Resources.getResource(resource);
    return Resources.toString(url, Charsets.UTF_8);
  }

  private static Path getResourcePath(String resource) throws Exception {
    return Paths.get(Resources.getResource(resource).toURI().getPath());
  }

}
