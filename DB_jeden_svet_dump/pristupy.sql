DROP TABLE IF EXISTS `pristupy`;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!40101 SET character_set_client = utf8 */;

CREATE TABLE `pristupy` (

  `jmeno` varchar(45) COLLATE utf8_czech_ci NOT NULL,

  `heslo` varchar(45) COLLATE utf8_czech_ci NOT NULL,

  PRIMARY KEY (`jmeno`)
) 
ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Dumping data for table `pristupy`
--



LOCK TABLES `pristupy` WRITE;

/*!40000 ALTER TABLE `pristupy` DISABLE KEYS */;

INSERT INTO `pristupy` VALUES ('jmeno','heslo');

/*!40000 ALTER TABLE `pristupy` ENABLE KEYS */;

UNLOCK TABLES;