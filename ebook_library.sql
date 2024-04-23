-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 23 Apr 2024 pada 04.41
-- Versi server: 10.4.27-MariaDB
-- Versi PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ebook_library`
--

DELIMITER $$
--
-- Prosedur
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateListBorrow` (`searchIsbn` VARCHAR(255))   BEGIN
	DECLARE varIsbn varchar(255);
    DECLARE varIdUser int(11);
    IF (SELECT COUNT(*) FROM bookqueue WHERE isbn = searchIsbn) > 0 THEN
        SELECT isbn INTO varIsbn FROM bookqueue WHERE `date` = (SELECT MAX(`date`) FROM bookqueue WHERE isbn = searchIsbn) LIMIT 1;
        SELECT id_user INTO varIdUser FROM bookqueue WHERE `date` = (SELECT MAX(`date`) FROM bookqueue WHERE isbn = searchIsbn) LIMIT 1;
        DELETE FROM bookqueue WHERE `date` = (SELECT MAX(`date`) FROM bookqueue WHERE isbn = searchIsbn) LIMIT 1;
        INSERT INTO listborrow (isbn, id_user, date_borrow) VALUES (varIsbn, varIdUser, NOW());
    END IF;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Struktur dari tabel `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `division` varchar(80) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `admin`
--

INSERT INTO `admin` (`id`, `division`) VALUES
(2, 'Division User Manager');

-- --------------------------------------------------------

--
-- Struktur dari tabel `book`
--

CREATE TABLE `book` (
  `isbn` varchar(255) NOT NULL,
  `year` int(4) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `genre` enum('ACTION','ROMANCE','SLICE_OF_LIFE','FANTASY','SCI_FI','HORROR','COMEDY') DEFAULT NULL,
  `category` enum('FICTION','NONFICTION') DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `pic_path` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `book`
--

INSERT INTO `book` (`isbn`, `year`, `title`, `genre`, `category`, `author`, `stock`, `pic_path`) VALUES
('1234567890', 2020, 'The Adventure Begins', 'ACTION', 'FICTION', 'A. Author', 5, 'src/images/book1.png'),
('5432109876', 2015, 'Slice of Life Chronicles', 'SLICE_OF_LIFE', 'FICTION', 'C. Novelist', 5, 'src/images/book3.png'),
('978-0-111-45678-1', 2018, 'A Man Called Ove', 'SLICE_OF_LIFE', 'FICTION', 'Author 6', 5, 'src/images/book9.jpg'),
('978-0-123-45678-9', 2023, 'The Spirit Glass', 'ACTION', 'FICTION', 'Author 1', 10, 'src/images/book4.jpg'),
('978-0-222-45678-2', 2017, 'Without Limits', 'ACTION', 'FICTION', 'Author 7', 5, 'src/images/book10.jpg'),
('978-0-333-45678-3', 2016, 'Wrath Becomes Her', 'ACTION', 'FICTION', 'Author 8', 5, 'src/images/book11.jpg'),
('978-0-345-45678-11', 2008, 'The Kiss Quotient', 'ROMANCE', 'FICTION', 'Author 16', 5, 'src/images/book15.jpg'),
('978-0-444-45678-4', 2015, 'Black Friend Essays', 'COMEDY', 'FICTION', 'Author 9', 5, 'src/images/book12.jpg'),
('978-0-444-56789-0', 2022, 'Spy X Family', 'COMEDY', 'FICTION', 'Author 2', 15, 'src/images/book5.jpg'),
('978-0-555-12345-6', 2021, 'Sword Catcher', 'FANTASY', 'FICTION', 'Author 3', 8, 'src/images/book6.jpg'),
('978-0-555-45678-5', 2014, 'The Jolliest Bunch', 'COMEDY', 'FICTION', 'Author 10', 5, 'src/images/book13.jpg'),
('978-0-567-45678-13', 2005, 'Legends & Lattes', 'SLICE_OF_LIFE', 'FICTION', 'Author 18', 5, 'src/images/book16.jpg'),
('978-0-666-45678-6', 2013, 'A Court of Thorns and Roses', 'FANTASY', 'FICTION', 'Author 11', 5, 'src/images/book7.jpg'),
('978-0-777-45678-7', 2012, 'The Name of the Wind', 'FANTASY', 'FICTION', 'Author 12', 5, 'src/images/book8.jpg'),
('978-0-987-65432-1', 2019, 'Me Before You', 'ROMANCE', 'FICTION', 'Author 5', 5, 'src/images/book14.jpg'),
('9876543210', 2018, 'Love in Bloom', 'ROMANCE', 'FICTION', 'B. Writer', 15, 'src/images/book2.png');

-- --------------------------------------------------------

--
-- Struktur dari tabel `bookqueue`
--

CREATE TABLE `bookqueue` (
  `isbn` varchar(255) NOT NULL,
  `id_user` int(11) NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `listborrow`
--

CREATE TABLE `listborrow` (
  `id_list_borrow` int(11) NOT NULL,
  `isbn` varchar(255) DEFAULT NULL,
  `id_user` int(11) DEFAULT NULL,
  `date_borrow` timestamp NULL DEFAULT NULL,
  `date_return` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `listborrow`
--

INSERT INTO `listborrow` (`id_list_borrow`, `isbn`, `id_user`, `date_borrow`, `date_return`) VALUES
(1, '1234567890', 1, '2022-12-31 17:00:00', '2023-01-31 17:00:00'),
(2, '9876543210', 3, '2023-02-28 17:00:00', '2023-03-05 17:00:00'),
(5, '1234567890', 1, '2023-11-24 17:34:39', '2023-11-25 06:03:46'),
(6, '1234567890', 1, '2023-11-25 06:03:46', '2023-11-25 06:06:58'),
(7, '1234567890', 1, '2023-11-25 06:09:20', '2023-11-25 06:09:20'),
(8, '1234567890', 1, '2024-04-20 04:17:50', '2024-04-20 04:17:51'),
(9, '1234567890', 1, '2024-04-20 05:10:38', '2024-04-20 05:10:38'),
(11, '1234567890', 1, '2024-04-20 05:16:36', '2024-04-20 05:16:36'),
(12, '1234567890', 1, '2024-04-20 05:17:27', '2024-04-20 05:17:27'),
(13, '1234567890', 1, '2024-04-20 05:20:25', '2024-04-20 05:20:25'),
(14, '1234567890', 1, '2024-04-20 05:47:33', '2024-04-20 05:47:33'),
(15, '1234567890', 1, '2024-04-20 06:21:43', '2024-04-20 06:21:43'),
(16, '1234567890', 1, '2024-04-20 06:27:57', '2024-04-20 06:27:57'),
(18, '1234567890', 3, '2024-04-20 08:23:04', NULL),
(19, '1234567890', 3, '2024-04-21 08:23:04', NULL),
(20, '1234567890', 3, '2024-04-20 08:38:25', NULL),
(22, '9876543210', 1, '2024-04-21 09:55:18', '2024-04-21 09:57:35'),
(23, '9876543210', 1, '2024-04-21 09:57:35', '2024-04-21 10:16:53'),
(26, '1234567890', 3, '2024-04-21 10:36:50', NULL),
(27, '9876543210', 3, '2024-04-21 11:18:39', '2024-04-21 11:18:46'),
(28, '1234567890', 3, '2024-04-21 11:19:19', NULL),
(30, '1234567890', 1, '2024-04-21 11:40:46', '2024-04-21 11:41:29'),
(31, '1234567890', 1, '2024-04-21 12:26:45', '2024-04-21 12:26:50'),
(32, '1234567890', 1, '2024-04-21 12:31:38', '2024-04-21 12:32:50'),
(33, '978-0-222-45678-2', 1, '2024-04-22 14:18:08', '2024-04-22 14:18:36'),
(34, '978-0-222-45678-2', 1, '2024-04-22 14:18:39', '2024-04-22 14:19:15'),
(35, '978-0-123-45678-9', 1, '2024-04-22 14:19:27', '2024-04-22 14:19:39'),
(36, '978-0-123-45678-9', 1, '2024-04-22 14:19:42', '2024-04-22 14:20:11'),
(37, '978-0-123-45678-9', 1, '2024-04-22 14:20:16', '2024-04-22 14:20:21'),
(38, '978-0-444-56789-0', 1, '2024-04-22 14:20:47', '2024-04-22 14:20:55'),
(39, '978-0-444-56789-0', 1, '2024-04-22 14:21:01', '2024-04-22 14:21:33'),
(40, '978-0-777-45678-7', 1, '2024-04-22 14:21:53', '2024-04-22 14:22:31'),
(41, '978-0-777-45678-7', 1, '2024-04-22 14:22:34', NULL),
(42, '978-0-444-56789-0', 1, '2024-04-22 14:26:45', '2024-04-22 14:27:19'),
(43, '978-0-555-12345-6', 1, '2024-04-22 14:27:44', '2024-04-22 14:28:10'),
(44, '978-0-987-65432-1', 1, '2024-04-22 14:32:49', '2024-04-22 14:33:02'),
(45, '978-0-987-65432-1', 1, '2024-04-22 14:33:04', '2024-04-22 14:33:11'),
(46, '978-0-987-65432-1', 1, '2024-04-22 14:33:15', '2024-04-22 14:33:20'),
(47, '978-0-666-45678-6', 1, '2024-04-22 14:34:29', '2024-04-22 14:34:39'),
(48, '978-0-444-56789-0', 3, '2024-04-22 16:20:03', '2024-04-22 16:20:10'),
(49, '978-0-444-56789-0', 3, '2024-04-22 16:20:33', '2024-04-22 16:20:38'),
(50, '9876543210', 3, '2024-04-22 16:20:57', '2024-04-22 16:21:03'),
(51, '978-0-222-45678-2', 4, '2024-04-22 16:27:12', '2024-04-22 16:27:17'),
(52, '978-0-444-56789-0', 3, '2024-04-22 16:30:52', '2024-04-22 16:31:03'),
(53, '978-0-444-56789-0', 3, '2024-04-22 16:31:54', '2024-04-22 16:32:55'),
(54, '978-0-666-45678-6', 3, '2024-04-22 16:33:29', '2024-04-22 16:33:35'),
(55, '978-0-444-45678-4', 1, '2024-04-23 01:44:12', '2024-04-23 01:44:19'),
(56, '978-0-444-56789-0', 1, '2024-04-23 01:51:57', '2024-04-23 01:52:04'),
(57, '5432109876', 1, '2024-04-23 01:56:31', '2024-04-23 01:56:34'),
(58, '978-0-444-56789-0', 1, '2024-04-23 02:05:37', '2024-04-23 02:05:40'),
(59, '1234567890', 1, '2024-04-23 02:38:55', '2024-04-23 02:39:58');

-- --------------------------------------------------------

--
-- Struktur dari tabel `person`
--

CREATE TABLE `person` (
  `id` int(11) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `pic_path` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `person`
--

INSERT INTO `person` (`id`, `password`, `name`, `email`, `phone`, `pic_path`) VALUES
(1, 'pass123', 'John Doe', 'john@example.com', '123456789', 'src\\images\\John Doe.jpg'),
(2, 'adminpass', 'Admin User', 'admin@example.com', '987654321', 'src/images/admin.jpg'),
(3, 'userpass', 'Jane Smith', 'jane@example.com', '5551234567', 'src/images/jane.jpg'),
(4, '123mudah', 'Martin', 'mada@email.com', '01038010', 'src\\images\\Martin.jpg');

-- --------------------------------------------------------

--
-- Struktur dari tabel `review`
--

CREATE TABLE `review` (
  `id_user` int(11) NOT NULL,
  `isbn` varchar(255) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `review`
--

INSERT INTO `review` (`id_user`, `isbn`, `content`, `rating`, `date`) VALUES
(1, '1234567890', 'test Review', 3, '2024-04-20'),
(1, '5432109876', 'Nice Book', 5, '2024-04-23'),
(1, '978-0-123-45678-9', 'Good to Read', 4, '2024-04-22'),
(1, '978-0-222-45678-2', 'Excellent!', 5, '2024-04-22'),
(1, '978-0-444-45678-4', 'nc', 5, '2024-04-23'),
(1, '978-0-444-56789-0', 'Good Story!', 5, '2024-04-22'),
(1, '978-0-666-45678-6', 'Good Job!', 5, '2024-04-22'),
(1, '978-0-777-45678-7', 'Awesome!', 5, '2024-04-22'),
(3, '9876543210', 'A beautiful love story.', 5, '2023-02-15'),
(4, '978-0-222-45678-2', 'Sheesh', 4, '2024-04-22');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `bio` varchar(255) DEFAULT NULL,
  `warning` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`id`, `bio`, `warning`) VALUES
(1, 'I love reading books!', 0),
(3, 'Bookworm and proud!', 0),
(4, 'Ain\'t no brakes', 0);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`isbn`);

--
-- Indeks untuk tabel `bookqueue`
--
ALTER TABLE `bookqueue`
  ADD PRIMARY KEY (`id_user`,`isbn`),
  ADD KEY `isbn` (`isbn`);

--
-- Indeks untuk tabel `listborrow`
--
ALTER TABLE `listborrow`
  ADD PRIMARY KEY (`id_list_borrow`),
  ADD KEY `id_user` (`id_user`),
  ADD KEY `isbn` (`isbn`);

--
-- Indeks untuk tabel `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `review`
--
ALTER TABLE `review`
  ADD PRIMARY KEY (`id_user`,`isbn`),
  ADD KEY `isbn` (`isbn`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `listborrow`
--
ALTER TABLE `listborrow`
  MODIFY `id_list_borrow` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;

--
-- AUTO_INCREMENT untuk tabel `person`
--
ALTER TABLE `person`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`id`) REFERENCES `person` (`id`);

--
-- Ketidakleluasaan untuk tabel `bookqueue`
--
ALTER TABLE `bookqueue`
  ADD CONSTRAINT `bookqueue_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `bookqueue_ibfk_2` FOREIGN KEY (`isbn`) REFERENCES `book` (`isbn`);

--
-- Ketidakleluasaan untuk tabel `listborrow`
--
ALTER TABLE `listborrow`
  ADD CONSTRAINT `listborrow_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `listborrow_ibfk_2` FOREIGN KEY (`isbn`) REFERENCES `book` (`isbn`);

--
-- Ketidakleluasaan untuk tabel `review`
--
ALTER TABLE `review`
  ADD CONSTRAINT `review_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `review_ibfk_2` FOREIGN KEY (`isbn`) REFERENCES `book` (`isbn`);

--
-- Ketidakleluasaan untuk tabel `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`id`) REFERENCES `person` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
