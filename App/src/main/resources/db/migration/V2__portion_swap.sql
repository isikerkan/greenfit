alter table if exists portion
    rename to portion_size;

alter table if exists portionsgrössen
    rename to portion;

alter table portion
    rename column portion_id to portion_size_id;

alter table portion
    drop column if exists menge;

INSERT INTO greenfit.rezept (id, name) VALUES (1, 'Döner');
INSERT INTO greenfit.rezept (id, name) VALUES (2, 'Älplermaccroni');
INSERT INTO greenfit.rezept (id, name) VALUES (3, 'Oyakodon');
