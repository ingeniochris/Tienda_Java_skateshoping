-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 12-10-2019 a las 21:28:15
-- Versión del servidor: 10.3.16-MariaDB
-- Versión de PHP: 7.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `huarache2`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `cod_cli` varchar(20) NOT NULL,
  `nom_cli` varchar(30) NOT NULL,
  `ape_cli` varchar(30) NOT NULL,
  `ape_ma_cli` varchar(30) NOT NULL,
  `sexo_cli` varchar(1) NOT NULL,
  `rfc_cli` varchar(15) NOT NULL,
  `tel_cli` varchar(10) NOT NULL,
  `email_cli` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`cod_cli`, `nom_cli`, `ape_cli`, `ape_ma_cli`, `sexo_cli`, `rfc_cli`, `tel_cli`, `email_cli`) VALUES
('SKATE0001', 'jesus christian', 'castillo', 'lozano', 'M', 'calj860123', '573363865', 'calj860123'),
('SKATE0002', 'diego', 'caishpal', 'reyes', 'M', 'card000000', '5555555', 'diego@hotmail.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalleventa`
--

CREATE TABLE `detalleventa` (
  `num_bol` varchar(10) NOT NULL,
  `cod_pro` varchar(15) NOT NULL,
  `des_pro` varchar(30) NOT NULL,
  `cant_pro` varchar(3) NOT NULL,
  `pre_unit` varchar(10) NOT NULL,
  `pre_venta` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `detalleventa`
--

INSERT INTO `detalleventa` (`num_bol`, `cod_pro`, `des_pro`, `cant_pro`, `pre_unit`, `pre_venta`) VALUES
('00000001', 'COA-MERCA0002', 'supreme bruce lee', '2', '10000', '20000.0'),
('00000002', 'COA-MERCA0004', 'tenis', '10', '25', '250.0');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `id_puesto`
--

CREATE TABLE `id_puesto` (
  `id_puesto` int(11) NOT NULL,
  `tipo` varchar(35) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `id_puesto`
--

INSERT INTO `id_puesto` (`id_puesto`, `tipo`) VALUES
(1, 'administrador'),
(2, 'empleado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `cod_pro` varchar(15) NOT NULL,
  `descripcion` varchar(30) NOT NULL,
  `precio` varchar(10) NOT NULL,
  `stock` varchar(10) NOT NULL,
  `id_tipo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`cod_pro`, `descripcion`, `precio`, `stock`, `id_tipo`) VALUES
('COA-MERCA0001', 'air 270 8', '5000', '5', 1),
('COA-MERCA0002', 'supreme bruce lee', '10000', '0', 2),
('COA-MERCA0003', 'phantom', '800', '50', 3),
('COA-MERCA0004', 'tenis', '25', '40', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipoproducto`
--

CREATE TABLE `tipoproducto` (
  `id_tipo` int(11) NOT NULL,
  `descrip` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tipoproducto`
--

INSERT INTO `tipoproducto` (`id_tipo`, `descrip`) VALUES
(1, 'sneakers'),
(2, 'boards'),
(3, 'truks'),
(4, 'wheels'),
(5, 'wear');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `idusuario` int(11) NOT NULL,
  `nick` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `id_puesto` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`idusuario`, `nick`, `password`, `id_puesto`) VALUES
(1, 'chris', '12345', 1),
(3, 'diego', '123', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `venta`
--

CREATE TABLE `venta` (
  `num_bol` varchar(10) NOT NULL,
  `cod_cli` varchar(20) NOT NULL,
  `pre_tot` varchar(10) NOT NULL,
  `fecha` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `venta`
--

INSERT INTO `venta` (`num_bol`, `cod_cli`, `pre_tot`, `fecha`) VALUES
('00000001', 'SKATE0001', '20000.0', '30/03/2018'),
('00000002', 'SKATE0002', '250.0', '12/10/2019');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`cod_cli`);

--
-- Indices de la tabla `detalleventa`
--
ALTER TABLE `detalleventa`
  ADD KEY `num_bol` (`num_bol`),
  ADD KEY `cod_pro` (`cod_pro`);

--
-- Indices de la tabla `id_puesto`
--
ALTER TABLE `id_puesto`
  ADD PRIMARY KEY (`id_puesto`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`cod_pro`,`descripcion`),
  ADD KEY `id_tipo` (`id_tipo`);

--
-- Indices de la tabla `tipoproducto`
--
ALTER TABLE `tipoproducto`
  ADD PRIMARY KEY (`id_tipo`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`idusuario`),
  ADD KEY `id_puesto` (`id_puesto`);

--
-- Indices de la tabla `venta`
--
ALTER TABLE `venta`
  ADD PRIMARY KEY (`num_bol`),
  ADD KEY `cod_cli` (`cod_cli`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `idusuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalleventa`
--
ALTER TABLE `detalleventa`
  ADD CONSTRAINT `detalleventa_ibfk_1` FOREIGN KEY (`cod_pro`) REFERENCES `producto` (`cod_pro`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detalleventa_ibfk_2` FOREIGN KEY (`num_bol`) REFERENCES `venta` (`num_bol`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `producto_ibfk_1` FOREIGN KEY (`id_tipo`) REFERENCES `tipoproducto` (`id_tipo`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`id_puesto`) REFERENCES `id_puesto` (`id_puesto`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
