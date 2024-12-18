@import ch.pa5.greenfit.repository.entity.ConsumptionEntity

@param ConsumptionEntity userConsumption

<div>${userConsumption.getArticle().getName()} - ${userConsumption.getPortion().getSize().getAmount()}${userConsumption.getPortion().getSize().getType().getDisplayName()}</div>
