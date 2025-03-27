@import java.util.List
@import ch.pa5.greenfit.model.SlotWithCalories
@import ch.pa5.greenfit.repository.entity.ConsumptionEntity

@param List<SlotWithCalories> slots
@param String selectedDate

@for (SlotWithCalories slot : slots)
    <div class="flex items-center justify-between border-2 shadow-xs">
        <div class="p-4">
            <p>${slot.slot().getName()}</p>
            <p>${slot.calories()} kcal</p>
        </div>
        <a class="p-2 cursor-pointer" href="/calories/add?slot=${slot.slot().getId()}&date=${selectedDate}" >
            <image class="size-16" src="icon/plus.svg"></image>
        </a>
    </div>
    @for (ConsumptionEntity consumptionEntity : slot.consumption ())
    ${consumptionEntity.getArticle().getName()}
    @endfor
@endfor
