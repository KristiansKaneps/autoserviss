create table users
(
    id                number(19) primary key,
    email             varchar2(320 char) not null,
    name              varchar2(63 char)  not null,
    surname           varchar2(63 char)  not null,
    phone             varchar2(23 char)  null,
    roles             varchar2(255 char) not null,
    status            number(1)          not null,
    confirmation_code raw(36)            null,
    password          raw(60)            not null,
    updated_at        timestamp          not null,
    created_at        timestamp          not null
);

create unique index users_email_unique_index on users (lower(email));
create unique index users_confirmation_code_unique_index on users (confirmation_code);

create sequence users_seq start with 1 increment by 1;
