-- Dump completed on 2021-07-20  0:51:46
-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: default_db
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
INSERT INTO `area` VALUES (1,'默认地址');
UNLOCK TABLES;

--
-- Table structure for table `tenant`
--

DROP TABLE IF EXISTS `tenant`;
CREATE TABLE `tenant` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tenant_name` varchar(50) NOT NULL,
  `tenant_code` varchar(50) NOT NULL,
  `data_source_url` varchar(250) DEFAULT NULL,
  `data_source_username` varchar(50) DEFAULT NULL,
  `data_source_password` varchar(20) DEFAULT NULL,
  `is_active` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tenant_tenantCode_uindex` (`tenant_code`),
  UNIQUE KEY `tenant_tenantName_uindex` (`tenant_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='租户表';

--
-- Dumping data for table `tenant`
--

LOCK TABLES `tenant` WRITE;
INSERT INTO `tenant` VALUES (1,'quanzhou','quanzhou','jdbc:mysql://127.0.0.1:3306/quanzhou?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&allowMultiQueries=true','test_user','ysys1314',_binary ''),(2,'xiamen','xiamen','jdbc:mysql://127.0.0.1:3306/xiamen?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&allowMultiQueries=true','test_user','ysys1314',_binary '');
UNLOCK TABLES;