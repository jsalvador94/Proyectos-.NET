-- phpMyAdmin SQL Dump
-- version 4.1.6
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 04-11-2014 a las 07:29:43
-- Versión del servidor: 5.6.16
-- Versión de PHP: 5.5.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `ventas`
--
CREATE DATABASE IF NOT EXISTS `ventas` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `ventas`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE IF NOT EXISTS `categoria` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`id`, `nombre`) VALUES
(1, 'Pc'),
(2, 'Juegos'),
(4, 'Mouses'),
(5, 'Algo');

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Volcado de datos para la tabla `datosp`
--

INSERT INTO `datosp` (`id`, `nombres`, `apellidos`, `direccion`, `genero`, `dui`, `fechaNac`, `Usuario_id`) VALUES
(1, 'salvador', 'urrutia', 'Usulutan', 'M', '12345678-9', '2014-11-17', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `datos_personales`
--

CREATE TABLE IF NOT EXISTS `datos_personales` (
  `datosid` int(11) NOT NULL AUTO_INCREMENT,
  `apellido` varchar(255) DEFAULT NULL,
  `direccion` longtext,
  `genero` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `usuarioid` int(11) DEFAULT NULL,
  PRIMARY KEY (`datosid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detallev`
--

CREATE TABLE IF NOT EXISTS `detallev` (
  `ndetalle` int(11) NOT NULL AUTO_INCREMENT,
  `precioP` double NOT NULL,
  `cantidadP` int(11) NOT NULL,
  `Venta_nVenta` int(11) DEFAULT NULL,
  `Producto_codigo` int(11) NOT NULL,
  PRIMARY KEY (`ndetalle`),
  KEY `fk_DetalleV_Venta1_idx` (`Venta_nVenta`),
  KEY `fk_DetalleV_Producto1_idx` (`Producto_codigo`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Volcado de datos para la tabla `detallev`
--

INSERT INTO `detallev` (`ndetalle`, `precioP`, `cantidadP`, `Venta_nVenta`, `Producto_codigo`) VALUES
(1, 10, 9, 1, 2),
(2, 3, 2, 1, 4),
(3, 9.5, 4, 3, 7),
(4, 2, 1, 2, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `login`
--

CREATE TABLE IF NOT EXISTS `login` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `marca`
--

CREATE TABLE IF NOT EXISTS `marca` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=27 ;

--
-- Volcado de datos para la tabla `marca`
--

INSERT INTO `marca` (`id`, `nombre`) VALUES
(1, 'Sonny'),
(2, 'Sansumg'),
(3, 'Hp'),
(21, 'Asus'),
(22, 'Panasonic'),
(23, 'Intel'),
(24, 'Celeron'),
(25, 'HDMI'),
(26, 'Kigstone');

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`codigo`, `nombre`, `cantidad`, `fechaRegistro`, `Marca_id`, `Categoria_id`) VALUES
(1, 'USB', 2, '2014-10-04', 3, 2),
(2, 'Joystic', 5, '2014-10-02', 21, 4),
(3, 'Teclado', 2, '2014-10-31', 23, 1),
(4, 'Monitor', 3, '2014-10-04', 22, 1),
(5, 'Compu', 4, '2014-10-16', 21, 1),
(7, 'Camaras', 3, '2014-10-02', 23, 4),
(8, 'DVD', 2, '2014-11-12', 1, 1);

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `email`, `login`, `contrasena`, `Tipo_id`) VALUES
(1, 'j.u.salvador@gmail.com', 'jose', 'jose.123', 1),
(2, 'j.u@gmail.com', 'chepe', 'chepe.123', 2),
(3, '.u@gmail.com', 'chepejuan', '123', 1),
(4, 'j@gmail.com', 'Petronilo', '123', 2),
(5, 'j.uy@gmail.com', 'naila', 'naila.123', 2),
(6, 'j@f.com', 'j', 'j', 2);

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Volcado de datos para la tabla `venta`
--

INSERT INTO `venta` (`nVenta`, `fechaVenta`, `nombreCliente`, `Usuario_id`) VALUES
(1, '2014-11-04', 'Jose ', 1),
(2, '2014-11-06', 'chepe jose', 1),
(3, '2014-11-19', 'juan perez', 1);

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
  ADD CONSTRAINT `fk_DetalleV_Producto1` FOREIGN KEY (`Producto_codigo`) REFERENCES `producto` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_DetalleV_Venta1` FOREIGN KEY (`Venta_nVenta`) REFERENCES `venta` (`nVenta`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `fk_Producto_Categoria1` FOREIGN KEY (`Categoria_id`) REFERENCES `categoria` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Producto_Marca1` FOREIGN KEY (`Marca_id`) REFERENCES `marca` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

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
