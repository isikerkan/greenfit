create table activity (
    id serial primary key,
    name text not null,
    met_score numeric not null

);

create table "user" (
    id serial primary key,
    external_id text not null,
    email text not null
);

create table activity_log (
   id serial primary key,
   user_id bigserial not null references "user",
   activity_id bigserial not null references activity,
   duration_s numeric not null default 0
)
