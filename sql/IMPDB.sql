-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: imatchprofile
-- ------------------------------------------------------
-- Server version	5.7.14

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
-- Table structure for table `applies`
--

DROP TABLE IF EXISTS `applies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `applies` (
  `applies_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `candidate_id` int(10) unsigned NOT NULL,
  `job_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`applies_id`),
  KEY `fk_applies_candidate_idx` (`candidate_id`),
  KEY `fk_applies_job_idx` (`job_id`),
  CONSTRAINT `fk_applies_candidate` FOREIGN KEY (`candidate_id`) REFERENCES `candidate` (`candidate_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_applies_job` FOREIGN KEY (`job_id`) REFERENCES `job` (`job_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `applies`
--

LOCK TABLES `applies` WRITE;
/*!40000 ALTER TABLE `applies` DISABLE KEYS */;
/*!40000 ALTER TABLE `applies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `candidate`
--

DROP TABLE IF EXISTS `candidate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `candidate` (
  `candidate_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `description` varchar(300) NOT NULL,
  `company` varchar(45) NOT NULL,
  `visibility` tinyint(4) NOT NULL DEFAULT '1',
  `user_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`candidate_id`),
  KEY `fk_candidate_user_idx` (`user_id`),
  CONSTRAINT `fk_candidate_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidate`
--

LOCK TABLES `candidate` WRITE;
/*!40000 ALTER TABLE `candidate` DISABLE KEYS */;
INSERT INTO `candidate` VALUES (19,'','','',1,26),(20,'','','',1,27),(21,'','','',1,28),(22,'','','',1,29),(23,'','','',1,30),(24,'','','',1,31);
/*!40000 ALTER TABLE `candidate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `candidatereports`
--

DROP TABLE IF EXISTS `candidatereports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `candidatereports` (
  `candidatereports_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `canditate_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`candidatereports_id`),
  KEY `fk_candidateReports_user_idx` (`user_id`),
  KEY `fk_candidateReports_candidate_idx` (`canditate_id`),
  CONSTRAINT `fk_candidateReports_candidate` FOREIGN KEY (`canditate_id`) REFERENCES `candidate` (`candidate_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_candidateReports_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidatereports`
--

LOCK TABLES `candidatereports` WRITE;
/*!40000 ALTER TABLE `candidatereports` DISABLE KEYS */;
/*!40000 ALTER TABLE `candidatereports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chatroom`
--

DROP TABLE IF EXISTS `chatroom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chatroom` (
  `chatroom_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `recruiter_id` int(10) unsigned NOT NULL,
  `candidate_id` int(10) unsigned NOT NULL,
  `status` tinyint(4) NOT NULL,
  PRIMARY KEY (`chatroom_id`),
  KEY `fk_chatroom_recruiter_idx` (`recruiter_id`),
  KEY `fk_chatroom_candidate_idx` (`candidate_id`),
  CONSTRAINT `fk_chatroom_candidate` FOREIGN KEY (`candidate_id`) REFERENCES `candidate` (`candidate_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_chatroom_recruiter` FOREIGN KEY (`recruiter_id`) REFERENCES `recruiter` (`recruiter_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chatroom`
--

LOCK TABLES `chatroom` WRITE;
/*!40000 ALTER TABLE `chatroom` DISABLE KEYS */;
/*!40000 ALTER TABLE `chatroom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `experience`
--

DROP TABLE IF EXISTS `experience`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `experience` (
  `experience_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `candidate_id` int(10) unsigned NOT NULL,
  `title` varchar(45) NOT NULL,
  `description` varchar(300) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `company` varchar(45) NOT NULL,
  PRIMARY KEY (`experience_id`),
  KEY `fk_experience_candidate_idx` (`candidate_id`),
  CONSTRAINT `fk_experience_candidate` FOREIGN KEY (`candidate_id`) REFERENCES `candidate` (`candidate_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `experience`
--

LOCK TABLES `experience` WRITE;
/*!40000 ALTER TABLE `experience` DISABLE KEYS */;
INSERT INTO `experience` VALUES (1,19,'IMatchProfile','Projet à 6 personnes à faire pendant un semestre','2018-03-19 08:00:00','2018-03-19 08:00:00','FST Nancy'),(2,20,'IMatchProfile','Projet à 6 personnes à faire pendant un semestre','2018-03-19 08:00:00','2018-03-19 08:00:00','FST Nancy'),(3,21,'IMatchProfile','Projet à 6 personnes à faire pendant un semestre','2018-03-19 08:00:00','2018-03-19 08:00:00','FST Nancy'),(4,22,'IMatchProfile','Projet à 6 personnes à faire pendant un semestre','2018-03-19 08:00:00','2018-03-19 08:00:00','FST Nancy'),(5,23,'IMatchProfile','Projet à 6 personnes à faire pendant un semestre','2018-03-19 08:00:00','2018-03-19 08:00:00','FST Nancy'),(6,24,'IMatchProfile','Projet à 6 personnes à faire pendant un semestre','2018-03-19 08:00:00','2018-03-19 08:00:00','FST Nancy');
/*!40000 ALTER TABLE `experience` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `job` (
  `job_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `recruiter_id` int(10) unsigned NOT NULL,
  `title` varchar(45) NOT NULL,
  `description` varchar(300) NOT NULL,
  `visibility` tinyint(4) NOT NULL DEFAULT '1',
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`job_id`),
  KEY `fk_job_recruiter_idx` (`recruiter_id`),
  CONSTRAINT `fk_job_recruiter` FOREIGN KEY (`recruiter_id`) REFERENCES `recruiter` (`recruiter_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
INSERT INTO `job` VALUES (1,1,'Stage - Prolog','Super Stage en Prolog !',1,'2018-03-19 08:00:00'),(2,1,'Stage PLIC','Allez venez svp',1,'2018-03-19 08:00:00');
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jobreports`
--

DROP TABLE IF EXISTS `jobreports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jobreports` (
  `jobreports_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `job_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`jobreports_id`),
  KEY `fk_jobreports_user_idx` (`user_id`),
  KEY `fk_jobreports_job_idx` (`job_id`),
  CONSTRAINT `fk_jobreports_job` FOREIGN KEY (`job_id`) REFERENCES `job` (`job_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_jobreports_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jobreports`
--

LOCK TABLES `jobreports` WRITE;
/*!40000 ALTER TABLE `jobreports` DISABLE KEYS */;
/*!40000 ALTER TABLE `jobreports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `masters`
--

DROP TABLE IF EXISTS `masters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `masters` (
  `masters_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `candidate_id` int(10) unsigned NOT NULL,
  `skill_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`masters_id`),
  KEY `fk_masters_candidate_idx` (`candidate_id`),
  KEY `fk_masters_skill_idx` (`skill_id`),
  CONSTRAINT `fk_masters_candidate` FOREIGN KEY (`candidate_id`) REFERENCES `candidate` (`candidate_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_masters_skill` FOREIGN KEY (`skill_id`) REFERENCES `skill` (`skill_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `masters`
--

LOCK TABLES `masters` WRITE;
/*!40000 ALTER TABLE `masters` DISABLE KEYS */;
INSERT INTO `masters` VALUES (1,19,3),(2,20,3),(3,21,3),(4,23,3),(5,22,3),(6,24,3),(7,19,1),(8,20,1),(9,21,4),(10,22,2),(11,23,5),(12,24,5);
/*!40000 ALTER TABLE `masters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matches`
--

DROP TABLE IF EXISTS `matches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `matches` (
  `matches_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `candidate_id` int(10) unsigned NOT NULL,
  `job_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`matches_id`),
  KEY `fk_matches_candidate_idx` (`candidate_id`),
  KEY `fk_matches_job_idx` (`job_id`),
  CONSTRAINT `fk_matches_candidate` FOREIGN KEY (`candidate_id`) REFERENCES `candidate` (`candidate_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_matches_job` FOREIGN KEY (`job_id`) REFERENCES `job` (`job_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matches`
--

LOCK TABLES `matches` WRITE;
/*!40000 ALTER TABLE `matches` DISABLE KEYS */;
/*!40000 ALTER TABLE `matches` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media`
--

DROP TABLE IF EXISTS `media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `media` (
  `media_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `candidate_id` int(10) unsigned NOT NULL,
  `path` varchar(300) NOT NULL,
  PRIMARY KEY (`media_id`),
  KEY `fk_media_candidate_idx` (`candidate_id`),
  CONSTRAINT `fk_media_candidate` FOREIGN KEY (`candidate_id`) REFERENCES `candidate` (`candidate_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media`
--

LOCK TABLES `media` WRITE;
/*!40000 ALTER TABLE `media` DISABLE KEYS */;
/*!40000 ALTER TABLE `media` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `message_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `chatroom_id` int(10) unsigned NOT NULL,
  `sent_by_recruiter` tinyint(4) NOT NULL,
  `content` varchar(300) NOT NULL,
  `sending_date` datetime NOT NULL,
  PRIMARY KEY (`message_id`),
  KEY `fk_message_chatroom_idx` (`chatroom_id`),
  CONSTRAINT `fk_message_chatroom` FOREIGN KEY (`chatroom_id`) REFERENCES `chatroom` (`chatroom_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `moderator`
--

DROP TABLE IF EXISTS `moderator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `moderator` (
  `moderator_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`moderator_id`),
  KEY `fk_moderator_user_idx` (`user_id`),
  CONSTRAINT `fk_moderator_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `moderator`
--

LOCK TABLES `moderator` WRITE;
/*!40000 ALTER TABLE `moderator` DISABLE KEYS */;
INSERT INTO `moderator` VALUES (1,33);
/*!40000 ALTER TABLE `moderator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `needs`
--

DROP TABLE IF EXISTS `needs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `needs` (
  `needs_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `job_id` int(10) unsigned NOT NULL,
  `skill_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`needs_id`),
  KEY `fk_needs_job_idx` (`job_id`),
  KEY `fk_needs_skill_idx` (`skill_id`),
  CONSTRAINT `fk_needs_job` FOREIGN KEY (`job_id`) REFERENCES `job` (`job_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_needs_skill` FOREIGN KEY (`skill_id`) REFERENCES `skill` (`skill_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `needs`
--

LOCK TABLES `needs` WRITE;
/*!40000 ALTER TABLE `needs` DISABLE KEYS */;
INSERT INTO `needs` VALUES (1,1,5),(2,2,4);
/*!40000 ALTER TABLE `needs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recruiter`
--

DROP TABLE IF EXISTS `recruiter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recruiter` (
  `recruiter_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `description` varchar(300) NOT NULL,
  `company` varchar(45) NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`recruiter_id`),
  KEY `fk_recruiter_user_idx` (`user_id`),
  CONSTRAINT `fk_recruiter_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recruiter`
--

LOCK TABLES `recruiter` WRITE;
/*!40000 ALTER TABLE `recruiter` DISABLE KEYS */;
INSERT INTO `recruiter` VALUES (1,'Recruteur d\'une super boîte ! #Desespere #VenezAMonStageSVP','Sopra Steria',32);
/*!40000 ALTER TABLE `recruiter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skill`
--

DROP TABLE IF EXISTS `skill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `skill` (
  `skill_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  PRIMARY KEY (`skill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skill`
--

LOCK TABLES `skill` WRITE;
/*!40000 ALTER TABLE `skill` DISABLE KEYS */;
INSERT INTO `skill` VALUES (1,'C++'),(2,'Brute Force'),(3,'Java'),(4,'PLIC'),(5,'Prolog');
/*!40000 ALTER TABLE `skill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training`
--

DROP TABLE IF EXISTS `training`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `training` (
  `training_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `candidate_id` int(10) unsigned NOT NULL,
  `title` varchar(45) NOT NULL,
  `description` varchar(300) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `institute` varchar(45) NOT NULL,
  PRIMARY KEY (`training_id`),
  KEY `fk_training_candidate_idx` (`candidate_id`),
  CONSTRAINT `fk_training_candidate` FOREIGN KEY (`candidate_id`) REFERENCES `candidate` (`candidate_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training`
--

LOCK TABLES `training` WRITE;
/*!40000 ALTER TABLE `training` DISABLE KEYS */;
INSERT INTO `training` VALUES (1,19,'Formation Java','On a appris plein de choses','2018-03-19 08:00:00','2018-03-19 08:00:00','FST Nancy'),(2,20,'Formation Java','On a appris plein de choses','2018-03-19 08:00:00','2018-03-19 08:00:00','FST Nancy'),(3,21,'Formation Java','On a appris plein de choses','2018-03-19 08:00:00','2018-03-19 08:00:00','FST Nancy'),(4,22,'Formation Java','On a appris plein de choses','2018-03-19 08:00:00','2018-03-19 08:00:00','FST Nancy'),(5,23,'Formation Java','On a appris plein de choses','2018-03-19 08:00:00','2018-03-19 08:00:00','FST Nancy'),(6,24,'Formation Java','On a appris plein de choses','2018-03-19 08:00:00','2018-03-19 08:00:00','FST Nancy');
/*!40000 ALTER TABLE `training` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `lastname` varchar(45) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(200) NOT NULL,
  `photoUrl` varchar(500) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  `candidate_id` int(10) unsigned DEFAULT NULL,
  `recruiter_id` int(10) unsigned DEFAULT NULL,
  `moderator_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_user_candidate_idx` (`candidate_id`),
  KEY `fk_user_recruiter_idx` (`recruiter_id`),
  KEY `fk_user_moderator_idx` (`moderator_id`),
  CONSTRAINT `fk_user_candidate` FOREIGN KEY (`candidate_id`) REFERENCES `candidate` (`candidate_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_moderator` FOREIGN KEY (`moderator_id`) REFERENCES `moderator` (`moderator_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_recruiter` FOREIGN KEY (`recruiter_id`) REFERENCES `recruiter` (`recruiter_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (26,'Parwany','Ashmat','ashmat@laposte.fr','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6InBhc3N3b3JkI3gjNTQyajcjWjMtSjg0RVUtVyNkeHhEU0Q3cyNtXzRmIn0.oqpTWPnMcoUIV2H-Tz7Fea0AZJIQXf9qh5Immi01L3s','http://images.pausecafein.fr/images/cafein/2015/06/20-faits-bresil/carte-drapeau-bresil.gif','C',19,NULL,NULL),(27,'Stocchi','Laurent','laurent@laposte.fr','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6InBhc3N3b3JkI3gjNTQyajcjWjMtSjg0RVUtVyNkeHhEU0Q3cyNtXzRmIn0.oqpTWPnMcoUIV2H-Tz7Fea0AZJIQXf9qh5Immi01L3s','http://m.memegen.com/1zzl08.jpg','C',20,NULL,NULL),(28,'Peters','Brice','brice@laposte.fr','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6InBhc3N3b3JkI3gjNTQyajcjWjMtSjg0RVUtVyNkeHhEU0Q3cyNtXzRmIn0.oqpTWPnMcoUIV2H-Tz7Fea0AZJIQXf9qh5Immi01L3s','https://i.ytimg.com/vi/B0-7RbQCL00/maxresdefault.jpg','C',21,NULL,NULL),(29,'Debicki','Jean-Marc','jm@laposte.fr','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6InBhc3N3b3JkI3gjNTQyajcjWjMtSjg0RVUtVyNkeHhEU0Q3cyNtXzRmIn0.oqpTWPnMcoUIV2H-Tz7Fea0AZJIQXf9qh5Immi01L3s','https://images-na.ssl-images-amazon.com/images/I/41kbIruQrnL.jpg','C',22,NULL,NULL),(30,'Ajdarpasic','Nihad','nihad@laposte.fr','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6InBhc3N3b3JkI3gjNTQyajcjWjMtSjg0RVUtVyNkeHhEU0Q3cyNtXzRmIn0.oqpTWPnMcoUIV2H-Tz7Fea0AZJIQXf9qh5Immi01L3s','http://www.clps.net/upload/image_1428_shutterstock_128748086NTC.jpg','C',23,NULL,NULL),(31,'Amrani','Driss','driss@laposte.fr','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6InBhc3N3b3JkI3gjNTQyajcjWjMtSjg0RVUtVyNkeHhEU0Q3cyNtXzRmIn0.oqpTWPnMcoUIV2H-Tz7Fea0AZJIQXf9qh5Immi01L3s','https://cdn.xl.thumbs.canstockphoto.fr/tasse-%C3%A0-caf%C3%A9-serveur-tenue-sourire-plateau-image_csp11181119.jpg','C',24,NULL,NULL),(32,'Steria','Sopra','sopra@steria.fr','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6InBhc3N3b3JkI3gjNTQyajcjWjMtSjg0RVUtVyNkeHhEU0Q3cyNtXzRmIn0.oqpTWPnMcoUIV2H-Tz7Fea0AZJIQXf9qh5Immi01L3s','https://s3-eu-west-1.amazonaws.com/th-prod/booth/0001/17/2409632a4db94f87baa2464e46ca2e176d866e67.jpeg','R',NULL,1,NULL),(33,'Modo','Modo','modo@modo.fr','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6InBhc3N3b3JkI3gjNTQyajcjWjMtSjg0RVUtVyNkeHhEU0Q3cyNtXzRmIn0.oqpTWPnMcoUIV2H-Tz7Fea0AZJIQXf9qh5Immi01L3s','https://akphoto1.ask.fm/498/028/229/2390003003-1rlhqmt-6flg9tp5b5q5qbk/original/agkkm7.jpg','M',NULL,NULL,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-09 16:52:54
