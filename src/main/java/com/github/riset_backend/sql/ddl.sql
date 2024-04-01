CREATE DATABASE `riset`;

USE riset;

CREATE TABLE `employee` (
    `employee` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `company_no` INT,
    `grade_no` INT,
    `department_no` INT,
    `position_no` INT,
    `employee_id` VARCHAR(50) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `name` VARCHAR(100) NOT NULL,
    `tel_no` VARCHAR(20),
    `address` VARCHAR(255),
    `zip_code` VARCHAR(10),
    `birth` DATE,
    `jab_title` VARCHAR(50)
);

CREATE TABLE `company` (
    `company_no` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `parent_company_no` INT,
    `company` VARCHAR(100) NOT NULL,
    `company_addr` VARChAR(255) NOT NULL,
    `latitude` DECIMAL(9, 6) NOT NULL,
    `longitude` DECIMAL(9, 6) NOT NULL,
    `zip_code` VARCHAR(10),
    `company_tel_no` VARCHAR(20)
);

CREATE TABLE `department` (
    `department_no` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `company_no` INT,
    `department_name` VARCHAR(50)
);

CREATE TABLE `schedule` (
    `schedule_no` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `employee_no` INT,
    `company_no` INT,
	`start_dt` DATETIME,
    `end_dt` DATETIME
);

CREATE TABLE `leave` (
    `leave_no` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `employee_no` INT,
	`start_dt` DATETIME,
    `end_dt` DATETIME,
    `type` VARCHAR(20),
    `status` INT
);

CREATE TABLE `position` (
    `position_no` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`position_name` VARCHAR(20),
    `salary` INT
);

CREATE TABLE `job_grade` (
    `grade_no` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`grade_name` VARCHAR(20)
);

CREATE TABLE `commute` (
    `commute_no` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`employee_no` INT,
    `start_dt` DATETIME,
    `end_dt` DATETIME,
    `status` int,
    `work_house` DECIMAL(5, 2)
);

CREATE TABLE `menu` (
    `menu_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`file_id` INT,
    `menu_name` VARCHAR(100),
    `parent_id` INT,
    `depth` INT,
    `menu_order` INT,
    `menu_url` VARCHAR(255)
);

CREATE TABLE `file` (
    `file_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`file_name` VARCHAR(255),
    `file_path` VARCHAR(512),
    `file_size` BIGINT,
    `file_type` VARCHAR(50)
);

CREATE TABLE `board` (
    `board_no` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`employee_no` INT,
    `tile` VARCHAR(255),
    `content` TEXT
);

CREATE TABLE `board_file` (
    `board_file_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`board_no` INT,
    `file_id` INT
);

CREATE TABLE `reply` (
    `reply_no` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`board_no` INT,
    `employee_no` INT,
    `content` TEXT
);

CREATE TABLE `favorite` (
    `favorite_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`board_no` INT,
    `employee_no` INT,
    `index_number` INT
);

CREATE TABLE `total_leave` (
    `work_year` INT AUTO_INCREMENT PRIMARY KEY,
    `total_leave` INT
);