package ch.pa5.greenfit.controller;

import java.math.BigDecimal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DashboardController {

  @GetMapping("/get-calorie-intake")
  public BigDecimal getCalorieIntake() {
    return new BigDecimal("1400.4");
  }

  @GetMapping("/get-calorie-burn")
  public BigDecimal getCalorieBurn() {
    return new BigDecimal("350.56");
  }
}
