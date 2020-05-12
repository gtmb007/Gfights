drop table if exists flight;
drop table if exists seat_map;
drop table if exists passenger;
drop table if exists booking;
drop table if exists myuser;

create table flight (
	flight_id varchar(10) primary key,
	flight_name varchar(20) not null,
	base_fare double(8,2) not null
);

create table seat_map (
	doj date primary key,
	available_seats int(4)
);

create table myuser (
	user_id varchar(10) primary key,
	first_name varchar(20) not null,
	last_name varchar(20),
	password varchar(10) not null,
	wallet_balance double(8,2) not null
);

create table booking (
	booking_id int(8) auto_increment primary key,
	flight_id varchar(10) not null,
	flight_name varchar(20) not null,
	source varchar(20) not null,
	destination varchar(20) not null,
	doj	date not null,
	departure time not null,
	arrival time not null,
	booked_on datetime not null,
	amount double(8,2) not null,
	user_id varchar(10)
);

create table passenger (
	passenger_id int(8) auto_increment primary key,
	first_name varchar(20) not null,
	last_name varchar(20),
	seat_no varchar(4) not null,
	booking_id int(8)
);

alter table booking add constraint fk_booking foreign key (user_id) references myuser(user_id);
alter table passenger add constraint fk_passenger foreign key (booking_id) references booking(booking_id);

commit;

select * from flight;
select * from seat_map;
select * from myuser;
select * from booking;
select * from passenger;

