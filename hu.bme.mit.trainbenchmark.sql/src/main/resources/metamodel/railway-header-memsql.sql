--
-- Database: `trainbenchmark`
--

DROP DATABASE IF EXISTS `trainbenchmark`;
CREATE DATABASE `trainbenchmark`;
USE `trainbenchmark`;

START TRANSACTION;
-- --------------------------------------------------------

--
-- Table structure: `Route`
--

CREATE TABLE IF NOT EXISTS `Route` (
  `id` int NOT NULL AUTO_INCREMENT,
  `entry` int,
  `exit` int,
  PRIMARY KEY  (`id`)
) DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ENGINE=MEMORY;

-- --------------------------------------------------------

--
-- Table structure: `definedBy`
--

CREATE TABLE IF NOT EXISTS `definedBy` (
  `Route_id` int NOT NULL,
  `Sensor_id` int NOT NULL,
  PRIMARY KEY  (`Route_id`, `Sensor_id`)
) DEFAULT CHARSET=utf8 ENGINE=MEMORY;

-- --------------------------------------------------------

--
-- Table structure: `Segment`
--

CREATE TABLE IF NOT EXISTS `Segment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `length` int NOT NULL DEFAULT 1,
  PRIMARY KEY  (`id`)
) DEFAULT CHARSET=utf8 ENGINE=MEMORY;

-- --------------------------------------------------------

--
-- Table structure: `Sensor`
--

CREATE TABLE IF NOT EXISTS `Sensor` (
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY  (`id`)
) DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ENGINE=MEMORY;

-- --------------------------------------------------------

--
-- Table structure: `Semaphore`
--

CREATE TABLE IF NOT EXISTS `Semaphore` (
  `id` int NOT NULL AUTO_INCREMENT,
  `signal` int NOT NULL,
  PRIMARY KEY  (`id`)
) DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ENGINE=MEMORY;

-- --------------------------------------------------------


--
-- Table structure: `Switch`
--

CREATE TABLE IF NOT EXISTS `Switch` (
  `id` int NOT NULL AUTO_INCREMENT,
  `currentPosition` int,
  PRIMARY KEY  (`id`)
) DEFAULT CHARSET=utf8 ENGINE=MEMORY;

-- --------------------------------------------------------

--
-- Table structure: `SwitchPosition`
--

CREATE TABLE IF NOT EXISTS `SwitchPosition` (
  `id` int NOT NULL AUTO_INCREMENT,
  `follows` int, -- inverse of the route edge
  `switch` int NOT NULL,
  `position` int NOT NULL,
  PRIMARY KEY  (`id`)
) DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ENGINE=MEMORY;

-- --------------------------------------------------------

--
-- Table structure: `TrackElement`
--

CREATE TABLE IF NOT EXISTS `TrackElement` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sensor` int,
  PRIMARY KEY  (`id`)
) DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ENGINE=MEMORY;

-- --------------------------------------------------------

--
-- Table structure: `TrackElement_connectsTo`
--

CREATE TABLE IF NOT EXISTS `connectsTo` (
  `TrackElement_id` int NOT NULL,
  `TrackElement_id_connectsTo` int NOT NULL,
  PRIMARY KEY  (`TrackElement_id`, `TrackElement_id_connectsTo`)
) DEFAULT CHARSET=utf8 ENGINE=MEMORY;

