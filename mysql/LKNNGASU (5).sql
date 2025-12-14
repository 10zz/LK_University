-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1:3306
-- Время создания: Дек 14 2025 г., 19:35
-- Версия сервера: 8.0.30
-- Версия PHP: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `LKNNGASU`
--

-- --------------------------------------------------------

--
-- Структура таблицы `ExternalEvents`
--

CREATE TABLE `ExternalEvents` (
  `event_id` int NOT NULL,
  `event_title` varchar(255) NOT NULL,
  `event_description` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `ExternalSchedule`
--

CREATE TABLE `ExternalSchedule` (
  `external_schedule_id` int NOT NULL,
  `schedule_subject` varchar(255) NOT NULL,
  `schedule_date` date NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  `room` varchar(50) DEFAULT NULL,
  `group_name` varchar(50) DEFAULT NULL,
  `teacher_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `FQW`
--

CREATE TABLE `FQW` (
  `fqw_id` int NOT NULL,
  `teacher_id` int NOT NULL,
  `student_id` int NOT NULL,
  `group_id` int NOT NULL,
  `department` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `theme` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `Groups`
--

CREATE TABLE `Groups` (
  `group_id` int NOT NULL,
  `group_name` varchar(50) NOT NULL,
  `faculty` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `Payments`
--

CREATE TABLE `Payments` (
  `payment_id` int NOT NULL,
  `student_id` int NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `payment_date` date NOT NULL,
  `payment_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `Students`
--

CREATE TABLE `Students` (
  `student_id` int NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `group_id` int NOT NULL,
  `user_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `Study`
--

CREATE TABLE `Study` (
  `study_id` int NOT NULL,
  `study_name` varchar(255) NOT NULL,
  `study_description` varchar(255) NOT NULL,
  `study_icon` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `StudyMaterials`
--

CREATE TABLE `StudyMaterials` (
  `material_id` int NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `material_type` varchar(50) NOT NULL,
  `icon_path` varchar(500) NOT NULL,
  `link` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `Teachers`
--

CREATE TABLE `Teachers` (
  `teacher_id` int NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `department` varchar(100) NOT NULL,
  `user_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `Users`
--

CREATE TABLE `Users` (
  `user_id` int NOT NULL,
  `email` varchar(255) NOT NULL,
  `snils` int NOT NULL,
  `passport` int NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_type` varchar(10) NOT NULL,
  `user_icon` varchar(500) NOT NULL DEFAULT '/profile_pictures/pic.jpg',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `ExternalEvents`
--
ALTER TABLE `ExternalEvents`
  ADD PRIMARY KEY (`event_id`);

--
-- Индексы таблицы `ExternalSchedule`
--
ALTER TABLE `ExternalSchedule`
  ADD PRIMARY KEY (`external_schedule_id`);

--
-- Индексы таблицы `FQW`
--
ALTER TABLE `FQW`
  ADD PRIMARY KEY (`fqw_id`),
  ADD KEY `fk_fqw_teachers` (`teacher_id`),
  ADD KEY `fk_fqw_students` (`student_id`),
  ADD KEY `fk_fqw_groups` (`group_id`);

--
-- Индексы таблицы `Groups`
--
ALTER TABLE `Groups`
  ADD PRIMARY KEY (`group_id`);

--
-- Индексы таблицы `Payments`
--
ALTER TABLE `Payments`
  ADD PRIMARY KEY (`payment_id`),
  ADD KEY `fk_payments_students` (`student_id`);

--
-- Индексы таблицы `Students`
--
ALTER TABLE `Students`
  ADD PRIMARY KEY (`student_id`),
  ADD KEY `fk_students_users` (`user_id`),
  ADD KEY `fk_students_groups` (`group_id`);

--
-- Индексы таблицы `Study`
--
ALTER TABLE `Study`
  ADD PRIMARY KEY (`study_id`);

--
-- Индексы таблицы `StudyMaterials`
--
ALTER TABLE `StudyMaterials`
  ADD PRIMARY KEY (`material_id`);

--
-- Индексы таблицы `Teachers`
--
ALTER TABLE `Teachers`
  ADD PRIMARY KEY (`teacher_id`),
  ADD KEY `fk_teachers_users` (`user_id`);

--
-- Индексы таблицы `Users`
--
ALTER TABLE `Users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `ExternalEvents`
--
ALTER TABLE `ExternalEvents`
  MODIFY `event_id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `ExternalSchedule`
--
ALTER TABLE `ExternalSchedule`
  MODIFY `external_schedule_id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `FQW`
--
ALTER TABLE `FQW`
  MODIFY `fqw_id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `Groups`
--
ALTER TABLE `Groups`
  MODIFY `group_id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `Payments`
--
ALTER TABLE `Payments`
  MODIFY `payment_id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `Students`
--
ALTER TABLE `Students`
  MODIFY `student_id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `Study`
--
ALTER TABLE `Study`
  MODIFY `study_id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `StudyMaterials`
--
ALTER TABLE `StudyMaterials`
  MODIFY `material_id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `Teachers`
--
ALTER TABLE `Teachers`
  MODIFY `teacher_id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `Users`
--
ALTER TABLE `Users`
  MODIFY `user_id` int NOT NULL AUTO_INCREMENT;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `FQW`
--
ALTER TABLE `FQW`
  ADD CONSTRAINT `fk_fqw_groups` FOREIGN KEY (`group_id`) REFERENCES `Groups` (`group_id`),
  ADD CONSTRAINT `fk_fqw_students` FOREIGN KEY (`student_id`) REFERENCES `Students` (`student_id`),
  ADD CONSTRAINT `fk_fqw_teachers` FOREIGN KEY (`teacher_id`) REFERENCES `Teachers` (`teacher_id`);

--
-- Ограничения внешнего ключа таблицы `Payments`
--
ALTER TABLE `Payments`
  ADD CONSTRAINT `fk_payments_students` FOREIGN KEY (`student_id`) REFERENCES `Students` (`student_id`);

--
-- Ограничения внешнего ключа таблицы `Students`
--
ALTER TABLE `Students`
  ADD CONSTRAINT `fk_students_groups` FOREIGN KEY (`group_id`) REFERENCES `Groups` (`group_id`),
  ADD CONSTRAINT `fk_students_users` FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`);

--
-- Ограничения внешнего ключа таблицы `Teachers`
--
ALTER TABLE `Teachers`
  ADD CONSTRAINT `fk_teachers_users` FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
