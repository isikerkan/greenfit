alter table consumption
    add column if not exists created_at timestamp with time zone not null default now();

alter table consumption
    add column if not exists portion_id bigserial;

alter table consumption
    add column if not exists date timestamp with time zone not null default now();
