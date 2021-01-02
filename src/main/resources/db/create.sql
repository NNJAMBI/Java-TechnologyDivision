SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS staff (
id int PRIMARY KEY auto_increment,
description VARCHAR,
completed BOOLEAN
);