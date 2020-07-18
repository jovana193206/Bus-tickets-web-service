-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 23, 2018 at 02:59 PM
-- Server version: 10.1.34-MariaDB
-- PHP Version: 7.2.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `piaprojekat`
--

-- --------------------------------------------------------

--
-- Table structure for table `autobusi`
--

CREATE TABLE `autobusi` (
  `id` int(11) NOT NULL,
  `marka` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `model` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `brSedista` int(11) NOT NULL,
  `slike` text COLLATE utf8_unicode_ci,
  `slobodan` int(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `autobusi`
--

INSERT INTO `autobusi` (`id`, `marka`, `model`, `brSedista`, `slike`, `slobodan`) VALUES
(1, 'Mercedes-Benz', 'Tourismo', 57, './resources/photos/MBTourismo1.jpg, ./resources/photos/MBTourismo2.jpg, ./resources/photos/MBTourismo3.jpg, ./resources/photos/MBTourismo4.jpg, ./resources/photos/MBTourismo5.jpg', 0),
(2, 'Mercedes-Benz', 'Tourismo', 57, './resources/photos/MBTourismo1.jpg, ./resources/photos/MBTourismo2.jpg, ./resources/photos/MBTourismo3.jpg', 1),
(3, 'Mercedes-Benz', 'Tourismo', 57, './resources/photos/MBTourismo1.jpg, ./resources/photos/MBTourismo2.jpg, ./resources/photos/MBTourismo3.jpg', 1),
(4, 'Volvo', 'B12B 6X2', 67, './resources/photos/volvoB12B1.jpg, ./resources/photos/volvoB12B2.jpg, ./resources/photos/volvoB12B3.jpg', 0),
(5, 'Volvo', 'B12B 6X2', 67, './resources/photos/volvoB12B1.jpg, ./resources/photos/volvoB12B2.jpg, ./resources/photos/volvoB12B3.jpg', 1),
(19, 'Moj autobus', 'model2', 50, './resources/photos/bus-1156729038590063395.jpg, ./resources/photos/bus1-7703572438046131647.jpg, ./resources/photos/bus2-4476629801046035211.jpg, ./resources/photos/bus3-3884401260593054051.jpg, ./resources/photos/bus4-2046890512682621178.jpg', 0);

-- --------------------------------------------------------

--
-- Table structure for table `cenemarkica`
--

CREATE TABLE `cenemarkica` (
  `id` int(11) NOT NULL,
  `kategorijakorisnika` varchar(21) COLLATE utf8_unicode_ci NOT NULL,
  `godisnja` int(1) NOT NULL,
  `cena` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `cenemarkica`
--

INSERT INTO `cenemarkica` (`id`, `kategorijakorisnika`, `godisnja`, `cena`) VALUES
(1, 'nezaposlen', 1, 10560),
(2, 'nezaposlen', 0, 1060),
(3, 'zaposlen', 1, 13740),
(4, 'zaposlen', 0, 1500),
(5, 'student', 1, 10550),
(6, 'student', 0, 1250),
(7, 'lice sa invaliditetom', 1, 8000),
(8, 'lice sa invaliditetom', 0, 730),
(9, 'penzioner', 1, 1000),
(10, 'penzioner', 0, 150);

-- --------------------------------------------------------

--
-- Table structure for table `gradske`
--

CREATE TABLE `gradske` (
  `brLinije` int(11) NOT NULL,
  `polaziste` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `odrediste` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `medjustanice` mediumtext COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `gradske`
--

INSERT INTO `gradske` (`brLinije`, `polaziste`, `odrediste`, `medjustanice`) VALUES
(45, 'Zemun/Novi Grad', 'Novi Beograd/Blok 44', 'Kupreska-Krajiska-Jaksiceva-Slovenska-Geteova-Atanasija Pulje-Hercegovacka-Rada Koncara-Ugrinovacka-Novogradska-Cara Dusana-Trg Branka Radicevica-Glavna-Avijaticarski trg-Nikolaja Ostrovskog-22. oktobra-Vrtlarska-Iviceva-Tosin bunar-Zemunska-Vojvodjanska-Dr Ivana Ribara-Jurija Gagarina-Nehruova'),
(56, 'Zeleni Venac', 'Petlovo Brdo', 'Jug Bogdanova-Kraljice Natalije-Kneza Milosa-Mostar-Bulevar vojvode Misica-Radnicka-Savska magistrala-Svetolika Lazarevica Laze-Radnicka-Marsala Tolbuhina-Trgovacka-Ibarska magistrala-Jedanaeste krajiske divizije-Oplenacka-Georgija Ostrogorskog-Milorada Draskovića'),
(68, 'Novi Beograd/blok 70', 'Zeleni Venac', 'Gandijeva-Jurija Gagarina-Omladinskih brigada-Bulevar Zorana Djindjica-Milentija Popovica-Bulevar Mihaila Pupina-Brankov most-Brankova '),
(73, 'Novi Beograd/Blok 45', 'Batajnica/zeleznicka stanica', 'dr Ivana Ribara-Jurija Gagarina-Omladinskih brigada-Bulevar Mihaila Pupina-Aleksandra Dubčeka-Nikolaja Ostrovskog-Avijatičarski trg-Glavna-Trg Branka Radičevića-Cara Dušana-Batajnički put-Majora Zorana Radosavljevića-Carice Jelene-Matrozova'),
(75, 'Bezanijska Kosa', 'Zeleni Venac', 'dr. Ivana Ribara-Nedeljka Gvozdenovica-dr Huga Klajna-Marka Celebonovica-Partizanske avijacije-Stari put Beograd-Zagreb-Studentska-Pariske komune-Bulevar Mihaila Pupina-Brankov most-Brankova ');

-- --------------------------------------------------------

--
-- Table structure for table `koordinate`
--

CREATE TABLE `koordinate` (
  `id` int(11) NOT NULL,
  `grad` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `koordinate`
--

INSERT INTO `koordinate` (`id`, `grad`, `latitude`, `longitude`) VALUES
(1, 'Beograd', 44.7866, 20.4489),
(2, 'Kragujevac', 44.0128, 20.9114),
(3, 'Novi Sad', 45.2671, 19.8335),
(4, 'Ivanjica', 43.5694, 20.2431),
(5, 'Subotica', 46.1005, 19.6651),
(6, 'Zrenjanin', 45.3816, 20.3686),
(7, 'Dimitrovgrad', 43.0184, 22.782),
(8, 'Jagodina', 43.9777, 21.2573),
(9, 'Nis', 43.3209, 21.8958),
(10, 'Vrsac', 45.1182, 21.2945),
(11, 'Kraljevo', 43.7238, 20.6873),
(12, 'Smederevo', 44.6659, 20.9335),
(13, 'Nova Varos', 43.4677, 19.8126),
(14, 'Valjevo', 44.2743, 19.8903),
(15, 'Pozega', 43.8461, 20.0356),
(16, 'Uzice', 43.8556, 19.8425),
(17, 'Zlatibor', 43.726, 19.697),
(18, 'Vranje', 42.545, 21.9003),
(19, 'Leskovac', 42.9964, 21.944),
(20, 'Aleksinac', 43.541, 21.7184),
(21, 'Paracin', 43.8586, 21.4039),
(22, 'Negotin', 44.2289, 22.5311),
(23, 'Prokuplje', 43.2368, 21.5916),
(24, 'Zajecar', 43.9015, 22.2738),
(25, 'Knjazevac', 43.5661, 22.2467),
(26, 'Svrljig', 43.4146, 22.1271),
(27, 'Sombor', 45.7733, 19.1151),
(28, 'Sabac', 44.7489, 19.6908),
(29, 'Vrbas', 45.5702, 19.645),
(30, 'Ruma', 45.0079, 19.8226),
(31, 'Kikinda', 45.8273, 20.4615),
(32, 'Sremska Mitrovica', 44.9795, 19.621),
(33, 'Vrnjacka Banja', 43.6251, 20.8942),
(34, 'Arandjelovac', 44.3082, 20.5563),
(35, 'Novi Pazar', 43.1407, 20.5214),
(36, 'Raska', 43.2896, 20.6161),
(37, 'Cacak', 43.8914, 20.3502);

-- --------------------------------------------------------

--
-- Table structure for table `korisnik`
--

CREATE TABLE `korisnik` (
  `username` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(13) COLLATE utf8_unicode_ci NOT NULL,
  `tip` int(1) NOT NULL,
  `ime` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `prezime` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `adresa` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `datumRodj` date NOT NULL,
  `telefon` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `zaposlenje` varchar(21) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `korisnik`
--

INSERT INTO `korisnik` (`username`, `password`, `tip`, `ime`, `prezime`, `adresa`, `datumRodj`, `telefon`, `zaposlenje`, `email`, `status`) VALUES
('admin', 'Admin 123', 0, 'Igor', 'Todorovic', 'Beograd, Vracar, Sumatovacka 30', '1992-11-10', '+381111848555', 'zaposlen', 'admin@gmail.com', 1),
('ana123', 'Sifra 123', 1, 'Ana', 'Ivanovic', 'Stojana Aralice 35', '1982-09-29', '0116187499', 'penzioner', 'ana.ivanovic@yahoo.com', 0),
('jovana123', 'Sifra 123', 1, 'Jovana', 'Matic', 'Gandijeva 109', '1996-09-14', '+38165555142', 'zaposlen', 'jovana.ctrl@gmail.com', 1),
('milance', 'Sifra 123', 1, 'Milan', 'Milanovic', 'Kragujevac, Visnjicka 21', '1975-03-20', '+381612899456', 'lice sa invaliditetom', 'milanceee@gmail.com', 0),
('mmilos', 'Sifra 123', 1, 'Milos', 'Milosevic', 'Beograd, Novi Beograd, Gandijeva 103', '1997-07-08', '+38164115715', 'student', 'miki97@gmail.com', 1),
('Zaki', 'si2Fr.a', 1, 'Zaki', 'Zaric', 'Beograd, Vracar, Branicevska 15', '1975-06-08', '061555391', 'zaposlen', 'zaki@yahoo.com', 0);

-- --------------------------------------------------------

--
-- Table structure for table `markice`
--

CREATE TABLE `markice` (
  `id` int(11) NOT NULL,
  `korisnikId` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `godisnja` int(1) NOT NULL,
  `odobrena` int(1) NOT NULL,
  `obavesti` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `markice`
--

INSERT INTO `markice` (`id`, `korisnikId`, `godisnja`, `odobrena`, `obavesti`) VALUES
(1, 'mmilos', 0, 1, 0),
(2, 'jovana123', 1, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `medjugradske`
--

CREATE TABLE `medjugradske` (
  `id` int(11) NOT NULL,
  `prevoznikId` int(11) NOT NULL,
  `polaziste` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `odrediste` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `medjustanice` mediumtext COLLATE utf8_unicode_ci NOT NULL,
  `polazak` timestamp NULL DEFAULT NULL,
  `dolazak` timestamp NULL DEFAULT NULL,
  `vozacId` int(11) DEFAULT NULL,
  `autobusId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `medjugradske`
--

INSERT INTO `medjugradske` (`id`, `prevoznikId`, `polaziste`, `odrediste`, `medjustanice`, `polazak`, `dolazak`, `vozacId`, `autobusId`) VALUES
(2, 1, 'Subotica', 'Dimitrovgrad', 'Novi Sad-Beograd-Jagodina-Nis', '2018-09-25 06:10:00', '2018-09-24 22:00:00', 1, 1),
(4, 3, 'Zrenjanin', 'Nova Varos', 'Beograd-Valjevo-Pozega-Uzice-Zlatibor', '2018-09-25 08:00:00', '2018-09-24 22:00:00', 8, 2),
(5, 4, 'Vranje', 'Kragujevac', 'Leskovac-Nis-Aleksinac-Paracin-Jagodina', '2018-09-25 08:45:00', '2018-09-24 22:00:00', NULL, NULL),
(6, 2, 'Negotin', 'Prokuplje', 'Zajecar-Knjazevac-Svrljig-Nis', '2018-09-25 12:19:00', '2018-09-24 22:00:00', NULL, NULL),
(7, 1, 'Sombor', 'Sabac', 'Vrbas-Novi Sad-Ruma', '2018-09-25 12:38:00', '2018-09-24 22:00:00', NULL, NULL),
(8, 3, 'Kikinda', 'Sremska Mitrovica', 'Novi Sad-Ruma', '2018-09-25 13:05:00', '2018-09-24 22:00:00', NULL, NULL),
(9, 1, 'Beograd', 'Vrnjacka Banja', 'Arandjelovac-Kragujevac-Kraljevo', '2018-09-25 16:00:00', '2018-09-24 22:00:00', NULL, NULL),
(10, 5, 'Novi Sad', 'Novi Pazar', 'Beograd-Kragujevac-Kraljevo-Raska', '2018-09-25 17:10:00', '2018-09-24 22:00:00', NULL, NULL),
(11, 1, 'Beograd', 'Subotica', 'Novi Sad', '2018-09-24 10:00:00', '2018-09-24 11:20:00', NULL, NULL),
(12, 4, 'Kraljevo', 'Zlatibor', 'Cacak', '2018-09-26 07:00:00', '2018-09-26 08:30:00', NULL, NULL),
(13, 3, 'Subotica', 'Beograd', 'Novi Sad', '2018-09-15 08:00:00', '2018-09-15 10:30:00', NULL, NULL),
(21, 3, 'Beograd', 'Kraljevo', 'Arandjelovac', '2018-09-26 07:00:00', '2018-09-26 10:00:00', 3, 4),
(22, 3, 'Nis', 'Beograd', 'Kragujevac', '2018-09-24 10:00:00', '2018-09-24 13:00:00', 8, 4),
(25, 11, 'Zrenjanin', 'Nova Varos', 'Beograd-Valjevo-Pozega-Uzice-Zlatibor', '2018-09-23 12:00:00', '2018-09-23 18:00:00', NULL, NULL),
(30, 3, 'Nis', 'Beograd', 'Kragujevac', '2018-09-29 06:00:00', '2018-09-23 09:17:00', 6, 19);

-- --------------------------------------------------------

--
-- Table structure for table `otkazane`
--

CREATE TABLE `otkazane` (
  `id` int(11) NOT NULL,
  `brLinije` int(11) NOT NULL,
  `periodOd` date NOT NULL,
  `periodDo` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `otkazane`
--

INSERT INTO `otkazane` (`id`, `brLinije`, `periodOd`, `periodDo`) VALUES
(1, 75, '2018-09-25', '2018-09-30'),
(2, 73, '2018-10-12', '2018-10-15');

-- --------------------------------------------------------

--
-- Table structure for table `prevoznici`
--

CREATE TABLE `prevoznici` (
  `id` int(11) NOT NULL,
  `naziv` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `adresa` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `telefon` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `logo` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `prevoznici`
--

INSERT INTO `prevoznici` (`id`, `naziv`, `adresa`, `telefon`, `logo`) VALUES
(1, 'Lasta', 'Beograd, Autoput Beograd-Niš 4', '+381113348555', './resources/photos/logo_lasta.png'),
(2, 'Nis ekspres', 'Niš, Čamurlija 160', '+38118255177', './resources/photos/logo_nisekspres.gif'),
(3, 'Fudeks', 'Beograd, Balkanska 47', '0800000007', ''),
(4, 'Jugekspres', 'Leskovac, Đorđa Stamenkovića bb', '+38116252622', ''),
(5, 'Pegaz', 'Ivanjica, Milinka Kušića 106/1', '+381648468348', './resources/photos/logo_pegaz.jpg'),
(11, 'Bozur', 'Beograd, Kneza Milosa 150', '+381607030051', './resources/photos/logo_bozur-2143235026410248252.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `redvoznje`
--

CREATE TABLE `redvoznje` (
  `id` int(11) NOT NULL,
  `brLinije` int(11) NOT NULL,
  `polaziste` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `odrediste` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `polasci` mediumtext COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `redvoznje`
--

INSERT INTO `redvoznje` (`id`, `brLinije`, `polaziste`, `odrediste`, `polasci`) VALUES
(1, 68, 'Zeleni Venac', 'Novi Beograd/blok 70', '5:55\r\n6:30\r\n7:00, 7:30\r\n8:00, 8:30\r\n9:00, 9:30\r\n10:00, 10:30\r\n11:00, 11:30\r\n12:00, 12:30\r\n13:00, 13:30\r\n14:00, 14:30\r\n15:00, 15:30\r\n16:00, 16:30\r\n17:00, 17:30\r\n18:00, 18:30\r\n19:00, 19:30\r\n20:00, 20:30\r\n21:00, 21:30\r\n22:00, 22:35'),
(2, 68, 'Novi Beograd/blok 70', 'Zeleni Venac', '5:30\r\n6:00, 6:30\r\n7:00, 7:30\r\n8:00, 8:30\r\n9:00, 9:30\r\n10:00, 10:30\r\n11:00, 11:30\r\n12:00, 12:30\r\n13:00, 13:30\r\n14:00, 14:30\r\n15:00, 15:30\r\n16:00, 16:30\r\n17:00, 17:30\r\n18:00, 18:30\r\n19:00, 19:30\r\n20:00, 20:30\r\n21:00, 21:30\r\n22:00, 22:35'),
(3, 75, 'Bezanijska Kosa', 'Zeleni Venac', '5:30\r\n6:00, 6:30\r\n7:00, 7:30\r\n8:00, 8:30\r\n9:00, 9:30\r\n10:00, 10:30\r\n11:00, 11:30\r\n12:00, 12:30\r\n13:00, 13:30\r\n14:00, 14:30\r\n15:00, 15:30\r\n16:00, 16:30\r\n17:00, 17:30\r\n18:00, 18:30\r\n19:00, 19:30\r\n20:00, 20:30\r\n21:00, 21:30\r\n22:00, 22:35'),
(4, 75, 'Zeleni Venac', 'Bezanijska Kosa', '5:30\r\n6:00, 6:30\r\n7:00, 7:30\r\n8:00, 8:30\r\n9:00, 9:30\r\n10:00, 10:30\r\n11:00, 11:30\r\n12:00, 12:30\r\n13:00, 13:30\r\n14:00, 14:30\r\n15:00, 15:30\r\n16:00, 16:30\r\n17:00, 17:30\r\n18:00, 18:30\r\n19:00, 19:30\r\n20:00, 20:30\r\n21:00, 21:30\r\n22:00, 22:35'),
(5, 45, 'Zemun/Novi Grad', 'Novi Beograd/Blok 44', '4:50\r\n5:10, 5:30, 5:44, 5:53\r\n6:00, 6:30\r\n7:00, 7:30\r\n8:00, 8:30\r\n9:00, 9:30\r\n10:00, 10:30\r\n11:00, 11:30\r\n12:00, 12:30\r\n13:00, 13:30\r\n14:00, 14:30\r\n15:00, 15:30\r\n16:00, 16:30\r\n17:00, 17:30\r\n18:00, 18:30\r\n19:00, 19:30\r\n20:00, 20:30\r\n21:00, 21:30\r\n22:00, 22:30\r\n23:00, 23:30\r\n'),
(6, 45, 'Novi Beograd/Blok 44', 'Zemun/Novi Grad', '4:50\r\n5:10, 5:30, 5:44, 5:53\r\n6:00, 6:30\r\n7:00, 7:30\r\n8:00, 8:30\r\n9:00, 9:30\r\n10:00, 10:30\r\n11:00, 11:30\r\n12:00, 12:30\r\n13:00, 13:30\r\n14:00, 14:30\r\n15:00, 15:30\r\n16:00, 16:30\r\n17:00, 17:30\r\n18:00, 18:30\r\n19:00, 19:30\r\n20:00, 20:30\r\n21:00, 21:30\r\n22:00, 22:30\r\n23:00, 23:30\r\n');

-- --------------------------------------------------------

--
-- Table structure for table `rezervacija`
--

CREATE TABLE `rezervacija` (
  `id` int(12) NOT NULL,
  `korisnik` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `linija` int(12) NOT NULL,
  `polaziste` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `odrediste` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `odobrena` int(1) NOT NULL,
  `obavesti` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `rezervacija`
--

INSERT INTO `rezervacija` (`id`, `korisnik`, `linija`, `polaziste`, `odrediste`, `odobrena`, `obavesti`) VALUES
(3, 'mmilos', 8, 'Kikinda', 'Sremska Mitrovica', 1, 0),
(5, 'mmilos', 10, 'Beograd', 'Kraljevo', 1, 0),
(15, 'mmilos', 2, 'Subotica', 'Beograd', 1, 0),
(16, 'mmilos', 13, 'Novi Sad', 'Beograd', 1, 0),
(17, 'mmilos', 12, 'Kraljevo', 'Zlatibor', 0, 0),
(18, 'mmilos', 2, 'Subotica', 'Dimitrovgrad', 0, 0),
(20, 'mmilos', 25, 'Zrenjanin', 'Nova Varos', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `vozaci`
--

CREATE TABLE `vozaci` (
  `id` int(11) NOT NULL,
  `ime` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `prezime` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `datumRodj` date NOT NULL,
  `iskustvo` int(11) NOT NULL,
  `slobodan` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `vozaci`
--

INSERT INTO `vozaci` (`id`, `ime`, `prezime`, `datumRodj`, `iskustvo`, `slobodan`) VALUES
(1, 'Dragan', 'Jovanovic', '1975-09-11', 23, 1),
(2, 'Djordje', 'Djordjevic', '1983-01-25', 12, 1),
(3, 'Milan', 'Tesic', '1969-12-12', 30, 0),
(4, 'Nemanja', 'Tatic', '1995-04-24', 4, 0),
(5, 'Ivan', 'Mandic', '1977-02-03', 21, 0),
(6, 'Dusan', 'Bozic', '1981-06-19', 15, 0),
(7, 'Nenad', 'Krstic', '1989-04-27', 18, 1),
(8, 'Petar', 'Petrovic', '1983-03-10', 18, 0),
(9, 'Marko', 'Maric', '1990-11-15', 10, 1),
(10, 'Jovan', 'Pavicevic', '1992-10-19', 3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `vozigradsku`
--

CREATE TABLE `vozigradsku` (
  `id` int(11) NOT NULL,
  `vozac` int(11) NOT NULL,
  `linija` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `vozigradsku`
--

INSERT INTO `vozigradsku` (`id`, `vozac`, `linija`) VALUES
(1, 4, 68),
(2, 5, 68);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `autobusi`
--
ALTER TABLE `autobusi`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cenemarkica`
--
ALTER TABLE `cenemarkica`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `gradske`
--
ALTER TABLE `gradske`
  ADD PRIMARY KEY (`brLinije`);

--
-- Indexes for table `koordinate`
--
ALTER TABLE `koordinate`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `korisnik`
--
ALTER TABLE `korisnik`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `markice`
--
ALTER TABLE `markice`
  ADD PRIMARY KEY (`id`),
  ADD KEY `markicaKorisniikConstraint` (`korisnikId`);

--
-- Indexes for table `medjugradske`
--
ALTER TABLE `medjugradske`
  ADD PRIMARY KEY (`id`),
  ADD KEY `medjugradskeAutobusiConstraint` (`autobusId`),
  ADD KEY `prevoznikConstraint` (`prevoznikId`),
  ADD KEY `medjugradskevozacConstraint` (`vozacId`);

--
-- Indexes for table `otkazane`
--
ALTER TABLE `otkazane`
  ADD PRIMARY KEY (`id`),
  ADD KEY `otkazanaLinijaConstraint` (`brLinije`);

--
-- Indexes for table `prevoznici`
--
ALTER TABLE `prevoznici`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `redvoznje`
--
ALTER TABLE `redvoznje`
  ADD PRIMARY KEY (`id`),
  ADD KEY `brLinijeConstraint` (`brLinije`);

--
-- Indexes for table `rezervacija`
--
ALTER TABLE `rezervacija`
  ADD PRIMARY KEY (`id`),
  ADD KEY `rezervacijaLinije` (`linija`),
  ADD KEY `usernameConstraint` (`korisnik`);

--
-- Indexes for table `vozaci`
--
ALTER TABLE `vozaci`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `vozigradsku`
--
ALTER TABLE `vozigradsku`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `vozac` (`vozac`),
  ADD KEY `vozigradskulinijaConstraint` (`linija`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `autobusi`
--
ALTER TABLE `autobusi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `cenemarkica`
--
ALTER TABLE `cenemarkica`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `koordinate`
--
ALTER TABLE `koordinate`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT for table `markice`
--
ALTER TABLE `markice`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `medjugradske`
--
ALTER TABLE `medjugradske`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `otkazane`
--
ALTER TABLE `otkazane`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `prevoznici`
--
ALTER TABLE `prevoznici`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `redvoznje`
--
ALTER TABLE `redvoznje`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `rezervacija`
--
ALTER TABLE `rezervacija`
  MODIFY `id` int(12) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `vozaci`
--
ALTER TABLE `vozaci`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `vozigradsku`
--
ALTER TABLE `vozigradsku`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `markice`
--
ALTER TABLE `markice`
  ADD CONSTRAINT `markicaKorisniikConstraint` FOREIGN KEY (`korisnikId`) REFERENCES `korisnik` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `medjugradske`
--
ALTER TABLE `medjugradske`
  ADD CONSTRAINT `medjugradskeAutobusiConstraint` FOREIGN KEY (`autobusId`) REFERENCES `autobusi` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `medjugradskevozacConstraint` FOREIGN KEY (`vozacId`) REFERENCES `vozaci` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `prevoznikConstraint` FOREIGN KEY (`prevoznikId`) REFERENCES `prevoznici` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `otkazane`
--
ALTER TABLE `otkazane`
  ADD CONSTRAINT `otkazanaLinijaConstraint` FOREIGN KEY (`brLinije`) REFERENCES `gradske` (`brLinije`);

--
-- Constraints for table `redvoznje`
--
ALTER TABLE `redvoznje`
  ADD CONSTRAINT `brLinijeConstraint` FOREIGN KEY (`brLinije`) REFERENCES `gradske` (`brLinije`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `rezervacija`
--
ALTER TABLE `rezervacija`
  ADD CONSTRAINT `rezervacijaLinije` FOREIGN KEY (`linija`) REFERENCES `medjugradske` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `usernameConstraint` FOREIGN KEY (`korisnik`) REFERENCES `korisnik` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `vozigradsku`
--
ALTER TABLE `vozigradsku`
  ADD CONSTRAINT `vozigradskulinijaConstraint` FOREIGN KEY (`linija`) REFERENCES `gradske` (`brLinije`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `vozigradskuvozacConstraint` FOREIGN KEY (`vozac`) REFERENCES `vozaci` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
