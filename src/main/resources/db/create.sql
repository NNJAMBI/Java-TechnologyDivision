SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS staff (
id int PRIMARY KEY auto_increment,
description VARCHAR,
);

CREATE TABLE IF NOT EXISTS departments (
id int PRIMARY KEY auto_increment,
name VARCHAR
);