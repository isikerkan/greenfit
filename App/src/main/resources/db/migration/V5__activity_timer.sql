create table activity_timer (
    id serial not null primary key ,
    user_id text not null,
    start_utc timestamp with time zone not null,
    stop_utc timestamp with time zone
)
