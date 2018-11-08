-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: localhost    Database: biblioteca
-- ------------------------------------------------------
-- Server version	5.7.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `transcription_project`
--

DROP TABLE IF EXISTS `transcription_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transcription_project` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ID_coordinator` int(10) unsigned DEFAULT NULL,
  `ID_document` int(10) unsigned NOT NULL,
  `date` date DEFAULT NULL,
  `transcription_complete` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_document` (`ID_document`),
  KEY `transcription_project_coordinator` (`ID_coordinator`),
  CONSTRAINT `transcription_project_coordinator` FOREIGN KEY (`ID_coordinator`) REFERENCES `user` (`ID`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `transcription_project_document` FOREIGN KEY (`ID_document`) REFERENCES `document` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transcription_project`
--

LOCK TABLES `transcription_project` WRITE;
/*!40000 ALTER TABLE `transcription_project` DISABLE KEYS */;
INSERT INTO `transcription_project` VALUES (1,1,2,'2018-01-01',1),(31,24,67,'1983-08-23',1),(32,47,50,'1979-03-05',0),(33,25,77,'1989-11-01',1),(34,19,80,'1995-07-10',0),(35,36,12,'2016-09-28',1),(36,10,16,'2000-12-22',0),(37,3,32,'1976-07-29',1),(38,19,37,'1980-11-30',0),(39,9,74,'1970-11-25',1),(40,7,53,'2002-02-23',1),(41,22,23,'2018-03-21',1),(42,42,15,'2016-02-28',1),(43,28,26,'2010-01-22',1),(44,40,87,'1995-04-02',0),(45,16,79,'2015-01-04',1),(46,5,78,'1975-03-26',1),(47,23,1,'1990-02-08',1),(48,14,95,'1990-11-02',1),(49,39,65,'1996-05-09',0),(50,38,49,'1975-05-06',1),(51,38,94,'1995-03-02',1),(52,32,81,'2017-12-31',0),(54,9,43,'1971-09-05',0),(55,38,30,'2017-01-18',0),(56,48,18,'2009-07-09',0),(58,16,8,'2008-11-20',1),(60,48,35,'1973-12-30',1);
/*!40000 ALTER TABLE `transcription_project` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-08 11:27:15
