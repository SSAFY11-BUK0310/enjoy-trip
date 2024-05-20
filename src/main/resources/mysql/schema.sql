drop table if exists message;
drop table if exists message_room;
drop table if exists comment;
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
    `roleType`    varchar(50),
    `phoneNumber` varchar(50),
    `created_at`  datetime(6),
    `updated_at`  datetime(6),
    `created_by`  varchar(255),
    `updated_by`  varchar(255)
);

create table `article`
(
    `id`          bigint PRIMARY KEY auto_increment,
    `member_id`   bigint,
    `title`       varchar(255),
    `content`     varchar(255),
    `imageName`   varchar(255),
    `imageUUID`   varchar(255),
    `views`       bigint,
    `address`     varchar(255),
    `articleType` varchar(50),
    `created_at`  datetime(6),
    `updated_at`  datetime(6),
    `created_by`  varchar(255),
    `updated_by`  varchar(255)
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

alter table `article`
    add constraint article_member_id
        foreign key (`member_id`) references `member` (`id`);

alter table `comment`
    add constraint comment_member_id
        foreign key (`member_id`) references `member` (`id`);

alter table `comment`
    add constraint comment_article_id
        foreign key (`article_id`) references `article` (`id`);

alter table `comment`
    add constraint comment_parent_id
        foreign key (`parent_id`) references `comment` (`id`);

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