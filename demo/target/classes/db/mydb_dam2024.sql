-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 01-06-2024 a las 15:04:23
-- Versión del servidor: 10.4.27-MariaDB
-- Versión de PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `mydb`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `apartamento`
--

CREATE TABLE `apartamento` (
  `ID_Apartamento` int(11) NOT NULL,
  `ubicacion` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `apartamento`
--

INSERT INTO `apartamento` (`ID_Apartamento`, `ubicacion`) VALUES
(1, 'Avenida Desengaño 21');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `gastos`
--

CREATE TABLE `gastos` (
  `ID_Gastos` int(11) NOT NULL,
  `apartamentoID` int(11) NOT NULL,
  `iva` double DEFAULT NULL,
  `total_con_IVA` double DEFAULT NULL,
  `total_gasto` double DEFAULT NULL,
  `NIF_proveedor` varchar(9) DEFAULT NULL,
  `nombre_proveedor` varchar(45) DEFAULT NULL,
  `gasto_concepto` varchar(90) DEFAULT NULL,
  `pagoCompletado` tinyint(4) DEFAULT NULL,
  `fecha` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `gastos`
--

INSERT INTO `gastos` (`ID_Gastos`, `apartamentoID`, `iva`, `total_con_IVA`, `total_gasto`, `NIF_proveedor`, `nombre_proveedor`, `gasto_concepto`, `pagoCompletado`, `fecha`) VALUES
(1, 1, 21, 1500, 1980, '98765432Z', 'José Viruta', 'un gasto muy importante', 1, '2024-06-01');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ingresos`
--

CREATE TABLE `ingresos` (
  `ID_Ingresos` int(11) NOT NULL,
  `Persona_DNI` varchar(9) NOT NULL,
  `intermediario` int(11) NOT NULL,
  `apartamento` int(11) NOT NULL,
  `tarifa` int(11) NOT NULL,
  `fecha_entrada` date DEFAULT NULL,
  `fecha_salida` date DEFAULT NULL,
  `num_coches` int(11) DEFAULT NULL,
  `num_personas` int(11) DEFAULT NULL,
  `descuento` double DEFAULT NULL,
  `total_iva` double DEFAULT NULL,
  `total_factura` double DEFAULT NULL,
  `observaciones` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ingresos`
--

INSERT INTO `ingresos` (`ID_Ingresos`, `Persona_DNI`, `intermediario`, `apartamento`, `tarifa`, `fecha_entrada`, `fecha_salida`, `num_coches`, `num_personas`, `descuento`, `total_iva`, `total_factura`, `observaciones`) VALUES
(1, '12345678Z', 1, 1, 1, '2024-06-01', '2024-06-01', 1, 2, 10, 21, 2000, 'muy bonita la piscina'),
(2, '12345678Z', 1, 1, 1, '2024-06-01', '2024-06-01', 3, 6, 21, 15, 3600, 'increible la pizzeria de abajo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `intermediarios`
--

CREATE TABLE `intermediarios` (
  `ID_Intermediario` int(11) NOT NULL,
  `nombre_intermediario` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `intermediarios`
--

INSERT INTO `intermediarios` (`ID_Intermediario`, `nombre_intermediario`) VALUES
(1, 'Copinsa SA');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persona`
--

CREATE TABLE `persona` (
  `DNI` varchar(9) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `apellidos` varchar(45) DEFAULT NULL,
  `telefono` int(11) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `persona`
--

INSERT INTO `persona` (`DNI`, `nombre`, `apellidos`, `telefono`, `password`) VALUES
('12345678Z', 'Usuario', 'De Prueba', 123123987, '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tarifa`
--

CREATE TABLE `tarifa` (
  `ID_Tarifa` int(11) NOT NULL,
  `tipo` varchar(45) DEFAULT NULL,
  `precio_base` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tarifa`
--

INSERT INTO `tarifa` (`ID_Tarifa`, `tipo`, `precio_base`) VALUES
(1, 'descuento verano romántico', 300);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `apartamento`
--
ALTER TABLE `apartamento`
  ADD PRIMARY KEY (`ID_Apartamento`);

--
-- Indices de la tabla `gastos`
--
ALTER TABLE `gastos`
  ADD PRIMARY KEY (`ID_Gastos`),
  ADD KEY `fk_Gastos_Apartamento1_idx` (`apartamentoID`);

--
-- Indices de la tabla `ingresos`
--
ALTER TABLE `ingresos`
  ADD PRIMARY KEY (`ID_Ingresos`),
  ADD KEY `fk_Ingresos_Persona2_idx` (`Persona_DNI`),
  ADD KEY `fk_Ingresos_Intermediarios1_idx` (`intermediario`),
  ADD KEY `fk_Ingresos_Apartamento2_idx` (`apartamento`),
  ADD KEY `fk_Ingresos_Tarifa2_idx` (`tarifa`);

--
-- Indices de la tabla `intermediarios`
--
ALTER TABLE `intermediarios`
  ADD PRIMARY KEY (`ID_Intermediario`);

--
-- Indices de la tabla `persona`
--
ALTER TABLE `persona`
  ADD PRIMARY KEY (`DNI`);

--
-- Indices de la tabla `tarifa`
--
ALTER TABLE `tarifa`
  ADD PRIMARY KEY (`ID_Tarifa`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `apartamento`
--
ALTER TABLE `apartamento`
  MODIFY `ID_Apartamento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `gastos`
--
ALTER TABLE `gastos`
  MODIFY `ID_Gastos` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `ingresos`
--
ALTER TABLE `ingresos`
  MODIFY `ID_Ingresos` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `intermediarios`
--
ALTER TABLE `intermediarios`
  MODIFY `ID_Intermediario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `tarifa`
--
ALTER TABLE `tarifa`
  MODIFY `ID_Tarifa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `gastos`
--
ALTER TABLE `gastos`
  ADD CONSTRAINT `fk_Gastos_Apartamento1` FOREIGN KEY (`apartamentoID`) REFERENCES `apartamento` (`ID_Apartamento`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Filtros para la tabla `ingresos`
--
ALTER TABLE `ingresos`
  ADD CONSTRAINT `fk_Ingresos_Apartamento2` FOREIGN KEY (`apartamento`) REFERENCES `apartamento` (`ID_Apartamento`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Ingresos_Intermediarios1` FOREIGN KEY (`intermediario`) REFERENCES `intermediarios` (`ID_Intermediario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Ingresos_Persona2` FOREIGN KEY (`Persona_DNI`) REFERENCES `persona` (`DNI`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Ingresos_Tarifa2` FOREIGN KEY (`tarifa`) REFERENCES `tarifa` (`ID_Tarifa`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
