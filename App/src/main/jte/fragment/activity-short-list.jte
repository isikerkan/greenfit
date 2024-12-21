@import ch.pa5.greenfit.model.ShortActivity
@import java.util.List

@param List<ShortActivity> userActivities

@for(ShortActivity userActivity : userActivities)
    <div class="flex justify-between w-24">
        <span>${"%02d:%02d".formatted(userActivity.duration().toMinutes(), userActivity.duration().toSecondsPart())}</span>
        <span>${userActivity.calories()}</span>
    </div>
@endfor

@if(userActivities == null || userActivities.isEmpty())
    <image class="size-24   " src="/icon/spider.svg"/>
@endif
