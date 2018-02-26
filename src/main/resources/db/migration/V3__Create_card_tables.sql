create table sets (
    id serial primary key,
    block_id integer references blocks (id),
    name varchar(100) not null,
    abbreviation varchar(10) not null,
    icon varchar(100),
    keyImage varchar(100),
    release_date date,
    created timestamp default current_timestamp
);

create table artists (
    id serial primary key,
    name varchar(500) not null,
    created timestamp default current_timestamp
);

create table cards (
    id serial primary key,
    name varchar(500) not null,
    color varchar(100) not null,
    casting_cost varchar(100) not null,
    type_text varchar(500) not null,
    rules_text text not null,
    created timestamp default current_timestamp
);

create table set_to_cards (
    id serial primary key,
    set_id integer references sets (id),
    card_id integer references cards (id),
    artist_id integer references artists (id),
    rarity varchar(100) not null,
    image varchar(100),
    is_foil boolean not null,
    features varchar(500),
    created timestamp default current_timestamp
);

create table types (
    id serial primary key,
    name varchar(500) not null,
    created timestamp default current_timestamp
);

create table card_to_types (
    id serial primary key,
    card_id integer references cards (id),
    type_id integer references types (id),
    created timestamp default current_timestamp
);
