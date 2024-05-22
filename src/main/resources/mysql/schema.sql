drop table if exists message;
drop table if exists message_room;
drop table if exists comment;
drop table if exists image;
drop table if exists article;
drop table if exists member;

create table `member`
(
    `id`          bigint PRIMARY KEY auto_increment,
    `email`       varchar(255),
    `password`    varchar(255),
    `name`        varchar(50),
    `age`         bigint,
    `gender`      varchar(50),
    `role_type`    varchar(50),
    `phone_number` varchar(50),
    `created_at`  datetime(6),
    `updated_at`  datetime(6),
    `created_by`  varchar(255),
    `updated_by`  varchar(255)
);

create table `article`
(
    `id`             bigint PRIMARY KEY auto_increment,
    `member_id`      bigint      DEFAULT NULL,
    `title`          longtext,
    `content`        longtext,
    `views`          bigint      DEFAULT NULL,
    `address`        longtext,
    `article_type`    varchar(50) DEFAULT NULL,
    `directory_uuid` varchar(50) DEFAULT NULL,
    `created_at`     datetime(6) DEFAULT NULL,
    `updated_at`     datetime(6) DEFAULT NULL,
    `created_by`     varchar(255) DEFAULT NULL,
    `updated_by`     varchar(255) DEFAULT NULL
);

CREATE TABLE `image`
(
    id             bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    article_id     bigint             NOT NULL,
    image_name     varchar(50)        NOT NULL,
    image_uuid     varchar(50)        NOT NULL,
    directory_uuid varchar(50)        NOT NULL,
    created_at     datetime(6) NOT NULL,
    updated_at     datetime(6) NOT NULL,
    created_by     varchar(50)        NOT NULL,
    updated_by     varchar(50)        NOT NULL
);

CREATE TABLE `comment`
(
    `id`         bigint PRIMARY KEY AUTO_INCREMENT,
    `member_id`  bigint      NOT NULL,
    `article_id` bigint      NOT NULL,
    `parent_id`  bigint,
    `content`    text        NOT NULL,
    `created_at` datetime(6) NOT NULL,
    `updated_at` datetime(6),
    `created_by` varchar(255),
    `updated_by` varchar(255)
);

CREATE TABLE `message_room`
(
    `id`          bigint PRIMARY KEY AUTO_INCREMENT,
    `article_id`  bigint      NOT NULL,
    `sender_id`   bigint      NOT NULL,
    `receiver_id` bigint      NOT NULL,
    `created_at`  datetime(6) NOT NULL,
    `updated_at`  datetime(6),
    `created_by`  varchar(255),
    `updated_by`  varchar(255)
);

CREATE TABLE `message`
(
    `id`              bigint PRIMARY KEY AUTO_INCREMENT,
    `member_id`       bigint      NOT NULL,
    `message_room_id` bigint      NOT NULL,
    `content`         text        NOT NULL,
    `created_at`      datetime(6) NOT NULL,
    `updated_at`      datetime(6),
    `created_by`      varchar(255),
    `updated_by`      varchar(255)
);

CREATE TABLE `likes`
(
    `id`          bigint PRIMARY KEY AUTO_INCREMENT,
    `member_id`   bigint NOT NULL,
    `article_id`  bigint NOT NULL,
    `created_at`  datetime(6) NOT NULL,
    `updated_at` datetime(6),
    `created_by`  varchar(255),
    `updated_by` varchar(255)
);


alter table `article`
    add constraint article_member_id
        foreign key (`member_id`) references `member` (`id`);

alter table `image`
    add constraint image_article_id
        foreign key (`article_id`) references `article` (`id`);

alter table `comment`
    add constraint comment_member_id
        foreign key (`member_id`) references `member` (`id`);

alter table `comment`
    add constraint comment_article_id
        foreign key (`article_id`) references `article` (`id`);

alter table `message`
    add constraint message_member_id
        foreign key (`member_id`) references `member` (`id`);

alter table `message`
    add constraint message_message_room_id
        foreign key (`message_room_id`) references `message_room` (`id`);

alter table `message_room`
    add constraint message_room_article_id
        foreign key (`article_id`) references `article` (`id`);

alter table `message_room`
    add constraint message_room_member_id
        foreign key (`sender_id`) references `member` (`id`);

alter table `message_room`
    add constraint message_room_message_room_id
        foreign key (`receiver_id`) references `member` (`id`);

alter table `likes`
    add constraint likes_article_id
        foreign key (`article_id`) references `article` (`id`)
            on delete cascade ;

alter table `likes`
    add constraint likes_member_id
        foreign key (`member_id`) references `member` (`id`)
            on delete cascade ;