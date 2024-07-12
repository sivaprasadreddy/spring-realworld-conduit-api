create sequence user_id_seq start with 100 increment by 10;

create table users
(
    id         bigint default nextval('user_id_seq') not null,
    email      varchar(100)                          not null,
    password   varchar(200)                          not null,
    username   varchar(50)                           not null,
    bio        varchar(500),
    image      varchar(200),
    created_at timestamp                             not null,
    updated_at timestamp,
    constraint UK_users_email unique (email),
    constraint UK_users_username unique (username),
    primary key (id)
);