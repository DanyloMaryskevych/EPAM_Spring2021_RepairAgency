alter table customers_data
    drop foreign key customers_data_ibfk_1;
alter table workers_data
    drop foreign key workers_data_ibfk_1;
alter table `order`
    drop foreign key order_ibfk_1;
alter table `order`
    drop foreign key order_ibfk_2;
alter table `order`
    drop foreign key order_ibfk_3;
alter table `order`
    drop foreign key order_ibfk_4;
alter table user
    drop foreign key user_ibfk_1;

drop table if exists role;
drop table if exists user;
drop table if exists customers_data;
drop table if exists workers_data;
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
    password varchar(45),
    role_id  int,
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

create table payment_status
(
    id     int primary key auto_increment,
    status enum ('Waiting for price', 'Waiting for payment', 'Paid')
);

create table performance_status
(
    id     int primary key auto_increment,
    status enum ('Not started', 'In work', 'Done', 'Rejected')
);

create table `order`
(
    id                    int PRIMARY KEY AUTO_INCREMENT,
    customer_id           int,
    worker_id             int      default 0,
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
    foreign key (worker_id) references workers_data (worker_id),
    foreign key (payment_status_id) references payment_status (id),
    foreign key (performance_status_id) references performance_status (id)
        on delete cascade
        on update cascade
)
    default charset = utf8mb4
    collate = utf8mb4_0900_ai_ci
;

insert into user
values (default, 'Admin', 'Admin', 1);
insert into user
values (default, 'Danylo', '1111', 2);
insert into user
values (default, 'Barnie', '1111', 3);

insert into customers_data
values (1, 1000);
insert into workers_data
values (2, default, default, default, default, default, default);