create table formats (
    id serial primary key,
    name varchar(100) not null,
    created timestamp default current_timestamp
);

create table format_to_sets (
    id serial primary key,
    format_id integer references formats(id),
    set_id integer references sets (id),
    created timestamp default current_timestamp
);
