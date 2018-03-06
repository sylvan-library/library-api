DO $$
declare
    _block_id integer;
    _set_id integer;
begin
    insert into blocks (name, image) values ('Kaladesh block', null) returning id into _block_id;
    insert into sets (block_id, name, abbreviation, icon, logo, release_date) values (_block_id, 'Kaladesh', 'KLD', 'kld-icon.png', 'kld-logo.png', '2016-09-30') returning id into _set_id;
    insert into format_to_sets (format_id, set_id) values (1, _set_id);
    insert into sets (block_id, name, abbreviation, icon, logo, release_date) values (_block_id, 'Aether Revolt', 'AER', 'aer-icon.png', 'aer-logo.png', '2017-01-20') returning id into _set_id;
    insert into format_to_sets (format_id, set_id) values (1, _set_id);

    insert into blocks (name, image) values ('Amonkhet block', null) returning id into _block_id;
    insert into sets (block_id, name, abbreviation, icon, logo, release_date) values (_block_id, 'Amonkhet', 'AKH', 'akh-icon.png', 'akh-logo.png', '2017-04-28') returning id into _set_id;
    insert into format_to_sets (format_id, set_id) values (1, _set_id);
    insert into sets (block_id, name, abbreviation, icon, logo, release_date) values (_block_id, 'Hour of Devastation', 'HOU', 'hou-icon.png', 'hou-logo.png', '2017-07-14') returning id into _set_id;
    insert into format_to_sets (format_id, set_id) values (1, _set_id);

    insert into blocks (name, image) values ('Ixalan block', null) returning id into _block_id;
    insert into sets (block_id, name, abbreviation, icon, logo, release_date) values (_block_id, 'Ixalan', 'XLN', 'xln-icon.png', 'xln-logo.png', '2017-09-29') returning id into _set_id;
    insert into format_to_sets (format_id, set_id) values (1, _set_id);
    insert into sets (block_id, name, abbreviation, icon, logo, release_date) values (_block_id, 'Rivals of Ixalan', 'RIX', 'rix-icon.png', 'rix-logo.png', '2018-01-19') returning id into _set_id;
    insert into format_to_sets (format_id, set_id) values (1, _set_id);
END $$;
