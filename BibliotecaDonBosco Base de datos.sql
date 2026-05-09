-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: biblioteca_donbosco
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
-- Table structure for table `categorias_documento`
--

DROP TABLE IF EXISTS `categorias_documento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorias_documento` (
  `id_categoria` int NOT NULL AUTO_INCREMENT,
  `nombre_categoria` varchar(100) NOT NULL,
  PRIMARY KEY (`id_categoria`),
  UNIQUE KEY `nombre_categoria` (`nombre_categoria`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias_documento`
--

LOCK TABLES `categorias_documento` WRITE;
/*!40000 ALTER TABLE `categorias_documento` DISABLE KEYS */;
INSERT INTO `categorias_documento` VALUES (14,'Administración'),(16,'Anatomía'),(2,'Bases de Datos'),(15,'Biología'),(9,'Ciberseguridad'),(18,'Electrónica'),(12,'Estadística'),(13,'Física'),(6,'Ingeniería'),(10,'Ingeniería de Software'),(20,'Inteligencia Artificial'),(11,'Matemática'),(8,'Medicina'),(1,'Programación'),(17,'Química'),(5,'Redes'),(19,'Sistemas Operativos'),(3,'Tecnología'),(4,'Tesis');
/*!40000 ALTER TABLE `categorias_documento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuracion_mora`
--

DROP TABLE IF EXISTS `configuracion_mora`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `configuracion_mora` (
  `id_configuracion_mora` int NOT NULL AUTO_INCREMENT,
  `anio` int NOT NULL,
  `mora_diaria` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id_configuracion_mora`),
  UNIQUE KEY `anio` (`anio`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuracion_mora`
--

LOCK TABLES `configuracion_mora` WRITE;
/*!40000 ALTER TABLE `configuracion_mora` DISABLE KEYS */;
INSERT INTO `configuracion_mora` VALUES (1,2026,0.50);
/*!40000 ALTER TABLE `configuracion_mora` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuracion_prestamo`
--

DROP TABLE IF EXISTS `configuracion_prestamo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `configuracion_prestamo` (
  `id_configuracion` int NOT NULL AUTO_INCREMENT,
  `id_rol` int NOT NULL,
  `max_ejemplares` int NOT NULL,
  `dias_prestamo` int NOT NULL,
  PRIMARY KEY (`id_configuracion`),
  KEY `id_rol` (`id_rol`),
  CONSTRAINT `configuracion_prestamo_ibfk_1` FOREIGN KEY (`id_rol`) REFERENCES `roles` (`id_rol`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuracion_prestamo`
--

LOCK TABLES `configuracion_prestamo` WRITE;
/*!40000 ALTER TABLE `configuracion_prestamo` DISABLE KEYS */;
INSERT INTO `configuracion_prestamo` VALUES (1,2,10,15),(2,3,3,8);
/*!40000 ALTER TABLE `configuracion_prestamo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `documentos`
--

DROP TABLE IF EXISTS `documentos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `documentos` (
  `id_documento` int NOT NULL AUTO_INCREMENT,
  `titulo` varchar(150) NOT NULL,
  `autor` varchar(100) DEFAULT NULL,
  `editorial` varchar(100) DEFAULT NULL,
  `anio_publicacion` int DEFAULT NULL,
  `id_tipo_documento` int NOT NULL,
  `categoria` varchar(100) DEFAULT NULL,
  `ubicacion` varchar(100) DEFAULT NULL,
  `descripcion` text,
  PRIMARY KEY (`id_documento`),
  KEY `id_tipo_documento` (`id_tipo_documento`),
  CONSTRAINT `documentos_ibfk_1` FOREIGN KEY (`id_tipo_documento`) REFERENCES `tipos_documento` (`id_tipo_documento`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documentos`
--

LOCK TABLES `documentos` WRITE;
/*!40000 ALTER TABLE `documentos` DISABLE KEYS */;
INSERT INTO `documentos` VALUES (1,'Programación en Java','Luis Joyanes Aguilar','McGraw Hill',2019,1,'Programación','Estante A1','Libro de programación orientada a objetos en Java'),(2,'Base de Datos con MySQL','Carlos Coronel','Cengage Learning',2020,1,'Bases de Datos','Estante B2','Libro sobre diseño y administración de bases de datos'),(3,'Revista Tecnología Actual','Editorial Tech','Tech Media',2024,2,'Tecnología','Revistero R1','Revista sobre avances tecnológicos'),(4,'Sistema de Gestión Bibliotecaria','Ana Martínez','Universidad Don Bosco',2023,3,'Tesis','Estante T1','Tesis sobre sistemas de biblioteca'),(5,'Curso Básico de Redes','Cisco Academy','Cisco',2021,4,'Redes','Estante CD1','Material digital sobre redes'),(6,'Introducción a la Ingeniería de Sistemas','Roberto Gómez','Editorial Académica',2022,1,'Ingeniería','Estante C1','Libro de introducción a sistemas de información.'),(7,'Fundamentos de Redes','Andrew Tanenbaum','Pearson',2021,1,'Redes','Estante R2','Libro sobre redes de computadoras.'),(8,'Java: Cómo Programar','Paul Deitel y Harvey Deitel','Pearson',2021,1,'Programación','Estante A1','Libro sobre fundamentos y programación orientada a objetos en Java.'),(9,'Python para Todos','Raúl González Duque','Autoedición',2020,1,'Programación','Estante A2','Introducción práctica al lenguaje Python.'),(10,'Fundamentos de Programación','Luis Joyanes Aguilar','McGraw Hill',2019,1,'Programación','Estante A3','Libro básico sobre algoritmos, lógica y estructuras de programación.'),(11,'Bases de Datos','Carlos Coronel','Cengage Learning',2020,1,'Bases de Datos','Estante B1','Texto sobre diseño, modelado y administración de bases de datos.'),(12,'Sistemas de Bases de Datos','Abraham Silberschatz','McGraw Hill',2018,1,'Bases de Datos','Estante B2','Libro avanzado sobre teoría y práctica de bases de datos.'),(13,'MySQL desde Cero','Miguel Ángel García','Alfaomega',2021,1,'Bases de Datos','Estante B3','Guía práctica para aprender MySQL y consultas SQL.'),(14,'Redes de Computadoras','Andrew S. Tanenbaum','Pearson',2021,1,'Redes','Estante C1','Libro sobre arquitectura, protocolos y funcionamiento de redes.'),(15,'Fundamentos de Redes','Cisco Networking Academy','Cisco Press',2022,1,'Redes','Estante C2','Material introductorio sobre redes, direccionamiento IP y conectividad.'),(16,'Seguridad Informática','William Stallings','Pearson',2020,1,'Ciberseguridad','Estante D1','Libro sobre principios de seguridad, criptografía y protección de sistemas.'),(17,'Ciberseguridad para Principiantes','Raúl Siles','Marcombo',2021,1,'Ciberseguridad','Estante D2','Introducción a conceptos básicos de ciberseguridad.'),(18,'Ingeniería de Software','Ian Sommerville','Pearson',2019,1,'Ingeniería de Software','Estante E1','Libro sobre análisis, diseño, desarrollo y mantenimiento de software.'),(19,'Análisis y Diseño de Sistemas','Kendall y Kendall','Pearson',2018,1,'Ingeniería de Software','Estante E2','Texto sobre metodologías para analizar y diseñar sistemas de información.'),(20,'Cálculo de una Variable','James Stewart','Cengage Learning',2020,1,'Matemática','Estante F1','Libro de cálculo diferencial e integral.'),(21,'Álgebra Lineal y sus Aplicaciones','David C. Lay','Pearson',2019,1,'Matemática','Estante F2','Libro sobre matrices, vectores, espacios vectoriales y transformaciones lineales.'),(22,'Probabilidad y Estadística','Mario F. Triola','Pearson',2021,1,'Estadística','Estante G1','Libro sobre estadística descriptiva, probabilidad e inferencia.'),(23,'Estadística Aplicada','Douglas Montgomery','Wiley',2020,1,'Estadística','Estante G2','Texto sobre análisis estadístico aplicado a ingeniería y ciencias.'),(24,'Física Universitaria','Sears y Zemansky','Pearson',2019,1,'Física','Estante H1','Libro sobre mecánica, ondas, termodinámica y electromagnetismo.'),(25,'Química General','Raymond Chang','McGraw Hill',2020,1,'Química','Estante I1','Libro introductorio sobre principios de química general.'),(26,'Biología','Neil A. Campbell','Pearson',2021,1,'Biología','Estante J1','Libro sobre fundamentos de biología celular, genética y evolución.'),(27,'El Cuerpo Humano','Elaine N. Marieb','Pearson',2020,1,'Anatomía','Estante J2','Libro sobre anatomía y fisiología humana.'),(28,'Administración Moderna','Stephen P. Robbins','Pearson',2018,1,'Administración','Estante K1','Libro sobre fundamentos de administración, liderazgo y organización.'),(29,'Sistemas Operativos','Abraham Silberschatz','Wiley',2019,1,'Sistemas Operativos','Estante L1','Libro sobre procesos, memoria, archivos y administración de sistemas operativos.'),(30,'Fundamentos de Electrónica','Thomas L. Floyd','Pearson',2018,1,'Electrónica','Estante M1','Libro introductorio sobre circuitos, componentes electrónicos y análisis básico.'),(31,'Inteligencia Artificial: Un Enfoque Moderno','Stuart Russell y Peter Norvig','Pearson',2021,1,'Inteligencia Artificial','Estante N1','Libro sobre agentes inteligentes, búsqueda, aprendizaje automático e IA.');
/*!40000 ALTER TABLE `documentos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ejemplares`
--

DROP TABLE IF EXISTS `ejemplares`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ejemplares` (
  `id_ejemplar` int NOT NULL AUTO_INCREMENT,
  `id_documento` int NOT NULL,
  `codigo_ejemplar` varchar(50) NOT NULL,
  `estado` varchar(30) DEFAULT 'Disponible',
  `fecha_registro` date DEFAULT NULL,
  `observaciones` text,
  PRIMARY KEY (`id_ejemplar`),
  UNIQUE KEY `codigo_ejemplar` (`codigo_ejemplar`),
  KEY `id_documento` (`id_documento`),
  CONSTRAINT `ejemplares_ibfk_1` FOREIGN KEY (`id_documento`) REFERENCES `documentos` (`id_documento`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ejemplares`
--

LOCK TABLES `ejemplares` WRITE;
/*!40000 ALTER TABLE `ejemplares` DISABLE KEYS */;
INSERT INTO `ejemplares` VALUES (1,1,'LIB-JAVA-001','Prestado','2026-05-05','Ejemplar nuevo'),(2,1,'LIB-JAVA-002','Disponible','2026-05-05','Ejemplar nuevo'),(3,2,'LIB-MYSQL-001','Prestado','2026-05-05','Buen estado'),(4,3,'REV-TEC-001','Prestado','2026-05-05','Revista en buen estado'),(5,4,'TES-BIB-001','Prestado','2026-05-05','Tesis empastada'),(6,5,'CD-RED-001','Prestado','2026-05-05','CD en caja'),(7,1,'LIB-JAVA-003','Disponible','2026-05-07','Ejemplar agregado desde Java.'),(9,5,'TES-BIB-002','Prestado','2026-05-07','Tesis Empastada, en toal buen estado'),(10,7,'LIB-RED-001','Disponible','2026-05-08',''),(11,6,'LIB-SIS-001','Disponible','2026-05-08',''),(12,8,'LIB-JAVA-004','Disponible','2026-05-08','Ejemplar agregado desde MySQL.'),(13,8,'LIB-JAVA-005','Disponible','2026-05-08','Ejemplar agregado desde MySQL.'),(14,9,'LIB-PYT-001','Disponible','2026-05-08','Ejemplar agregado desde MySQL.'),(15,10,'LIB-PROG-001','Disponible','2026-05-08','Ejemplar agregado desde MySQL.'),(16,11,'LIB-BD-001','Disponible','2026-05-08','Ejemplar agregado desde MySQL.'),(17,12,'LIB-BD-002','Disponible','2026-05-08','Ejemplar agregado desde MySQL.'),(18,13,'LIB-MYSQL-002','Disponible','2026-05-08','Ejemplar agregado desde MySQL.'),(22,28,'LIB-ADM-001','Disponible','2026-05-08',''),(23,21,'LIB-ALG-001','Disponible','2026-05-08',''),(24,19,'LIB-ADS-001','Disponible','2026-05-08',''),(25,26,'LIB-BIO-001','Disponible','2026-05-08',''),(26,20,'LIB-CAL-001','Disponible','2026-05-08',''),(27,17,'LIB-CIBS-001','Disponible','2026-05-08',''),(28,27,'LIB-ECH-001','Disponible','2026-05-08',''),(29,23,'LIB-EST-001','Disponible','2026-05-08',''),(30,24,'LIB-FIS-001','Disponible','2026-05-08',''),(31,30,'LIB-ELEC-001','Disponible','2026-05-08',''),(32,15,'LIB-FUNRED-001','Disponible','2026-05-08',''),(33,18,'LIB-INGSOFT-001','Disponible','2026-05-08',''),(34,31,'LIB-INTART-001','Disponible','2026-05-08',''),(35,22,'LIB-PROB&ESTAT-001','Disponible','2026-05-08',''),(36,25,'LIB-QUIG-001','Disponible','2026-05-08',''),(37,14,'LIB-REDC-001','Disponible','2026-05-08',''),(38,16,'LIB-SEGINF-001','Disponible','2026-05-08',''),(39,29,'LIB-SISOPE-001','Disponible','2026-05-08',''),(40,26,'LIB-BIO-002','Disponible','2026-05-08',''),(41,26,'LIB-BIO-003','Disponible','2026-05-08','');
/*!40000 ALTER TABLE `ejemplares` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prestamos`
--

DROP TABLE IF EXISTS `prestamos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prestamos` (
  `id_prestamo` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `id_ejemplar` int NOT NULL,
  `fecha_prestamo` date NOT NULL,
  `fecha_devolucion_estimada` date NOT NULL,
  `fecha_devolucion_real` date DEFAULT NULL,
  `estado` varchar(30) DEFAULT 'Activo',
  `mora_calculada` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`id_prestamo`),
  KEY `id_usuario` (`id_usuario`),
  KEY `id_ejemplar` (`id_ejemplar`),
  CONSTRAINT `prestamos_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`),
  CONSTRAINT `prestamos_ibfk_2` FOREIGN KEY (`id_ejemplar`) REFERENCES `ejemplares` (`id_ejemplar`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prestamos`
--

LOCK TABLES `prestamos` WRITE;
/*!40000 ALTER TABLE `prestamos` DISABLE KEYS */;
INSERT INTO `prestamos` VALUES (1,3,1,'2026-05-07','2026-05-15','2026-05-08','Devuelto',0.00),(2,2,4,'2026-05-07','2026-05-22','2026-05-08','Devuelto',0.00),(3,2,1,'2026-05-08','2026-05-23','2026-05-08','Devuelto',0.00),(4,2,2,'2026-05-08','2026-05-23','2026-05-08','Devuelto',0.00),(5,2,4,'2026-05-08','2026-05-23','2026-05-08','Devuelto',0.00),(6,2,1,'2026-05-08','2026-05-23',NULL,'Activo',0.00),(7,3,3,'2026-05-08','2026-05-16',NULL,'Activo',0.00),(8,3,4,'2026-05-08','2026-05-16',NULL,'Activo',0.00),(9,3,5,'2026-05-08','2026-05-16',NULL,'Activo',0.00),(10,2,6,'2026-05-08','2026-05-23',NULL,'Activo',0.00);
/*!40000 ALTER TABLE `prestamos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id_rol` int NOT NULL AUTO_INCREMENT,
  `nombre_rol` varchar(50) NOT NULL,
  PRIMARY KEY (`id_rol`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Administrador'),(2,'Profesor'),(3,'Alumno');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipos_documento`
--

DROP TABLE IF EXISTS `tipos_documento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipos_documento` (
  `id_tipo_documento` int NOT NULL AUTO_INCREMENT,
  `nombre_tipo` varchar(50) NOT NULL,
  PRIMARY KEY (`id_tipo_documento`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipos_documento`
--

LOCK TABLES `tipos_documento` WRITE;
/*!40000 ALTER TABLE `tipos_documento` DISABLE KEYS */;
INSERT INTO `tipos_documento` VALUES (1,'Libro'),(2,'Revista'),(3,'Tesis'),(4,'CD');
/*!40000 ALTER TABLE `tipos_documento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `nombres` varchar(100) NOT NULL,
  `apellidos` varchar(100) NOT NULL,
  `usuario` varchar(50) NOT NULL,
  `contrasena` varchar(100) NOT NULL,
  `id_rol` int NOT NULL,
  `area_seccion` varchar(150) DEFAULT NULL,
  `estado` varchar(20) DEFAULT 'Activo',
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `usuario` (`usuario`),
  KEY `id_rol` (`id_rol`),
  CONSTRAINT `usuarios_ibfk_1` FOREIGN KEY (`id_rol`) REFERENCES `roles` (`id_rol`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Administrador','Principal','admin','12345',1,'Administrador de Mediateca','Activo'),(2,'Carlos','Ramírez','CarlosRP26','12345',2,'Profesor de Biologia','Activo'),(3,'María','López','MariaLA26','12345',3,'Ingenieria en Sistemas - Clase 901IES','Activo'),(4,'Katherine','Garcia','KatherineGA26','12345',3,'Licenciatura en Contabilidad Administrativa - Clase 886LCA','Activo'),(5,'Michelle','Lopez','MichelleLA26','12345',3,'Licenciatura en Comunicacion - Clase 804LC','Activo'),(6,'Elias','Jovel','EliasJP26','12345',2,'Profesor de Programacion','Activo'),(7,'Francisco','Duran','FranciscoDP26','12345',2,'Profesor de Diseño Web','Activo'),(8,'Claudia','Escalante','ClaudiaEP26','12345',2,'Profesora de Telecomunicaciones','Activo'),(9,'Bryan','Joel','BryanJA26','12345',3,'Ingenieria Industrial - Clase 941II','Activo');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-08 22:18:44
