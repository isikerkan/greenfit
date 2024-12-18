alter table if exists portion
    rename to portion_size;

alter table if exists portion_sizes
    rename to portion;

alter table portion
    rename column portion_id to portion_size_id;

alter table portion
    drop column if exists amount;

INSERT INTO greenfit.recipe (id, name) VALUES (1, 'Döner');
INSERT INTO greenfit.recipe (id, name) VALUES (2, 'Älplermaccroni');
INSERT INTO greenfit.recipe (id, name) VALUES (3, 'Oyakodon');
