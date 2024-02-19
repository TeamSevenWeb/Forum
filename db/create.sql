create table forum.tags
(
    tag_id int auto_increment
        primary key,
    name   varchar(20) not null
);

create table forum.users
(
    user_id              int auto_increment
        primary key,
    username             varchar(50) not null,
    password             varchar(50) not null,
    first_name           varchar(32) not null,
    last_name            varchar(32) not null,
    email                varchar(50) not null,
    date_of_registration date        not null,
    is_blocked           tinyint     not null,
    is_admin             tinyint     not null,
    is_active            tinyint(1)  not null,
    constraint users_pk
        unique (username),
    constraint users_pk2
        unique (email)
);

create table forum.admins
(
    admin_id int auto_increment
        primary key,
    user_id  int not null,
    constraint admins_users_user_id_fk
        foreign key (user_id) references forum.users (user_id)
);

create table forum.admins_phone_numbers
(
    phone_number_id int auto_increment
        primary key,
    admin_id        int         not null,
    phone_number    varchar(20) not null,
    constraint admins_phone_numbers_admins_admin_id_fk
        foreign key (admin_id) references forum.admins (admin_id)
);

create table forum.blocked_users
(
    user_id int not null
        primary key,
    constraint blocked_users_users_user_id
        foreign key (user_id) references forum.users (user_id)
);

create table forum.deactivated_users
(
    user_id int not null
        primary key,
    constraint deactivated_users_users_user_id
        foreign key (user_id) references forum.users (user_id)
);

create table forum.posts
(
    post_id                   int auto_increment
        primary key,
    created_by                int           not null,
    title                     varchar(64)   not null,
    content                   varchar(8192) not null,
    date_and_time_of_creation datetime      null,
    constraint posts_users_user_id_fk
        foreign key (created_by) references forum.users (user_id)
);

create table forum.comments
(
    comment_id                int auto_increment
        primary key,
    post_id                   int          not null,
    created_by                int          not null,
    comment                   varchar(250) null,
    date_and_time_of_creation datetime     null,
    constraint posts_comments_posts_post_id_fk
        foreign key (post_id) references forum.posts (post_id),
    constraint posts_comments_users_user_id_fk
        foreign key (created_by) references forum.users (user_id)
);

create table forum.posts_comments
(
    post_id    int                  not null,
    comment_id int                  not null,
    deleted    tinyint(1) default 0 not null,
    constraint joined_posts_comments_posts_post_id_fk
        foreign key (post_id) references forum.posts (post_id),
    constraint posts_comments_comments_comment_id_fk
        foreign key (comment_id) references forum.comments (comment_id)
);

create table forum.posts_tags
(
    post_id int null,
    tag_id  int null,
    constraint posts_tags_post_id_fk
        foreign key (post_id) references forum.posts (post_id),
    constraint posts_tags_tag_id_fk
        foreign key (tag_id) references forum.tags (tag_id)
);

create table forum.profile_photos
(
    profile_photo_id int auto_increment
        primary key,
    user_id          int          not null,
    profile_photo    varchar(640) not null,
    constraint profile_photos_users_user_id_fk
        foreign key (user_id) references forum.users (user_id)
);

create table forum.reactions
(
    reaction_id int auto_increment
        primary key,
    created_by  int        not null,
    post_id     int        not null,
    isUpVoted   tinyint(1) null,
    constraint liked_post_posts_post_id_fk
        foreign key (post_id) references forum.posts (post_id),
    constraint liked_post_users_user_id_fk
        foreign key (created_by) references forum.users (user_id)
);

create table forum.users_comments
(
    user_id    int                  not null,
    comment_id int                  not null,
    deleted    tinyint(1) default 0 not null,
    constraint users_comments_comments_comment_id_fk
        foreign key (comment_id) references forum.comments (comment_id),
    constraint users_comments_users_user_id_fk
        foreign key (user_id) references forum.users (user_id)
);

create table forum.users_posts
(
    user_id int                  not null,
    post_id int                  not null,
    deleted tinyint(1) default 0 not null,
    constraint users_posts_posts_post_id_fk
        foreign key (post_id) references forum.posts (post_id),
    constraint users_posts_users_user_id_fk
        foreign key (user_id) references forum.users (user_id)
);

