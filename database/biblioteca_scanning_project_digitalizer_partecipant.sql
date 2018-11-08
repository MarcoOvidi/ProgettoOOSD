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
-- Table structure for table `scanning_project_digitalizer_partecipant`
--

DROP TABLE IF EXISTS `scanning_project_digitalizer_partecipant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scanning_project_digitalizer_partecipant` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ID_scanning_project` int(10) unsigned NOT NULL,
  `ID_digitalizer_user` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_scanning_project` (`ID_scanning_project`,`ID_digitalizer_user`),
  KEY `scanning_project_digitalizer_partecipant_user` (`ID_digitalizer_user`),
  CONSTRAINT `scanning_project_digitalizer_partecipant_scanning_project` FOREIGN KEY (`ID_scanning_project`) REFERENCES `scanning_project` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `scanning_project_digitalizer_partecipant_user` FOREIGN KEY (`ID_digitalizer_user`) REFERENCES `user` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scanning_project_digitalizer_partecipant`
--

LOCK TABLES `scanning_project_digitalizer_partecipant` WRITE;
/*!40000 ALTER TABLE `scanning_project_digitalizer_partecipant` DISABLE KEYS */;
INSERT INTO `scanning_project_digitalizer_partecipant` VALUES (4,29,36),(19,29,60),(20,34,40),(17,35,25),(15,35,29),(16,36,39),(6,37,40),(3,37,54),(8,39,53),(14,41,26),(12,44,15),(5,44,24),(13,45,13),(1,45,48),(2,48,14),(10,51,49),(9,52,41),(11,54,41),(18,55,22),(7,55,40);
/*!40000 ALTER TABLE `scanning_project_digitalizer_partecipant` ENABLE KEYS */;
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
