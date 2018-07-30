# --- !Ups

insert into "user" ("firstName", "lastName", "mobile", "email", "age")
values ('Rendy', 'Wandarosanza', '085717855566', 'r.wandarosanza@gmail.com', 24);

# --- !Downs

delete from "user" where "email" = 'r.wandarosanza@gmail.com';
