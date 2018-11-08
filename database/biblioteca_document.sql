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
-- Table structure for table `document`
--

DROP TABLE IF EXISTS `document`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `document` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(70) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `title` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `document`
--

LOCK TABLES `document` WRITE;
/*!40000 ALTER TABLE `document` DISABLE KEYS */;
INSERT INTO `document` VALUES (70,'a'),(42,'accusamus'),(3,'accusantium'),(20,'adipisci'),(89,'alias'),(92,'amet'),(75,'animi'),(107,'Antonio'),(2,'aperiam'),(9,'architecto'),(27,'assumenda'),(100,'at'),(54,'autem'),(84,'beatae'),(106,'C'),(104,'Ccarlooooo'),(101,'ciaone'),(14,'commodi'),(17,'consectetur'),(7,'consequatur'),(50,'corporis'),(61,'culpa'),(25,'cumque'),(93,'cupiditate'),(49,'delectus'),(81,'deleniti'),(43,'dignissimos'),(18,'distinctio'),(78,'dolor'),(55,'dolore'),(21,'dolorem'),(68,'dolores'),(30,'doloribus'),(82,'dolorum'),(39,'ducimus'),(29,'ea'),(23,'Earum'),(6,'eligendi'),(96,'enim'),(99,'eos'),(64,'error'),(5,'est'),(1,'et'),(79,'excepturi'),(62,'exercitationem'),(12,'explicabo'),(8,'facilis'),(52,'fuga'),(44,'harum'),(28,'id'),(72,'illo'),(86,'in'),(85,'inventore'),(76,'ipsam'),(80,'ipsum'),(88,'magni'),(40,'minima'),(16,'modi'),(53,'molestiae'),(51,'molestias'),(41,'nam'),(48,'nihil'),(94,'nobis'),(60,'non'),(13,'nostrum'),(58,'odio'),(24,'officia'),(69,'officiis'),(83,'omnis'),(47,'pariatur'),(10,'perferendis'),(45,'placeat'),(90,'porro'),(91,'possimus'),(102,'PROVIAMOOOOO'),(77,'provident'),(103,'PROVLLALALALALOOO'),(33,'quam'),(98,'qui'),(36,'quia'),(56,'quibusdam'),(22,'quidem'),(19,'quisquam'),(11,'quo'),(73,'quod'),(95,'quos'),(66,'recusandae'),(67,'repellendus'),(65,'repudiandae'),(71,'rerum'),(63,'saepe'),(97,'sequi'),(26,'similique'),(59,'sint'),(4,'sit'),(46,'soluta'),(31,'sunt'),(87,'tempora'),(32,'ullam'),(74,'unde'),(37,'ut'),(57,'vel'),(15,'veniam'),(34,'vitae'),(35,'voluptas'),(38,'voluptatem');
/*!40000 ALTER TABLE `document` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `biblioteca`.`document_BEFORE_INSERT` BEFORE INSERT ON `document` FOR EACH ROW
BEGIN

DECLARE up varchar(100);

SET up = UPPER(SUBSTRING(NEW.title,1,1));
SET NEW.title = CONCAT(up,SUBSTRING(NEW.title,2,LENGTH(NEW.title)-1));


END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `biblioteca`.`document_BEFORE_UPDATE` BEFORE UPDATE ON `document` FOR EACH ROW
BEGIN


DECLARE up varchar(100);

SET up = UPPER(SUBSTRING(NEW.title,1,1));
SET NEW.title = CONCAT(up,SUBSTRING(NEW.title,2,LENGTH(NEW.title)-1));


END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-08 11:27:16
