-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sty 07, 2024 at 02:14 AM
-- Wersja serwera: 10.4.32-MariaDB
-- Wersja PHP: 8.2.12

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
-- Struktura tabeli dla tabeli `surveys`
--

CREATE TABLE `surveys` (
  `id` int(11) NOT NULL,
  `author` varchar(50) NOT NULL,
  `title` varchar(100) NOT NULL,
  `description` text DEFAULT NULL,
  `dateAdded` datetime DEFAULT NULL,
  `nquestions` int(11) DEFAULT NULL,
  `tags` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `surveys`
--

INSERT INTO `surveys` (`id`, `author`, `title`, `description`, `dateAdded`, `nquestions`, `tags`) VALUES
(76, 'Kaziof1scher', 'Gra roku', 'W tej ankiecie będziemy wybierać 10 najlepszych gier 2023 roku', '2024-01-06 22:04:36', 5, '#game #tierlist'),
(77, 'Kaziof1scher', 'Opinia studentów na temat pracy w grupie', 'Ankieta dotyczy opinii studentów z Wydziału Inżynierii Elektrycznej i Komputerowej', '2024-01-06 23:49:22', 12, '#study #teamwork'),
(78, 'Kaziof1scher', 'Badanie opinii na temat konfliktów w zespole', 'Badanie skupia się na motywach i przyjmowanych postawach wśród studentów WIEiK', '2024-01-07 00:04:19', 11, '#study #teamwork'),
(79, 'Kaziof1scher', 'Badanie preferencji politycznych Polaków.', 'Wpływ mediów na preferencję polityczne polaków.', '2024-01-07 00:26:00', 12, '#politics #Poland'),
(80, 'Kaziof1scher', 'Zakaz sprzedaży energetyków osobom niepełnoletnim', 'Ankieta obejmuję opinię społeczeństwa na temat picia napojów energetyzujących ', '2024-01-07 00:37:24', 8, '#EnergyDrink #Poland'),
(81, 'Kaziof1scher', 'Mobbing', 'Mobbing – prześladowanie, uporczywe nękanie i zastraszanie, stosowanie przemocy psychicznej wobec podwładnego lub współpracownika w miejscu pracy.', '2024-01-07 01:14:07', 10, '#Mobbing #WorkplaceHarassment'),
(82, 'Kaziof1scher', 'Ocena szkolenia', 'Ankieta zbiera opinię na temat przeprowadzonego szkolenia ', '2024-01-07 01:26:51', 11, '#institute'),
(83, 'Kaziof1scher', 'Ankieta sportowa', 'Ankieta dotycząca kondycji fizycznej', '2024-01-07 01:38:56', 11, '#sport #health #fitness'),
(84, 'Kaziof1scher', 'Ankieta na temat muzyki', 'Udziel odpowiedzi na kilka krótkich pytań dotyczących muzyki!', '2024-01-07 01:50:41', 9, '#music #rap'),
(85, 'Kaziof1scher', 'Żywność ekologiczna', 'Ankieta zbiera dane o stosunku społeczeństwa do żywności ekologicznej', '2024-01-07 02:06:58', 19, '#Eco #Eatables');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `surveys`
--
ALTER TABLE `surveys`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `surveys`
--
ALTER TABLE `surveys`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=86;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
