<?php
require 'db.php';
header('Content-Type: application/json; charset=utf-8');

try {
    if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
        echo json_encode([
            'status' => 'error', 
            'message' => 'Требуется POST'
        ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    $input = getRequestData();
    $email = trim($input['email'] ?? '');
    $passport = trim($input['passport'] ?? '');
    $snils = trim($input['snils'] ?? '');
    $new_password = trim($input['new_password'] ?? '');

    if (empty($email) || empty($passport) || empty($snils) || empty($new_password)) {
        echo json_encode([
            'status' => 'error',
            'message' => 'Все поля обязательны: email, пасспорт, СНИЛС, новый пароль'
        ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    // Существование пользователя и соответствие данных
    $stmt = $pdo->prepare("SELECT user_id FROM `Users` WHERE email = :email AND passport = :passport AND snils = :snils LIMIT 1");
    $stmt->execute([
        ':email' => $email,
        ':passport' => $passport,
        ':snils' => $snils
    ]);
    
    $user = $stmt->fetch();

    if (!$user) {
        echo json_encode([
            'status' => 'error',
            'message' => 'Пользователь не найден'
        ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    // Обновление пароля
    $update_stmt = $pdo->prepare("UPDATE `Users` SET password = :password WHERE email = :email AND passport = :passport AND snils = :snils");
    
    $update_stmt->execute([
        ':password' => $new_password,
        ':email' => $email,
        ':passport' => $passport,
        ':snils' => $snils
    ]);

    // Проврочка был ли обновлён пароль
    $rows_affected = $update_stmt->rowCount();

    if ($rows_affected > 0) {
        echo json_encode([
            'status' => 'success',
            'message' => 'Пароль успешно изменён'
        ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
    } else {
        echo json_encode([
            'status' => 'error',
            'message' => 'Не удалось изменить пароль'
        ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
    }

} catch (Exception $e) {
    echo json_encode([
        'status' => 'error',
        'message' => 'Ошибка сервера: ' . $e->getMessage()
    ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
}