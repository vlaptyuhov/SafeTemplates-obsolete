package laptyuhov.safetemplates.model;

import lombok.NonNull;
import lombok.Value;

@Value
public class TemplateToken {
  @NonNull boolean isStaticText;
  @NonNull String staticTextOrPlaceholderName;
}
