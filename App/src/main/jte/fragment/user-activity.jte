@import ch.pa5.greenfit.repository.entity.ActivityLogEntity

@param ActivityLogEntity userActivity

<div>${userActivity.getActivity().getName()} - ${userActivity.getDuration()}s</div>
