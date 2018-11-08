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
-- Table structure for table `document_of_collection`
--

DROP TABLE IF EXISTS `document_of_collection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `document_of_collection` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ID_document_collection` int(10) unsigned NOT NULL,
  `ID_document` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `document_of_collection_document_collection` (`ID_document_collection`),
  KEY `document_of_collection_document` (`ID_document`),
  CONSTRAINT `document_of_collection_document` FOREIGN KEY (`ID_document`) REFERENCES `document` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `document_of_collection_document_collection` FOREIGN KEY (`ID_document_collection`) REFERENCES `document_collection` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `document_of_collection`
--

LOCK TABLES `document_of_collection` WRITE;
/*!40000 ALTER TABLE `document_of_collection` DISABLE KEYS */;
INSERT INTO `document_of_collection` VALUES (1,2,88),(2,2,63),(3,4,69),(4,3,80),(5,5,64),(6,2,30),(7,4,67),(8,5,80),(9,1,12),(10,1,18),(11,5,96),(12,2,16),(13,1,88),(14,5,24),(15,4,59),(16,1,93),(17,3,31),(18,3,24),(19,5,98),(20,1,81);
/*!40000 ALTER TABLE `document_of_collection` ENABLE KEYS */;
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
