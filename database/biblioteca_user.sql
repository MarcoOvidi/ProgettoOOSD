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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(25) NOT NULL,
  `password` varchar(250) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  `name` varchar(25) NOT NULL,
  `surname` varchar(25) NOT NULL,
  `registration_date` date DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email_unica` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'harold15','ba8bc6472c308799ffe7ecd93f869dfba78ace9a',1,'Thea','Dietrich','2013-01-22','cleveland.keeling@example.com'),(2,'whowell','c37ff9060f2aa2d0865a68766d23cdd2226de1b3',0,'Elvera','Wunsch','1977-06-08','orice@example.net'),(3,'brogahn','e408fa6c483066fe65146b07311af2b5409edf79',1,'Valentin','Fahey','1982-01-04','feest.don@example.net'),(4,'iterry','e23fe0c7b2e8ec53b097eb3190196a155a79444d',1,'Mikayla','Lockman','1983-11-14','bessie58@example.org'),(5,'fausto.mertz','c6180a5d06365a32c87b0b15eec050e676733e85',0,'Zackary','O\'Kon','2006-04-01','rex.corwin@example.com'),(6,'djenkins','ff97c644b7230ca95d30c1fb86446b47bfd61ff4',1,'Alayna','Nitzsche','2011-06-30','meredith.heaney@example.com'),(7,'glover.althea','be7a98fac33fac0b2ee1cad6e96ea284a95075c5',0,'Kyla','Johns','1985-05-03','jazmin03@example.com'),(8,'eldridge70','9ee1535044ee4b26358a0b79430f4a8557f81220',0,'Toney','Conn','2003-12-26','ljohns@example.net'),(9,'rnolan','0a4f0ee54f5941241af25ffb7bcb7346451a7b5d',0,'Casimer','Schneider','1993-01-22','connie86@example.net'),(10,'wisozk.queen','5b545b44c5f92382d0f12e9446ed920dce974dea',0,'Israel','Feest','1983-11-03','ettie25@example.net'),(11,'mckenzie.alfreda','b4cbc04ed32b1ff5f623c0fcf3c1944f3605babe',0,'Elfrieda','Eichmann','1989-05-19','fbayer@example.com'),(12,'aufderhar.henri','ce0abbb7d5f230fa847c67785594c0f67f7319d4',0,'Willis','Gusikowski','2001-07-13','wabshire@example.net'),(13,'walsh.abigayle','048cc6fae6f570f746339c35697c470af41c0326',1,'Johnson','Krajcik','1983-02-21','tremblay.mercedes@example.org'),(14,'d\'amore.cindy','aee5a61107a6cd41ce515041abe934c480a04d6c',1,'Dejuan','Ritchie','1993-09-10','deborah74@example.com'),(15,'xdare','1887f620e9ba90e3bdf12c6d091f71e06b2e6679',1,'Sabryna','Becker','2004-07-08','santiago.haag@example.com'),(16,'chaya97','7762f157bfd6d06af75fdb4b7246588017821da0',0,'Alfreda','Crona','2006-01-22','adah94@example.com'),(17,'pearline44','dbd3d1dfe210d26e0a7e6c25a0612c93db1d42d6',0,'Mabelle','Bradtke','2012-03-20','marlene.stoltenberg@example.org'),(18,'metz.dana','43c656afe592470a45f3b61ca118976988c5eaf6',0,'Ronny','Wunsch','1990-02-14','gonzalo90@example.org'),(19,'breanna.cronin','c2185ed6c33df6a969c0aae889f34a45c804b18f',1,'Quinn','Emard','2017-01-28','miller.anabel@example.org'),(20,'kyra.gerhold','db96cf70e75740f9d71002ee0a7ab1c7e7125cd7',1,'Lourdes','Ankunding','1983-11-28','sherwood.wilkinson@example.org'),(21,'zemlak.caleigh','fcccb72a665f7f4ecc7fc621615eda95c61cf928',0,'Elvera','Wintheiser','1986-11-25','trantow.christop@example.net'),(22,'skuhn','8811f39258c5380bce7a7431dfc930acc08a8992',0,'Myah','Reinger','2009-08-06','marcel20@example.com'),(23,'btorp','408a9085b9d89d1da4ab83425335c5bb2d72a35e',0,'Kimberly','Breitenberg','1999-05-17','effie19@example.com'),(24,'ralph.balistreri','7884d24e598b2c6bf191bdc96fbd6432b620e89e',0,'Monroe','Parker','2010-10-07','ludie66@example.com'),(25,'elsa.bergstrom','7bd4e3657ff9fbaae47b511989110abc0293df0a',0,'Vicente','Brakus','2017-06-30','crooks.lessie@example.org'),(26,'doyle.zion','ba10c50bf6663f335bb7b71d6c223043a4b10907',1,'Justine','Howell','1977-01-06','golden.reichert@example.net'),(27,'irving.will','a3155c168311959619b7a6b171e0369f8059133a',1,'Jayde','Lesch','1979-03-10','khansen@example.net'),(28,'geovanny.lind','cbaa95bca833a0e41d61707477390978c3f4dca9',0,'Orland','Miller','1977-12-01','pat.raynor@example.net'),(29,'una.wyman','0d943fe81003476ff9b2b59c002c429eb95386a2',1,'Kraig','Mante','1990-09-23','madisen.zieme@example.com'),(30,'krajcik.darian','21231090f5347b981f6e1737724937d3e5870070',1,'Mary','Miller','2014-08-21','virgie96@example.org'),(31,'arvel65','6d2bb55f566e5029d041cece2e50c3caac572ff3',0,'Gage','Cole','1989-03-04','hubert.larkin@example.net'),(32,'dustin50','6decb01c7d33e551a609330e56d61b41c25d8966',1,'Enrico','Kessler','2000-10-14','kd\'amore@example.com'),(33,'christopher.schroeder','bccec30bd7e7b99a3d5a076b9e1da598ae3ad055',1,'Virgie','Homenick','1976-02-16','marquardt.vicente@example.org'),(34,'mozell.hartmann','44f3ee8ef015b45bdfe6a8877cf0e5e75e148917',1,'Delbert','Wolf','1991-10-07','marisa08@example.com'),(35,'leonie.leannon','c9ee036e582feb9b96fcf3cc33ca569d6fb572b1',1,'Halle','Hauck','2003-10-25','jaclyn64@example.org'),(36,'delphine.ward','e6681f1b20686b1d02b9c61a1411ac1fb98db4ba',0,'Catherine','Conroy','2009-03-21','lacy96@example.com'),(37,'linda51','3eb2dadd75aac00f28e423d3e3e094f71816f216',1,'Estelle','D\'Amore','1978-07-19','melyssa.feil@example.com'),(38,'darrick40','43207a23a21e9dd2c0e5b5410bcc6915e92628eb',0,'Fredy','Hauck','1978-06-07','elian99@example.org'),(39,'bashirian.roma','609c7eefe93f3ed70c62f4f03b02e6366321b55e',1,'Emma','Ruecker','1993-12-15','katelynn.crist@example.com'),(40,'van31','521ea7f5c7fb9cae33aea4b1148760cad822a328',1,'Vicenta','Emmerich','1989-12-02','brandy.krajcik@example.net'),(41,'pnolan','8f74e565875dad8fbc834fa0211edd9539a2eb47',1,'Kenton','O\'Reilly','1982-09-17','kpowlowski@example.net'),(42,'brigitte26','e9b10067bc89a8c5e97e8cc31244980eaed355b4',0,'Prudence','Vandervort','1995-06-01','fidel.haag@example.org'),(43,'breanna58','6c60f47cb425a2453572e2366d758d407e1fa6fe',1,'Chaya','Dickens','1999-01-27','kathleen.luettgen@example.com'),(44,'kilback.valerie','0b1e9fee5871219e0dd6cf637d6de6bb6440fef5',0,'Vincenzo','Beahan','1983-02-16','ffunk@example.com'),(45,'elna80','96621d151aff717778d7613aa0d80351cb9bd780',0,'Charlotte','Shanahan','1972-07-10','whilpert@example.org'),(46,'emiliano51','7996ebe42be102010f391efcc0acdc5a00df541c',0,'Tyrell','Donnelly','2005-04-20','orn.kristoffer@example.net'),(47,'ericka.hintz','9b6aa61a88911ad5e724687de3296cc739bfaae1',0,'Howell','Gutmann','1979-05-12','uzieme@example.net'),(48,'schulist.meta','04061dc06cf5331a3b8bada78663f25d4455c364',1,'Paris','Thompson','2007-09-08','amya.berge@example.org'),(49,'erika.rice','4ceaa4b5196f52b3a9e9d645c10d37748a2debd6',0,'Ila','Simonis','2012-03-10','darien94@example.net'),(50,'reichel.letitia','dc4bff2a16cf53e31af854c8ed50a23905449bc0',0,'Kylee','Pagac','1979-02-11','shanel34@example.org'),(51,'liliana.konopelski','a3ce61852407dd5ab4ab5f007c2eec214ab776df',1,'Miracle','Huels','1973-04-18','wconsidine@example.org'),(52,'price.jamil','4243f21efe686a73a27e0f02435d3b9ba5a34386',1,'Edgar','Waters','2006-10-05','tyrel58@example.com'),(53,'prosacco.terrance','2e75cf1fca3c081fb6cce837e26dab3accff11cf',1,'Nichole','Altenwerth','1971-04-06','maggie41@example.net'),(54,'dmraz','341ee75cdae6d0f43ab7a73a1404398d4f7039bd',0,'Tressie','Steuber','1972-07-30','gwhite@example.net'),(55,'predovic.jules','6c317df828e75ff48a1047aac62b69588d8bfe45',0,'Bria','Schumm','2000-05-15','garret.breitenberg@example.org'),(56,'adelia.rohan','b40e1b3a7cdcbec055a81a0de35184b4f77bfff4',0,'Eusebio','Ullrich','2013-09-27','alan66@example.com'),(57,'emerson19','83f2d8777b31f9076000a99f6cd5d9e482c6842d',1,'Unique','Kunde','2003-07-26','gdach@example.net'),(58,'wolf.jesse','f06d1b1e5a0ff20e647090c85cb55d682afd4c6f',1,'Zander','Ratke','2002-04-20','charlotte51@example.com'),(59,'schowalter.una','7f50c5177c23b7df00e572f385a997dc414d2aa5',1,'Maureen','Bailey','1976-11-24','sean45@example.net'),(60,'ugraham','d310ef97606d2b0a3b52a809dacf53f7ca1da48f',0,'Cedrick','Dare','1981-11-15','christop21@example.com'),(61,'Username','Password',0,'okok','okokok','2027-08-18','okok'),(62,'kjkjkjkjkjkjkj','password',0,'name','surname','2027-08-18','email'),(63,'kjkjjkj','password',0,'name','surname','2027-08-18','eml');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 trigger permissions_creation after insert on user for each row 
begin 

INSERT into perm_authorization(ID_user,download,upload,editMetadata,reviewPage,
modifyTranscription,requestTranscriptionTask,reviewTranscription,
addNewProject,assignDigitalizationTask,assignTranscriptionTask,
publishDocument) value(new.ID,0,0,0,0,0,0,0,0,0,0,0);

end */;;
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

-- Dump completed on 2018-11-08 11:27:15
