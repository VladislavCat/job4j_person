create table if not exists person (
    id serial primary key not null,
    login varchar not null,
    password varchar(2000) not null
);