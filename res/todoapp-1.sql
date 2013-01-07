-- phpMyAdmin SQL Dump
-- version 3.2.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Erstellungszeit: 07. Januar 2013 um 11:46
-- Server Version: 5.1.44
-- PHP-Version: 5.3.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `todoapp`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `todo`
--

DROP TABLE IF EXISTS `todo`;
CREATE TABLE IF NOT EXISTS `todo` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `place` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `placemark_latitude` decimal(9,6) DEFAULT NULL,
  `placemark_longitude` decimal(9,6) DEFAULT NULL,
  `details` varchar(4000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `dueAt` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `done` varchar(5) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'false',
  `radius` int(10) unsigned DEFAULT NULL,
  `isDeleted` tinyint(1) NOT NULL DEFAULT '0',
  `modifiedAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=159 ;
