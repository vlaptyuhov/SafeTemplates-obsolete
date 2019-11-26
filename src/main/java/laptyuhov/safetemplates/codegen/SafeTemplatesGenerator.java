package laptyuhov.safetemplates.codegen;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Objects;
import laptyuhov.safetemplates.model.InterfaceSpec;
import laptyuhov.safetemplates.model.MethodSpec;
import laptyuhov.safetemplates.model.TemplateToken;

public final class SafeTemplatesGenerator {

  @VisibleForTesting static final String TAB_1X = "  ";
  @VisibleForTesting static final String TAB_2X = Strings.repeat(TAB_1X, 2);
  @VisibleForTesting static final String TAB_4X = Strings.repeat(TAB_1X, 4);

  public static void generate(
      PrintWriter writer, InterfaceSpec interfaceSpec, ImmutableList<MethodSpec> methodSpecs) {
    Objects.requireNonNull(writer);
    Objects.requireNonNull(interfaceSpec);
    Objects.requireNonNull(methodSpecs);

    if (!interfaceSpec.getPackageOrEmptyString().isEmpty()) {
      writer.println(String.format("package %s;", interfaceSpec.getPackageOrEmptyString()));
      writer.println();
    }

    writer.println(
        String.format("public final class $%s implements %s {", interfaceSpec.getName(), interfaceSpec.getName()));
    writer.println();

    for (MethodSpec methodSpec : methodSpecs) {
      writer.print(TAB_1X);
      writer.println("@Override");

      // method signature
      writer.print(TAB_1X);
      if (methodSpec.getParameterNames().isEmpty()) {
        writer.println(String.format("public String %s() {", methodSpec.getName()));
      } else {
        StringBuilder parametersBuilder = new StringBuilder();
        for (String parameterName : methodSpec.getParameterNames()) {
          parametersBuilder.append("String").append(" ").append(parameterName).append(", ");
        }
        parametersBuilder.delete(parametersBuilder.length() - ", ".length(), parametersBuilder.length());
        writer.println(String.format("public String %s(%s) {", methodSpec.getName(), parametersBuilder.toString()));
      }

      writer.print(TAB_2X);
      writer.println("return new StringBuilder()");

      //TODO(vlaptyuhov): create new iterator for getting tokens from methodSpec.getTemplatePath()
      Iterator<TemplateToken> templateTokens = null;
      while (templateTokens.hasNext()) {
        writer.println(TAB_4X);
        TemplateToken token = templateTokens.next();
        writer.println(String.format(".append(%s)", token.getStaticTextOrPlaceholderName()));
      }

      writer.print(TAB_4X);
      writer.println(".build();");

      writer.print(TAB_1X);
      writer.println("}");
    }

    writer.println("}"); // end of class
  }
}
