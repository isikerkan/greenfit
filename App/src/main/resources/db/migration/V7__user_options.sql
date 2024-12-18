

alter table "user" add column if not exists options_id bigserial ;

alter table person drop column if exists name ;
