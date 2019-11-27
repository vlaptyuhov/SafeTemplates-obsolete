package laptyuhov.safetemplates.model;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import java.util.Objects;

public final class MethodSpec {
  private final String templatePath;
  private final String name;
  private final ImmutableList<String> parameterNames;

  public MethodSpec(String templatePath, String name, ImmutableList<String> parameterNames) {
    this.templatePath = Objects.requireNonNull(templatePath);
    this.name = Objects.requireNonNull(name);
    this.parameterNames = Objects.requireNonNull(parameterNames);
  }

  public String getTemplatePath() {
    return templatePath;
  }

  public String getName() {
    return name;
  }

  public ImmutableList<String> getParameterNames() {
    return parameterNames;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MethodSpec that = (MethodSpec) o;
    return com.google.common.base.Objects.equal(templatePath, that.templatePath) &&
        com.google.common.base.Objects.equal(name, that.name) &&
        com.google.common.base.Objects.equal(parameterNames, that.parameterNames);
  }

  @Override
  public int hashCode() {
    return com.google.common.base.Objects.hashCode(templatePath, name, parameterNames);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("templatePath", templatePath)
        .add("name", name)
        .add("parameterNames", parameterNames)
        .toString();
  }
}
