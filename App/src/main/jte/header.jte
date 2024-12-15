@param ch.pa5.greenfit.repository.entity.UserEntity user

<div>
    <ul>
        <li><a href="/activities">Activities</a></li>
        <li><a href="/calories">Calories</a></li>
        <li><a href="/options">Options</a></li>
    </ul>
    @if (user == null)
    <div>
        <span>Login</span>
        <span>Github</span>
    </div>
    @else
    <span>Logout</span>
    @endif
</div>
