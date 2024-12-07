package ch.pa5.greenfit.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public enum PortionSize {
  GRAMM("g");

  private final String displayName;
}