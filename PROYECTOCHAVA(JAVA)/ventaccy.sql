-- phpMyAdmin SQL Dump
-- version 4.1.6
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 11-11-2014 a las 04:32:12
-- Versión del servidor: 5.6.16
-- Versión de PHP: 5.5.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `ventaccy`
--
CREATE DATABASE IF NOT EXISTS `ventaccy` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `ventaccy`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE IF NOT EXISTS `categoria` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`id`, `nombre`) VALUES
(1, 'otra');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `datosp`
--

CREATE TABLE IF NOT EXISTS `datosp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombres` varchar(45) NOT NULL,
  `apellidos` varchar(45) NOT NULL,
  `direccion` text NOT NULL,
  `genero` enum('M','F') NOT NULL,
  `dui` varchar(10) NOT NULL,
  `fechaNac` date NOT NULL,
  `Usuario_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_DatosP_Usuario1_idx` (`Usuario_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detallev`
--

CREATE TABLE IF NOT EXISTS `detallev` (
  `ndetalle` int(11) NOT NULL AUTO_INCREMENT,
  `precioP` double NOT NULL,
  `cantidadP` int(11) NOT NULL,
  `Venta_nVenta` int(11) NOT NULL,
  `Producto_codigo` int(11) NOT NULL,
  PRIMARY KEY (`ndetalle`),
  KEY `fk_DetalleV_Venta1_idx` (`Venta_nVenta`),
  KEY `fk_DetalleV_Producto1_idx` (`Producto_codigo`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Volcado de datos para la tabla `detallev`
--

INSERT INTO `detallev` (`ndetalle`, `precioP`, `cantidadP`, `Venta_nVenta`, `Producto_codigo`) VALUES
(1, 8.4, 5, 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `marca`
--

CREATE TABLE IF NOT EXISTS `marca` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Volcado de datos para la tabla `marca`
--

INSERT INTO `marca` (`id`, `nombre`) VALUES
(1, 'algo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE IF NOT EXISTS `producto` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `fechaRegistro` date NOT NULL,
  `Marca_id` int(11) NOT NULL,
  `Categoria_id` int(11) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_Producto_Marca1_idx` (`Marca_id`),
  KEY `fk_Producto_Categoria1_idx` (`Categoria_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`codigo`, `nombre`, `cantidad`, `fechaRegistro`, `Marca_id`, `Categoria_id`) VALUES
(1, 'perol', 3, '2014-11-06', 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo`
--

CREATE TABLE IF NOT EXISTS `tipo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipoU` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Volcado de datos para la tabla `tipo`
--

INSERT INTO `tipo` (`id`, `tipoU`) VALUES
(1, 'A'),
(2, 'U');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `login` varchar(45) NOT NULL,
  `contrasena` varchar(45) NOT NULL,
  `Tipo_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Usuario_Tipo1_idx` (`Tipo_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `email`, `login`, `contrasena`, `Tipo_id`) VALUES
(1, 'cecilia@gmail.com', 'cecy', 'cecy.123', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `venta`
--

CREATE TABLE IF NOT EXISTS `venta` (
  `nVenta` int(11) NOT NULL AUTO_INCREMENT,
  `fechaVenta` date NOT NULL,
  `nombreCliente` varchar(45) DEFAULT NULL,
  `Usuario_id` int(11) NOT NULL,
  PRIMARY KEY (`nVenta`),
  KEY `fk_Venta_Usuario1_idx` (`Usuario_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Volcado de datos para la tabla `venta`
--

INSERT INTO `venta` (`nVenta`, `fechaVenta`, `nombreCliente`, `Usuario_id`) VALUES
(1, '2014-11-13', 'yo', 1);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `datosp`
--
ALTER TABLE `datosp`
  ADD CONSTRAINT `fk_DatosP_Usuario1` FOREIGN KEY (`Usuario_id`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `detallev`
--
ALTER TABLE `detallev`
  ADD CONSTRAINT `fk_DetalleV_Venta1` FOREIGN KEY (`Venta_nVenta`) REFERENCES `venta` (`nVenta`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_DetalleV_Producto1` FOREIGN KEY (`Producto_codigo`) REFERENCES `producto` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `fk_Producto_Marca1` FOREIGN KEY (`Marca_id`) REFERENCES `marca` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Producto_Categoria1` FOREIGN KEY (`Categoria_id`) REFERENCES `categoria` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `fk_Usuario_Tipo1` FOREIGN KEY (`Tipo_id`) REFERENCES `tipo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `venta`
--
ALTER TABLE `venta`
  ADD CONSTRAINT `fk_Venta_Usuario1` FOREIGN KEY (`Usuario_id`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
