# --- !Ups

INSERT INTO "user" (firstname, lastname, mobile, email, age)
VALUES ('Rendy', 'Wandarosanza', '085717855566', 'r.wandarosanza@gmail.com', 24);

# --- !Downs

DELETE FROM "user" where email = 'r.wandarosanza@gmail.com';
