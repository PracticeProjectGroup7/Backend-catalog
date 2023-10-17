CREATE TABLE users (
    is_active bit not null,
    created_at datetime(6) not null,
    date_of_birth datetime(6) not null,
    modified_at datetime(6),
    id binary(16) not null,
    address varchar(255) not null,
    email varchar(255) not null,
    gender VARCHAR(10) not null,
    ic_document varchar(255) not null,
    name varchar(255) not null,
    password varchar(255) not null,
    phone_number varchar(255) not null,
    type VARCHAR(20) not null,
    primary key (id)
);