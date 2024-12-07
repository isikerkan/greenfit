@import ch.pa5.greenfit.repository.entity.ActivityEntity
@import java.util.List

@param List<ActivityEntity> activities

@for(ActivityEntity activity: activities)
    <option value="${activity.getId()}">${activity.getName()}</option>
@endfor
