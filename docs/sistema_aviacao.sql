-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 20-Maio-2026 às 19:29
-- Versão do servidor: 10.4.22-MariaDB
-- versão do PHP: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `sistema_aviacao`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `aeronave`
--

CREATE TABLE `aeronave` (
  `id_aeronave` int(5) NOT NULL,
  `modelo` varchar(80) DEFAULT NULL,
  `capacidade` int(3) DEFAULT NULL,
  `envergadura` int(3) DEFAULT NULL,
  `fabricante` varchar(80) DEFAULT NULL,
  `status_ativo` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `aeronave`
--

INSERT INTO `aeronave` (`id_aeronave`, `modelo`, `capacidade`, `envergadura`, `fabricante`, `status_ativo`) VALUES
(0, 'Boeing 737-300', 180, 90, 'Boeing', 'S'),
(1, '737-300', 180, 90, 'BOEING', 'A'),
(2, '777-400', 350, 120, 'BOEING', 'A'),
(3, 'Boeing 787-9 Dreamliner', 290, 118, 'Boeing', 'S'),
(4, 'Airbus A319', 144, 89, 'Airbus', 'S'),
(5, 'Airbus A320', 174, 92, 'Airbus', 'S'),
(6, 'Airbus A321', 220, 98, 'Airbus', 'S'),
(7, 'Airbus A330-901', 300, 121, 'Airbus', 'S'),
(8, 'Embraer E195-E2', 136, 85, 'Embraer', 'S'),
(9, 'ATR 72-600', 70, 28, 'ATR', 'S');

-- --------------------------------------------------------

--
-- Estrutura da tabela `aeronave_cia`
--

CREATE TABLE `aeronave_cia` (
  `id_aeronave` int(5) NOT NULL,
  `id_cia` int(5) NOT NULL,
  `data_aquisicao` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `aeronave_cia`
--

INSERT INTO `aeronave_cia` (`id_aeronave`, `id_cia`, `data_aquisicao`) VALUES
(0, 11110, '2018-03-10'),
(1, 11111, '2019-06-15'),
(2, 11112, '2020-01-20'),
(3, 11113, '2021-08-05'),
(4, 11114, '2017-11-12'),
(5, 11115, '2022-04-18'),
(6, 11116, '2023-02-25'),
(7, 11117, '2024-04-21'),
(8, 11118, '2024-01-14'),
(9, 11119, '2025-05-22');

-- --------------------------------------------------------

--
-- Estrutura da tabela `aeroporto`
--

CREATE TABLE `aeroporto` (
  `cod_aeroporto` int(4) NOT NULL,
  `nome` varchar(30) DEFAULT NULL,
  `sigla` char(3) DEFAULT NULL,
  `cidade` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `aeroporto`
--

INSERT INTO `aeroporto` (`cod_aeroporto`, `nome`, `sigla`, `cidade`) VALUES
(1110, 'Aeroporto de Guarulhos', 'GRU', 'Sao Paulo'),
(1111, 'Aeroporto de Congonhas', 'CGH', 'Sao Paulo'),
(1112, 'Aeroporto de Viracopos', 'VCP', 'Campinas'),
(1113, 'Aeroporto Santos Dumont', 'SDU', 'Rio de Janeiro'),
(1114, 'Aeroporto do Galeao', 'GIG', 'Rio de Janeiro'),
(1115, 'Aeroporto de Brasilia', 'BSB', 'Brasilia'),
(1116, 'Aeroporto de Confins', 'CNF', 'Belo Horizonte'),
(1117, 'Aeroporto Salgado Filho', 'POA', 'Porto Alegre'),
(1118, 'Aeroporto Afonso Pena', 'CWB', 'Curitiba'),
(1119, 'Aeroporto de Recife', 'REC', 'Recife');

-- --------------------------------------------------------

--
-- Estrutura da tabela `cia_aerea`
--

CREATE TABLE `cia_aerea` (
  `id_cia` int(5) NOT NULL,
  `nome` varchar(80) DEFAULT NULL,
  `cnpj` varchar(15) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `status_ativo` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `cia_aerea`
--

INSERT INTO `cia_aerea` (`id_cia`, `nome`, `cnpj`, `email`, `status_ativo`) VALUES
(11110, 'GOL Linhas A?reas', '111111111111110', 'gol@gol.com.br', 'S'),
(11111, 'LATAM Airlines Brasil', '111111111111111', 'latam@latam.com.br', 'S'),
(11112, 'Azul Linhas A?reas', '111111111111112', 'azul@azul.com.br', 'S'),
(11113, 'Voepass Linhas A?reas', '111111111111113', 'voepass@voepass.com.br', 'S'),
(11114, 'Avianca Brasil', '111111111111114', 'avianca@avianca.com.br', 'S'),
(11115, 'Sky Brasil', '111111111111115', 'sky@sky.com.br', 'S'),
(11116, 'AeroSul Linhas A?reas', '111111111111116', 'aerosul@aerosul.com.br', 'S'),
(11117, 'Brasil Air Express', '111111111111117', 'brasilair@brasilair.com.br', 'S'),
(11118, 'Atl?ntica Airlines', '111111111111118', 'atlantica@atlantica.com.br', 'S'),
(11119, 'Cruzeiro do Sul Avia??o', '111111111111119', 'cruzeiro@cruzeiro.com.br', 'S');

-- --------------------------------------------------------

--
-- Estrutura da tabela `passageiro`
--

CREATE TABLE `passageiro` (
  `id_passageiro` int(5) NOT NULL,
  `nome` varchar(30) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `tel` varchar(15) DEFAULT NULL,
  `dt_nasc` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `passageiro`
--

INSERT INTO `passageiro` (`id_passageiro`, `nome`, `email`, `tel`, `dt_nasc`) VALUES
(0, 'Caio Marcio', 'caio@email.com', '21965399168', '1993-03-08'),
(1, 'Rog?rio Bailly', 'bailly@email.com', '21959875636', '1057-03-25'),
(2, 'Raffos', 'raffos@email.com', '21942659875', '1997-04-25'),
(3, 'Ana Lima', 'Ana@email.com', '21945626987', '2000-12-11'),
(4, 'Laura Dias', 'Laura@email.com', '21965849874', '1999-11-05'),
(5, 'Emanuel', 'Emanuel@email.com', '21934589756', '1998-12-31'),
(6, 'Maria', 'Maria@email.com', '21987456123', '2005-08-08'),
(7, 'Gabriela', 'Gabriela@email.com', '21989554652', '2007-02-04'),
(8, 'Juliana Machia', 'Juliana@email.com', '21987845621', '2004-01-06'),
(9, 'Lira', 'Lira@email.com', '21987846518', '2001-03-04');

-- --------------------------------------------------------

--
-- Estrutura da tabela `reserva`
--

CREATE TABLE `reserva` (
  `cod_reserva` varchar(10) NOT NULL,
  `id_passageiro` int(5) DEFAULT NULL,
  `cod_voo` varchar(10) DEFAULT NULL,
  `assento` varchar(3) DEFAULT NULL,
  `dt_reserva` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `reserva`
--

INSERT INTO `reserva` (`cod_reserva`, `id_passageiro`, `cod_voo`, `assento`, `dt_reserva`) VALUES
('R000', 0, 'G31001', '12A', '2026-03-25'),
('R001', 1, 'LA2002', '14B', '2026-03-25'),
('R002', 2, 'AZ3003', '08C', '2026-03-25'),
('R003', 3, 'VP4004', '16D', '2026-03-25'),
('R004', 4, 'AV5005', '10A', '2026-03-25'),
('R005', 5, 'SK6006', '22F', '2026-03-25'),
('R006', 6, 'AS7007', '18B', '2026-03-25'),
('R007', 7, 'BE8008', '05C', '2026-03-25'),
('R008', 8, 'AT9009', '11D', '2026-03-25'),
('R009', 9, 'CS1010', '03A', '2026-03-25');

-- --------------------------------------------------------

--
-- Estrutura da tabela `voo`
--

CREATE TABLE `voo` (
  `cod_voo` varchar(10) NOT NULL,
  `hora_partida` int(4) DEFAULT NULL,
  `hora_chegada` int(4) DEFAULT NULL,
  `cod_aeroporto` int(4) DEFAULT NULL,
  `cidade_origem` varchar(20) DEFAULT NULL,
  `cidade_destino` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `voo`
--

INSERT INTO `voo` (`cod_voo`, `hora_partida`, `hora_chegada`, `cod_aeroporto`, `cidade_origem`, `cidade_destino`) VALUES
('AS7007', 1330, 1430, 1116, 'Belo Horizonte', 'Monaco'),
('AT9009', 1745, 615, 1118, 'Curitiba', 'Milao'),
('AV5005', 1530, 2200, 1114, 'Rio de Janeiro', 'Singapura'),
('AZ3003', 1200, 2000, 1112, 'Campinas', 'Nova York'),
('BE8008', 615, 1745, 1117, 'Porto Alegre', 'Los Angeles'),
('CS1010', 2100, 500, 1119, 'Recife', 'Dubai'),
('G31001', 830, 1600, 1110, 'Sao Paulo', 'Paris'),
('LA2002', 945, 1100, 1111, 'Sao Paulo', 'Londres'),
('SK6006', 1015, 2300, 1115, 'Brasilia', 'Zurique'),
('VP4004', 700, 2100, 1113, 'Rio de Janeiro', 'Toquio');

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `aeronave`
--
ALTER TABLE `aeronave`
  ADD PRIMARY KEY (`id_aeronave`);

--
-- Índices para tabela `aeronave_cia`
--
ALTER TABLE `aeronave_cia`
  ADD PRIMARY KEY (`id_aeronave`,`id_cia`),
  ADD KEY `fk_id_cia` (`id_cia`),
  ADD KEY `fk_id_aeronave` (`id_aeronave`);

--
-- Índices para tabela `aeroporto`
--
ALTER TABLE `aeroporto`
  ADD PRIMARY KEY (`cod_aeroporto`);

--
-- Índices para tabela `cia_aerea`
--
ALTER TABLE `cia_aerea`
  ADD PRIMARY KEY (`id_cia`);

--
-- Índices para tabela `passageiro`
--
ALTER TABLE `passageiro`
  ADD PRIMARY KEY (`id_passageiro`);

--
-- Índices para tabela `reserva`
--
ALTER TABLE `reserva`
  ADD PRIMARY KEY (`cod_reserva`),
  ADD KEY `fk_passageiro` (`id_passageiro`),
  ADD KEY `fk_id_voo` (`cod_voo`);

--
-- Índices para tabela `voo`
--
ALTER TABLE `voo`
  ADD PRIMARY KEY (`cod_voo`),
  ADD KEY `fk_aeroporto` (`cod_aeroporto`);

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `aeronave_cia`
--
ALTER TABLE `aeronave_cia`
  ADD CONSTRAINT `fk_id_aeronave` FOREIGN KEY (`id_aeronave`) REFERENCES `aeronave` (`id_aeronave`),
  ADD CONSTRAINT `fk_id_cia` FOREIGN KEY (`id_cia`) REFERENCES `cia_aerea` (`id_cia`);

--
-- Limitadores para a tabela `reserva`
--
ALTER TABLE `reserva`
  ADD CONSTRAINT `fk_id_voo` FOREIGN KEY (`cod_voo`) REFERENCES `voo` (`cod_voo`),
  ADD CONSTRAINT `fk_passageiro` FOREIGN KEY (`id_passageiro`) REFERENCES `passageiro` (`id_passageiro`);

--
-- Limitadores para a tabela `voo`
--
ALTER TABLE `voo`
  ADD CONSTRAINT `fk_aeroporto` FOREIGN KEY (`cod_aeroporto`) REFERENCES `aeroporto` (`cod_aeroporto`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
