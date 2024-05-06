CREATE DATABASE wiss_db;
USE wiss_db;
CREATE USER dbadmin IDENTIFIED BY "secretpw";
GRANT ALL ON wiss_db.* TO dbadmin;
