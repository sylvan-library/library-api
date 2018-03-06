create table restricted_and_banned_cards (
    id serial primary key,
    set_id integer references sets (id),
    card_id integer references cards (id),
    is_banned boolean default false,
    created timestamp default current_timestamp
);
