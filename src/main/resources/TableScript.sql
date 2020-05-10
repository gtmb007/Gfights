drop table if exists booking;
drop table if exists myuser;

create table myuser (
	user_id varchar(10) primary key,
	first_name varchar(20) not null,
	last_name varchar(20),
	password varchar(10) not null,
	wallet_balance double(8,2) not null
);

create table booking (
	booking_id int(8) auto_increment primary key,
	user_id varchar(10)
);

alter table booking add constraint fk_booking foreign key (user_id) references myuser(user_id);

commit;

select * from myuser;
select * from booking;