<?php
require 'db.php';
header('Content-Type: application/json; charset=utf-8');

try {
    if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
        echo json_encode(['status' => 'error', 'message' => 'Требуется POST'], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    $input = getRequestData();
    $full_name = trim($input['full_name'] ?? '');
    $email = trim($input['email'] ?? '');
    $password = $input['password'] ?? '';
    $passport = trim($input['passport'] ?? '');
    $snils = trim($input['snils'] ?? '');
    $icon = trim($input['user_icon'] ?? '');

    if ($full_name === '' || $email === '' || $password === '' || $passport === '' || $snils === '') {
        echo json_encode(['status' => 'error', 'message' => 'Все поля обязательны: ФИО, email, пароль, пасспорт, СНИЛС'], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    // проверка на существующий email
    $stmt = $pdo->prepare("SELECT user_id FROM `Users` WHERE email = :email LIMIT 1");
    $stmt->execute([':email' => $email]);
    if ($stmt->fetch()) {
        echo json_encode(['status' => 'error', 'message' => 'Пользователь с таким email уже существует'], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    $now = date('Y-m-d H:i:s');
    $user_type = 'Студент'; // по умолчанию — студент

    $ins = $pdo->prepare("INSERT INTO `Users` (full_name, email, snils, passport, password, user_type, user_icon, created_at) VALUES (:full_name, :email, :snils, :passport, :password, :user_type, :user_icon, :created_at)");
    $ins->execute([
        ':full_name' => $full_name,
        ':email' => $email,
        ':snils' => $snils,
        ':passport' => $passport,
        ':password' => $password,
        ':user_type' => $user_type,
        ':user_icon' => $icon,
        ':created_at' => $now
    ]);

    $user_id = $pdo->lastInsertId();

    echo json_encode([
        'status' => 'success',
        'full_name' => $full_name,
        'user_type' => $user_type,
        'user_icon' => $icon
    ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);

} catch (Exception $e) {
    echo json_encode(['status' => 'error', 'message' => $e->getMessage()], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
}