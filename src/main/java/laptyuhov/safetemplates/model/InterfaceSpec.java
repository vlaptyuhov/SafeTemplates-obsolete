package laptyuhov.safetemplates.model;

import com.google.common.base.MoreObjects;
import java.util.Objects;

public final class InterfaceSpec {
  private final String packageOrEmptyString;
  private final String name;

  public InterfaceSpec(String packageOrEmptyString, String name) {
    this.packageOrEmptyString = Objects.requireNonNull(packageOrEmptyString);
    this.name = Objects.requireNonNull(name);
  }

  public String getPackageOrEmptyString() {
    return packageOrEmptyString;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InterfaceSpec that = (InterfaceSpec) o;
    return com.google.common.base.Objects.equal(packageOrEmptyString, that.packageOrEmptyString) &&
        com.google.common.base.Objects.equal(name, that.name);
  }

  @Override
  public int hashCode() {
    return com.google.common.base.Objects.hashCode(packageOrEmptyString, name);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("packageOrEmptyString", packageOrEmptyString)
        .add("name", name)
        .toString();
  }
}
