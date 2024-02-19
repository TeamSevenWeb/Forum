INSERT INTO forum.users (user_id, username, password, first_name, last_name, email, date_of_registration, is_blocked, is_admin, is_active) VALUES (1, 'alex.m', 'ForumSeven', 'Alexander', 'Mechkarov', 'alex.m@abv.bg', '2024-01-01', 0, 1, 0);
INSERT INTO forum.users (user_id, username, password, first_name, last_name, email, date_of_registration, is_blocked, is_admin, is_active) VALUES (2, 'georgi.i', 'ForumSeven', 'Georgi', 'Iliev', 'georgi.i@abv.bg', '2024-01-02', 0, 1, 0);
INSERT INTO forum.users (user_id, username, password, first_name, last_name, email, date_of_registration, is_blocked, is_admin, is_active) VALUES (3, 'svetlio.d', 'ForumSeven', 'Svetoslav', 'Dimitrov', 'svetlio.d@abv.bg', '2024-01-03', 0, 1, 0);
INSERT INTO forum.users (user_id, username, password, first_name, last_name, email, date_of_registration, is_blocked, is_admin, is_active) VALUES (4, 'hansgunter', 'ForumSeven', 'Hans', 'Gunter', 'hans.g@gmx.de', '2024-01-06', 0, 0, 0);
INSERT INTO forum.users (user_id, username, password, first_name, last_name, email, date_of_registration, is_blocked, is_admin, is_active) VALUES (5, 'peter1996', 'ForumSeven', 'Peter', 'Smith', 'peter.s@gmail.com', '2024-01-06', 0, 0, 0);
INSERT INTO forum.users (user_id, username, password, first_name, last_name, email, date_of_registration, is_blocked, is_admin, is_active) VALUES (6, 'todor.k', 'ForumSeven', 'Todor', 'Krustev', 'tosho.k@abv.bg', '2024-01-08', 0, 0, 0);
INSERT INTO forum.users (user_id, username, password, first_name, last_name, email, date_of_registration, is_blocked, is_admin, is_active) VALUES (7, 'vanko02', 'ForumSeven', 'Ivan', 'Ivanov', 'ivan.i@gmail.com', '2024-01-10', 0, 0, 0);
INSERT INTO forum.users (user_id, username, password, first_name, last_name, email, date_of_registration, is_blocked, is_admin, is_active) VALUES (8, 'stambata', 'ForumSeven', 'Stanimir', 'Hristov', 'stambata@mail.bg', '2024-01-12', 0, 0, 0);
INSERT INTO forum.users (user_id, username, password, first_name, last_name, email, date_of_registration, is_blocked, is_admin, is_active) VALUES (9, 'johnson', 'ForumSeven', 'John', 'Smith', 'johnny@gmail.com', '2024-01-20', 0, 0, 0);
INSERT INTO forum.users (user_id, username, password, first_name, last_name, email, date_of_registration, is_blocked, is_admin, is_active) VALUES (10, 'linuxenjoyer93', 'ForumSeven', 'Dragomir', 'Georgiev', 'dragolinux@yahoo.com', '2024-01-20', 0, 0, 0);


INSERT INTO forum.profile_photos (profile_photo_id, user_id, profile_photo) VALUES (29, 3, 'http://res.cloudinary.com/dykfvzq8y/image/upload/v1708322421/f8plknzgwh933vtxglek.png');
INSERT INTO forum.profile_photos (profile_photo_id, user_id, profile_photo) VALUES (30, 4, 'http://res.cloudinary.com/dykfvzq8y/image/upload/v1708322664/s0o7t91zi8bkwjkg9bht.jpg');
INSERT INTO forum.profile_photos (profile_photo_id, user_id, profile_photo) VALUES (31, 9, 'http://res.cloudinary.com/dykfvzq8y/image/upload/v1708322855/hjpstp4suaaoskqtpfhh.jpg');
INSERT INTO forum.profile_photos (profile_photo_id, user_id, profile_photo) VALUES (32, 10, 'http://res.cloudinary.com/dykfvzq8y/image/upload/v1708322960/hbxjeeycbxnlkrc9arro.jpg');
INSERT INTO forum.profile_photos (profile_photo_id, user_id, profile_photo) VALUES (33, 6, 'http://res.cloudinary.com/dykfvzq8y/image/upload/v1708323351/kdimcrkxfqbcw1dtervw.jpg');
INSERT INTO forum.profile_photos (profile_photo_id, user_id, profile_photo) VALUES (34, 1, 'http://res.cloudinary.com/dykfvzq8y/image/upload/v1708323465/bnsz9z5ddpa3t3bjcelg.jpg');
INSERT INTO forum.profile_photos (profile_photo_id, user_id, profile_photo) VALUES (35, 5, 'http://res.cloudinary.com/dykfvzq8y/image/upload/v1708323519/olfvdwbmcwrifnkdcqot.jpg');
INSERT INTO forum.profile_photos (profile_photo_id, user_id, profile_photo) VALUES (36, 2, 'http://res.cloudinary.com/dykfvzq8y/image/upload/v1708324086/nief2yyitcixcayy128g.jpg');
INSERT INTO forum.profile_photos (profile_photo_id, user_id, profile_photo) VALUES (37, 7, 'http://res.cloudinary.com/dykfvzq8y/image/upload/v1708324109/l38ovbs7iipzobjg3tjc.jpg');
INSERT INTO forum.profile_photos (profile_photo_id, user_id, profile_photo) VALUES (38, 8, 'http://res.cloudinary.com/dykfvzq8y/image/upload/v1708324166/h4ifsvd6dw4y6m09pqk7.jpg');


