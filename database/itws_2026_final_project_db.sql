-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: itws_2026_final_project_db
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `chess_match`
--

DROP TABLE IF EXISTS `chess_match`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chess_match` (
  `chess_match_id` int unsigned NOT NULL AUTO_INCREMENT,
  `tournament_id` int unsigned NOT NULL,
  `white_player_id` int unsigned NOT NULL,
  `black_player_id` int unsigned NOT NULL,
  `round_number` int NOT NULL,
  `result` enum('WHITE_WIN','BLACK_WIN','DRAW','NOT_PLAYED') NOT NULL DEFAULT 'NOT_PLAYED',
  `played_at` datetime NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`chess_match_id`),
  KEY `fk_match_tournament_idx` (`tournament_id`),
  KEY `fk_match_white_player_idx` (`white_player_id`),
  KEY `fk_match_black_player_idx` (`black_player_id`),
  CONSTRAINT `fk_chess_match_black_player` FOREIGN KEY (`black_player_id`) REFERENCES `player` (`player_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_chess_match_tournament` FOREIGN KEY (`tournament_id`) REFERENCES `tournament` (`tournament_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_chess_match_white_player` FOREIGN KEY (`white_player_id`) REFERENCES `player` (`player_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chess_match`
--

LOCK TABLES `chess_match` WRITE;
/*!40000 ALTER TABLE `chess_match` DISABLE KEYS */;
INSERT INTO `chess_match` VALUES (1,1,1,2,1,'DRAW','2026-05-25 15:00:00','2026-06-16 20:13:04',NULL,NULL),(2,1,3,4,5,'WHITE_WIN','2026-05-30 16:30:00','2026-06-16 20:13:04',NULL,NULL),(3,1,5,6,3,'NOT_PLAYED','2026-05-28 15:20:00','2026-06-16 20:13:04',NULL,NULL),(4,2,1,3,2,'BLACK_WIN','2026-07-03 18:15:00','2026-06-16 20:13:04',NULL,NULL),(5,2,2,3,8,'DRAW','2026-07-08 14:15:00','2026-06-16 20:13:04',NULL,NULL),(6,2,5,7,4,'WHITE_WIN','2026-07-05 17:00:00','2026-06-16 20:13:04',NULL,NULL),(7,3,8,1,1,'BLACK_WIN','2026-12-26 10:10:00','2026-06-16 20:13:04',NULL,NULL),(8,3,5,2,3,'NOT_PLAYED','2026-12-27 12:25:00','2026-06-16 20:13:04',NULL,NULL),(9,3,4,7,8,'WHITE_WIN','2026-12-29 13:00:00','2026-06-16 20:13:04',NULL,NULL),(10,3,3,6,4,'DRAW','2026-12-27 18:30:00','2026-06-16 20:13:04',NULL,NULL);
/*!40000 ALTER TABLE `chess_match` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player`
--

DROP TABLE IF EXISTS `player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `player` (
  `player_id` int unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `rating` int NOT NULL,
  `title` enum('GM','IM','FM','CM','NONE') NOT NULL DEFAULT 'NONE',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`player_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player`
--

LOCK TABLES `player` WRITE;
/*!40000 ALTER TABLE `player` DISABLE KEYS */;
INSERT INTO `player` VALUES (1,'Magnus','Carlsen','Norway',2837,'GM','2026-06-16 19:59:45',NULL,NULL),(2,'Hikaru','Nakamura','USA',2804,'GM','2026-06-16 19:59:45',NULL,NULL),(3,'Fabiano','Caruana','USA',2781,'GM','2026-06-16 19:59:45',NULL,NULL),(4,'Anna','Muzychuk','Ukraine',2455,'IM','2026-06-16 19:59:45',NULL,NULL),(5,'Nodirbek','Abdusattorov','Uzbekistan',2763,'GM','2026-06-16 19:59:45',NULL,NULL),(6,'Teodora','Injac','Serbia',2450,'IM','2026-06-16 19:59:45',NULL,NULL),(7,'Vincent','Keymer','Germany',2720,'FM','2026-06-16 19:59:45',NULL,NULL),(8,'Bibisara','Assaubayeva','Kazakhstan',2490,'CM','2026-06-16 19:59:45',NULL,NULL);
/*!40000 ALTER TABLE `player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tournament`
--

DROP TABLE IF EXISTS `tournament`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tournament` (
  `tournament_id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `location` varchar(255) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `time_control` enum('CLASSICAL','RAPID','BLITZ') NOT NULL DEFAULT 'CLASSICAL',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`tournament_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tournament`
--

LOCK TABLES `tournament` WRITE;
/*!40000 ALTER TABLE `tournament` DISABLE KEYS */;
INSERT INTO `tournament` VALUES (1,'Norway Chess 2026','Stavanger, Norway','2026-05-25','2026-06-05','CLASSICAL','2026-06-16 20:03:55',NULL,NULL),(2,'Grand Chess Tour Zagreb 2026','Zagreb, Croatia','2026-07-01','2026-07-10','RAPID','2026-06-16 20:03:55',NULL,NULL),(3,'World Blitz Championship 2026','Doha, Qatar','2026-12-26','2026-12-30','BLITZ','2026-06-16 20:03:55',NULL,NULL);
/*!40000 ALTER TABLE `tournament` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-17 11:00:50
