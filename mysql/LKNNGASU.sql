-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1:3306
-- Время создания: Дек 18 2025 г., 16:15
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

--
-- Дамп данных таблицы `ExternalEvents`
--

INSERT INTO `ExternalEvents` (`event_id`, `event_title`, `event_description`) VALUES
(1, 'Научная конференция', 'Ежегодная конференция молодых ученых'),
(2, 'День открытых дверей', 'Знакомство с университетом для абитуриентов'),
(3, 'Хакатон по веб-разработке', 'Соревнование по программированию'),
(4, 'Мастер-класс по ораторскому искусству', 'Развитие навыков публичных выступлений');

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
  `group_id` int DEFAULT NULL,
  `teacher_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `ExternalSchedule`
--

INSERT INTO `ExternalSchedule` (`external_schedule_id`, `schedule_subject`, `schedule_date`, `start_time`, `end_time`, `room`, `group_id`, `teacher_id`) VALUES
(1, 'Проектирование ПО', '2025-11-20', '14:00:00', '15:30:00', '101', 1, 1),
(2, 'Математика', '2025-11-22', '18:00:00', '19:30:00', '303', 2, 2),
(3, 'Квантовая физика', '2025-11-25', '10:00:00', '11:30:00', '405', 3, 3),
(4, 'Химия', '2025-11-28', '15:00:00', '16:30:00', '210', 4, 4);

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

--
-- Дамп данных таблицы `FQW`
--

INSERT INTO `FQW` (`fqw_id`, `teacher_id`, `student_id`, `group_id`, `department`, `theme`) VALUES
(1, 1, 1, 1, 'Кафедра информатики', 'Разработка системы управления базами данных'),
(2, 2, 2, 1, 'Кафедра математики', 'Применение методов машинного обучения в экономике'),
(3, 3, 3, 2, 'Кафедра физики', 'Исследование свойств новых материалов'),
(4, 4, 4, 3, 'Кафедра химии', 'Синтез и изучение органических соединений');

-- --------------------------------------------------------

--
-- Структура таблицы `Groups`
--

CREATE TABLE `Groups` (
  `group_id` int NOT NULL,
  `group_name` varchar(50) NOT NULL,
  `faculty` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `Groups`
--

INSERT INTO `Groups` (`group_id`, `group_name`, `faculty`) VALUES
(1, 'И-24', 'Информатика'),
(2, 'ПМ-24', 'Прикладная математика'),
(3, 'ФИЗ-24', 'Физический'),
(4, 'ХИМ-24', 'Химический');

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

--
-- Дамп данных таблицы `Payments`
--

INSERT INTO `Payments` (`payment_id`, `student_id`, `amount`, `payment_date`, `payment_type`) VALUES
(1, 1, '12500.50', '2024-02-15', 'Инвалидность'),
(2, 2, '15000.00', '2024-02-20', 'Инвалидность'),
(3, 1, '3000.00', '2024-03-01', 'Стипендия'),
(4, 3, '20000.00', '2024-02-25', 'Грант');

-- --------------------------------------------------------

--
-- Структура таблицы `Students`
--

CREATE TABLE `Students` (
  `student_id` int NOT NULL,
  `group_id` int NOT NULL,
  `user_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `Students`
--

INSERT INTO `Students` (`student_id`, `group_id`, `user_id`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 2, 5),
(4, 3, 6);

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

--
-- Дамп данных таблицы `Study`
--

INSERT INTO `Study` (`study_id`, `study_name`, `study_description`, `study_icon`) VALUES
(1, 'Программирование', 'Изучение языков программирования и алгоритмов', 'study_icons/code.png'),
(2, 'Математика', 'Высшая математика и математический анализ', 'study_icons/math.png'),
(3, 'Физика', 'Классическая и современная физика', 'study_icons/physics.png'),
(4, 'Химия', 'Органическая и неорганическая химия', 'study_icons/chemistry.png');

-- --------------------------------------------------------

--
-- Структура таблицы `StudyMaterials`
--

