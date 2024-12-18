@import ch.pa5.greenfit.repository.entity.ConsumptionEntity
@import ch.pa5.greenfit.repository.entity.PortionEntity
@import java.util.List

@param List<ConsumptionEntity> userConsumptions

@for(ConsumptionEntity userConsumption : userConsumptions)
    @template.fragment.user-portion(userConsumption = userConsumption)
@endfor
