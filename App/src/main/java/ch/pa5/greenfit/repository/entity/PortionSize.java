package ch.pa5.greenfit.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public enum PortionSize {
  GRAMM("g"),
  MILLILITER("ml");

  private final String displayName;
}
