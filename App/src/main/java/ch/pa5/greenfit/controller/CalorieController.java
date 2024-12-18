package ch.pa5.greenfit.controller;

import ch.pa5.greenfit.model.SlotWithCalories;
import ch.pa5.greenfit.repository.entity.ConsumptionEntity;
import ch.pa5.greenfit.repository.entity.SlotEntity;
import ch.pa5.greenfit.service.CalorieService;
import ch.pa5.greenfit.service.UserService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class CalorieController {

  private final CalorieService calorieService;
  private final UserService userService;

  @GetMapping("/get-foods")
  public String getFoods(Model model) {

    model.addAttribute("articles", calorieService.getAllCalories());

    return "fragment/food-options";
  }

  @GetMapping("/food")
  public String getFood(@RequestParam Long articleId, Model model) {
    model.addAttribute("article", calorieService.getArticle(articleId));
    return "fragment/food-option";
  }

  @GetMapping("/add-food")
  public String getFoodForm(Model model, @RequestParam Long articleId) {
    model.addAttribute("article", calorieService.getArticle(articleId));
    return "fragment/food-option-form";
  }

  @GetMapping("/calorie-log/{userId}")
  public String getAllUserCalories(@PathVariable Long userId, Model model) {
    val userConsumptions = calorieService.getAllConsumptions(userId);
    log.debug("Loaded user consumptions: {}", userConsumptions);
    model.addAttribute("userConsumptions", userConsumptions);
    return "fragment/user-portions";
  }

  @PostMapping("/calorie-log")
  public String saveCalorieLog(
      @RequestBody ConsumptionEntity consumptionEntity, Model model, HttpServletResponse response) {
    log.info("Saving calorie log: {}", consumptionEntity);
    val savedConsumptionOpt = calorieService.saveConsumption(consumptionEntity);
    savedConsumptionOpt.ifPresent(
        consumption -> model.addAttribute("userConsumption", consumption));

    response.setHeader("HX-Redirect", "/calories");
    return "fragment/user-portion";
  }

  @GetMapping("/calories-by-slot")
  public String caloriesBySlot(Model model, @RequestParam(required = false) LocalDate date) {
    val user = userService.findUser();
    val todaysConsumptions = calorieService.getAllConsumptions(user.getId(), date);

    model.addAttribute("slots", calorieService.groupConsumptionsBySlot(todaysConsumptions));
    model.addAttribute("selectedDate", date.toString());
    return "fragment/calories-by-slot";
  }
}