INSERT INTO forum.admins (admin_id, user_id) VALUES (1, 1);
INSERT INTO forum.admins (admin_id, user_id) VALUES (2, 2);
INSERT INTO forum.admins (admin_id, user_id) VALUES (3, 3);


INSERT INTO forum.admins_phone_numbers (admin_id, phone_number) VALUES (1, '0899999999');
INSERT INTO forum.admins_phone_numbers (admin_id, phone_number)VALUES (2, '0877777777');
INSERT INTO forum.admins_phone_numbers (admin_id, phone_number) VALUES (3, '0888888888');

# INSERT INTO forum.posts (post_id, created_by, title, content, date_and_time_of_creation) VALUES (13, 3, 'What do you guys think of Eminem\'s new boomer aesthetic? ', 'It has been 2 decades and I feel like things will never be the same..', '2024-02-18 07:30:24');
INSERT INTO forum.posts (post_id, created_by, title, content, date_and_time_of_creation) VALUES (14, 9, 'Where should I visit in Bulgaria?', 'I have heard from my colleagues at work that Bulgaria is a beautiful and a cheap country. I know this forum has its fair share of Bulgarians, so I would love to get some feedback :)', '2024-02-18 07:44:47');
INSERT INTO forum.posts (post_id, created_by, title, content, date_and_time_of_creation) VALUES (15, 10, 'Linux is better than Windows and here is why', 'I\'ve done tech support for years, and that means Windows when I\'m out in the wild.

I don\'t know about "special", but I will say that I like Linux because it\'s not always wanting something from me. I install Linux and get it configured how I like, and except for an update every couple of weeks, I can go about my business. I reboot far less than on a Windows machine. I find it. . .peaceful. Also, I feel like my computer is mine.

After more than 15 years of using only Linux on all of my home office computers, I was forced to buy a Windows computer for someone else in the household, and for something that couldn\'t be gotten around any other way. It\'s always squawking about something. Check this, reset that, what seems to be never ending notifications about unimportant things, constant squawking for attention. I\'m not amused. I don\'t feel any need to return to Windows like \'a lost sheep\'.

When this Windows laptop ages out of its OS, I will be delighted to turn it into a Linux computer.

But that\'s me. For my own self, I don\'t have any needs that can\'t be met by what is in the repositories or install with a .deb file.', '2024-02-18 08:22:39');
INSERT INTO forum.posts (post_id, created_by, title, content, date_and_time_of_creation) VALUES (16, 7, 'Anyone wanna form a zerg in Rust??', 'Let\'s get the gang going to dominate an official server', '2024-02-18 09:02:07');
INSERT INTO forum.posts (post_id, created_by, title, content, date_and_time_of_creation) VALUES (17, 4, 'Morning Routines for a Productive Day?', 'Looking to revamp my morning routine to kickstart my day on a positive note. What does your morning routine look like, and how does it help you stay productive?', '2024-02-18 09:09:46');
INSERT INTO forum.posts (post_id, created_by, title, content, date_and_time_of_creation) VALUES (18, 3, 'Eco-Friendly Home Decor Ideas?', 'I\'m redecorating my apartment and want to make sustainable choices. Do you have any eco-friendly home decor ideas or brands you love? Looking for anything from furniture to small decorative items.', '2024-02-18 09:14:22');
INSERT INTO forum.posts (post_id, created_by, title, content, date_and_time_of_creation) VALUES (19, 5, 'Hidden Gems on Steam for 2024?', 'Hey fellow gamers! I\'m on the hunt for some hidden gems on Steam that I might have missed. I\'m open to any genre, just looking for something unique and engaging that\'s flown under the radar. What are your top picks for 2024 that deserve more attention?', '2024-02-18 09:20:01');


