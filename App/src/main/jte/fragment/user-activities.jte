@import ch.pa5.greenfit.repository.entity.ActivityLogEntity
@import java.util.List

@param List<ActivityLogEntity> userActivities

@for(ActivityLogEntity userActivity : userActivities)
    @template.fragment.user-activity(userActivity = userActivity)
@endfor
