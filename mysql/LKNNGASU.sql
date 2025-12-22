-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1:3306
-- Время создания: Дек 22 2025 г., 20:35
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
(1, 'Уважаемые студенты!', 'В этом разделе Вы можете оценить работу университета. Это поможет нам выявить проблемные аспекты нашей деятельности и реализовать мероприятия по повышению качества образования!\r\n\r\nОбращаем Ваше внимание, что все данные собираются обезличено и анонимно. Преподаватели, кафедры, факультеты, администрация не имеют технической возможности узнать, кто ставил оценки и оставлял комментарии. \r\n\r\nПросим Вас дать максимально объективную оценку! Это позволит нам повысить качество образования и сделать Ваше обучение более комфортным и результативным!\r\n\r\nСПАСИБО ЗА ВАШЕ МНЕНИЕ!\r\n\r\nДля более полной оценки Вы можете ответить на вопросы размещенных ниже анкет и оценить Вашу удовлетворенность качеством образовательного процесса, вежливостью работников университета, а также оставить свои отзывы и предложения.\r\n\r\nЭти анкеты доступны для заполнения на постоянной основе. Вы можете пройти анкетирование в любое удобное для Вас время.');

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
(1, 'Проектирование программного обеспечения (Л)', '2025-11-10', '08:00:00', '09:30:00', 'к.-ауд. 9-104', 1, 1),
(2, 'Проектирование программного обеспечения (П)', '2025-11-10', '09:45:00', '11:15:00', 'к.-ауд. 9-103', 1, 1),
(3, 'Разработка мобильных приложений (П)', '2025-11-10', '11:30:00', '13:00:00', 'к.-ауд. 9-101', 1, 2),
(4, 'Архитектура ЭВМ (Л)', '2025-11-11', '08:00:00', '09:30:00', 'к.-ауд. 9-104', 1, 3),
(5, 'Архитектура ЭВМ (П)', '2025-10-11', '09:45:00', '11:15:00', 'к.-ауд. 9-101', 1, 3),
(6, 'Проектный практикум (Л) ', '2025-10-11', '11:30:00', '13:00:00', 'к.-ауд. 3-502', 1, 4),
(7, 'Проектный практикум (П)', '2025-11-13', '08:00:00', '09:30:00', 'к.-ауд. 9-104', 1, 4),
(8, 'Проектный практикум (П)', '2025-11-13', '08:00:00', '09:45:00', 'к.-ауд. 9-104', 1, 4);

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
(1, 1, 1, 1, 'Кафедра прикладной информатики и статистики', 'Проектирование библиотеки для создания компонентов'),
(2, 2, 2, 1, 'Кафедра прикладной информатики и статистики', 'Разработка и внедрение микросервисной архитектуры для высоконагруженного веб-приложения'),
(3, 3, 3, 2, 'Кафедра прикладной информатики и статистики', 'Система прогнозирования отказов оборудования на основе анализа временных рядов данных'),
(4, 4, 4, 3, 'Кафедра прикладной информатики и статистики', 'Приложение для совместной разработки кода в реальном времени с использованием операционных преобразований');

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
(1, 'ПРИ22/1', 'Программная инженерия'),
(2, 'ПРИ22/2', 'Программная инженерия'),
(3, 'ПИЭ22', 'Прикладная информатика');

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
(1, 1, '4000.50', '2024-02-15', 'Стипендия'),
(2, 2, '-10000.00', '2024-02-20', 'Оплата обучения'),
(3, 1, '4000.00', '2024-03-01', 'Стипендия'),
(4, 3, '-10000.00', '2024-02-25', 'Оплата обучения');

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
(1, 'Moodle LMS', 'lms nngasu - портал для организации сопровождения образовательного процесса с применением электронного обучения.', 'study_icons/moodle_image.png', 'https://example.com/materials/programming'),
(2, 'Видеолекции', '', 'study_icons/videolecturers_image.png', 'https://example.com/materials/database'),
(3, 'Telegram Бот ЛК', 'Telegram Бот ЛК - инструмент для взаимодействия с сервисами Личного кабинета, посредством системы мнгновенного обмена сообщениями Telegram', 'study_icons/telegram_icon.png', 'https://example.com/materials/math');

-- --------------------------------------------------------

--
-- Структура таблицы `Teachers`
--

CREATE TABLE `Teachers` (
  `teacher_id` int NOT NULL,
  `department` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `Teachers`
--

INSERT INTO `Teachers` (`teacher_id`, `department`, `user_id`) VALUES
(1, 'Кафедра прикладной информатики и статистики', 3),
(2, 'Кафедра прикладной информатики и статистики', 4),
(3, 'Кафедра прикладной информатики и статистики', 7),
(4, 'Кафедра прикладной информатики и статистики', 8);

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
  `user_icon` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'profile_pictures/pic.png',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `Users`
--

INSERT INTO `Users` (`user_id`, `full_name`, `email`, `snils`, `passport`, `password`, `user_type`, `user_icon`, `created_at`) VALUES
(1, 'Иванов Иван Иванович', 'ivanov@example.com', 12345678901, 4500123456, '12345678', 'Студент', 'profile_pictures/pic.png', '2024-01-15 07:30:00'),
(2, 'Петрова Анна Сергеевна', 'petrova@example.com', 23456789012, 4501234567, '23456789', 'Студент', 'profile_pictures/pic.png', '2024-01-16 08:45:00'),
(3, 'Родионов Александр Олегович', 'rodionov@example.com', 34567890123, 4502345678, '34567890', 'Преподаватель', 'profile_pictures/pic.png', '2024-01-10 06:20:00'),
(4, 'Ларичева Татьяна Викторовна', 'laricheva@example.com', 45678901234, 4503456789, '45678901', 'Преподаватель', 'profile_pictures/pic.png', '2024-01-12 11:15:00'),
(5, 'Сидоров Алексей Петрович', 'sidorov@example.com', 56789012345, 4500413456, '56789012', 'Студент', 'profile_pictures/pic.png', '2024-01-11 08:30:00'),
(6, 'Козлов Иван Козлович', 'kozlov@example.com', 67890123456, 4501276667, '67890123', 'Студент', 'profile_pictures/pic.png', '2024-01-13 09:45:00'),
(7, 'Смычёк Мария Михайловна', 'smychek@example.com', 78901234567, 4502311178, '78901234', 'Преподаватель', 'profile_pictures/pic.png', '2024-01-14 05:20:00'),
(8, 'Родионова Светлана Владимировна', 'arzhenovskiy@example.com', 89012345678, 4507686789, '89012345', 'Преподаватель', 'profile_pictures/pic.png', '2024-01-19 12:15:00'),
(9, 'Зубенко Михаил Петрович', 'zubenko@example.com', 90123456789, 5500123456, '92345679', 'Студент', 'profile_pictures/pic.png', '2025-12-17 10:37:01');

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
  MODIFY `external_schedule_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

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
