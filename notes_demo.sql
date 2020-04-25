DROP DATABASE IF EXISTS NOTES_DEMO;
CREATE DATABASE NOTES_DEMO;
USE NOTES_DEMO;

CREATE TABLE users (
   id INT AUTO_INCREMENT NOT NULL,
   email VARCHAR(255) NOT NULL,
   password VARCHAR(255) NOT NULL,
   create_time BIGINT,
   last_update_time  BIGINT,
   CONSTRAINT employees_pk PRIMARY KEY (id)
);

CREATE TABLE notes (
   id INT AUTO_INCREMENT NOT NULL,
   user_id BIGINT NOT NULL,
   title VARCHAR(50) NOT NULL,
   note VARCHAR(1000) NOT NULL,
   create_time BIGINT,
   last_update_time BIGINT,
   CONSTRAINT employees_pk PRIMARY KEY (id)
);

INSERT INTO users(email, password, create_time, last_update_time) VALUES('xyz@123.com', 'password', 1500000000, 1500000000);
INSERT INTO users(email, password, create_time, last_update_time) VALUES('xyzz@1234.com', 'password2', 1500000000, 1500000000);


