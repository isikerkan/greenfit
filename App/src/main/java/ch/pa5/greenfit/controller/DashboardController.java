package ch.pa5.greenfit.controller;

import ch.pa5.greenfit.service.ActivityService;
import ch.pa5.greenfit.service.CalorieService;
import ch.pa5.greenfit.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class DashboardController {

  private final CalorieService calorieService;
  private final UserService userService;
  private final ActivityService activityService;

  @GetMapping("/get-calorie-intake")
  @Operation(summary = "Loads the total of calories ingested.")
  public String getCalorieIntake(
      Model model,
      @Parameter(example = "2024-12-01") @RequestParam(required = false) LocalDate date) {
    val user = userService.findUser();
    val todaysCalories =
        calorieService.calculateCalories(calorieService.getAllConsumptions(user.getId(), date));

    log.info("Today's calories: {}", todaysCalories);
    model.addAttribute("calories", todaysCalories);
    return "fragment/calorie-display";
  }

  @GetMapping("/get-calorie-burn")
  @Operation(summary = "Loads the total of calories burnt.")
  public String getCalorieBurn(
      Model model,
      @Parameter(example = "2024-12-01") @RequestParam(required = false) LocalDate date) {
    val user = userService.findUser();
    val todaysBurn = activityService.calculateTotalBurn(user.getId(), date);
    log.info("Today's burn: {}", todaysBurn);
    model.addAttribute("calories", todaysBurn.stripTrailingZeros());
    return "fragment/calorie-display";
  }
}
