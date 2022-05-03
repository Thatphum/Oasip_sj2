CREATE DATABASE  IF NOT EXISTS `oasip` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `oasip`;
-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: oasip
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `eventcategories`
--

DROP TABLE IF EXISTS `eventcategories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eventcategories` (
  `eventCategoryId` int NOT NULL,
  `eventCategoryName` varchar(100) NOT NULL,
  `eventCategoryDescription` text,
  `eventCategoryDuration` int NOT NULL,
  PRIMARY KEY (`eventCategoryId`),
  UNIQUE KEY `eventCategoryName_UNIQUE` (`eventCategoryName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eventcategories`
--

LOCK TABLES `eventcategories` WRITE;
/*!40000 ALTER TABLE `eventcategories` DISABLE KEYS */;
INSERT INTO `eventcategories` VALUES (1,'Project Management Clinic','ตารางนัดหมายนี้ใช้สำหรับนัดหมาย project management clinic ในวิชา INT221 integrated project I ให้นักศึกษาเตรียมเอกสารที่เกี่ยวข้องเพื่อแสดงระหว่างขอคำปรึกษา',30),(2,'Devops/Infra Clinic','Use this event category for DevOps/Infra clinic.',20),(3,'Database Clinic','ตารางนัดหมายนี้ใช้สำหรับนัดหมาย database clinic ใน  วิชา INT221 integrated project I',15),(4,'Client-side Clinic','ตารางนัดหมายนี้ใช้สำหรับนัดหมาย client-side clinic ใน  วิชา INT221 integrated project I',30),(5,'Server-side clinic','',30);
/*!40000 ALTER TABLE `eventcategories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `events`
--

DROP TABLE IF EXISTS `events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `events` (
  `eventId` int NOT NULL,
  `bookingName` varchar(100) NOT NULL,
  `bookingEmail` varchar(255) NOT NULL,
  `eventStartTime` datetime NOT NULL,
  `eventDuration` int NOT NULL,
  `eventNotes` text,
  `EventCategory_id` int NOT NULL,
  PRIMARY KEY (`eventId`),
  KEY `fk_Event_EventCategory_idx` (`EventCategory_id`),
  CONSTRAINT `fk_Events_EventCategories` FOREIGN KEY (`EventCategory_id`) REFERENCES `eventcategories` (`eventCategoryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events`
--

LOCK TABLES `events` WRITE;
/*!40000 ALTER TABLE `events` DISABLE KEYS */;
INSERT INTO `events` VALUES (1,'Somchai Jaidee (OR-7)','somchai.jai@mail.kmutt.ac.th','2022-05-23 13:30:00',30,'',2),(2,'Somchai Rakdee (SJ-3)','somsri.jai@mail.kmutt.ac.th','2022-05-27 09:30:00',30,'',1),(3,'สมเกียรติ ขยันเรียน กลุ่ม TT-4','somkiat.kay@kmutt.ac.th','2022-05-23 16:30:00',30,'',3);
/*!40000 ALTER TABLE `events` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-30 23:42:10
CREATE USER 'dev'@'%' IDENTIFIED WITH mysql_native_password BY 'Oasip_sj2';
-- create user 'dev'@'%' identified with mysql_native_password by 'Oasip_sj2';
-- grant all privileges on . to 'dev'@'%';
GRANT ALL PRIVILEGES ON *.* TO 'dev'@'%';
flush privileges;







