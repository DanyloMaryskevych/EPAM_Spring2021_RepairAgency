# alter table customers_data
#     drop foreign key customers_data_ibfk_1;
# alter table workers_data
#     drop foreign key workers_data_ibfk_1;
# alter table `order`
#     drop foreign key order_ibfk_1;
# alter table `order`
#     drop foreign key order_ibfk_2;
# alter table `order`
#     drop foreign key order_ibfk_3;
# alter table `order`
#     drop foreign key order_ibfk_4;
# alter table user
#     drop foreign key user_ibfk_1;

drop table if exists role;
drop table if exists user;
drop table if exists customers_data;
drop table if exists workers_data;
drop table if exists language;
drop table if exists payment_status;
drop table if exists performance_status;
drop table if exists `order`;

create table role
(
    id   int primary key auto_increment,
    role enum ('Admin', 'Customer', 'Worker')
);

insert into role
values (default, 'Admin');
insert into role
values (default, 'Customer');
insert into role
values (default, 'Worker');

create table user
(
    id       int PRIMARY KEY AUTO_INCREMENT,
    login    varchar(45) UNIQUE,
    role_id  int,
    salt varchar(40),
    password varchar(64),
    foreign key (role_id) references role (id)
        on delete cascade
        on update cascade
)
    default charset = utf8mb4
    collate = utf8mb4_0900_ai_ci
;

create table customers_data
(
    customer_id int PRIMARY KEY,
    balance     int default 0,
    foreign key (customer_id) references user (id)
);

create table workers_data
(
    worker_id       int PRIMARY KEY,
    orders_amount   int    default 0,
    average         double default 0,
    votes           int    default 0,
    wilson_score    double default 0,
    positive_grades int    default 0,
    negative_grades int    default 0,
    foreign key (worker_id) references user (id)
        on delete cascade
        on update cascade
);

create table language
(
    id   int primary key auto_increment,
    lang varchar(2) unique
);

insert into language
values (default, 'en');
insert into language
values (default, 'ua');

create table performance_status
(
    id                    int primary key auto_increment,
    performance_status_id int,
    lang_id               int,
    performance_status    varchar(25),
    foreign key (lang_id) references language (id)
)
    default charset = utf8mb4
    collate = utf8mb4_0900_ai_ci
;

insert into performance_status
values (default, 1, 1, 'Not started');
insert into performance_status
values (default, 1, 2, 'Не розпочато');
insert into performance_status
values (default, 2, 1, 'In work');
insert into performance_status
values (default, 2, 2, 'В роботі');
insert into performance_status
values (default, 3, 1, 'Done');
insert into performance_status
values (default, 3, 2, 'Виконано');
insert into performance_status
values (default, 4, 1, 'Rejected');
insert into performance_status
values (default, 4, 2, 'Відхилено');

create table payment_status
(
    id                int primary key auto_increment,
    payment_status_id int,
    lang_id           int,
    payment_status    varchar(25),
    foreign key (lang_id) references language (id)
)
    default charset = utf8mb4
    collate = utf8mb4_0900_ai_ci
;

insert into payment_status
values (default, 1, 1, 'Waiting for price');
insert into payment_status
values (default, 1, 2, 'Очікування ціни');
insert into payment_status
values (default, 2, 1, 'Waiting for payment');
insert into payment_status
values (default, 2, 2, 'Очікування оплати');
insert into payment_status
values (default, 3, 1, 'Paid');
insert into payment_status
values (default, 3, 2, 'Оплачено');

create table `order`
(
    id                    int PRIMARY KEY AUTO_INCREMENT,
    customer_id           int,
    worker_id             int,
    date                  DATETIME default NOW(),
    title                 varchar(40),
    description           varchar(500),
    expected_worker_id    int,
    payment_status_id     int      default 1,
    performance_status_id int      default 1,
    price                 int      default 0,
    rating                int      default 0,
    comment               varchar(100),
    foreign key (customer_id) references customers_data (customer_id),
    foreign key (worker_id) references workers_data (worker_id)
#     foreign key (payment_status_id) references payment_status (payment_status_id),
#     foreign key (performance_status_id) references performance_status (performance_status_id)
        on delete cascade
        on update cascade
)
    default charset = utf8mb4
    collate = utf8mb4_0900_ai_ci
;

insert into user
values (default, 'Admin', 1,
        '4826256FFBE73E72D94CC1C72763F53C35B2509C',
        'B868321770D77ABFEFB72CC427EC79C2E909109A10912A68F25A22345CA0E93D');