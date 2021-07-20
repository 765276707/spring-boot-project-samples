-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: xiamen
-- ------------------------------------------------------
-- Server version	8.0.19
--
-- Table structure for table `area`
--

DROP TABLE IF EXISTS `area`;
CREATE TABLE `area` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `area`
--

LOCK TABLES `area` WRITE;
INSERT INTO `area` VALUES (1,'厦门市');
UNLOCK TABLES;