create table if not exists USERS
(
    id int not null primary key auto_increment,
    email varchar(255) not null,
    login varchar(255) not null,
    birthday date,
    deleted bool default false,
    UNIQUE (email),
    UNIQUE (login),
);

create table if not exists GENRES
(
    id int not null primary key auto_increment,
    name varchar(255)
);

create table if not exists MPA
(
    id int not null primary key auto_increment,
    name varchar(255)
);

create table if not exists FILMS
(
    id int not null primary key auto_increment,
    name varchar(255),
    description varchar(255),
    release_date date,
    rate int,
    duration int,
    mpa_id int references MPA (id),
    deleted bool default false
);

create table if not exists FRIENDS
(
    user_id int not null references USERS (id),
    friend_id int not null references USERS (id),
    PRIMARY KEY (user_id, friend_id)
);

create table if not exists LIKES
(
    user_id int references USERS (id),
    film_id int references FILMS (id),
    PRIMARY KEY (user_id, friend_id)
);
