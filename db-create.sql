# alter table customers_data drop foreign key customers_data_ibfk_1;
# alter table orders drop foreign key orders_ibfk_1;

drop table if exists users;
drop table if exists customers_data;
drop table if exists workers_data;
drop table if exists orders;

create table users(
    id int PRIMARY KEY AUTO_INCREMENT,
    login varchar(45) UNIQUE,
    password varchar(45),
    role varchar(20)
);

create table customers_data (
    customer_id int PRIMARY KEY,
    balance int default 0
);

create table workers_data (
    worker_id int PRIMARY KEY,
    bio varchar(1000) default 'No information',
    orders_amount int default 0,
    average double default 0,
    votes int default 0,
    wilson_score double default 0,
    positive_grades int default 0,
    negative_grades int default 0
);

create table orders (
    id int PRIMARY KEY AUTO_INCREMENT,
    customer_id int,
    worker_id int default 0,
    date DATETIME default NOW(),
    title varchar(40),
    description varchar(500),
    expected_worker int default 0,
    payment_status varchar(30) default 'Waiting for price',
    performance_status varchar(30) default 'Not started',
    price int default 0,
    rating int default 0,
    comment varchar(100) default 'None'
);

insert into users values(default, 'Danylo', '1111', 'Customer');
insert into users values(default, 'Barnie', '1111', 'Worker');
insert into users values(default, 'Admin', '1111', 'Admin');

insert into customers_data values (1, 1000);
insert into workers_data values (2, default, default, default, default, default, default, default);