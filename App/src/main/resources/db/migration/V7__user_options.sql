create table if not exists user_option (
    id bigserial not null primary key ,
    user_id bigserial not null references "user",
    weight_kg numeric,
    age numeric,
    height_cm numeric
)
;


alter table "user" add column if not exists option_id bigserial not null references user_option;
