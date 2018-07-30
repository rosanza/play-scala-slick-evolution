# --- !Ups

create table "user" (
  "id" bigint generated by default as identity(start with 1) not null primary key,
  "firstName" varchar not null,
  "lastName" varchar not null,
  "mobile" varchar not null,
  "email" varchar not null,
  "age" int not null
);

# --- !Downs

drop table "user" if exists;