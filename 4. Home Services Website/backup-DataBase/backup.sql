-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: serviciidomiciliu
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `angajati`
--

DROP TABLE IF EXISTS `angajati`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `angajati` (
  `AngajatId` int NOT NULL AUTO_INCREMENT,
  `Nume` varchar(50) NOT NULL,
  `Prenume` varchar(50) NOT NULL,
  `DataNastere` datetime DEFAULT NULL,
  `Salariu` int NOT NULL,
  `NrTelefon` varchar(15) NOT NULL,
  PRIMARY KEY (`AngajatId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `angajati`
--

LOCK TABLES `angajati` WRITE;
/*!40000 ALTER TABLE `angajati` DISABLE KEYS */;
INSERT INTO `angajati` VALUES (1,'Iliescu','Paul','1980-04-10 00:00:00',3500,'0745123456'),(2,'Georgescu','Diana','1992-09-17 00:00:00',3000,'0734123456'),(3,'Popa','Adrian','1988-02-25 00:00:00',4000,'0723123456'),(4,'Stan','Elena','1990-12-12 00:00:00',3200,'0755123456'),(5,'Matei','Cristina','1995-03-18 00:00:00',2800,'0765123456');
/*!40000 ALTER TABLE `angajati` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facturi`
--

DROP TABLE IF EXISTS `facturi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `facturi` (
  `FacturaId` int NOT NULL AUTO_INCREMENT,
  `UserId` int NOT NULL,
  `DataFactura` datetime NOT NULL,
  `TipPlata` enum('Cash','Card') DEFAULT NULL,
  PRIMARY KEY (`FacturaId`),
  KEY `FK_Facturi_UserId` (`UserId`),
  CONSTRAINT `FK_Facturi_UserId` FOREIGN KEY (`UserId`) REFERENCES `users` (`UserId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facturi`
--

LOCK TABLES `facturi` WRITE;
/*!40000 ALTER TABLE `facturi` DISABLE KEYS */;
INSERT INTO `facturi` VALUES (1,1,'2024-11-15 00:00:00','Card'),(2,2,'2024-11-16 00:00:00','Cash'),(3,3,'2024-11-17 00:00:00','Card'),(4,4,'2024-11-18 00:00:00','Cash'),(5,5,'2024-11-19 00:00:00','Card');
/*!40000 ALTER TABLE `facturi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facturiservicii`
--

DROP TABLE IF EXISTS `facturiservicii`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `facturiservicii` (
  `FacturaId` int NOT NULL,
  `ServiciuId` int NOT NULL,
  `OreLucrate` int NOT NULL,
  PRIMARY KEY (`FacturaId`,`ServiciuId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facturiservicii`
--

LOCK TABLES `facturiservicii` WRITE;
/*!40000 ALTER TABLE `facturiservicii` DISABLE KEYS */;
INSERT INTO `facturiservicii` VALUES (1,1,2),(1,3,1),(2,2,3),(3,4,4),(4,5,2),(5,1,3);
/*!40000 ALTER TABLE `facturiservicii` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `ProgramareId` int NOT NULL,
  `Experienta` enum('BUNA','REA') DEFAULT NULL,
  `Descriere` varchar(255) DEFAULT NULL,
  `Nota` int DEFAULT NULL,
  PRIMARY KEY (`ProgramareId`),
  CONSTRAINT `FK_FeedBack_Programari` FOREIGN KEY (`ProgramareId`) REFERENCES `programari` (`ProgramareId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `CHK_FeedBack_Nota` CHECK ((`Nota` <= 10))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
INSERT INTO `feedback` VALUES (1,'BUNA','Multumit de serviciu',8),(2,'REA','A durat putin cam mult, dar totul a fost in regula',9),(3,'BUNA','Se putea si mai bine',7),(5,'BUNA','Rapid si de incredere',8);
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `programari`
--

DROP TABLE IF EXISTS `programari`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `programari` (
  `ProgramareId` int NOT NULL AUTO_INCREMENT,
  `ServiciuPrestatDeAngajatId` int NOT NULL,
  `UserId` int NOT NULL,
  `DataProgramare` datetime NOT NULL,
  `StatusProgramare` enum('In asteptare','In lucru','Finalizata','Esuata') NOT NULL,
  PRIMARY KEY (`ProgramareId`),
  KEY `FK_Programari_ServiciiPrestateDeAngajati` (`ServiciuPrestatDeAngajatId`),
  KEY `FK_Programari_users` (`UserId`),
  CONSTRAINT `FK_Programari_ServiciiPrestateDeAngajati` FOREIGN KEY (`ServiciuPrestatDeAngajatId`) REFERENCES `serviciiprestatedeangajati` (`ServiciuPrestatDeAngajatId`),
  CONSTRAINT `FK_Programari_users` FOREIGN KEY (`UserId`) REFERENCES `users` (`UserId`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `programari`
--

LOCK TABLES `programari` WRITE;
/*!40000 ALTER TABLE `programari` DISABLE KEYS */;
INSERT INTO `programari` VALUES (1,1,1,'2024-11-10 10:00:00','In asteptare'),(2,2,2,'2024-11-11 15:00:00','Finalizata'),(3,3,3,'2024-11-12 09:30:00','Finalizata'),(4,4,4,'2024-11-13 12:00:00','Esuata'),(5,5,5,'2024-11-14 14:00:00','In asteptare'),(11,6,2,'2024-12-29 18:56:00','Finalizata'),(12,1,2,'2024-12-29 19:17:00','Finalizata'),(13,1,2,'2025-01-01 09:04:00','Esuata'),(14,2,2,'2025-01-01 09:48:00','Esuata'),(15,3,2,'2025-01-01 08:49:00','Finalizata'),(16,4,2,'2025-01-01 09:53:00','Finalizata'),(17,2,2,'2025-01-01 15:37:00','Finalizata'),(18,5,2,'2025-01-02 09:40:00','Finalizata'),(19,2,2,'2025-01-05 19:44:00','Finalizata'),(21,2,2,'2025-01-16 08:16:00','Finalizata'),(22,3,20,'2025-01-30 17:21:00','Finalizata'),(24,2,2,'2025-01-03 20:26:00','Esuata'),(25,1,2,'2025-01-05 16:01:00','Finalizata'),(26,6,2,'2025-01-05 16:03:00','Finalizata'),(28,2,2,'2025-01-17 12:48:00','Finalizata'),(29,6,2,'2025-01-16 16:49:00','Finalizata');
/*!40000 ALTER TABLE `programari` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicii`
--

DROP TABLE IF EXISTS `servicii`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servicii` (
  `ServiciuId` int NOT NULL AUTO_INCREMENT,
  `NumeServiciu` varchar(50) NOT NULL,
  `Descriere` varchar(500) DEFAULT NULL,
  `PretPeOra` int NOT NULL,
  `NumeImagine` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`ServiciuId`),
  UNIQUE KEY `NumeServiciu` (`NumeServiciu`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicii`
--

LOCK TABLES `servicii` WRITE;
/*!40000 ALTER TABLE `servicii` DISABLE KEYS */;
INSERT INTO `servicii` VALUES (1,'Instalator','Servicii de instalare si reparatii tevi, chiuvete, toalete si alte echipamente sanitare la domiciliu',200,'instalator.jpg'),(2,'Electrician','Interventii electrice de reparatii, instalare si intretinere a retelei electrice la domiciliu',180,'electrician.jpg'),(3,'Ingrijire Batrani','Asistenta pentru ingrijirea persoanelor varstnice la domiciliu',100,'ingrijire_batrani.jpg'),(4,'Servicii Funerare','Servicii de transport si pregatire sicrie pentru ceremoniile funerare',250,'servicii_funerare.jpg'),(5,'Bucatar la Domiciliu','Prepararea mancarurilor la domiciliul clientului pentru evenimente speciale sau mese private',200,'bucatar.jpg'),(6,'Menajera','Servicii de curatenie si intretinere in locuinta clientului',120,'menajera.jpg');
/*!40000 ALTER TABLE `servicii` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `serviciiprestatedeangajati`
--

DROP TABLE IF EXISTS `serviciiprestatedeangajati`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `serviciiprestatedeangajati` (
  `ServiciuPrestatDeAngajatId` int NOT NULL AUTO_INCREMENT,
  `ServiciuId` int NOT NULL,
  `AngajatId` int NOT NULL,
  `NOTA` int DEFAULT '0',
  PRIMARY KEY (`ServiciuPrestatDeAngajatId`),
  UNIQUE KEY `UNQ_ServiciiPrestateDeAngajati_ServiciuId_AngajatId` (`ServiciuId`,`AngajatId`),
  KEY `FK_ServiciiPrestateDeAngajati_Angajati` (`AngajatId`),
  CONSTRAINT `FK_ServiciiPrestateDeAngajati_Angajati` FOREIGN KEY (`AngajatId`) REFERENCES `angajati` (`AngajatId`),
  CONSTRAINT `FK_ServiciiPrestateDeAngajati_Servicii` FOREIGN KEY (`ServiciuId`) REFERENCES `servicii` (`ServiciuId`),
  CONSTRAINT `CHK_ServiciiPrestateDeAngajati_Nota` CHECK ((`Nota` <= 10))
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `serviciiprestatedeangajati`
--

LOCK TABLES `serviciiprestatedeangajati` WRITE;
/*!40000 ALTER TABLE `serviciiprestatedeangajati` DISABLE KEYS */;
INSERT INTO `serviciiprestatedeangajati` VALUES (1,1,1,8),(2,2,2,9),(3,3,3,7),(4,4,4,6),(5,5,5,8),(6,1,2,10);
/*!40000 ALTER TABLE `serviciiprestatedeangajati` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `UserId` int NOT NULL AUTO_INCREMENT,
  `Nume` varchar(50) NOT NULL,
  `Prenume` varchar(50) NOT NULL,
  `Sex` char(1) DEFAULT NULL,
  `DataNastere` datetime NOT NULL,
  `Judet` varchar(50) NOT NULL,
  `Oras` varchar(50) NOT NULL,
  `Strada` varchar(50) DEFAULT NULL,
  `NumarStrada` int DEFAULT NULL,
  `Bloc` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `Scara` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `Apartament` int DEFAULT NULL,
  `Email` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `NumarTelefon` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `username` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `IsAdmin` char(2) DEFAULT 'NU',
  PRIMARY KEY (`UserId`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `UNQ_USERS_USERID` (`UserId`),
  CONSTRAINT `CHK_USERS_ISADMIN` CHECK (((`IsAdmin` = _utf8mb4'DA') or (`IsAdmin` = _utf8mb4'NU'))),
  CONSTRAINT `CHK_USERS_SEX` CHECK (((`Sex` = _utf8mb4'M') or (`Sex` = _utf8mb4'F')))
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Popescu','Ion','M','1990-05-20 00:00:00','Cluj','Cluj-Napoca','Principala',10,NULL,NULL,NULL,'ion.popescu@gmail.com','0741234567','ionpopescu','password1','NU'),(2,'Guzencu','Claudiu','M','2003-04-17 00:00:00','Bucuresti','Sector 6','Splaiul Independentei',290,NULL,NULL,NULL,'guzencu.claudiu@gmail.com','0732123456','claulau001','da123','DA'),(3,'Radu','Ana','F','1993-01-10 00:00:00','Timis','Timisoara','Republicii',21,NULL,NULL,NULL,'ana.radu@gmail.com','0723123456','anaradu','password3','NU'),(4,'Mihai','George','M','1987-11-30 00:00:00','Iasi','Iasi','Pacurari',7,NULL,NULL,NULL,'george.mihai@gmail.com','0753123456','georgemihai','password4','NU'),(5,'Dumitru','Alina','F','1995-07-22 00:00:00','Brasov','Brasov','Victoria',13,NULL,NULL,NULL,'alina.dumitru@gmail.com','0763123456','alinadumitru','password5','NU'),(16,'Guzi','Clau',NULL,'1991-02-02 00:00:00','Tulcea','Niculitel',NULL,NULL,NULL,NULL,NULL,'2@22','0722','koro','da123','NU'),(17,'Tozaru','Stefania','F','2005-12-14 00:00:00','Tulcea','Tulcea','Alexandru Cel Bun',56,NULL,NULL,NULL,'ceva@email.com','072222222','stefi','lanimereala','NU'),(18,'guzencu','claudiu',NULL,'1991-02-02 00:00:00','tulcea','tulcea',NULL,23,NULL,NULL,NULL,'2@2.ro','072222222','claulau','1234','NU'),(20,'Gavrila','Ionut','M','2002-01-15 00:00:00','Timis','Timisoara','Neptun',2,'2','C',4,'ghygfhghgh@fbgfbgfbf.com','07446546','Gavrila','Da12345.','NU'),(21,'ergerger','gergergerg','M','2003-02-22 00:00:00','gergerg','egrerger','efergerger',2,'gergerger','rgfdgerg',34,'fdgfdd@rfdgvfdgd.com','35435435','refgfdg','Da12345.','NU');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-13 14:02:46
