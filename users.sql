-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 21, 2023 at 09:18 PM
-- Wersja serwera: 10.4.28-MariaDB
-- Wersja PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `twojaopinia`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `login` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `admin` tinyint(1) NOT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `birthday` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`login`, `password`, `admin`, `salt`, `name`, `surname`, `email`, `birthday`, `country`, `city`) VALUES
('KazioF1scher', '77e68256bbfe0a5d23f1d4378a418770310f4f6bc6446eccf24ba87f673ce585', 1, 'y91F72FBdviqP5caF0bhCg', 'Maksymilian', 'Fischer', 'maksymilian.fischer7@gmail.com', '07.08.2004', 'Polska', 'Łuck'),
('testUser1', '30d211daef6348c113fe297d08e34360f18db88437d199008a4e00884a3816c7', 0, 'vJ9o_0Uu6LuE9pLdWQw94Q', 'TestName1', 'TestSurname1', 'testemail1@gmail.com', '01.05.2000', 'Polska', 'Warszawa'),
('testUser2', '360154a154105ae8a51d897270dc15fcd512245d59680bef405cd9eb3a24a6ef', 0, '8hESEu7P9PqqRHEtVN7F_Q', 'TestName2', 'TestSurname2', 'testemail2@gmail.com', '23.06.1999', 'Polska', 'Kraków'),
('testUser3', '9b0a46a7032c1b77b8b1238a5e8a926077ed93ef734d37b283fe5d5ca1a5399c', 0, 'nNRIP3iUPgWZieqkzBauSA', 'TestName3', 'TestSurname3', 'testemail3@gmail.com', '13.09.2007', 'Polska', 'Gliwice');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`login`),
  ADD UNIQUE KEY `login` (`login`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
