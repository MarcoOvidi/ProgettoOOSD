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
-- Table structure for table `document_metadata`
--

DROP TABLE IF EXISTS `document_metadata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `document_metadata` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `author` varchar(30) DEFAULT NULL,
  `description` text,
  `composition_date` date DEFAULT NULL,
  `composition_period_from` date DEFAULT NULL,
  `composition_period_to` date DEFAULT NULL,
  `preservation_state` enum('1','2','3','4','5') DEFAULT NULL,
  `ID_document` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_document` (`ID_document`),
  CONSTRAINT `metadata_document` FOREIGN KEY (`ID_document`) REFERENCES `document` (`ID`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `document_metadata`
--

LOCK TABLES `document_metadata` WRITE;
/*!40000 ALTER TABLE `document_metadata` DISABLE KEYS */;
INSERT INTO `document_metadata` VALUES (101,'Miss Liza Wuckert DDS','Eius ipsa et et. Nesciunt repudiandae doloremque labore et quidem. Aliquid velit deleniti ut labore blanditiis reiciendis voluptatem voluptatem. Qui tempora consequatur et mollitia aut iste officiis.','1975-01-02','1942-04-03','2010-11-30','5',54),(102,'Deron Yost','Voluptas pariatur dicta consectetur similique nihil. Et maxime et ipsa repudiandae. Enim porro ex ducimus quibusdam.','1972-03-02','1988-08-07','1984-11-11','5',44),(103,'Miss Eveline Bauch III','Illo nulla consectetur aut. Ut dolorem ea qui enim. Quisquam et tempore tempora quasi nobis sed. Eaque sunt facere quaerat unde est libero.','2017-02-25','2018-05-16','1924-06-10','5',55),(104,'Alexandro Jacobson','Ipsam cum cumque et ea aut enim totam repudiandae. Nihil nisi temporibus accusamus reiciendis amet. Consequatur mollitia officiis repudiandae animi praesentium totam officiis.','1979-01-09','1998-02-14','1924-03-15','4',50),(105,'Nathen Bode','Omnis ut et inventore vel sit aspernatur dolor. Ut adipisci voluptas ut nihil. Est eos et recusandae modi sunt reprehenderit laborum.','1996-03-16','1938-04-28','1956-05-20','4',33),(106,'Manuela Lehner','Qui praesentium a velit accusantium aut repudiandae ducimus. Qui qui quia quam ut.','1973-10-20','1975-02-04','1933-06-19','2',52),(108,'Elliot Baumbach','Maiores architecto molestiae reiciendis eos molestiae dolor. Sed est doloribus ut veritatis distinctio omnis voluptate. Rerum est vel sapiente vitae voluptates aut assumenda.','1977-01-06','1981-03-16','2016-01-28','3',48),(109,'Savanah Auer','Aut sapiente alias occaecati qui beatae. Maiores molestias eius sit maxime ab iure maiores. Sequi ab iure laboriosam numquam quod omnis aspernatur enim. Qui animi totam reiciendis sapiente nihil quo.','1971-01-02','1953-08-18','1919-05-20','3',74),(110,'Rex Leuschke','Totam qui deserunt quae nihil labore rerum laudantium. Perspiciatis excepturi excepturi qui qui occaecati enim eos vel. Illum vel praesentium quia doloremque totam.','1978-07-21','1921-02-20','1921-08-02','1',78),(111,'Emory Ledner','Delectus quasi molestias ut officia aperiam accusamus est quo. Placeat iusto magnam porro nobis. Accusamus ut incidunt molestias in omnis facilis.','2007-04-08','1997-09-09','1919-11-25','2',86),(112,'Eden Vandervort','Necessitatibus quidem temporibus perferendis sapiente. Tempora ut totam voluptatum et qui id corrupti. Et modi qui at voluptatibus praesentium. Tempora quo porro ex veniam earum consequatur. Praesentium eos quam consectetur molestiae at.','1980-06-05','1983-06-21','1983-08-19','5',99),(113,'Miss Alexandrea Hettinger Sr.','Fugit quo aut nihil voluptas quos quia suscipit. Dolorem est voluptatibus qui qui eos consequatur.','1978-09-10','1959-12-23','2004-03-06','5',35),(114,'Randi Lueilwitz','Excepturi fuga possimus totam possimus. Labore nostrum asperiores dolores deleniti iste omnis illo. Temporibus reprehenderit sint voluptatum optio et ullam. Qui praesentium maiores id sit natus.','1978-03-18','2015-02-21','1961-06-10','3',47),(115,'Carissa Medhurst','Laudantium distinctio nostrum corrupti voluptatum nisi dolor dolore. Ut reiciendis omnis exercitationem assumenda ipsa. Nihil magni sed ea at aut. Itaque corrupti cumque et non.','1996-10-11','2006-06-07','1949-11-29','4',96),(117,'Vincenza Lindgren III','Officia consequatur repellat consectetur. Eum soluta asperiores non voluptate dolor. Tenetur non doloremque repellat inventore et sed qui.','1990-11-21','1974-05-07','1936-08-19','5',2),(118,'Brielle Schuster','Odio voluptas iste saepe. Nisi consequuntur suscipit ullam similique ipsum eos. Quis aliquid minus aspernatur quis ab. Numquam eum consequatur temporibus perspiciatis iusto.','2001-08-02','1998-12-12','1925-07-03','5',61),(119,'Oscar Bradtke','Commodi quasi voluptatum autem necessitatibus ut dicta laudantium. Qui placeat nulla libero asperiores ex qui odit. Tempora enim eum reiciendis quas aperiam. Beatae aut ad qui aliquid est aliquam.','1995-11-12','1940-02-24','1919-06-21','1',88),(121,'Emmet Cormier','Perferendis corporis enim at quo. Pariatur repellat nesciunt eum temporibus quis sed cumque. Neque fugiat voluptatem molestias corrupti et velit. Quae molestiae dignissimos dicta magnam dolorum.','2011-11-10','1976-11-10','2014-03-12','3',66),(122,'Joana Borer','Qui consequatur sunt ex corrupti. Facere mollitia quibusdam iure tempora suscipit optio error. Ut et esse soluta quibusdam quis consequatur. Voluptas laboriosam quod ut dolore. Adipisci ipsa perferendis enim vero.','2008-04-20','1954-08-02','1980-09-11','4',14),(123,'Prof. Emmitt Frami DVM','Voluptatem nihil quod rem minima ea. Soluta dolores et fugiat culpa. Nemo voluptatem expedita et voluptate tempore. Atque in aliquam nihil.','2018-04-20','1952-06-23','2006-03-27','3',98),(124,'Ardella Smith','Voluptatem qui hic ipsa amet. At natus ut culpa eaque qui consequuntur. Nisi sunt debitis ea recusandae et laudantium enim rerum. Labore corporis sequi qui.','1972-04-12','1976-06-26','1978-09-21','2',25),(125,'Prof. Christ Hills','Consequuntur molestias et commodi enim. Itaque id nobis cupiditate voluptas. Voluptas vitae et sapiente laudantium perferendis dolore expedita et.','1982-11-10','1929-05-19','1934-04-20','4',65),(126,'Prof. Collin Oberbrunner','Ut quia dolor fugiat nemo atque illo rem. Dolorem cumque necessitatibus sunt.','1982-03-27','1984-08-21','1934-09-16','4',36),(127,'Merl Ryan II','Labore itaque nulla eligendi praesentium quaerat. Expedita quam explicabo ratione optio quae similique quia. Ratione voluptatem consequuntur ipsam ut. Quis quos natus consequatur sint quibusdam est aliquid. Voluptate eveniet voluptas quo veniam et et qui.','1978-09-20','1934-09-17','1949-09-16','2',79),(128,'Lulu Stanton I','Ducimus laboriosam dignissimos vel dolorum totam nisi ab assumenda. Quasi quo illum esse asperiores eveniet. Fuga et asperiores repudiandae et voluptatem voluptas porro omnis.','2000-10-08','1924-06-09','1940-03-05','3',87),(129,'Elias Daugherty','Labore aut maxime quia praesentium. Odio officia libero corporis eaque blanditiis nam. Qui dicta nisi inventore odit quis nihil minus. A et voluptas laudantium recusandae unde. Unde id ullam ut et.','2016-04-09','1997-04-09','2017-06-29','4',85),(130,'Verdie Little','Error labore velit ab itaque labore rerum aut. Deleniti eaque quas voluptas commodi quae ut. Et corporis aperiam fugiat nulla aut magnam iste.','2001-05-19','1977-02-28','2008-05-07','4',18),(132,'Addison Adams','Rem itaque a rerum autem. Vel odio et porro eligendi aliquid ut. Aut quaerat id voluptas aut fuga aut non enim. Aliquid enim eum optio non.','2003-01-18','1964-06-12','1984-11-11','1',38),(136,'Ms. Kali Goodwin DVM','Occaecati nam et nemo. Iste aut dolores sed omnis illo qui ex. Necessitatibus qui et voluptas sit voluptate et quia. Libero magnam rerum rerum qui quisquam voluptatibus. Voluptas qui non qui.','1977-02-13','1953-02-18','1959-12-25','5',93),(137,'Wanda Graham','Id officiis aut sed voluptates aut eos. Tenetur officiis dicta dolore odio provident dolorum incidunt similique. Tempora mollitia dignissimos rerum qui. Velit eos earum quia totam consequatur sequi.','1989-02-09','1985-02-08','1935-10-14','4',53),(138,'Peyton Goodwin','Voluptatem quos laboriosam laboriosam quidem ipsum. Sit et omnis quidem doloribus sed. Officiis odit dolorem rerum rem voluptatem et eos.','1986-04-18','1924-03-15','1994-06-04','3',41),(140,'Verner Dibbert','Qui maiores voluptatem ea numquam est aut. Non corrupti perferendis debitis laboriosam ipsam dolores voluptatem. Recusandae suscipit non voluptatibus fuga quo sint. Quia expedita optio aut quia.','1995-01-03','2006-08-09','1921-01-23','2',27),(142,'Shanel Effertz','Veritatis voluptatem unde ea et quia sapiente adipisci. Inventore sunt placeat eius ipsam ad hic. Numquam est in excepturi eveniet voluptas et.','1984-07-21','1919-08-07','2005-04-05','3',26),(145,'Dianna Rempel','Dicta corporis magni tempore soluta ipsa aperiam. Dolor corporis nesciunt maxime facilis aut est. Vel ut ut et.','2011-03-29','1919-05-23','1930-09-17','5',72),(146,'Mrs. Lilyan Tillman DVM','Aliquid iusto inventore in a distinctio et. Ut animi praesentium consequuntur eligendi voluptatem nostrum. Consequatur sint accusantium quos est dignissimos hic.','1970-08-26','2007-05-03','2004-04-15','3',21),(148,'Ms. Leonor Kovacek','Velit esse perferendis molestiae molestiae. Impedit enim molestiae labore laudantium ut quia dolores veritatis. Sint repellendus sit similique.','2006-06-03','2009-04-19','1933-07-22','4',73),(149,'Shanon Leuschke','Expedita est sapiente sapiente cupiditate in. Odio quae dolorum qui enim minus ducimus facilis. Sequi at sint consequatur tempore nobis ipsam sit dolorem. Saepe sed nemo dolorum et eius temporibus rerum.','1978-07-10','2016-12-30','1964-06-02','2',82),(150,'Lemuel Lindgren','Doloremque amet qui dolorem libero dignissimos. A aut ipsum veritatis dicta. Nihil est dolor itaque velit cum nostrum molestias. Nisi tempora ullam quidem quisquam odio.','1972-10-14','1922-07-23','2008-07-18','4',83),(154,'Prof. Pearl Adams DVM','Quia reiciendis tempore dolore nihil officiis eaque. Est rerum esse in molestiae. Et eaque esse ipsam quo voluptas. Illum qui nobis omnis quas et quia.','1985-10-03','1993-09-03','1931-11-21','4',70),(156,'Brigitte Gerhold III','Placeat unde libero blanditiis. Fugiat delectus quibusdam esse ex architecto quo. Et omnis omnis vel et quia.','2013-09-15','1959-09-25','1937-07-24','5',60),(157,'Bridie Franecki II','Et facere in et molestias autem modi. Natus molestiae cupiditate autem est quia. Quos et facilis est amet consequatur aut possimus.','1996-10-29','1934-11-01','1923-09-11','2',5),(160,'Prof. Carlee Pollich Jr.','Enim nesciunt laboriosam aut ratione perferendis natus. Voluptatem rerum soluta est placeat et cumque. Velit est repudiandae optio rem ut.','1971-11-12','1958-08-25','1926-04-10','3',89),(161,'Lupe VonRueden II','Distinctio ex fugit et. Ut fugit fuga iure consequatur ut. Voluptatibus harum commodi laboriosam possimus.','1996-01-08','1990-01-02','1971-12-09','2',67),(162,'Mrs. Britney Thompson DDS','Consequatur neque ab in facere accusamus. Cumque quia aut dolorem. Sed minus voluptas velit consequatur reiciendis quod aliquid.','1984-03-07','1968-12-17','2002-05-24','3',94),(164,'Cristian Abshire','Quidem voluptates itaque velit rem. Animi non atque rerum autem.','2017-03-25','1921-09-17','1991-07-03','3',28),(166,'Nikko Jaskolski III','Veritatis omnis illo nemo sunt provident molestiae libero. Similique harum accusamus quia ut est magni sint. Corrupti sed corporis aut aliquam non qui.','2017-02-07','1939-10-15','1967-04-05','4',13),(167,'Ethyl Dickens','Quia qui dolorem molestiae ducimus. Aut adipisci voluptas aspernatur illo praesentium incidunt. Est veritatis sit officiis tempora excepturi est quibusdam.','2017-06-07','1939-02-28','1997-03-31','5',20),(175,'Merlin Durgan V','Quis libero minus maiores quae repellat. Nihil enim quaerat et perferendis delectus. Esse enim quis qui harum quidem. Fugit voluptatibus ex aspernatur aliquid assumenda minima.','1986-08-10','1932-11-26','1976-04-24','3',24),(176,'Mr. Tito Conroy Sr.','Voluptatum tempora non aspernatur ducimus. Mollitia quis labore tempore consequatur quibusdam asperiores est. Id dolore ex quas suscipit fugit sint.','1984-01-24','2008-03-24','2001-09-19','2',57),(177,'Mr. Sam Reilly','Laboriosam impedit atque ea excepturi. Ex illum eum qui ab blanditiis et et sed. Cum quam qui maiores non.','2002-05-26','1974-05-17','1982-08-10','4',19),(179,'Mia Farrell','Nisi molestiae omnis dignissimos corrupti. Soluta impedit eligendi est dolorem veniam saepe sunt. Aliquam explicabo quos consequuntur mollitia et. Repellat occaecati odit quaerat ad magnam et.','1998-07-16','1965-07-26','1944-12-14','1',30),(180,'Dr. Santino Bergstrom','Porro sed deserunt quia et rem pariatur repudiandae non. Nihil architecto velit quidem quis architecto non architecto saepe. Adipisci et voluptate optio fuga.','1986-06-05','1923-09-22','1988-04-15','2',1),(184,'Theodore Prosacco DVM','Rerum est quis laborum consequatur asperiores. Consectetur architecto similique nihil ut distinctio. Est id repellendus facilis repellendus perspiciatis reiciendis. Molestiae est nostrum itaque itaque nesciunt consequuntur.','1985-11-14','2008-04-12','2005-02-02','1',97),(185,'Darian Sanford Sr.','Minus ea a qui quisquam cumque. Recusandae possimus dolorem provident fuga assumenda reiciendis saepe. Et aperiam vero reiciendis dolor odio sed.','1982-12-25','1941-10-14','2002-06-14','1',71),(188,'Eleazar Kuhic','Maxime minima mollitia et libero. Cumque harum dolores placeat quaerat exercitationem voluptate est quis. Est odio aperiam consequatur quis autem iste voluptatum.','1981-12-16','2015-03-12','1977-10-20','1',75),(189,'Jena Hauck','Possimus id qui a quis ut perspiciatis. Corrupti repudiandae exercitationem error. Est voluptas qui qui rerum. Voluptatem tempora optio aliquid autem.','1990-07-01','2009-10-14','1971-07-31','3',69),(190,'Judson Stark','Reprehenderit eligendi quod aut itaque est itaque. Quasi facilis quo quis corporis omnis eum repellat explicabo. Dolor neque pariatur voluptas beatae consequatur perferendis.','2015-05-22','1943-07-23','1993-04-01','1',3),(191,'Brayan Kovacek','Aliquam dicta ducimus ut dolorum vel eum. Qui explicabo quaerat vitae quia eligendi. Ab quae doloribus consectetur nobis at tenetur ad. Repudiandae voluptas nihil sapiente quo nihil ullam eum optio.','2000-07-13','1945-12-27','1950-03-13','1',64),(194,'Josie Pagac','Rerum quibusdam atque et quas. Tenetur ex illum ipsa est consequatur ratione. Aut dolorem quia voluptates. Omnis natus earum similique voluptatem ad dolorem aspernatur.','1980-12-08','1981-01-15','1950-04-24','4',90),(195,'Jerrold Frami I','Iste ea non similique sed. Similique sint nemo laborum ut rem rem quia. Dolores impedit libero modi consequuntur ut molestiae dolores. Et et deserunt iure.','1979-11-18','1935-07-16','1967-10-28','4',63),(196,'Zion Abbott','Corporis in consequatur aut voluptas eveniet impedit quia. Aut iste et ut corporis. Magnam eos quis excepturi et nisi blanditiis. Cupiditate unde iure et officiis delectus non aut.','1992-09-23','2014-04-13','1997-03-07','1',91),(199,'Gerson Hartmann','Nihil repellat nam qui tempora. Eligendi id tempore quae voluptatem. Nihil dolore ipsam sed corporis architecto veritatis. Nihil ut quidem est consequatur optio.','1991-04-14','2009-03-22','2014-10-02','5',39);
/*!40000 ALTER TABLE `document_metadata` ENABLE KEYS */;
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
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 trigger date_control before insert on document_metadata for each row 
BEGIN

IF (NEW.composition_date <> NULL OR NEW.composition_date != ' ') THEN
	IF(NEW.composition_period_from <> NULL 
		OR NEW.composition_period_from != ' ' 
        OR NEW.composition_period_to <> NULL 
        OR NEW.composition_period_to != ' ' ) THEN
			signal sqlstate '45000' set message_text = 'Errore non si può inserire una data certa insieme ad un periodo di composizione';
	END IF;
END IF;

IF ((NEW.composition_period_from <> NULL 
		OR NEW.composition_period_from != ' ') 
        AND (NEW.composition_period_to <> NULL 
        OR NEW.composition_period_to != ' ')) THEN
	IF(NEW.composition_date <> NULL OR NEW.composition_date != ' ') THEN
			signal sqlstate '45000' set message_text = 'Errore non si può inserire una data certa insieme ad un periodo di composizione';
	END IF;
END IF;

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
