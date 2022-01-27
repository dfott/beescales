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