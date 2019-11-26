package laptyuhov.safetemplates.model;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.Value;

@Value
public class MethodSpec {
  @NonNull String templatePath;
  @NonNull String name;
  @NonNull ImmutableList<String> parameterNames;
}
