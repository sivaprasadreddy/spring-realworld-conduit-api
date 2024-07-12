create sequence tag_id_seq start with 100 increment by 10;

create table tags
(
    id         bigint default nextval('tag_id_seq') not null,
    name       varchar(20)                          not null,
    created_at timestamp                            not null,
    primary key (id),
    constraint UK_tags_name unique (name)
);

create sequence article_id_seq start with 100 increment by 10;

create table articles
(
    id          bigint default nextval('article_id_seq') not null,
    title       varchar(200)                             not null,
    slug        varchar(250)                             not null,
    description varchar(500)                             not null,
    content     text                                     not null,
    author_id   bigint                                   not null,
    created_at  timestamp                                not null,
    updated_at  timestamp                                null,
    primary key (id),
    constraint UK_articles_title unique (title),
    constraint UK_articles_slug unique (slug),
    constraint FK_articles_author foreign key (author_id) references users (id)
);

create table article_favorite
(
    article_id bigint    not null,
    user_id    bigint    not null,
    created_at timestamp not null,
    primary key (article_id, user_id),
    constraint FK_article_favorite_user_id foreign key (user_id) references users (id),
    constraint FK_article_favorite_article_id foreign key (article_id) references articles (id)
);

create table article_tag
(
    article_id bigint    not null,
    tag_id     bigint    not null,
    created_at timestamp not null,
    primary key (article_id, tag_id),
    constraint FK_article_tag_article_id foreign key (article_id) references articles (id),
    constraint FK_article_tag_tag_id foreign key (tag_id) references tags (id)
);

create sequence comment_id_seq start with 100 increment by 10;

create table comments
(
    id         bigint default nextval('comment_id_seq') not null,
    content    text                                     not null,
    article_id bigint                                   not null,
    author_id  bigint                                   not null,
    created_at timestamp                                not null,
    updated_at timestamp                                null,
    primary key (id),
    constraint FK_comments_article_id foreign key (article_id) references articles (id),
    constraint FK_comments_author_id foreign key (author_id) references users (id)
);
