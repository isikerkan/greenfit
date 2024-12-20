package ch.pa5.greenfit.model;

import java.math.BigDecimal;
import java.time.Duration;

public record ShortActivity(Duration duration, BigDecimal calories) {}