CREATE TABLE `StudyMaterials` (
  `material_id` int NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `icon_path` varchar(500) NOT NULL,
  `link` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `StudyMaterials`
--

INSERT INTO `StudyMaterials` (`material_id`, `title`, `description`, `icon_path`, `link`) VALUES
(1, 'Основы программирования', 'УбиватьУбиватьУбиватьУбиватьУбиватьУбивать', 'study_icons/book.png', 'https://example.com/materials/programming'),
(2, 'Базы данных', 'УбиватьУбиватьУбиватьУбиватьУбиватьУбиватьУ', 'study_icons/presentation.png', 'https://example.com/materials/database'),
(3, 'Математический анализ', 'УбиватьУбиватьУбиватьУбиватьУбиватьУбиватьБ', 'study_icons/textbook.png', 'https://example.com/materials/math'),
(4, 'Лабораторная работа по физике', 'УбиватьУбиватьУбиватьУбиватьУбиватьУбиватьИ', 'study_icons/lab.png', 'https://example.com/materials/physics_lab');

-- --------------------------------------------------------

--
-- Структура таблицы `Teachers`
--

CREATE TABLE `Teachers` (
  `teacher_id` int NOT NULL,
  `department` varchar(100) NOT NULL,
  `user_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `Teachers`
--

INSERT INTO `Teachers` (`teacher_id`, `department`, `user_id`) VALUES
(1, 'Кафедра информатики', 3),
(2, 'Кафедра математики', 4),
(3, 'Кафедра физики', 7),
(4, 'Кафедра химии', 8);

-- --------------------------------------------------------

--
-- Структура таблицы `Users`
--

CREATE TABLE `Users` (
  `user_id` int NOT NULL,
  `full_name` varchar(500) NOT NULL,
  `email` varchar(255) NOT NULL,
  `snils` bigint NOT NULL,
  `passport` bigint NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_icon` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'profile_pictures/pic.jpg',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `Users`
--

INSERT INTO `Users` (`user_id`, `full_name`, `email`, `snils`, `passport`, `password`, `user_type`, `user_icon`, `created_at`) VALUES
(1, 'Иванов Иван Иванович', 'ivanov@example.com', 12345678901, 4500123456, '12345678', 'Студент', 'profile_pictures/student1.png', '2024-01-15 07:30:00'),
(2, 'Петрова Анна Сергеевна', 'petrova@example.com', 23456789012, 4501234567, '23456789', 'Студент', 'profile_pictures/student2.png', '2024-01-16 08:45:00'),
(3, 'Смирнов Александр Васильевич', 'smirnov@example.com', 34567890123, 4502345678, '34567890', 'Преподаватель', 'profile_pictures/teacher1.png', '2024-01-10 06:20:00'),
(4, 'Кузнецов Виктор Кузнецович', 'kuznetsov@example.com', 45678901234, 4503456789, '45678901', 'Преподаватель', 'profile_pictures/teacher2.png', '2024-01-12 11:15:00'),
(5, 'Сидоров Алексей Петрович', 'sidorov@example.com', 56789012345, 4500413456, '56789012', 'Студент', 'profile_pictures/student3.png', '2024-01-11 08:30:00'),
(6, 'Козлов Иван Козлович', 'kozlov@example.com', 67890123456, 4501276667, '67890123', 'Студент', 'profile_pictures/student4.png', '2024-01-13 09:45:00'),
(7, 'Попов Дмитрий Сергеевич', 'popov@example.com', 78901234567, 4502311178, '78901234', 'Преподаватель', 'profile_pictures/teacher3.png', '2024-01-14 05:20:00'),
(8, 'Васильев Антон Волков', 'vasiliev@example.com', 89012345678, 4507686789, '89012345', 'Преподаватель', 'profile_pictures/teacher4.png', '2024-01-19 12:15:00'),
(9, 'Зубенко Михаил Петрович', 'zubenko@example.com', 90123456789, 5500123456, '12345679', 'Студент', 'profile_pictures/pic.jpeg', '2025-12-17 10:37:01');

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
  ADD PRIMARY KEY (`external_schedule_id`),
  ADD KEY `fk_teacher_schedule` (`teacher_id`),
  ADD KEY `fk_schedule_groups` (`group_id`);

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
  MODIFY `event_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT для таблицы `ExternalSchedule`
--
ALTER TABLE `ExternalSchedule`
  MODIFY `external_schedule_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT для таблицы `FQW`
--
ALTER TABLE `FQW`
  MODIFY `fqw_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT для таблицы `Groups`
--
ALTER TABLE `Groups`
  MODIFY `group_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT для таблицы `Payments`
--
ALTER TABLE `Payments`
  MODIFY `payment_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT для таблицы `Students`
--
ALTER TABLE `Students`
  MODIFY `student_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT для таблицы `Study`
--
ALTER TABLE `Study`
  MODIFY `study_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT для таблицы `StudyMaterials`
--
ALTER TABLE `StudyMaterials`
  MODIFY `material_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT для таблицы `Teachers`
--
ALTER TABLE `Teachers`
  MODIFY `teacher_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT для таблицы `Users`
--
ALTER TABLE `Users`
  MODIFY `user_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `ExternalSchedule`
--
ALTER TABLE `ExternalSchedule`
  ADD CONSTRAINT `fk_schedule_groups` FOREIGN KEY (`group_id`) REFERENCES `Groups` (`group_id`),
  ADD CONSTRAINT `fk_teacher_schedule` FOREIGN KEY (`teacher_id`) REFERENCES `Teachers` (`teacher_id`);

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
