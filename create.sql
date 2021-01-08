CREATE DATABASE technologydivision;
\c technologydivision;
CREATE TABLE staff (id SERIAL PRIMARY KEY, description VARCHAR, departmentid INTEGER, staff_id VARCHAR UNIQUE);
CREATE TABLE roles (id SERIAL PRIMARY KEY, staff_roles VARCHAR);
CREATE TABLE departments (id SERIAL PRIMARY KEY, name VARCHAR);
CREATE DATABASE technologydivision_test WITH TEMPLATE technologydivision;