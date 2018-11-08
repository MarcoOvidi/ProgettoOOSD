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
-- Table structure for table `personal_collection`
--

DROP TABLE IF EXISTS `personal_collection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personal_collection` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ID_user` int(10) unsigned NOT NULL,
  `ID_document` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `personal_collection_user` (`ID_user`),
  KEY `personal_Collection_document` (`ID_document`),
  CONSTRAINT `personal_Collection_document` FOREIGN KEY (`ID_document`) REFERENCES `document` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `personal_collection_user` FOREIGN KEY (`ID_user`) REFERENCES `user` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personal_collection`
--

LOCK TABLES `personal_collection` WRITE;
/*!40000 ALTER TABLE `personal_collection` DISABLE KEYS */;
INSERT INTO `personal_collection` VALUES (1,35,64),(2,20,58),(3,13,69),(4,31,65),(5,22,36),(6,43,94),(7,32,61),(8,4,89),(9,4,56),(10,8,21),(11,48,68),(12,37,44),(13,4,38),(14,30,64),(15,11,35),(16,20,21),(17,15,12),(18,50,88),(19,9,81),(20,44,65),(21,16,62),(22,35,45),(23,36,9),(24,45,74),(25,18,64),(26,10,46),(27,8,56),(28,40,82),(29,2,44),(30,7,56),(31,50,1),(32,24,76),(33,3,54),(34,11,93),(35,25,15),(36,35,52),(37,50,15),(38,58,100),(39,6,29),(40,52,88),(41,13,68),(42,45,22),(43,36,24),(44,31,16),(45,17,49);
/*!40000 ALTER TABLE `personal_collection` ENABLE KEYS */;
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
