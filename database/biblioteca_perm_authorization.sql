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
-- Table structure for table `perm_authorization`
--

DROP TABLE IF EXISTS `perm_authorization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `perm_authorization` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ID_user` int(10) unsigned NOT NULL,
  `download` tinyint(1) NOT NULL DEFAULT '0',
  `upload` tinyint(1) NOT NULL DEFAULT '0',
  `editMetadata` tinyint(1) NOT NULL DEFAULT '0',
  `reviewPage` tinyint(1) NOT NULL DEFAULT '0',
  `modifyTranscription` tinyint(1) NOT NULL DEFAULT '0',
  `requestTranscriptionTask` tinyint(1) NOT NULL DEFAULT '0',
  `reviewTranscription` tinyint(1) NOT NULL DEFAULT '0',
  `addNewProject` tinyint(1) NOT NULL DEFAULT '0',
  `assignDigitalizationTask` tinyint(1) NOT NULL DEFAULT '0',
  `assignTranscriptionTask` tinyint(1) NOT NULL DEFAULT '0',
  `publishDocument` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `perm_authorization_user` (`ID_user`),
  CONSTRAINT `perm_authorization_user` FOREIGN KEY (`ID_user`) REFERENCES `user` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perm_authorization`
--

LOCK TABLES `perm_authorization` WRITE;
/*!40000 ALTER TABLE `perm_authorization` DISABLE KEYS */;
INSERT INTO `perm_authorization` VALUES (1,1,0,0,0,0,0,0,0,0,0,0,0),(2,2,0,0,0,0,0,0,0,0,0,0,0),(3,3,0,0,0,0,0,0,0,0,0,0,0),(4,4,0,0,0,0,0,0,0,0,0,0,0),(5,5,0,0,0,0,0,0,0,0,0,0,0),(6,6,0,0,0,0,0,0,0,0,0,0,0),(7,7,0,0,0,0,0,0,0,0,0,0,0),(8,8,0,0,0,0,0,0,0,0,0,0,0),(9,9,0,0,0,0,0,0,0,0,0,0,0),(10,10,0,0,0,0,0,0,0,0,0,0,0),(11,11,0,0,0,0,0,0,0,0,0,0,0),(12,12,0,0,0,0,0,0,0,0,0,0,0),(13,13,0,0,0,0,0,0,0,0,0,0,0),(14,14,0,0,0,0,0,0,0,0,0,0,0),(15,15,0,0,0,0,0,0,0,0,0,0,0),(16,16,0,0,0,0,0,0,0,0,0,0,0),(17,17,0,0,0,0,0,0,0,0,0,0,0),(18,18,0,0,0,0,0,0,0,0,0,0,0),(19,19,0,0,0,0,0,0,0,0,0,0,0),(20,20,0,0,0,0,0,0,0,0,0,0,0),(21,21,0,0,0,0,0,0,0,0,0,0,0),(22,22,0,0,0,0,0,0,0,0,0,0,0),(23,23,0,0,0,0,0,0,0,0,0,0,0),(24,24,0,0,0,0,0,0,0,0,0,0,0),(25,25,0,0,0,0,0,0,0,0,0,0,0),(26,26,0,0,0,0,0,0,0,0,0,0,0),(27,27,0,0,0,0,0,0,0,0,0,0,0),(28,28,0,0,0,0,0,0,0,0,0,0,0),(29,29,0,0,0,0,0,0,0,0,0,0,0),(30,30,0,0,0,0,0,0,0,0,0,0,0),(31,31,0,0,0,0,0,0,0,0,0,0,0),(32,32,0,0,0,0,0,0,0,0,0,0,0),(33,33,0,0,0,0,0,0,0,0,0,0,0),(34,34,0,0,0,0,0,0,0,0,0,0,0),(35,35,0,0,0,0,0,0,0,0,0,0,0),(36,36,0,0,0,0,0,0,0,0,0,0,0),(37,37,0,0,0,0,0,0,0,0,0,0,0),(38,38,0,0,0,0,0,0,0,0,0,0,0),(39,39,0,0,0,0,0,0,0,0,0,0,0),(40,40,0,0,0,0,0,0,0,0,0,0,0),(41,41,0,0,0,0,0,0,0,0,0,0,0),(42,42,0,0,0,0,0,0,0,0,0,0,0),(43,43,0,0,0,0,0,0,0,0,0,0,0),(44,44,0,0,0,0,0,0,0,0,0,0,0),(45,45,0,0,0,0,0,0,0,0,0,0,0),(46,46,0,0,0,0,0,0,0,0,0,0,0),(47,47,0,0,0,0,0,0,0,0,0,0,0),(48,48,0,0,0,0,0,0,0,0,0,0,0),(49,49,0,0,0,0,0,0,0,0,0,0,0),(50,50,0,0,0,0,0,0,0,0,0,0,0),(51,51,0,0,0,0,0,0,0,0,0,0,0),(52,52,0,0,0,0,0,0,0,0,0,0,0),(53,53,0,0,0,0,0,0,0,0,0,0,0),(54,54,0,0,0,0,0,0,0,0,0,0,0),(55,55,0,0,0,0,0,0,0,0,0,0,0),(56,56,0,0,0,0,0,0,0,0,0,0,0),(57,57,0,0,0,0,0,0,0,0,0,0,0),(58,58,0,0,0,0,0,0,0,0,0,0,0),(59,59,0,0,0,0,0,0,0,0,0,0,0),(60,60,1,1,0,0,0,0,0,0,0,0,0),(61,61,1,1,1,1,1,1,1,1,1,1,1),(62,62,0,0,0,0,0,0,0,0,0,0,0),(63,63,0,0,0,0,0,0,0,0,0,0,0);
/*!40000 ALTER TABLE `perm_authorization` ENABLE KEYS */;
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
