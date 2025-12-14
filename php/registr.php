<?php
require 'db.php';
header('Content-Type: application/json; charset=utf-8');

try {
    if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
        echo json_encode(['status' => 'неуспешно', 'message' => 'Требуется POST'], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    $input = getRequestData();
    $email = trim($input['email'] ?? '');
    $password = $input['password'] ?? '';
    $passport = trim($input['passport'] ?? '');
    $snils = trim($input['snils'] ?? '');

    if ($email === '' || $password === '' || $passport === '' || $snils === '') {
        echo json_encode(['status' => 'неуспешно', 'message' => 'Все поля обязательны: email, password, passport, snils'], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    // проверка на существующий email
    $stmt = $pdo->prepare("SELECT user_id FROM Users WHERE email = :email LIMIT 1");
    $stmt->execute([':email' => $email]);
    if ($stmt->fetch()) {
        echo json_encode(['status' => 'неуспешно', 'message' => 'Пользователь с таким email уже существует'], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    $now = date('Y-m-d H:i:s');
    $user_type = 'student'; // по умолчанию — студент; изменить логику при необходимости

    $ins = $pdo->prepare("INSERT INTO Users (email, snils, passport, password, user_type, created_at) VALUES (:email, :snils, :passport, :password, :user_type, :created_at)");
    $ins->execute([
        ':email' => $email,
        ':snils' => $snils,
        ':passport' => $passport,
        ':password' => $password,
        ':user_type' => $user_type,
        ':created_at' => $now
    ]);

    $user_id = $pdo->lastInsertId();

    // Попытка найти full_name в таблице Students (возможно, внешняя логика добавит запись позже).
    $full_name = '';
    $stmtS = $pdo->prepare("SELECT full_name FROM Students WHERE user_id = :uid LIMIT 1");
    $stmtS->execute([':uid' => $user_id]);
    $s = $stmtS->fetch();
    if ($s && !empty($s['full_name'])) $full_name = $s['full_name'];

    echo json_encode([
        'status' => 'успешно',
        'full_name' => $full_name,
        'user_type' => $user_type
    ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);

} catch (Exception $e) {
    echo json_encode(['status' => 'ошибка сервера', 'message' => $e->getMessage()], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
}