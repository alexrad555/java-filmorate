CREATE TABLE "users" (
  "id" id PRIMARY KEY,
  "email" varchar,
  "login" varchar,
  "name" varchar,
  "birthday" timestamp,
  "deleted" bool
);

CREATE TABLE "films" (
  "id" id PRIMARY KEY,
  "name" varchar,
  "description" varchar,
  "release_date" timestamp,
  "rate" int,
  "duration" int,
  "mpa_id" int
);

CREATE TABLE "mpa" (
  "id" id PRIMARY KEY,
  "name" varchar
);

CREATE TABLE "genres" (
  "id" id PRIMARY KEY,
  "name" varchar
);

CREATE TABLE "film_genres" (
  "id" serial,
  "film_id" int,
  "genre_id" int
);

CREATE TABLE "friends" (
  "id" int,
  "user_id" int,
  "friend_id" int
);

CREATE TABLE "likes" (
  "user_id" int,
  "film_id" int
);

ALTER TABLE "films" ADD FOREIGN KEY ("mpa_id") REFERENCES "mpa" ("id");

ALTER TABLE "film_genres" ADD FOREIGN KEY ("film_id") REFERENCES "films" ("id");

ALTER TABLE "film_genres" ADD FOREIGN KEY ("genre_id") REFERENCES "genres" ("id");

ALTER TABLE "friends" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "friends" ADD FOREIGN KEY ("friend_id") REFERENCES "users" ("id");

ALTER TABLE "likes" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "likes" ADD FOREIGN KEY ("film_id") REFERENCES "films" ("id");
