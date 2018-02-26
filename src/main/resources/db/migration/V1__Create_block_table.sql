create table blocks (
    id serial primary key,
    name varchar(100),
    image varchar(100),
    created timestamp default current_timestamp
);
