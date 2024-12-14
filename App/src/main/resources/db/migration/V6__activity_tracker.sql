create table if not exists activity_tracker (
    id bigserial not null primary key ,
    type text not null,
    user_id bigserial not null references "user",
    activity_id bigserial not null references activity,
    start_utc timestamp with time zone not null,
    stop_utc timestamp with time zone
);

alter table activity_log add column if not exists created_at timestamp with time zone not null default now();
alter table portion add column if not exists created_at timestamp with time zone not null default now();
