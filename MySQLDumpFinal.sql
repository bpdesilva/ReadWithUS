-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: localhost    Database: lms
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
-- Table structure for table `author`
--

DROP TABLE IF EXISTS `author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `author` (
  `idAuthor` int(11) NOT NULL AUTO_INCREMENT,
  `AuthorName` varchar(60) DEFAULT NULL,
  `AuthorInitals` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`idAuthor`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `author`
--

LOCK TABLES `author` WRITE;
/*!40000 ALTER TABLE `author` DISABLE KEYS */;
INSERT INTO `author` VALUES (2,'Joanne Rowling','J. K. Rowling'),(4,'Clive Staples Lewis','C. S. Lewis'),(5,'John Ronald Reuel Tolkien','J. R. R. Tolkien'),(6,'Charles Dickens','C.D'),(7,'Mark Twain','MT'),(8,'Emily Jane Brontë','E. Brontë'),(9,'Charlotte Brontë','C. Brontë'),(10,'William Shakespeare','W.H.'),(12,'Malcolm Gladwell','M. Gladwell'),(13,'William Shakespeare','W. Shakespeare'),(14,'Charles Dicknes','Charles D.'),(15,'martin wikramasinha','m w'),(16,'Jane Austen','J.A');
/*!40000 ALTER TABLE `author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book` (
  `idBook` int(11) NOT NULL AUTO_INCREMENT,
  `ReferenceId` varchar(20) DEFAULT NULL,
  `BookName` varchar(60) DEFAULT NULL,
  `Genre` varchar(60) DEFAULT NULL,
  `Availability` varchar(45) DEFAULT NULL,
  `Status` varchar(45) DEFAULT NULL,
  `AId` int(11) DEFAULT NULL,
  PRIMARY KEY (`idBook`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (1,'199826','Harry Potter and the Chamber of Secrets','Fantasy Fiction','Borrowed','Existing',2),(3,'100','Programminc Java','java','Available','Existing',10),(6,'1003','harry potter and the deathly hollows ','Fiction','Available','Reserved for 2',2);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrow`
--

DROP TABLE IF EXISTS `borrow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `borrow` (
  `idBorrow` int(11) NOT NULL AUTO_INCREMENT,
  `BId` int(11) DEFAULT NULL,
  `MId` int(11) DEFAULT NULL,
  `BorrowedDate` varchar(50) DEFAULT NULL,
  `ReturnDate` varchar(50) DEFAULT NULL,
  `Fine` varchar(45) DEFAULT NULL,
  `Fine_Calculated_for` varchar(50) NOT NULL,
  PRIMARY KEY (`idBorrow`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrow`
--

LOCK TABLES `borrow` WRITE;
/*!40000 ALTER TABLE `borrow` DISABLE KEYS */;
INSERT INTO `borrow` VALUES (6,100,1,'29/04/2018','06/05/2018','65.0','19/05/2018'),(7,100,1,'29/04/2018','06/05/2018','65.0','19/05/2018'),(8,100,1,'29/04/2018','06/05/2018','65.0','19/05/2018'),(9,199826,1,'29/04/2018','06/05/2018','65.0','19/05/2018');
/*!40000 ALTER TABLE `borrow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history`
--

DROP TABLE IF EXISTS `history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `history` (
  `idHistory` int(11) NOT NULL AUTO_INCREMENT,
  `MId` int(45) NOT NULL,
  `BookRefId` int(45) NOT NULL,
  `BorrowedDate` varchar(45) NOT NULL,
  `ReturnedDate` varchar(45) NOT NULL,
  PRIMARY KEY (`idHistory`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history`
--

LOCK TABLES `history` WRITE;
/*!40000 ALTER TABLE `history` DISABLE KEYS */;
INSERT INTO `history` VALUES (1,1,100,'29/04/2018','29/04/2018'),(2,2,1001,'05/05/2018','05/05/2018'),(3,2,1003,'05/05/2018','05/05/2018');
/*!40000 ALTER TABLE `history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `librarian`
--

DROP TABLE IF EXISTS `librarian`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `librarian` (
  `idLibrarian` int(11) NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(45) DEFAULT NULL,
  `LastName` varchar(45) DEFAULT NULL,
  `NIC` varchar(45) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `PhoneNo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idLibrarian`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `member` (
  `idMember` int(11) NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(45) DEFAULT NULL,
  `LastName` varchar(45) DEFAULT NULL,
  `NIC` varchar(45) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `PhoneNo` varchar(45) DEFAULT NULL,
  `No_of_Borrows` tinyint(1) NOT NULL DEFAULT '0',
  `No_of_Reservations` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idMember`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `reserve`
--

DROP TABLE IF EXISTS `reserve`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reserve` (
  `idReserve` int(11) NOT NULL AUTO_INCREMENT,
  `BId` int(11) DEFAULT NULL,
  `MId` int(11) DEFAULT NULL,
  `ReservedDate` varchar(50) DEFAULT NULL,
  `Special_Remark` varchar(255) NOT NULL,
  PRIMARY KEY (`idReserve`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reserve`
--

LOCK TABLES `reserve` WRITE;
/*!40000 ALTER TABLE `reserve` DISABLE KEYS */;
INSERT INTO `reserve` VALUES (2,100,1,'29/04/2018',''),(3,1001,2,'05/05/2018',''),(4,1003,2,'05/05/2018','');
/*!40000 ALTER TABLE `reserve` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `idUsers` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(45) DEFAULT NULL,
  `Password` varchar(45) DEFAULT NULL,
  `UserType` varchar(45) DEFAULT NULL,
  `UId` int(11) DEFAULT NULL,
  `ustatus` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idUsers`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

