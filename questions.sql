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
-- Struktura tabeli dla tabeli `questions`
--

CREATE TABLE `questions` (
  `id` int(11) NOT NULL,
  `survey_id` int(11) NOT NULL,
  `question_text` text NOT NULL,
  `question_order` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`id`, `survey_id`, `question_text`, `question_order`) VALUES
(10, 76, 'Najlepsza gra roku?', 1),
(11, 76, 'Najlepsza gra z innowacyjną rozgrywką?', 2),
(12, 76, 'Najlepsza gra ze znakomitą fabułą?', 3),
(13, 76, 'Najlepsza gra do relaksu?', 4),
(14, 76, 'Najlepsza gra z wyśmienitą ścieżką dźwiękową?', 5),
(15, 77, 'Proszę zaznaczyć kierunek studiów, na które uczęszczasz', 1),
(16, 77, 'Proszę zaznaczyć semestr, na którym obecnie studiujesz', 2),
(17, 77, 'Czy podczas studiów często musisz pracować w grupie?', 3),
(18, 77, 'Wolisz pracować w grupie czy sam/a?', 4),
(19, 77, 'Czy na studiach pracujesz częściej w grupie niż np. w szkole średniej (liceum/technikum)?', 5),
(20, 77, 'Czy uważasz, że posiadasz umiejętność pracy w grupie?', 6),
(21, 77, 'Jak myślisz, czy umiejętności pracy w grupie można się nauczyć?', 7),
(22, 77, 'Jak myślisz, czy w Twojej przyszłej pracy zawodowej ważna będzie umiejętność pracy w grupie?', 8),
(23, 77, 'Jak myślisz, czy doświadczenie zdobyte podczas pracy w grupie w trakcie zajęć na studiach, może Cię przygotować do przyszłej pracy zawodowej?', 9),
(24, 77, 'Czy lubisz pracować w grupie?', 10),
(25, 77, 'Jakie emocje wywołuje u Ciebie praca w grupie?', 11),
(26, 77, 'Jak myślisz, jakie działania należy podjąć, aby współpraca w grupie była dobra?', 12),
(27, 78, 'Proszę zaznaczyć kierunek studiów, na które obecnie uczęszczasz', 1),
(28, 78, 'Proszę wpisać semestr, na którym obecnie studiujesz.', 2),
(29, 78, 'W sytuacji konfliktu, gdy druga osoba stosuję agresję słowną najczęściej:\n', 3),
(30, 78, 'Który z przedstawionych sposobów rozwiązywania konfliktów uważasz za najczęstszy?', 4),
(31, 78, 'Jak często według Pana/Pani w zespole pojawiają się konflikty?', 5),
(32, 78, 'Co według Pana/Pani jest najczęstszą przyczyną konfliktu w zespole?', 6),
(33, 78, 'Czy konflikt w zespole ma wpływ na produktywność zespołu?\n', 7),
(34, 78, 'Czy miał Pan/Pani okazję pracy w zespole np. podczas praktyk?\n', 8),
(35, 78, 'Jakie typy konfliktu według Pana/i najczęściej występują w Pana/Pani pracy/na praktykach?', 9),
(36, 78, 'Według Pana/Pani negatywnymi skutkami konfliktu są najczęściej:', 10),
(37, 78, 'Czy uważasz za bardziej skuteczne korzystanie z pomocy z zewnątrz (np. Mediator) czy rozwiązanie konfliktu tylko między członkami zespołu?', 11),
(38, 79, 'Na ile oceniasz swoja świadomosć polityczną?', 1),
(39, 79, 'Jak często oglądasz/słuchasz wiadomości bądz szukasz informacji na temat polityki w państwie?', 2),
(40, 79, 'Czy uważasz, że polityka w Polsce idzie w dobrym kierunku?', 3),
(41, 79, 'Z jakich mediów naczęściej czerpiesz informacje dotyczące polityki?', 4),
(42, 79, 'Jaką partię polityczną popierasz?', 5),
(43, 79, 'Który polityk według Ciebie jest najbardziej godny zaufania?', 6),
(44, 79, 'Kto Twoim zdaniem jest odpowiedzialny za problemy w rozstrzygnięciu wypadku Katyńskiego?', 7),
(45, 79, 'Czy jesteś za rozłączeniem spraw Kościała od Państwa?', 8),
(46, 79, 'Czy jesteś za legalizacją aborcji w Polsce?', 9),
(47, 79, 'Płeć', 10),
(48, 79, 'Wiek', 11),
(49, 79, 'Wykształcenie', 12),
(50, 80, 'Co sądzisz o zakazie sprzedaży energetyków osobom niepełnoletnim?', 1),
(51, 80, 'Czy pijesz energetyki?', 2),
(52, 80, 'Jak myślisz, czy młodzież faktycznie przestanie pić energetyki?', 3),
(53, 80, 'Czy według Ciebie energetyki są szkodliwe dla młodzieży?', 4),
(54, 80, 'Czy spotkałeś się już z sytuacją, gdy kasjerka pytała o dowód osobę kupującą energetyka?', 5),
(55, 80, 'Czy według Ciebie energetyki dodają sił?', 6),
(56, 80, 'Od jakiego wieku energetyki powinny być dozwolone?', 7),
(57, 80, 'Jak myślisz, czy wkrótce powstanie jakiś zamiennik energetyków?', 8),
(58, 81, 'Czy jesteś/byłeś ofiarą mobbingu?', 1),
(59, 81, 'Kto cię mobbingował?', 2),
(60, 81, 'Czy zgłosiłeś mobbing pracodawcy?', 3),
(61, 81, 'Czy Twój mobber został ukarany?', 4),
(62, 81, 'Czy sprawę mobbingu zgłosiłeś/aś do sądu?', 5),
(63, 81, 'Czy wygrałeś w sądzie sprawę o mobbing?', 6),
(64, 81, 'W jakim przedziale wiekowym jesteś?', 7),
(65, 81, 'Z jaką płcią się utożsamiasz?', 8),
(66, 81, 'Sytuacja zawodowa', 9),
(67, 81, 'Miejsce zamieszkania', 10),
(68, 82, 'Czy według Pana(i) został osiągnięty cel i założenia projektu / szkolenia / kursu?', 1),
(69, 82, 'Czy był Pan(i) zadowolony(a) z ogólnego programu projektu / szkolenia / kursu?', 2),
(70, 82, 'Czy zdobył(a) Pan(i) oczekiwaną przez Pana(ią) praktyczną wiedzę podczas tego projektu / szkolenia / kursu?', 3),
(71, 82, 'Czy zdobył Pan(i) oczekiwaną przez Pana(ią) teoretyczną wiedzę podczas tego projektu / szkolenia / kursu?', 4),
(72, 82, 'Czy plan zajęć i rozkład klas był według Pana(i) odpowiedni?', 5),
(73, 82, 'Czy prowadzący efektywnie prowadził zajęcia?', 6),
(74, 82, 'Czy materiały dydaktyczne były dobrze zaprojektowane?', 7),
(75, 82, 'Proszę ocenić wartość wykonanej przez Pana(ią) pracy.', 8),
(76, 82, 'Płeć', 9),
(77, 82, 'Wiek ', 10),
(78, 82, 'Wykształcenie', 11),
(79, 83, 'Jak Pan/Pani szacuje swą kondycję fizyczną?', 1),
(80, 83, 'Jak często Pan/Pani uprawia sport?', 2),
(81, 83, 'Dlaczego Pan/Pani uprawia sport?', 3),
(82, 83, ' Ma Pan/Pani ustalony plan ćwiczeń?', 4),
(83, 83, 'Najczęściej uprawia Pan/Pani sport:', 5),
(84, 83, 'Próbuje Pan/Pani wylepszyć swoje dyspozycje sportowe?', 6),
(85, 83, 'Pali Pan/Pani?', 7),
(86, 83, 'Ma Pan/Pani jakieś ograniczenia zdrowotne?', 8),
(87, 83, 'Ma Pan/Pani zrównoważoną dietę, aby utrzymać swą kondycję fizyczną?', 9),
(88, 83, 'Używa Pan/Pani suplementów diety dla sportowców?', 10),
(89, 83, 'Uczęszcza pan/pani aktywnie we współzawodnictwach swego ulubionego sportu?', 11),
(90, 84, 'Czy słuchanie muzyki sprawia Pani/Panu przyjemność?', 1),
(91, 84, 'Jakiego rodzaju muzyki Pan(i) słucha?', 2),
(92, 84, 'Woli Pan(i) słuchać muzyki polskiej czy zagranicznej?', 3),
(93, 84, 'Z czego najczęściej słucha Pan(i) muzyki?', 4),
(94, 84, 'Woli Pan(i) słuchać muzyki z głośników czy na słuchawkach?', 5),
(95, 84, 'Czy gra Pan(i) na jakichś instrumentach? Jeśli tak, proszę zaznaczyć, do których grup one należą.', 6),
(96, 84, 'Co sądzi Pan(i) o stwierdzeniu, że muzyka łagodzi obyczaje?', 7),
(97, 84, 'Płeć:', 8),
(98, 84, 'Wiek:', 9),
(99, 85, 'Płeć', 1),
(100, 85, 'Wiek', 2),
(101, 85, 'Wykształcenie', 3),
(102, 85, 'Jak Pani/ Pan ocenia swoją sytuację finansową?', 4),
(103, 85, 'Jaka jest Pani/ Pana wiedza na temat żywności ekologicznej?', 5),
(104, 85, 'Czy uważa Pani/ Pan że żywność ekologiczna jest synonimem zdrowej żywności?', 6),
(105, 85, 'Czy ma Pani/ Pan zaufanie do żywności ekologicznej?', 7),
(106, 85, 'Czy kupując żywność ekologiczną zwraca Pani/ Pan na informacje oraz znaki umiesczone na opakowaniach?', 8),
(107, 85, 'Jakie niżej wymienione określenia kojarzą się Pani/ Panu z żywnością ekologiczną?', 9),
(108, 85, 'Czy zna Pani/ Pan oznakowanie żywności ekologicznej?', 10),
(109, 85, 'Jak często kupuje Pani/ Pan żywność ekologiczną?', 11),
(110, 85, 'Gdzie najczęściej kupuje Pani/ Pan żywność ekologiczną?', 12),
(111, 85, 'Jakie produkty kupuje Pani/ Pan najczęściej?', 13),
(112, 85, 'Ile pieniędzy przeznacza Pani/ Pan miesięcznie na zakup żywności?', 14),
(113, 85, 'Ile pieniędzy przeznacza Pani/ Pan miesięcznie na zakup żywności ekologicznej?', 15),
(114, 85, 'Dlaczego nie kupuje Pani/ Pan żywności ekologicznej?', 16),
(115, 85, 'Co skłoniłoby Panią/ Pana do kupowania w przyszłości żywności ekologicznej?', 17),
(116, 85, 'Czy uważa Pani/ Pan iż w Polsce potrzebne są akcje promujące spożywanie żywności ekologicznej?', 18),
(117, 85, 'Z jakich źródeł Pani/ Pan czerpie wiedzę na temat żywności ekologicznej?', 19);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `survey_id` (`survey_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `questions`
--
ALTER TABLE `questions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=118;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