INSERT INTO forum.tags (tag_id, name) VALUES (8, 'Eminem');
INSERT INTO forum.tags (tag_id, name) VALUES (9, 'music');
INSERT INTO forum.tags (tag_id, name) VALUES (10, 'tourism');
INSERT INTO forum.tags (tag_id, name) VALUES (11, 'Bulgaria');
INSERT INTO forum.tags (tag_id, name) VALUES (12, 'travel');
INSERT INTO forum.tags (tag_id, name) VALUES (13, 'Rust');
INSERT INTO forum.tags (tag_id, name) VALUES (14, 'Steam');
INSERT INTO forum.tags (tag_id, name) VALUES (15, 'health');
INSERT INTO forum.tags (tag_id, name) VALUES (16, 'mental health');
INSERT INTO forum.tags (tag_id, name) VALUES (17, 'Games');


# INSERT INTO forum.comments (comment_id, post_id, created_by, comment, date_and_time_of_creation) VALUES (19, 13, 9, 'This is true. Eminem is not the only one, too', '2024-02-18 07:57:00');
INSERT INTO forum.comments (comment_id, post_id, created_by, comment, date_and_time_of_creation) VALUES (20, 14, 7, 'Sofia is the capital and has a lot of stuff. You can also sleep at my place in Lyulin if you come <3', '2024-02-18 08:02:08');
INSERT INTO forum.comments (comment_id, post_id, created_by, comment, date_and_time_of_creation) VALUES (21, 14, 3, 'Consider Varna! We have beaches and a massive park', '2024-02-18 08:14:26');
INSERT INTO forum.comments (comment_id, post_id, created_by, comment, date_and_time_of_creation) VALUES (22, 14, 10, 'I have to agree with svetlio here. If you want a unique tourist\'s experience, you should never visit a capital.', '2024-02-18 08:27:46');
INSERT INTO forum.comments (comment_id, post_id, created_by, comment, date_and_time_of_creation) VALUES (23, 14, 4, 'I remember going on a business trip to Sofia and it was a unique experience. Every country\'s capital is special. ', '2024-02-18 08:34:59');
# INSERT INTO forum.comments (comment_id, post_id, created_by, comment, date_and_time_of_creation) VALUES (24, 13, 4, 'I like him more now that he looks older. Some things are better left as memories.', '2024-02-18 08:37:43');
INSERT INTO forum.comments (comment_id, post_id, created_by, comment, date_and_time_of_creation) VALUES (25, 15, 7, 'I didn\'t read anything, but Windows is better!
', '2024-02-18 08:55:23');
INSERT INTO forum.comments (comment_id, post_id, created_by, comment, date_and_time_of_creation) VALUES (26, 17, 9, 'I start my day with meditation and a short workout. It really helps clear my mind.', '2024-02-18 09:10:47');
INSERT INTO forum.comments (comment_id, post_id, created_by, comment, date_and_time_of_creation) VALUES (27, 16, 3, 'lets go bro  add me on steam friend tag 421124124', '2024-02-18 09:12:15');
INSERT INTO forum.comments (comment_id, post_id, created_by, comment, date_and_time_of_creation) VALUES (28, 17, 3, 'I’ve found that avoiding screens for the first hour after waking up makes me more focused throughout the day.', '2024-02-18 09:13:51');
INSERT INTO forum.comments (comment_id, post_id, created_by, comment, date_and_time_of_creation) VALUES (29, 18, 1, 'Thrifting is not only budget-friendly but also eco-friendly. You can find some unique pieces with character.', '2024-02-18 09:15:10');
INSERT INTO forum.comments (comment_id, post_id, created_by, comment, date_and_time_of_creation) VALUES (30, 18, 4, 'Look for brands that use recycled materials. There are some great options for everything from rugs to throw pillows.', '2024-02-18 09:15:32');
INSERT INTO forum.comments (comment_id, post_id, created_by, comment, date_and_time_of_creation) VALUES (31, 18, 10, 'Plants are a great way to decorate and purify the air in your home. Plus, they add a nice touch of green.', '2024-02-18 09:16:09');
INSERT INTO forum.comments (comment_id, post_id, created_by, comment, date_and_time_of_creation) VALUES (32, 19, 3, 'Have you tried \'Echoes of the Lost\'? It’s an indie puzzle-platformer with a really captivating story.', '2024-02-18 09:22:32');
INSERT INTO forum.comments (comment_id, post_id, created_by, comment, date_and_time_of_creation) VALUES (33, 19, 4, 'I recommend \'Stellar Drift\', a space exploration game with a twist on resource management. Barely seen it mentioned, but it’s fantastic.', '2024-02-18 09:22:58');
INSERT INTO forum.comments (comment_id, post_id, created_by, comment, date_and_time_of_creation) VALUES (34, 19, 2, 'A Thief\'s Melody\' is a great stealth game with a beautiful art style. Definitely a hidden gem!', '2024-02-18 09:23:49');
INSERT INTO forum.comments (comment_id, post_id, created_by, comment, date_and_time_of_creation) VALUES (36, 19, 5, 'Thanks!!', '2024-02-18 09:24:34');
