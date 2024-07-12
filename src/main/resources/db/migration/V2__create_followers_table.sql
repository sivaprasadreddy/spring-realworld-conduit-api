create table user_follower
(
    from_id bigint not null references users (id),
    to_id   bigint not null references users (id),
    primary key (from_id, to_id)
);
