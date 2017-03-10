drop table STOCK if exists
drop table KEYS if exists
create table STOCK (category varchar(1024), item varchar(1024), stock INTEGER, primary key (category, item))
create table KEYS (actual_key Integer)
insert into keys values(0)
select * from BOOKS