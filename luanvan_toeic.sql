-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th6 14, 2024 lúc 09:36 AM
-- Phiên bản máy phục vụ: 10.4.28-MariaDB
-- Phiên bản PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `luanvan_toeic`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `answer`
--

CREATE TABLE `answer` (
  `id` bigint(20) NOT NULL,
  `content` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `correct_answer` bit(1) NOT NULL,
  `question_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `kind_of_structure`
--

CREATE TABLE `kind_of_structure` (
  `id` bigint(20) NOT NULL,
  `name` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `level`
--

CREATE TABLE `level` (
  `id` bigint(20) NOT NULL,
  `name` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `level`
--

INSERT INTO `level` (`id`, `name`) VALUES
(1, 'easy'),
(2, 'average'),
(3, 'hard');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `part`
--

CREATE TABLE `part` (
  `id` bigint(20) NOT NULL,
  `description` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `name` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `part_number` int(11) NOT NULL,
  `skill_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `part`
--

INSERT INTO `part` (`id`, `description`, `name`, `part_number`, `skill_id`) VALUES
(1, 'test mô tả', 'part_5_reading', 5, 2),
(2, 'test mô tả', 'part_1_listening', 1, 1),
(3, 'test mô tả', 'part_2_listening', 2, 1),
(4, 'test mô tả', 'part_3_listening', 3, 1),
(5, 'test mô tả', 'part_4_listening', 4, 1),
(6, 'test mô tả', 'part_6_reading', 6, 2),
(7, 'test mô tả', 'part_7_reading', 7, 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `question`
--

CREATE TABLE `question` (
  `id` bigint(20) NOT NULL,
  `name` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `topic_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `result`
--

CREATE TABLE `result` (
  `id` bigint(20) NOT NULL,
  `status` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `total_mark` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `role`
--

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `name` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
(1, 'Role_Admin'),
(2, 'Role_Student');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `skill`
--

CREATE TABLE `skill` (
  `id` bigint(20) NOT NULL,
  `name` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `skill`
--

INSERT INTO `skill` (`id`, `name`) VALUES
(1, 'listening'),
(2, 'reading'),
(3, 'speaking');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `structure`
--

CREATE TABLE `structure` (
  `id` bigint(20) NOT NULL,
  `level_of_topic` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `name` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `number_of_topic` int(11) DEFAULT NULL,
  `kind_of_structure_id` bigint(20) NOT NULL,
  `part_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `test`
--

CREATE TABLE `test` (
  `id` bigint(20) NOT NULL,
  `create_at` datetime DEFAULT NULL,
  `status` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `test_detail`
--

CREATE TABLE `test_detail` (
  `id` bigint(20) NOT NULL,
  `question_id` bigint(20) NOT NULL,
  `test_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `test_stucture_detail`
--

CREATE TABLE `test_stucture_detail` (
  `id` bigint(20) NOT NULL,
  `structure_id` bigint(20) NOT NULL,
  `test_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `topic`
--

CREATE TABLE `topic` (
  `id` bigint(20) NOT NULL,
  `audio_name` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `audio_path` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `content` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `image_name` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `image_path` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `is_deleted` bit(1) NOT NULL,
  `name` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `level_id` bigint(20) NOT NULL,
  `part_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `address` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `email` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_deleted` bit(1) NOT NULL,
  `name` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone_number` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `reset_token` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `role_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`id`, `address`, `email`, `is_deleted`, `name`, `password`, `phone_number`, `reset_token`, `role_id`) VALUES
(1, NULL, 'admin@gmail.com', b'0', 'admin123', '$2a$10$waqCqL3rDBvfj9axcUBC5uP5lJmC12HZ2HulyusGGdJnTyv/CQalG', NULL, NULL, 1),
(2, 'nha a ngo B', 'giap@gmail.com', b'0', 'giapgiap', '$2a$10$OP62vt9Nj/YJsvdQOmvmAOXQBgGXfL/5NtaVx6AsTbZ4pKfoKKJcq', '0967852035', NULL, 2),
(8, NULL, 'giap123@gmail.com', b'0', 'giapgiap123', '$2a$10$wu8kcI6AWrguafta2TAW7OfAe0ujjTfHwdrZKUotY2njnIT5SRcT6', NULL, NULL, 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_answer`
--

CREATE TABLE `user_answer` (
  `id` bigint(20) NOT NULL,
  `answer_number` int(11) DEFAULT NULL,
  `answer_choice` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `mark` int(11) DEFAULT NULL,
  `result_id` bigint(20) NOT NULL,
  `test_detail_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `answer`
--
ALTER TABLE `answer`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK8frr4bcabmmeyyu60qt7iiblo` (`question_id`);

--
-- Chỉ mục cho bảng `kind_of_structure`
--
ALTER TABLE `kind_of_structure`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `level`
--
ALTER TABLE `level`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `part`
--
ALTER TABLE `part`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK8ydnfkj2151xeq6jb4f1cgo0s` (`skill_id`);

--
-- Chỉ mục cho bảng `question`
--
ALTER TABLE `question`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK9h1t7swdq9eej6qf9yxtc8g9w` (`topic_id`);

--
-- Chỉ mục cho bảng `result`
--
ALTER TABLE `result`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKpjjrrf0483ih2cvyfmx70a16b` (`user_id`);

--
-- Chỉ mục cho bảng `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_8sewwnpamngi6b1dwaa88askk` (`name`);

--
-- Chỉ mục cho bảng `skill`
--
ALTER TABLE `skill`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `structure`
--
ALTER TABLE `structure`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKsfadkrfswxbx6r18995eg2fie` (`kind_of_structure_id`),
  ADD KEY `FKdku7w7r1wdmrkrvud92u2l5kx` (`part_id`);

--
-- Chỉ mục cho bảng `test`
--
ALTER TABLE `test`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `test_detail`
--
ALTER TABLE `test_detail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKe5rgmvgs525mxbjylo4jeoe6b` (`question_id`),
  ADD KEY `FKp2sksenmtggnynlniaqougqfm` (`test_id`);

--
-- Chỉ mục cho bảng `test_stucture_detail`
--
ALTER TABLE `test_stucture_detail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK7w0h453ll5r4hi0cgs1du2yyf` (`structure_id`),
  ADD KEY `FKmp8xjvcsvmhh5ykl5u8dxur37` (`test_id`);

--
-- Chỉ mục cho bảng `topic`
--
ALTER TABLE `topic`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK7an4m7qxxtyhdbihi1q11kfw6` (`level_id`),
  ADD KEY `FK626sxjf0td58gaj6oncou5wel` (`part_id`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  ADD UNIQUE KEY `UK_gj2fy3dcix7ph7k8684gka40c` (`name`),
  ADD KEY `FKn82ha3ccdebhokx3a8fgdqeyy` (`role_id`);

--
-- Chỉ mục cho bảng `user_answer`
--
ALTER TABLE `user_answer`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjh092vl8km1jtqe40lq63l7vt` (`result_id`),
  ADD KEY `FK6gqn4bkst4k2p2wga0ckluomj` (`test_detail_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `answer`
--
ALTER TABLE `answer`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `kind_of_structure`
--
ALTER TABLE `kind_of_structure`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `level`
--
ALTER TABLE `level`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `part`
--
ALTER TABLE `part`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `question`
--
ALTER TABLE `question`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `result`
--
ALTER TABLE `result`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `role`
--
ALTER TABLE `role`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `skill`
--
ALTER TABLE `skill`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `structure`
--
ALTER TABLE `structure`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `test`
--
ALTER TABLE `test`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `test_detail`
--
ALTER TABLE `test_detail`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `test_stucture_detail`
--
ALTER TABLE `test_stucture_detail`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `topic`
--
ALTER TABLE `topic`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT cho bảng `user_answer`
--
ALTER TABLE `user_answer`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `answer`
--
ALTER TABLE `answer`
  ADD CONSTRAINT `FK8frr4bcabmmeyyu60qt7iiblo` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`);

--
-- Các ràng buộc cho bảng `part`
--
ALTER TABLE `part`
  ADD CONSTRAINT `FK8ydnfkj2151xeq6jb4f1cgo0s` FOREIGN KEY (`skill_id`) REFERENCES `skill` (`id`);

--
-- Các ràng buộc cho bảng `question`
--
ALTER TABLE `question`
  ADD CONSTRAINT `FK9h1t7swdq9eej6qf9yxtc8g9w` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`);

--
-- Các ràng buộc cho bảng `result`
--
ALTER TABLE `result`
  ADD CONSTRAINT `FKpjjrrf0483ih2cvyfmx70a16b` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Các ràng buộc cho bảng `structure`
--
ALTER TABLE `structure`
  ADD CONSTRAINT `FKdku7w7r1wdmrkrvud92u2l5kx` FOREIGN KEY (`part_id`) REFERENCES `part` (`id`),
  ADD CONSTRAINT `FKsfadkrfswxbx6r18995eg2fie` FOREIGN KEY (`kind_of_structure_id`) REFERENCES `kind_of_structure` (`id`);

--
-- Các ràng buộc cho bảng `test_detail`
--
ALTER TABLE `test_detail`
  ADD CONSTRAINT `FKe5rgmvgs525mxbjylo4jeoe6b` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`),
  ADD CONSTRAINT `FKp2sksenmtggnynlniaqougqfm` FOREIGN KEY (`test_id`) REFERENCES `test` (`id`);

--
-- Các ràng buộc cho bảng `test_stucture_detail`
--
ALTER TABLE `test_stucture_detail`
  ADD CONSTRAINT `FK7w0h453ll5r4hi0cgs1du2yyf` FOREIGN KEY (`structure_id`) REFERENCES `structure` (`id`),
  ADD CONSTRAINT `FKmp8xjvcsvmhh5ykl5u8dxur37` FOREIGN KEY (`test_id`) REFERENCES `test` (`id`);

--
-- Các ràng buộc cho bảng `topic`
--
ALTER TABLE `topic`
  ADD CONSTRAINT `FK626sxjf0td58gaj6oncou5wel` FOREIGN KEY (`part_id`) REFERENCES `part` (`id`),
  ADD CONSTRAINT `FK7an4m7qxxtyhdbihi1q11kfw6` FOREIGN KEY (`level_id`) REFERENCES `level` (`id`);

--
-- Các ràng buộc cho bảng `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FKn82ha3ccdebhokx3a8fgdqeyy` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);

--
-- Các ràng buộc cho bảng `user_answer`
--
ALTER TABLE `user_answer`
  ADD CONSTRAINT `FK6gqn4bkst4k2p2wga0ckluomj` FOREIGN KEY (`test_detail_id`) REFERENCES `test_detail` (`id`),
  ADD CONSTRAINT `FKjh092vl8km1jtqe40lq63l7vt` FOREIGN KEY (`result_id`) REFERENCES `result` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
