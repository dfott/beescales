create table scale_location
(
    id   int auto_increment
        primary key,
    name varchar(50) not null
);
create table scale_details
(
    device_id         int         not null
        primary key,
    scale_location_id int         not null,
    name              varchar(50) not null,
    color             varchar(7)  not null,
    constraint scale_location__fk
        foreign key (scale_location_id) references scale_location (id)
);

create table scale_threshold
(
    id          int auto_increment
        primary key,
    device_id   int        not null,
    min         int        not null,
    max         int        not null,
    is_absolute tinyint(1) not null,
    is_active   tinyint(1) not null,
    constraint scale_threshold_scale_details_device_id_fk
        foreign key (device_id) references scale_details (device_id)
);

create table scaleData
(
    id             int(11) unsigned auto_increment
        primary key,
    deviceid       int       null,
    ts             timestamp null,
    weight         double    null,
    corr           double    null,
    temp           double    null,
    batt           double    null,
    crop           double    null,
    altitude       double    null,
    latitude       double    null,
    longitude      double    null,
    epoche         datetime  null,
    WindRichtung   int       null,
    Windgeschw     double    null,
    redLuftdruck   double    null,
    relLuftfeuchte int       null,
    Lufttemp       double    null
);

insert into scaleData
(id, deviceid, ts, weight, corr, temp, batt, crop, altitude, latitude, longitude, epoche, WindRichtung, Windgeschw, redLuftdruck, relLuftfeuchte, Lufttemp)
values (1, 1234, null, 1, 1, 1, 1, 1, 1, 1, 1, '2021-11-23 01:00:00', 1, 1, 1, 1, 1);

insert into scaleData
(id, deviceid, ts, weight, corr, temp, batt, crop, altitude, latitude, longitude, epoche, WindRichtung, Windgeschw, redLuftdruck, relLuftfeuchte, Lufttemp)
values (2, 1234, null, 1, 1, 1, 1, 1, 1, 1, 1, '2021-11-23 02:00:00', 1, 1, 1, 1, 1);

insert into scaleData
(id, deviceid, ts, weight, corr, temp, batt, crop, altitude, latitude, longitude, epoche, WindRichtung, Windgeschw, redLuftdruck, relLuftfeuchte, Lufttemp)
values (3, 1234, null, 1, 1, 1, 1, 1, 1, 1, 1, '2021-11-23 03:00:00', 1, 1, 1, 1, 1);

insert into scaleData
(id, deviceid, ts, weight, corr, temp, batt, crop, altitude, latitude, longitude, epoche, WindRichtung, Windgeschw, redLuftdruck, relLuftfeuchte, Lufttemp)
values (4, 5678, null, 1, 1, 1, 1, 1, 1, 1, 1, '2021-11-23 04:00:00', 1, 1, 1, 1, 1);

insert into scaleData
(id, deviceid, ts, weight, corr, temp, batt, crop, altitude, latitude, longitude, epoche, WindRichtung, Windgeschw, redLuftdruck, relLuftfeuchte, Lufttemp)
values (5, 5678, null, 1, 1, 1, 1, 1, 1, 1, 1, '2021-11-23 05:00:00', 1, 1, 1, 1, 1);

insert into scale_location
(id, name)
VALUES (1, 'zu hause');

insert into scale_location
(id, name)
VALUES (2, 'in der uni');

insert into scale_details
(device_id, scale_location_id, name, color)
VALUES (1234, 1, 'weisse', '#FFFFFF');

insert into scale_details
(device_id, scale_location_id, name, color)
VALUES (5678, 2, 'schwarze', '#000000');

insert into scale_details
(device_id, scale_location_id, name, color)
VALUES (12, 2, 'schwarze', '#000000');
