create table if not exists pictures
(
    id           int auto_increment
        primary key,
    login_name   varchar(40)  not null,
    picture_name varchar(30)  null,
    picture_url  varchar(255) not null,
    picture_data varchar(255) null,
    picture_type tinyint      not null,
    upload_time  datetime     not null,
    constraint login_name
        unique (login_name)
)
    charset = utf8;

create table if not exists users
(
    id            int auto_increment
        primary key,
    login_name    varchar(40)  not null,
    login_pwd     varchar(40)  not null,
    user_name     varchar(40)  not null,
    user_avatar   varchar(255) null,
    register_time datetime     not null,
    login_time    datetime     not null,
    constraint login_name
        unique (login_name)
)
    charset = utf8;


