create table contact_us_requests
(
    id         number(19) primary key,
    name       varchar2(63 char)   not null,
    email      varchar2(320 char)  not null,
    phone      varchar2(23 char)   null,
    subject    varchar2(63 char)   not null,
    content    varchar2(511 char)  not null,
    replied    number(1) default 0 not null,
    created_at timestamp           not null
);
create index contact_us_requests_email on contact_us_requests (email);
create index contact_us_requests_created_at on contact_us_requests (created_at);

create sequence contact_us_requests_seq start with 1 increment by 1;
