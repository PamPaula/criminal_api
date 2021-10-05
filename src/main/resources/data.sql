-- --------------------------------------------------------
-- Servidor:                     127.0.0.1
-- Versão do servidor:           8.0.26 - MySQL Community Server - GPL
-- OS do Servidor:               Win64
-- HeidiSQL Versão:              11.3.0.6337
-- --------------------------------------------------------

SET FOREIGN_KEY_CHECKS=0;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Copiando dados para a tabela criminal_api.advogado: ~3 rows (aproximadamente)
/*!40000 ALTER TABLE `advogado` DISABLE KEYS */;
INSERT INTO `advogado` (`id`, `cpf`, `email`, `nome`, `telefone`, `usuario_id`) VALUES
	(2, '98745632100', 'vitor@vitor.com', 'Vitor', '99999999999', 6),
	(3, '12345678900', 'bruna@bruna.com', 'Bruna', '99999999999', 7);
/*!40000 ALTER TABLE `advogado` ENABLE KEYS */;

-- Copiando dados para a tabela criminal_api.autopsia: ~3 rows (aproximadamente)
/*!40000 ALTER TABLE `autopsia` DISABLE KEYS */;
INSERT INTO `autopsia` (`id`, `data`, `laudo`, `legista_id`, `vitima_id`) VALUES
	(1, '2021-09-09 21:00:00.000000', 'roubo pra brincar', 1, 1),
	(2, '2021-09-09 21:00:00.000000', 'roubo pra brincar', 1, 1),
	(3, '2021-09-09 21:00:00.000000', 'assassinato para comer', 1, 3);
/*!40000 ALTER TABLE `autopsia` ENABLE KEYS */;

-- Copiando dados para a tabela criminal_api.crime: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `crime` DISABLE KEYS */;
INSERT INTO `crime` (`id`, `data`, `descricao`, `criminoso_id`, `vitima_id`) VALUES
	(1, '2021-09-06 21:00:00.000000', 'roubo', 1, 1),
	(3, '2021-09-06 21:00:00.000000', 'assassinato', 1, 1);
/*!40000 ALTER TABLE `crime` ENABLE KEYS */;

-- Copiando dados para a tabela criminal_api.criminoso: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `criminoso` DISABLE KEYS */;
INSERT INTO `criminoso` (`id`, `cpf`, `nome`) VALUES
	(1, '12345678910', 'Tifa'),
	(3, '12345678910', 'Gary');
/*!40000 ALTER TABLE `criminoso` ENABLE KEYS */;

-- Copiando dados para a tabela criminal_api.delegacia: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `delegacia` DISABLE KEYS */;
INSERT INTO `delegacia` (`id`, `batalhao`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `telefone`) VALUES
	(2, 'batalhao', NULL, NULL, NULL, NULL, NULL, NULL, '9999999888'),
	(3, 'brabo', NULL, NULL, NULL, NULL, NULL, NULL, '11234567789');
/*!40000 ALTER TABLE `delegacia` ENABLE KEYS */;

-- Copiando dados para a tabela criminal_api.delegado: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `delegado` DISABLE KEYS */;
INSERT INTO `delegado` (`id`, `funcional`, `nome`, `turno`, `delegacia_id`, `usuario_id`, `email`) VALUES
	(1, '56654324167', 'José', 'noite', 2, NULL, NULL),
	(3, '56654324167', 'Maria', 'noite', 3, NULL, NULL);
/*!40000 ALTER TABLE `delegado` ENABLE KEYS */;

-- Copiando dados para a tabela criminal_api.juiz: ~4 rows (aproximadamente)
/*!40000 ALTER TABLE `juiz` DISABLE KEYS */;
INSERT INTO `juiz` (`id`, `cpf`, `email`, `nome`, `telefone`, `usuario_id`) VALUES
	(1, '12345678900', 'luiz@luiz.com', 'Luiz', '99999999999', 2),
	(2, '12345678900', 'luiz@luiz.com', 'Luiz', '99999999999', 3),
	(3, '12345678900', 'britney@britney.com', 'Britney', '99999999999', NULL);
/*!40000 ALTER TABLE `juiz` ENABLE KEYS */;

-- Copiando dados para a tabela criminal_api.legista: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `legista` DISABLE KEYS */;
INSERT INTO `legista` (`id`, `crm`, `nome`) VALUES
	(1, '123456456', 'Olivia'),
	(3, '123456456', 'Regiane');
/*!40000 ALTER TABLE `legista` ENABLE KEYS */;

-- Copiando dados para a tabela criminal_api.perfil: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `perfil` DISABLE KEYS */;
INSERT INTO `perfil` (`id`, `nome`) VALUES
	(1, 'ADMIN'),
	(2, 'USUARIO');
/*!40000 ALTER TABLE `perfil` ENABLE KEYS */;

-- Copiando dados para a tabela criminal_api.policial: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `policial` DISABLE KEYS */;
INSERT INTO `policial` (`id`, `funcional`, `nome`, `patente`) VALUES
	(1, '9441634', 'Peralta', 'patente'),
	(3, '9441634', 'Lorelay', 'patente');
/*!40000 ALTER TABLE `policial` ENABLE KEYS */;

-- Copiando dados para a tabela criminal_api.prisao: ~3 rows (aproximadamente)
/*!40000 ALTER TABLE `prisao` DISABLE KEYS */;
INSERT INTO `prisao` (`id`, `data`, `criminoso_id`, `delegacia_id`, `delegado_id`, `policial_id`, `vitima_id`) VALUES
	(3, '2021-09-09 21:00:00.000000', 3, 3, 3, 3, NULL),
	(4, '2021-09-09 21:00:00.000000', 1, 3, 1, 1, 1),
	(5, '2021-09-09 21:00:00.000000', 1, 3, 1, 1, 1);
/*!40000 ALTER TABLE `prisao` ENABLE KEYS */;

-- Copiando dados para a tabela criminal_api.usuario: ~6 rows (aproximadamente)
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`id`, `email`, `senha`, `perfil_id`) VALUES
	(1, 'Lulay', '$2a$10$bERcnsrxnphwfVAs9psfS.bDIg4uoqQQ3aRL0Sx9mSX1olLxyZzz.', 1),
	(2, 'luiz@luiz.com', '$2a$10$RjCTSu5Vcxw7phIyRVH9tefiUZd5t1cOjevhknRgUvzEsiqvtu0bm', 1),
	(3, 'britney@britney.com', '$2a$10$F3z5N5lVFfmfMkOUkDKze.ZDLKH1bf5QqKZpli.zdOH0DtL3FWJCG', 1),
	(5, 'luiz@luiz.com', '$2a$10$YreZeJ2py0JKw1GI4i2wau7xz0WWXy39ng9PdC8OX6ew7nYkAHJi2', 1),
	(6, 'vitor@vitor.com', '$2a$10$RtC/M8yZ6XJMqI7aW2UWaO22Xx/1960eK78K.a0.jZWFeGRQaJT/e', 2),
	(7, 'bruna@bruna.com', '$2a$10$a9MQ1I/V1L2G6QSbeN1G.OtdJorOFQbRRytAbokrovcRb.B1q3NGm', 2);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;

-- Copiando dados para a tabela criminal_api.vitima: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `vitima` DISABLE KEYS */;
INSERT INTO `vitima` (`id`, `cpf`, `nome`) VALUES
	(1, '01234567891', 'mariposa'),
	(3, '01234567891', 'passaro'),
	(4, '40735464812', 'rato');
/*!40000 ALTER TABLE `vitima` ENABLE KEYS */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;

SET FOREIGN_KEY_CHECKS=1;
