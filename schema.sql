create database web default charset='utf8';
create user web@'%' identified by 'java';
grant all on web.* to web;
use web;
create table member(
	code serial,
	name varchar(200),
	email varchar(200) unique,
	password varchar(200)
);

insert into member(name, email, password)
values('webmaster', 'webmaster@email.com', sha2('password', 512) );

create table topic(
	code serial,
	title varchar(1000),
	detail varchar(8000)
);
alter table topic add member bigint;

insert into topic(title, detail)
values('Welcome', 'Welcome to the first topic');
update topic set member=1 where code=1;
