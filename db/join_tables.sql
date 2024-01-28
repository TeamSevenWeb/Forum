create table users_posts
(
    user_id int        not null,
    post_id int        not null,
    deleted   boolean not null default 0,
    constraint users_posts_posts_post_id_fk
        foreign key (post_id) references posts (post_id),
    constraint users_posts_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create table users_comments
(
    user_id int        not null,
    comment_id int        not null,
    deleted   boolean not null default 0,
    constraint users_comments_comments_comment_id_fk
        foreign key (comment_id) references comments (comment_id),
    constraint users_comments_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create table posts_comments
(
    post_id int        not null,
    comment_id int        not null,
    deleted   boolean not null default 0,
    constraint posts_comments_comments_comment_id_fk
        foreign key (comment_id) references comments (comment_id),
    constraint joined_posts_comments_posts_post_id_fk
        foreign key (post_id) references posts (post_id)
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