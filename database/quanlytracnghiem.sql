-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 18, 2025 at 03:26 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `quanlytracnghiem`
--

-- --------------------------------------------------------

--
-- Table structure for table `exams`
--

CREATE TABLE `exams` (
  `exam_id` int(11) NOT NULL,
  `test_id` int(11) DEFAULT NULL,
  `exam_code` varchar(255) DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `exams`
--

INSERT INTO `exams` (`exam_id`, `test_id`, `exam_code`, `updated_at`, `is_deleted`) VALUES
(1, 1, 'A', '2025-03-12 14:30:26', 1),
(2, 2, 'B', '2025-02-25 08:44:35', 0),
(3, 3, 'C', '2025-02-25 08:44:35', 0),
(11, 1, '123', '2025-03-12 14:28:16', 0),
(12, 7, 'a', '2025-03-11 09:29:46', 0),
(13, 8, 'ss', '2025-03-11 14:02:05', 0),
(14, 1, '2', '2025-03-12 14:41:16', 0);

-- --------------------------------------------------------

--
-- Table structure for table `exam_questions`
--

CREATE TABLE `exam_questions` (
  `exam_question_id` int(11) NOT NULL,
  `exam_id` int(11) DEFAULT NULL,
  `question_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `exam_questions`
--

INSERT INTO `exam_questions` (`exam_question_id`, `exam_id`, `question_id`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 2, 3),
(4, 2, 4),
(5, 3, 5),
(6, 3, 15),
(7, 3, 20),
(8, 1, 0),
(9, 1, 123123128),
(41, 11, 6),
(42, 11, 16),
(43, 11, 1),
(45, 12, 1),
(46, 12, 6),
(47, 12, 16),
(48, 12, 123123128),
(49, 12, 7),
(50, 13, 14),
(51, 13, 3),
(52, 13, 8),
(53, 13, 4),
(54, 14, 1),
(55, 14, 6),
(56, 14, 16),
(57, 14, 123123128);

-- --------------------------------------------------------

--
-- Table structure for table `options`
--

CREATE TABLE `options` (
  `option_id` int(11) NOT NULL,
  `question_id` int(11) DEFAULT NULL,
  `option_text` text DEFAULT NULL,
  `is_correct` tinyint(1) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `options`
--

INSERT INTO `options` (`option_id`, `question_id`, `option_text`, `is_correct`, `image_url`) VALUES
(1, 1, 'Python', 1, NULL),
(2, 1, 'HTML', 0, NULL),
(3, 1, 'CSS', 0, NULL),
(4, 1, 'SQL', 0, NULL),
(5, 2, 'HashMap', 1, NULL),
(6, 2, 'Array', 0, NULL),
(7, 2, 'Queue', 0, NULL),
(8, 2, 'Stack', 0, NULL),
(13, 4, 'Đà Lạt', 1, NULL),
(14, 4, 'Hà Nội', 0, NULL),
(15, 4, 'Huế', 0, NULL),
(16, 4, 'TP.HCM', 0, NULL),
(17, 5, '6', 1, NULL),
(18, 5, '4', 0, NULL),
(19, 5, '8', 0, NULL),
(20, 5, '2', 0, NULL),
(21, 6, 'Quản lý mã nguồn', 1, NULL),
(22, 6, 'Chỉnh sửa hình ảnh', 0, NULL),
(23, 6, 'Soạn thảo văn bản', 0, NULL),
(24, 6, 'Dịch thuật', 0, NULL),
(25, 7, 'Quản lý dữ liệu', 1, NULL),
(26, 7, 'Chỉnh sửa ảnh', 0, NULL),
(27, 7, 'Lập trình web', 0, NULL),
(28, 7, 'Bảo mật hệ thống', 0, NULL),
(29, 8, 'Sudan', 1, NULL),
(30, 8, 'Ai Cập', 0, NULL),
(31, 8, 'Mexico', 0, NULL),
(32, 8, 'Peru', 0, NULL),
(33, 9, 'Quảng Ninh', 1, NULL),
(34, 9, 'Hà Nội', 0, NULL),
(35, 9, 'Đà Nẵng', 0, NULL),
(36, 9, 'Huế', 0, NULL),
(37, 10, '3.14', 1, NULL),
(38, 10, '2.71', 0, NULL),
(39, 10, '1.62', 0, NULL),
(40, 10, '4.13', 0, NULL),
(41, 11, 'Tam giác vuông', 1, NULL),
(42, 11, 'Tam giác đều', 0, NULL),
(43, 11, 'Tam giác cân', 0, NULL),
(44, 11, 'Tam giác tù', 0, NULL),
(45, 12, 'Trình duyệt & Máy chủ', 1, NULL),
(46, 12, 'Chỉ trình duyệt', 0, NULL),
(47, 12, 'Chỉ máy chủ', 0, NULL),
(48, 12, 'Cả hai đều sai', 0, NULL),
(49, 13, 'Bậc trung', 1, NULL),
(50, 13, 'Bậc thấp', 0, NULL),
(51, 13, 'Bậc cao', 0, NULL),
(52, 13, 'Bậc rất cao', 0, NULL),
(53, 14, 'Rome', 1, NULL),
(54, 14, 'Paris', 0, NULL),
(55, 14, 'London', 0, NULL),
(56, 14, 'Athens', 0, NULL),
(57, 15, '4', 1, NULL),
(58, 15, '5', 0, NULL),
(59, 15, '6', 0, NULL),
(60, 15, '7', 0, NULL),
(61, 16, 'Hyper Transfer Markup Language', 0, NULL),
(62, 16, 'High Tech Modern Language', 0, NULL),
(63, 16, 'Hyper Transfer Markup Language', 0, NULL),
(64, 16, 'HyperText Markup Language', 1, NULL),
(65, 17, 'Lặp qua danh sách', 1, NULL),
(66, 17, 'Viết hàm', 0, NULL),
(67, 17, 'Khai báo biến', 0, NULL),
(68, 17, 'Xuất dữ liệu', 0, NULL),
(69, 18, 'New York', 1, NULL),
(70, 18, 'Tokyo', 0, NULL),
(71, 18, 'London', 0, NULL),
(72, 18, 'Paris', 0, NULL),
(73, 19, 'Paris', 1, NULL),
(74, 19, 'Rome', 0, NULL),
(75, 19, 'Berlin', 0, NULL),
(76, 19, 'Madrid', 0, NULL),
(77, 20, '5', 1, NULL),
(78, 20, '4', 0, NULL),
(79, 20, '6', 0, NULL),
(80, 20, '3', 0, NULL),
(81, 21, '2', 1, NULL),
(82, 21, '1', 0, NULL),
(83, 21, '3', 0, NULL),
(84, 21, '5', 0, NULL),
(85, 22, 'Java', 1, NULL),
(86, 22, 'Python', 0, NULL),
(87, 22, 'C#', 0, NULL),
(88, 22, 'Ruby', 0, NULL),
(89, 23, 'JavaScript', 1, NULL),
(90, 23, 'Python', 0, NULL),
(91, 23, 'C++', 0, NULL),
(92, 23, 'Go', 0, NULL),
(93, 24, 'Vịnh Hạ Long', 1, NULL),
(94, 24, 'Phú Quốc', 0, NULL),
(95, 24, 'Sapa', 0, NULL),
(96, 24, 'Côn Đảo', 0, NULL),
(97, 25, 'Peru', 1, NULL),
(98, 25, 'Mexico', 0, NULL),
(99, 25, 'Brazil', 0, NULL),
(100, 25, 'Colombia', 0, NULL),
(129, 123123126, 'dfasdfasd', 0, NULL),
(130, 123123126, 'ádfas', 0, NULL),
(131, 123123126, 'ádfasd', 0, NULL),
(132, 123123126, 'ádfasdf', 1, NULL),
(145, 123123125, 'fasdfasdf', 1, NULL),
(146, 123123125, 'asdfasfasf', 0, NULL),
(147, 123123125, 'fsadfasdsdfafsdfasdfs', 0, NULL),
(148, 123123125, 'ádfadf', 0, NULL),
(149, 123123127, 'kiểu chuổi', 0, NULL),
(150, 123123127, 'ký tự ', 0, NULL),
(151, 123123127, 'số nguyên', 1, NULL),
(152, 123123127, 'văn bản', 0, NULL),
(157, 3, 'Paris', 1, NULL),
(158, 3, 'London', 0, NULL),
(159, 3, 'Berlin', 0, NULL),
(160, 3, 'Rome', 0, NULL),
(161, 123123128, 'sdfsdfas', 1, NULL),
(162, 123123128, 'asdfasdfasf', 0, NULL),
(163, 123123128, 'fsadfasdfas', 0, NULL),
(164, 123123128, 'fdasdfasf', 0, NULL),
(165, 123123129, '1', 1, NULL),
(166, 123123129, '4', 0, NULL),
(167, 123123129, '2', 0, NULL),
(168, 123123129, '3', 0, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE `questions` (
  `question_id` int(11) NOT NULL,
  `topic_id` int(11) DEFAULT NULL,
  `question_text` text DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `difficulty` enum('easy','medium','difficult') DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `updater` varchar(200) DEFAULT NULL,
  `create_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`question_id`, `topic_id`, `question_text`, `image_url`, `difficulty`, `updated_at`, `updater`, `create_at`, `is_deleted`) VALUES
(1, 1, 'Ngôn ngữ lập trình nào phổ biến nhất hiện nay?', NULL, 'easy', '2025-02-25 07:53:15', 'tran duong', '2025-02-24 07:15:54', 0),
(2, 1, 'Cấu trúc dữ liệu nào dùng để lưu trữ dữ liệu dạng cặp khóa-giá  trị?.', NULL, 'medium', '2025-02-24 08:18:18', NULL, '2025-02-24 07:15:54', 0),
(3, 2, 'Thủ đô của Pháp là gì?', 'aWQQt5fPuk_Cover.png', 'easy', '2025-02-27 13:54:22', 'tran duong', '2025-02-24 07:15:54', 0),
(4, 2, 'Địa danh nào được gọi là thành phố ngàn hoa ở Việt Nam?', NULL, 'medium', '2025-02-23 08:18:43', NULL, '2025-02-24 07:15:54', 0),
(5, 3, 'Kết quả của 2 + 2 * 2 là bao nhiêu?', NULL, 'easy', '2025-02-23 08:18:47', NULL, '2025-02-24 07:15:54', 0),
(6, 1, 'Git là công cụ dùng để làm gì?', NULL, 'easy', '2025-02-23 08:18:50', NULL, '2025-02-24 07:15:54', 0),
(7, 1, 'SQL dùng để làm gì trong cơ sở dữ liệu?', NULL, 'medium', '2025-02-23 08:18:54', NULL, '2025-02-24 07:15:54', 0),
(8, 2, 'Đất nước nào có nhiều kim tự tháp cổ nhất?', NULL, 'medium', '2025-02-23 08:19:00', NULL, '2025-02-24 07:15:54', 0),
(9, 2, 'Vịnh Hạ Long thuộc tỉnh nào của Việt Nam?', NULL, 'easy', '2025-02-25 07:53:05', 'tran duong', '2025-02-24 07:15:54', 0),
(10, 3, 'Số Pi có giá trị gần đúng là bao nhiêu?', NULL, 'medium', '2025-02-25 07:52:59', 'tran duong', '2025-02-24 07:15:54', 0),
(11, 3, 'Định lý Pitago áp dụng cho loại tam giác nào?', NULL, 'easy', '2025-02-25 07:52:56', 'tran duong', '2025-02-24 07:15:54', 0),
(12, 1, 'JavaScript có thể chạy ở đâu?', NULL, 'medium', '2025-02-23 08:19:09', NULL, '2025-02-24 07:15:54', 0),
(13, 1, 'C++ là ngôn ngữ lập trình bậc nào?', NULL, 'medium', '2025-02-23 08:19:13', NULL, '2025-02-24 07:15:54', 0),
(14, 2, 'Địa danh nào nổi tiếng với đấu trường Colosseum?', NULL, 'easy', '2025-02-23 08:20:20', NULL, '2025-02-24 07:15:54', 0),
(15, 3, 'Giá trị của căn bậc hai của 16 là bao nhiêu?', NULL, 'easy', '2025-02-23 08:19:17', NULL, '2025-02-24 07:15:54', 0),
(16, 1, 'HTML là viết tắt của gì?', NULL, 'easy', '2025-02-23 08:19:20', NULL, '2025-02-24 07:15:54', 0),
(17, 1, 'Trong lập trình, vòng lặp for dùng để làm gì?', NULL, 'medium', '2025-02-23 08:19:23', NULL, '2025-02-24 07:15:54', 0),
(18, 2, 'Thành phố nào được mệnh danh là thành phố không bao giờ ngủ?', NULL, 'easy', '2025-02-23 08:19:25', NULL, '2025-02-24 07:15:54', 0),
(19, 2, 'Tháp Eiffel nằm ở đâu?', NULL, 'easy', '2025-02-25 08:02:50', 'tran duong', '2025-02-24 07:15:54', 0),
(20, 3, 'Kết quả của 10 / 2 là bao nhiêu?', NULL, 'easy', '2025-02-25 08:02:47', 'tran duong', '2025-02-24 07:15:54', 0),
(21, 3, 'Số nguyên tố nhỏ nhất là số nào?', NULL, 'medium', '2025-02-25 08:02:45', 'tran duong', '2025-02-24 07:15:54', 0),
(22, 1, 'Ngôn ngữ nào được dùng để lập trình Android?', NULL, 'medium', '2025-02-25 08:02:42', 'tran duong', '2025-02-24 07:15:54', 0),
(23, 1, 'Node.js được viết bằng ngôn ngữ nào?', NULL, 'medium', '2025-02-25 07:59:35', 'tran duong', '2025-02-24 07:15:54', 0),
(24, 2, 'Địa danh nào là kỳ quan thiên nhiên của thế giới ở Việt Nam?', NULL, 'medium', '2025-02-23 08:19:45', NULL, '2025-02-24 07:15:54', 0),
(25, 2, 'Công trình Machu Picchu nằm ở quốc gia nào?', NULL, 'medium', '2025-02-23 08:19:51', NULL, '2025-02-24 07:15:54', 0),
(123123125, 1, 'sdfsdfasdfasfdasfasffs', 'akhEx6hDW6_Cover.png', 'medium', '2025-02-25 08:03:03', 'tran duong', '2025-02-24 13:39:31', 1),
(123123126, 1, 'update 222222', NULL, 'medium', '2025-02-25 08:03:01', 'tran duong', '2025-02-24 14:05:30', 1),
(123123127, 1, ' int là kiểu dữ liêu gì', 'null', 'easy', '2025-02-25 08:02:58', 'tran duong', '2025-02-25 04:24:40', 1),
(123123128, 1, 'sadASDFASDFSDFrsdfsdf', NULL, 'easy', '2025-02-28 06:38:15', 'tran duong', '2025-02-27 09:45:22', 0),
(123123129, 1, 'cáu hỏi được thêm 1', NULL, 'easy', '2025-03-12 15:14:44', NULL, '2025-03-12 15:14:44', 0);

-- --------------------------------------------------------

--
-- Table structure for table `results`
--

CREATE TABLE `results` (
  `result_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `exam_id` int(11) DEFAULT NULL,
  `start_time` timestamp NULL DEFAULT NULL,
  `end_time` timestamp NULL DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `correct` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `results`
--

INSERT INTO `results` (`result_id`, `user_id`, `exam_id`, `start_time`, `end_time`, `score`, `correct`) VALUES
(1, 1, 1, '2025-02-22 03:00:00', '2025-02-22 03:30:00', 90, 90),
(2, 2, 2, '2025-02-22 04:00:00', '2025-02-22 04:25:00', 80, 80),
(3, 3, 3, '2025-02-22 05:00:00', '2025-02-22 05:20:00', 70, 70),
(4, 2, 1, '2025-03-06 08:18:30', '2025-03-06 08:48:30', 60, 66),
(5, 3, 9, '2025-03-06 08:18:30', '2025-03-06 08:48:30', 60, 66),
(6, 3, 10, '2025-03-06 08:18:30', '2025-03-06 08:48:30', 60, 60);

-- --------------------------------------------------------

--
-- Table structure for table `result_details`
--

CREATE TABLE `result_details` (
  `result_detail_id` int(11) NOT NULL,
  `result_id` int(11) DEFAULT NULL,
  `question_id` int(11) DEFAULT NULL,
  `option_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `result_details`
--

INSERT INTO `result_details` (`result_detail_id`, `result_id`, `question_id`, `option_id`) VALUES
(1, 1, 1, 1),
(2, 1, 2, 5),
(3, 2, 3, 9),
(4, 2, 4, 13),
(5, 3, 5, 17);

-- --------------------------------------------------------

--
-- Table structure for table `tests`
--

CREATE TABLE `tests` (
  `test_id` int(11) NOT NULL,
  `topic_id` int(11) DEFAULT NULL,
  `test_name` varchar(255) DEFAULT NULL,
  `test_time` int(11) DEFAULT NULL,
  `num_limit` int(11) DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0,
  `easy` int(11) NOT NULL DEFAULT 0,
  `medium` int(11) NOT NULL DEFAULT 0,
  `difficult` int(11) NOT NULL DEFAULT 0,
  `number_of_questions` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tests`
--

INSERT INTO `tests` (`test_id`, `topic_id`, `test_name`, `test_time`, `num_limit`, `updated_at`, `is_deleted`, `easy`, `medium`, `difficult`, `number_of_questions`) VALUES
(1, 1, 'Bài kiểm tra lập trình cơ bản 1', 30, 3, '2025-03-11 13:42:45', 0, 4, 0, 0, 4),
(2, 2, 'Bài kiểm tra kiến thức du lịch', 25, 2, '2025-03-11 09:08:15', 0, 2, 0, 0, 2),
(3, 3, 'Bài kiểm tra toán học', 20, 5, '2025-03-11 09:08:20', 0, 3, 0, 0, 3),
(7, 1, 'bai test co ban', 30, 1, '2025-03-11 09:29:46', 0, 4, 1, 0, 5),
(8, 2, 'bai test 2', 20, 1, '2025-03-11 14:02:05', 0, 2, 2, 0, 4);

-- --------------------------------------------------------

--
-- Table structure for table `topics`
--

CREATE TABLE `topics` (
  `topic_id` int(11) NOT NULL,
  `topic_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `topics`
--

INSERT INTO `topics` (`topic_id`, `topic_name`) VALUES
(1, 'Lập trình'),
(2, 'Du lịch'),
(3, 'Toán học');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `role` enum('student','admin') DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0,
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `updater` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `password`, `fullname`, `email`, `role`, `is_deleted`, `updated_at`, `updater`) VALUES
(1, '1', 'tran duong 1', 'admin@gmail.com', 'admin', 0, '2025-03-11 08:08:38', 'tran duong 1'),
(2, '1', 'abc', 'a@gmail.com', 'student', 0, '2025-03-02 14:38:54', 'tran duong'),
(3, '1', 'nam', 'nam@gmail.com', 'student', 0, '2025-03-02 06:58:12', ''),
(4, '123', 'dasd', 'sd@gmail.com', 'student', 0, '2025-03-11 09:55:32', 'tran duong 1'),
(5, 'pass123', 'Nguyen Van A', 'email1@gmail.com', 'student', 0, '2025-03-02 14:25:03', NULL),
(6, 'pass456', 'Tran Thi B', 'email2@gmail.com', 'student', 0, '2025-03-02 14:25:03', NULL),
(7, 'pass789', 'Le Van C', 'email3@gmail.com', 'student', 0, '2025-03-02 14:25:03', NULL),
(8, 'pass101', 'Pham Thi D', 'email4@gmail.com', 'student', 0, '2025-03-02 14:25:03', NULL),
(9, 'pass112', 'Hoang Van E', 'email5@gmail.com', 'student', 0, '2025-03-02 14:25:03', NULL),
(10, 'pass131', 'Vu Thi F', 'email6@gmail.com', 'student', 0, '2025-03-02 14:25:03', NULL),
(11, 'pass141', 'Dao Van G', 'email7@gmail.com', 'student', 0, '2025-03-02 14:25:03', NULL),
(12, 'pass151', 'Do Thi H', 'email8@gmail.com', 'student', 0, '2025-03-02 14:25:03', NULL),
(13, 'pass161', 'Truong Van I', 'email9@gmail.com', 'student', 0, '2025-03-02 14:25:03', NULL),
(14, 'pass171', 'Phan Thi K', 'email10@gmail.com', 'student', 1, '2025-03-11 09:55:41', 'tran duong 1');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `exams`
--
ALTER TABLE `exams`
  ADD PRIMARY KEY (`exam_id`);

--
-- Indexes for table `exam_questions`
--
ALTER TABLE `exam_questions`
  ADD PRIMARY KEY (`exam_question_id`);

--
-- Indexes for table `options`
--
ALTER TABLE `options`
  ADD PRIMARY KEY (`option_id`);

--
-- Indexes for table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`question_id`);

--
-- Indexes for table `results`
--
ALTER TABLE `results`
  ADD PRIMARY KEY (`result_id`);

--
-- Indexes for table `result_details`
--
ALTER TABLE `result_details`
  ADD PRIMARY KEY (`result_detail_id`);

--
-- Indexes for table `tests`
--
ALTER TABLE `tests`
  ADD PRIMARY KEY (`test_id`);

--
-- Indexes for table `topics`
--
ALTER TABLE `topics`
  ADD PRIMARY KEY (`topic_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `exams`
--
ALTER TABLE `exams`
  MODIFY `exam_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `exam_questions`
--
ALTER TABLE `exam_questions`
  MODIFY `exam_question_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=59;

--
-- AUTO_INCREMENT for table `options`
--
ALTER TABLE `options`
  MODIFY `option_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=169;

--
-- AUTO_INCREMENT for table `questions`
--
ALTER TABLE `questions`
  MODIFY `question_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=123123130;

--
-- AUTO_INCREMENT for table `results`
--
ALTER TABLE `results`
  MODIFY `result_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `result_details`
--
ALTER TABLE `result_details`
  MODIFY `result_detail_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tests`
--
ALTER TABLE `tests`
  MODIFY `test_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `topics`
--
ALTER TABLE `topics`
  MODIFY `topic_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
