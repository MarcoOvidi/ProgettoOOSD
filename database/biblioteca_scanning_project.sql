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
-- Table structure for table `scanning_project`
--

DROP TABLE IF EXISTS `scanning_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scanning_project` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ID_coordinator` int(10) unsigned DEFAULT NULL,
  `ID_document` int(10) unsigned NOT NULL,
  `date` date DEFAULT NULL,
  `scanning_complete` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_document` (`ID_document`),
  KEY `scanning_project_coordinator` (`ID_coordinator`),
  CONSTRAINT `scanning_project_coordinator` FOREIGN KEY (`ID_coordinator`) REFERENCES `user` (`ID`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `scanning_project_document` FOREIGN KEY (`ID_document`) REFERENCES `document` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scanning_project`
--

LOCK TABLES `scanning_project` WRITE;
/*!40000 ALTER TABLE `scanning_project` DISABLE KEYS */;
INSERT INTO `scanning_project` VALUES (29,1,74,'1990-11-04',1),(30,12,67,'2012-01-07',0),(31,3,57,'2004-03-16',0),(32,53,11,'1976-02-02',1),(34,12,18,'1978-09-16',1),(35,28,76,'2011-12-10',0),(36,16,61,'1970-06-04',1),(37,19,20,'2001-03-28',1),(38,50,75,'1976-11-24',0),(39,51,79,'1970-03-23',0),(41,32,80,'1995-04-28',0),(42,52,68,'2014-10-10',1),(43,13,81,'2000-05-27',1),(44,7,40,'1981-09-03',0),(45,30,50,'1984-05-27',0),(48,50,86,'2014-11-18',1),(49,57,82,'1983-07-22',1),(51,35,19,'1970-05-31',1),(52,49,66,'1997-03-12',1),(54,49,99,'1988-12-18',1),(55,15,36,'1984-10-01',0),(56,34,27,'1997-09-23',0);
/*!40000 ALTER TABLE `scanning_project` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-08 11:27:16
