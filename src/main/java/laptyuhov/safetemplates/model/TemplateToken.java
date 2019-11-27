package laptyuhov.safetemplates.model;

import com.google.common.base.MoreObjects;
import java.util.Objects;

public class TemplateToken {
  private final boolean isStaticText;
  private final String staticTextOrPlaceholderName;

  public TemplateToken(boolean isStaticText, String staticTextOrPlaceholderName) {
    this.isStaticText = isStaticText;
    this.staticTextOrPlaceholderName = Objects.requireNonNull(staticTextOrPlaceholderName);
  }

  public boolean isStaticText() {
    return isStaticText;
  }

  public String getStaticTextOrPlaceholderName() {
    return staticTextOrPlaceholderName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TemplateToken that = (TemplateToken) o;
    return isStaticText == that.isStaticText &&
        com.google.common.base.Objects
            .equal(staticTextOrPlaceholderName, that.staticTextOrPlaceholderName);
  }

  @Override
  public int hashCode() {
    return com.google.common.base.Objects.hashCode(isStaticText, staticTextOrPlaceholderName);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("isStaticText", isStaticText)
        .add("staticTextOrPlaceholderName", staticTextOrPlaceholderName)
        .toString();
  }
}
