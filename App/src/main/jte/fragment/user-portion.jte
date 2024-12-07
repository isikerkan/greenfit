@import ch.pa5.greenfit.repository.entity.PortionEntity

@param PortionEntity userPortion

<div>${userPortion.getRecipe().getName()} - ${userPortion.getSize().getAmount()}${userPortion.getSize().getType().getDisplayName()}</div>
