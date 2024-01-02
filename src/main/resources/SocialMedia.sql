-- drop tables if exists: message, account
drop table if exists message;
drop table if exists account;

-- create table account
create table account (
    account_id int primary key auto_increment,
    username varchar(255) unique,
    password varchar(255)
);

-- create table message
create table message (
    message_id int primary key auto_increment,
    posted_by int,
    message_text varchar(255),
    time_posted_epoch bigint,  /* readme.md ---> time_posted_epoch long */
    foreign key (posted_by) references account(account_id)
);

-- insert seed data into account table
insert into account (username, password) values ('testuser1', 'password');

-- insert seed data into message table
insert into message (posted_by, message_text, time_posted_epoch) values (1, 'test message 1', 1669947792);