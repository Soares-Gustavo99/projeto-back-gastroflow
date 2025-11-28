-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: gastro_flow
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id` binary(16) NOT NULL,
  `user_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKhawikyhwwfvbnog5byokutpff` (`user_id`),
  CONSTRAINT `FK8ahhk8vqegfrt6pd1p9i03aej` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `aula`
--

DROP TABLE IF EXISTS `aula`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aula` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ano` int DEFAULT NULL,
  `data` datetime(6) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `instrutor` varchar(255) DEFAULT NULL,
  `materia` varchar(255) DEFAULT NULL,
  `modulo` int DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `periodo` varchar(255) DEFAULT NULL,
  `semestre` int DEFAULT NULL,
  `fk_usuario_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4mel7uiyfgpddubk43exfmyu` (`fk_usuario_id`),
  CONSTRAINT `FK4mel7uiyfgpddubk43exfmyu` FOREIGN KEY (`fk_usuario_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `aula_receita`
--

DROP TABLE IF EXISTS `aula_receita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aula_receita` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantidade` int DEFAULT NULL,
  `fk_aula_id` bigint DEFAULT NULL,
  `fk_receita_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjpbhil6hl06a9vb7f4e64e23n` (`fk_aula_id`),
  KEY `FK3e0h8oway83tjw0n339x60jpj` (`fk_receita_id`),
  CONSTRAINT `FK3e0h8oway83tjw0n339x60jpj` FOREIGN KEY (`fk_receita_id`) REFERENCES `receita` (`id`),
  CONSTRAINT `FKjpbhil6hl06a9vb7f4e64e23n` FOREIGN KEY (`fk_aula_id`) REFERENCES `aula` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `entrada_produto`
--

DROP TABLE IF EXISTS `entrada_produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entrada_produto` (
  `preco` decimal(38,2) DEFAULT NULL,
  `quantidade` int DEFAULT NULL,
  `fk_entrada_id` bigint NOT NULL,
  `fk_produto_id` bigint NOT NULL,
  PRIMARY KEY (`fk_entrada_id`,`fk_produto_id`),
  KEY `FK5bl0tiubtaotkmobvbo38e7tq` (`fk_produto_id`),
  CONSTRAINT `FK5bl0tiubtaotkmobvbo38e7tq` FOREIGN KEY (`fk_produto_id`) REFERENCES `produtos` (`id`),
  CONSTRAINT `FKfp1llrhb7yam2s909856dtn5a` FOREIGN KEY (`fk_entrada_id`) REFERENCES `entradas` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `entradas`
--

DROP TABLE IF EXISTS `entradas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entradas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `data_entrada` datetime(6) NOT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `fornecedor_id` bigint DEFAULT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1nijbh8ajewc7fcuv3wk88t7d` (`fornecedor_id`),
  KEY `FKhtl3tn9p1xkxp1d9pibxpo02u` (`user_id`),
  CONSTRAINT `FK1nijbh8ajewc7fcuv3wk88t7d` FOREIGN KEY (`fornecedor_id`) REFERENCES `supplier` (`id`),
  CONSTRAINT `FKhtl3tn9p1xkxp1d9pibxpo02u` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `guest`
--

DROP TABLE IF EXISTS `guest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `guest` (
  `id` binary(16) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `user_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKchdaocwoknpkpjjcb6dyv8os8` (`user_id`),
  CONSTRAINT `FKake2867xxr6o753o6kqc4rott` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `guest_admin_relationship`
--

DROP TABLE IF EXISTS `guest_admin_relationship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `guest_admin_relationship` (
  `admin_id` binary(16) NOT NULL,
  `guest_id` binary(16) NOT NULL,
  KEY `FKbefuj2q9jnpk9medc806ds0l1` (`guest_id`),
  KEY `FKfv0f282v5dhklwsvg5hn4kjsd` (`admin_id`),
  CONSTRAINT `FKbefuj2q9jnpk9medc806ds0l1` FOREIGN KEY (`guest_id`) REFERENCES `guest` (`id`),
  CONSTRAINT `FKfv0f282v5dhklwsvg5hn4kjsd` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `produtos`
--

DROP TABLE IF EXISTS `produtos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produtos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `categoria` tinyint DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `quantidade_estoque` int DEFAULT NULL,
  `unidade_medida` tinyint DEFAULT NULL,
  `validade` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `receita`
--

DROP TABLE IF EXISTS `receita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receita` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `data_alteracao` datetime(6) DEFAULT NULL,
  `data_cadastro` datetime(6) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `professor_receita` varchar(255) DEFAULT NULL,
  `rendimento` int DEFAULT NULL,
  `tempo_preparo` int DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `usuario_alteracao` varchar(255) DEFAULT NULL,
  `fk_usuario_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKajff2umcnrf3hbx08jrds40b6` (`fk_usuario_id`),
  CONSTRAINT `FKajff2umcnrf3hbx08jrds40b6` FOREIGN KEY (`fk_usuario_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `receita_produto`
--

DROP TABLE IF EXISTS `receita_produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receita_produto` (
  `quantidade` int DEFAULT NULL,
  `fk_produto_id` bigint NOT NULL,
  `fk_receita_id` bigint NOT NULL,
  PRIMARY KEY (`fk_produto_id`,`fk_receita_id`),
  KEY `FKehwxnka0x8tcnwx64ly7ywbrx` (`fk_receita_id`),
  CONSTRAINT `FK995rvk2deumu180mc3mljk7c0` FOREIGN KEY (`fk_produto_id`) REFERENCES `produtos` (`id`),
  CONSTRAINT `FKehwxnka0x8tcnwx64ly7ywbrx` FOREIGN KEY (`fk_receita_id`) REFERENCES `receita` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `retiradas`
--

DROP TABLE IF EXISTS `retiradas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `retiradas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `data_retirada` datetime(6) NOT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `quantidade` int NOT NULL,
  `produto_id` bigint NOT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2n97g5eyys52uhykrymdens0o` (`produto_id`),
  KEY `FK9uie01hk8ylkebohv4t89w9we` (`user_id`),
  CONSTRAINT `FK2n97g5eyys52uhykrymdens0o` FOREIGN KEY (`produto_id`) REFERENCES `produtos` (`id`),
  CONSTRAINT `FK9uie01hk8ylkebohv4t89w9we` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `standard`
--

DROP TABLE IF EXISTS `standard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `standard` (
  `id` binary(16) NOT NULL,
  `user_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKn1v3sniq4mupsct1e8q1ddtau` (`user_id`),
  CONSTRAINT `FKf1rijpklvcopt67ytyp4ay77h` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `standard_admin_relationship`
--

DROP TABLE IF EXISTS `standard_admin_relationship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `standard_admin_relationship` (
  `admin_id` binary(16) NOT NULL,
  `standard_id` binary(16) NOT NULL,
  KEY `FKihj9dqls2vrg79s64ty45px2w` (`standard_id`),
  KEY `FKhe7n2cknnfwmrdekkxihvkk6x` (`admin_id`),
  CONSTRAINT `FKhe7n2cknnfwmrdekkxihvkk6x` FOREIGN KEY (`admin_id`) REFERENCES `standard` (`id`),
  CONSTRAINT `FKihj9dqls2vrg79s64ty45px2w` FOREIGN KEY (`standard_id`) REFERENCES `guest` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `endereco` varchar(255) DEFAULT NULL,
  `nome_fantasia` varchar(255) DEFAULT NULL,
  `razao_social` varchar(255) DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKp6t4f5dlq6qi8r68ipnqjmy5t` (`razao_social`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` binary(16) NOT NULL,
  `access_level` enum('ROLE_ADMIN','ROLE_GUEST','ROLE_STANDARD') NOT NULL,
  `email` varchar(255) NOT NULL,
  `fcm_token` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `state` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-27 20:36:28
