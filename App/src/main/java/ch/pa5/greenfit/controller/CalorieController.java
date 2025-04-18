package ch.pa5.greenfit.controller;

import ch.pa5.greenfit.model.SlotWithCalories;
import ch.pa5.greenfit.repository.entity.ConsumptionEntity;
import ch.pa5.greenfit.repository.entity.SlotEntity;
import ch.pa5.greenfit.service.CalorieService;
import ch.pa5.greenfit.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class CalorieController {

  private final CalorieService calorieService;
  private final UserService userService;

  @GetMapping("/get-foods")
  @Operation(
      summary = "Loads the full list of articles for the user to choose from.",
      responses =
          @ApiResponse(
              responseCode = "200",
              description = "The rendered html containing all the articles."))
  public String getFoods(Model model) {

    model.addAttribute("articles", calorieService.getAllCalories());

    return "fragment/food-options";
  }

  @GetMapping("/food")
  @Operation(
      summary =
          "Loads the singular food option. Used to reinstate the list entry if no food is added and it is toggled back.")
  public String getFood(@Parameter(example = "5") @RequestParam Long articleId, Model model) {
    model.addAttribute("article", calorieService.getArticle(articleId));
    return "fragment/food-option";
  }

  @GetMapping("/add-food")
  @Operation(summary = "Loads the food option containing the form for adding a food.")
  public String getFoodForm(Model model, @Parameter(example = "5") @RequestParam Long articleId) {
    model.addAttribute("article", calorieService.getArticle(articleId));
    return "fragment/food-option-form";
  }

  @GetMapping("/calorie-log/{userId}")
  @Operation(
      summary = "Loads the logged calories for a user",
      description = "Deprecated in favor of /calories-by-slot",
      deprecated = true)
  public String getAllUserCalories(@PathVariable Long userId, Model model) {
    val userConsumptions = calorieService.getAllConsumptions(userId);
    log.debug("Loaded user consumptions: {}", userConsumptions);
    model.addAttribute("userConsumptions", userConsumptions);
    return "fragment/user-portions";
  }

  @PostMapping("/calorie-log")
  @Operation(
      summary = "Saves a new calorie log",
      description = "User, article and slot are only referenced by id",
      responses =
          @ApiResponse(
              responseCode = "200",
              description = "Returns empty. Redirects to the calories slot overview."))
  public String saveCalorieLog(
      @RequestBody ConsumptionEntity consumptionEntity, HttpServletResponse response) {
    log.info("Saving calorie log: {}", consumptionEntity);
    calorieService.saveConsumption(consumptionEntity);

    response.setHeader("HX-Redirect", "/calories");
    return "empty";
  }

  @DeleteMapping("/calorie-log/{id}" )
  public String deleteCalorieLog(@Parameter(example = "5") @PathVariable("id") Long calorielogId) {
    calorieService.deletecalorieLog(calorielogId);
    return "empty";
  }

  @GetMapping("/calories-by-slot")
  @Operation(summary = "Loads a users calories by date, grouped by slot.")
  public String caloriesBySlot(
      Model model,
      @Parameter(example = "2024-12-01") @RequestParam(required = false) LocalDate date) {
    val user = userService.findUser();
    val todaysConsumptions = calorieService.getAllConsumptions(user.getId(), date);

    model.addAttribute("slots", calorieService.groupConsumptionsBySlot(todaysConsumptions));
    model.addAttribute("selectedDate", date.toString());
    return "fragment/calories-by-slot";
  }
}
