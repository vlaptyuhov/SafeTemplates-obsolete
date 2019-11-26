package laptyuhov.safetemplates.model;

import lombok.NonNull;
import lombok.Value;

@Value
public class InterfaceSpec {
  @NonNull String packageOrEmptyString;
  @NonNull String name;
}
