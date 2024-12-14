@import ch.pa5.greenfit.repository.entity.ActivityEntity
@import java.util.List

@param List<ActivityEntity> activities
@param Long selected

@for(ActivityEntity activity: activities)
    <option value="${activity.getId()}" selected="${activity.getId().equals(selected)}">${activity.getName()}</option>
@endfor
