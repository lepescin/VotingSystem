DELETE FROM users;
DELETE FROM dishes;
DELETE FROM user_roles;
DELETE FROM restaurants;
DELETE FROM votes;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users(name, email, password)
VALUES ('User1', 'user1@yandex.ru', '{noop}password1'),
       ('User2', 'user2@yandex.ru', '{noop}password2'),
       ('User3', 'user3@yandex.ru', '{noop}password3'),
       ('User4', 'user4@yandex.ru', '{noop}password4'),
       ('User5', 'user5@yandex.ru', '{noop}password5'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('USER', 100001),
       ('USER', 100002),
       ('USER', 100003),
       ('USER', 100004),
       ('ADMIN', 100005),
       ('USER', 100005);

INSERT INTO restaurants(name)
VALUES ('Restaurant1'),
       ('Restaurant2');

INSERT INTO dishes (name, price, restaurant_id)
VALUES ('Dish1', 100, 100006),
       ('Dish2', 200, 100006),
       ('Dish3', 300, 100006),
       ('Dish4', 400, 100006),
       ('Dish5', 500, 100006),
       ('Dish6', 600, 100006),
       ('Dish7', 700, 100006),
       ('Dish8', 800, 100006),
       ('Dish9', 900, 100006),
       ('Dish10', 1000, 100006),
       ('Dish11', 1100, 100007),
       ('Dish12', 1200, 100007),
       ('Dish13', 1300, 100007),
       ('Dish14', 1400, 100007),
       ('Dish15', 1500, 100007),
       ('Dish16', 1600, 100007),
       ('Dish17', 1700, 100007),
       ('Dish18', 1800, 100007),
       ('Dish19', 1900, 100007),
       ('Dish20', 2000, 100007);

INSERT INTO votes(user_id, restaurant_id, date_time)
VALUES (100000,100006,now()),
       (100001,100006,now()),
       (100002,100006,now()),
       (100003,100007,now()),
       (100004,100007,now());


