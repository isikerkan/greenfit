@import ch.pa5.greenfit.repository.entity.PortionEntity
@import java.util.List

@param List<PortionEntity> userPortions

@for(PortionEntity userPortion : userPortions)
    @template.fragment.user-portion(userPortion = userPortion)
@endfor
