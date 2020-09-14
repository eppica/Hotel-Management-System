drop database hms;
create database hms;
use hms;
create table if not exists booking
(
    id        int auto_increment
        primary key,
    room_fk   int            null,
    guest_fk  int            null,
    arrival   date           null,
    departure date           null,
    total     decimal(13, 4) null,
    staff_fk  int            null
);

create index booking_guest_id_fk
    on booking (guest_fk);

create index booking_room_id_fk
    on booking (room_fk);

create index booking_staff_id_fk
    on booking (staff_fk);

create table if not exists `check`
(
    id         int auto_increment
        primary key,
    `check`    datetime   null,
    staff_fk   int        null,
    booking_fk int        null,
    status     tinyint(1) null
);

create index check_booking_id_fk
    on `check` (booking_fk);

create index check_staff_id_fk
    on `check` (staff_fk);

create table if not exists guest
(
    id           int auto_increment
        primary key,
    name         varchar(255) null,
    document     varchar(32)  null,
    birth_date   date         null,
    email        varchar(64)  null,
    phone_number varchar(32)  null
);

create table if not exists room
(
    id           int auto_increment
        primary key,
    room_type_fk int null,
    number       int null
);

create index room_room_type_id_fk
    on room (room_type_fk);

create table if not exists room_type
(
    id          int auto_increment
        primary key,
    name        varchar(64)    null,
    description varchar(255)   null,
    daily_price decimal(13, 4) null
);

create table if not exists staff
(
    id           int auto_increment
        primary key,
    name         varchar(255)            null,
    access_level enum ('OWNER', 'STAFF') null,
    login        varchar(32)             null,
    password     varchar(64)             null
);


