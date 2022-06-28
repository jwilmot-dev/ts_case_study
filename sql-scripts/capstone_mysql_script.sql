DROP DATABASE  IF EXISTS `capstone_db`;

CREATE DATABASE  IF NOT EXISTS `capstone_db`;
USE `capstone_db`;

--
-- Table `user`
--

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` char(80) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `date_of_birth` date NOT NULL,
  `email` varchar(50) NOT NULL,
  `phone` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Add sample data for table `user`
--
-- NOTE: The passwords are encrypted using BCrypt
--
-- Default password for admin is: admin123
-- Default password for manager is: manager123
-- Default password for user is: user123

--

INSERT INTO `user` (username,password,first_name,last_name,date_of_birth,email,phone)
VALUES 
('admin','$2a$10$mQaLhR/7RqPvbv3zJYjUOeTBm62f5UsNTCcBX6WgOuA5di8vULr7m','admin','capstone','2000-1-01','admin@capstone.com','555-555-5555'),
('manager','$2a$10$ahyRoDeClgdvtZ1gQNbe5.x/aTsdXgE8Jt7/yzMsDTHBPwvd648Qi','manager','capstone','2000-1-01','onsite@capstone.com','555-555-5555'),
('user1','$2a$10$zLdF5LaXyzs6xt8gleP4Tetc5T8MiVfkKlyAbHBEZFXHPWZPj6v8i','user','capstone','2000-1-01','user@capstone.com','555-555-5555');


--
-- Table `role`
--

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Add sample data for table `role`
--

INSERT INTO `role` (name)
VALUES 
('ROLE_USER'),('ROLE_MANAGER'),('ROLE_ADMIN');

--
-- Table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  
  PRIMARY KEY (`user_id`,`role_id`),
  
  KEY `FK_ROLE_idx` (`role_id`),
  
  CONSTRAINT `FK_USER_01` FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_ROLE` FOREIGN KEY (`role_id`) 
  REFERENCES `role` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



--
-- Dumping data for table `users_roles`
--

INSERT INTO `users_roles` (user_id,role_id)
VALUES 
(1, 3),
(2, 2),
(3, 1);


--
-- Table `location`
--

DROP TABLE IF EXISTS `location`;

CREATE TABLE `location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `location_desc` varchar(255) NOT NULL,
  `street_address` varchar(80) NOT NULL,
  `city` varchar(50) NOT NULL,
  `state` varchar(50) NOT NULL,
  `zip` varchar(24) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Add sample data for table `location`
--

INSERT INTO `location` (location_desc,street_address,city,state,zip)
VALUES 
('Gateway Shopping Center - North Parking Lot','123 Main St NE','Minneapolis','MN','55401'),
('Glory Lutheran Church','1701 Roosevelt Rd','Minneapolis','MN','55413'),
('Johnson Elementary School','4122 Spring Ave','Minneapolis','MN','55404');


--
-- Table `appointment_slot`
--

DROP TABLE IF EXISTS `appointment_slot`;

CREATE TABLE `appointment_slot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `location_id` int(11) NOT NULL,
  `appointment_date` date NOT NULL,
  `appointment_time` time NOT NULL,
  `patient_id` int(11),
  `sample_id` int(11),
  PRIMARY KEY (`id`),
  
  CONSTRAINT `FK_LOCATION_01` FOREIGN KEY (`location_id`) 
  REFERENCES `location` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_PATIENT_01` FOREIGN KEY (`patient_id`) 
  REFERENCES `user` (`id`) 
  ON DELETE SET NULL ON UPDATE NO ACTION
  
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;
--
-- Add sample data for table `location`
--

INSERT INTO `appointment_slot` (location_id,appointment_date,appointment_time)
VALUES 
(1,'2022-7-01','10:00'),
(1,'2022-7-01','10:30'),
(1,'2022-7-01','11:00'),
(1,'2022-7-01','11:30'),
(1,'2022-7-01','12:00'),
(1,'2022-7-01','12:30'),
(1,'2022-7-01','13:00'),
(1,'2022-7-01','13:30'),
(1,'2022-7-01','14:00'),
(1,'2022-7-01','14:30'),
(1,'2022-7-01','15:00'),
(1,'2022-7-01','15:30'),
(1,'2022-7-01','16:00'),
(1,'2022-7-01','16:30'),

(2,'2022-7-07','10:00'),
(2,'2022-7-07','10:30'),
(2,'2022-7-07','11:00'),
(2,'2022-7-07','11:30'),
(2,'2022-7-07','12:00'),
(2,'2022-7-07','12:30'),
(2,'2022-7-07','13:00'),
(2,'2022-7-07','13:30'),
(2,'2022-7-07','14:00'),
(2,'2022-7-07','14:30'),
(2,'2022-7-07','15:00'),
(2,'2022-7-07','15:30'),
(2,'2022-7-07','16:00'),
(2,'2022-7-07','16:30'),

(3,'2022-7-08','10:00'),
(3,'2022-7-08','10:30'),
(3,'2022-7-08','11:00'),
(3,'2022-7-08','11:30'),
(3,'2022-7-08','12:00'),
(3,'2022-7-08','12:30'),
(3,'2022-7-08','13:00'),
(3,'2022-7-08','13:30'),
(3,'2022-7-08','14:00'),
(3,'2022-7-08','14:30'),
(3,'2022-7-08','15:00'),
(3,'2022-7-08','15:30'),
(3,'2022-7-08','16:00'),
(3,'2022-7-08','16:30'),

(1,'2022-7-011','10:00'),
(1,'2022-7-011','10:30'),
(1,'2022-7-011','11:00'),
(1,'2022-7-011','11:30'),
(1,'2022-7-011','12:00'),
(1,'2022-7-011','12:30'),
(1,'2022-7-011','13:00'),
(1,'2022-7-011','13:30'),
(1,'2022-7-011','14:00'),
(1,'2022-7-011','14:30'),
(1,'2022-7-011','15:00'),
(1,'2022-7-011','15:30'),
(1,'2022-7-011','16:00'),
(1,'2022-7-011','16:30'),

(2,'2022-7-17','10:00'),
(2,'2022-7-17','10:30'),
(2,'2022-7-17','11:00'),
(2,'2022-7-17','11:30'),
(2,'2022-7-17','12:00'),
(2,'2022-7-17','12:30'),
(2,'2022-7-17','13:00'),
(2,'2022-7-17','13:30'),
(2,'2022-7-17','14:00'),
(2,'2022-7-17','14:30'),
(2,'2022-7-17','15:00'),
(2,'2022-7-17','15:30'),
(2,'2022-7-17','16:00'),
(2,'2022-7-17','16:30'),

(3,'2022-7-28','10:00'),
(3,'2022-7-28','10:30'),
(3,'2022-7-28','11:00'),
(3,'2022-7-28','11:30'),
(3,'2022-7-28','12:00'),
(3,'2022-7-28','12:30'),
(3,'2022-7-28','13:00'),
(3,'2022-7-28','13:30'),
(3,'2022-7-28','14:00'),
(3,'2022-7-28','14:30'),
(3,'2022-7-28','15:00'),
(3,'2022-7-28','15:30'),
(3,'2022-7-28','16:00'),
(3,'2022-7-28','16:30'),

(3,'2022-4-16','12:30');

UPDATE `capstone_db`.`appointment_slot` SET `patient_id` = '3' WHERE (`id` = '85');
UPDATE `capstone_db`.`appointment_slot` SET `sample_id` = '10437312' WHERE (`id` = '85');