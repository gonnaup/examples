drop table teacher if exists;

create table teacher
(
    id     integer primary key,
    name   varchar(30) not null,
    age    integer     not null,
    school varchar(30) not null,
    level  varchar(10) not null
)
