create table tags
(
    tag_id int auto_increment
        primary key,
    name   varchar(20) not null
);

create table users
(
    user_id              int auto_increment
        primary key,
    username             varchar(50) not null,
    password             varchar(50) not null,
    first_name           varchar(32) not null,
    last_name            varchar(32) not null,
    email                varchar(50) not null,
    date_of_registration date        not null,
    is_blocked           tinyint(1)  not null,
    is_active             tinyint(1)  not null,
    is_admin             tinyint(1)  not null
);

create table admins
(
    admin_id int auto_increment
        primary key,
    user_id  int not null,
    constraint admins_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create table admins_phone_numbers
(
    admin_id     int         not null
        primary key,
    phone_number varchar(20) not null,
    constraint admins_phone_numbers_admins_admin_id_fk
        foreign key (admin_id) references admins (admin_id)
);

create table posts
(
    post_id                   int auto_increment
        primary key,
    created_by                int           not null,
    title                     varchar(64)   not null,
    content                   varchar(8192) not null,
    likes                     int default 0 null,
    date_and_time_of_creation datetime      null,
    constraint posts_users_user_id_fk
        foreign key (created_by) references users (user_id)
);

create table comments
(
    comment_id int auto_increment
        primary key,
    post_id    int          not null,
    created_by int          not null,
    comment    varchar(250) null,
    constraint posts_comments_posts_post_id_fk
        foreign key (post_id) references posts (post_id),
    constraint posts_comments_users_user_id_fk
        foreign key (created_by) references users (user_id)
);

create table posts_comments
(
    post_id    int                  not null,
    comment_id int                  not null,
    deleted    tinyint(1) default 0 not null,
    constraint joined_posts_comments_posts_post_id_fk
        foreign key (post_id) references posts (post_id),
    constraint posts_comments_comments_comment_id_fk
        foreign key (comment_id) references comments (comment_id)
);

create table posts_tags
(
    post_id int not null,
    tag_id  int not null,
    constraint posts_tags_posts_post_id_fk
        foreign key (post_id) references posts (post_id),
    constraint posts_tags_tags_tag_id_fk
        foreign key (tag_id) references tags (tag_id)
);

create table reactions
(
    reaction_id int auto_increment
        primary key,
    created_by  int        not null,
    post_id     int        not null,
    isLiked     tinyint(1) null,
    constraint liked_post_posts_post_id_fk
        foreign key (post_id) references posts (post_id),
    constraint liked_post_users_user_id_fk
        foreign key (created_by) references users (user_id)
);

create table posts_reactions
(
    post_id    int                  not null,
    reaction_id int                  not null,
    isPresent    tinyint(1) default 0 not null,
    constraint joined_posts_reactions_posts_post_id_fk
        foreign key (post_id) references posts (post_id),
    constraint posts_reactions_reactions_reaction_id_fk
        foreign key (reaction_id) references reactions (reaction_id)
);

create table user_profile_photo
(
    user_id       int  not null
        primary key,
    profile_photo blob not null,
    constraint user_profile_photo_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create table users_comments
(
    user_id    int                  not null,
    comment_id int                  not null,
    deleted    tinyint(1) default 0 not null,
    constraint users_comments_comments_comment_id_fk
        foreign key (comment_id) references comments (comment_id),
    constraint users_comments_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create table users_posts
(
    user_id int                  not null,
    post_id int                  not null,
    deleted tinyint(1) default 0 not null,
    constraint users_posts_posts_post_id_fk
        foreign key (post_id) references posts (post_id),
    constraint users_posts_users_user_id_fk
        foreign key (user_id) references users (user_id)
);



