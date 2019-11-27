package laptyuhov.safetemplates.processor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableListMultimap.Builder;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;
import laptyuhov.safetemplates.SafeTemplate;
import laptyuhov.safetemplates.codegen.SafeTemplatesGenerator;
import laptyuhov.safetemplates.model.InterfaceSpec;
import laptyuhov.safetemplates.model.MethodSpec;

@SupportedAnnotationTypes("laptyuhov.safetemplates.SafeTemplate")
public final class SafeTemplateProcessor extends AbstractProcessor {

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    if (annotations.isEmpty() || roundEnv.processingOver()) {
      return false;
    }

    Builder<InterfaceSpec, MethodSpec> modelBuilder = ImmutableListMultimap.builder();
    for (Element element : roundEnv.getElementsAnnotatedWith(SafeTemplate.class)) {
      if (element instanceof ExecutableElement) {
        ExecutableElement executableElement = (ExecutableElement) element;
        TypeElement typeElement = (TypeElement) executableElement.getEnclosingElement();

        String packageNameOrEmptyString = "";
        int lastDot = typeElement.getQualifiedName().toString().lastIndexOf('.');
        if (lastDot > 0) {
          packageNameOrEmptyString = typeElement.getQualifiedName().toString()
              .substring(0, lastDot);
        }
        modelBuilder.put(
            new InterfaceSpec(packageNameOrEmptyString, typeElement.getSimpleName().toString()),
            new MethodSpec(
                executableElement.getAnnotation(SafeTemplate.class).value(),
                executableElement.getSimpleName().toString(),
                executableElement.getParameters().stream()
                    .map(VariableElement::getSimpleName)
                    .map(Name::toString)
                    .collect(ImmutableList.toImmutableList())));
      }
    }

    ImmutableListMultimap<InterfaceSpec, MethodSpec> model = modelBuilder.build();
    for (InterfaceSpec interfaceSpec : model.keySet()) {
      try {
        JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile("$" + interfaceSpec.getName());
        try (Writer writer = sourceFile.openWriter()) {
          SafeTemplatesGenerator.generate(
              new PrintWriter(writer),
              interfaceSpec,
              model.get(interfaceSpec));
        }
      } catch (IOException e) {
        System.err.println(e.getMessage());
        processingEnv.getMessager().printMessage(Kind.ERROR, e.getMessage());
      }
    }

    return false;
  }
}
