<?php
require 'db.php';
header('Content-Type: application/json; charset=utf-8');

try {
    if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
        echo json_encode([ 
            'status' => 'error',
            'message' => 'Требуется POST запрос'
        ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    $input = getRequestData();
    $email = trim($input['email'] ?? '');
    $new_password = $input['new_password'] ?? '';

    // Валидация входных данных
    if (empty($email) || empty($new_password)) {
        echo json_encode([
            'status' => 'error',
            'message' => 'Email и новый пароль обязательны'
        ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    // Проверяем существование пользователя
    $stmt = $pdo->prepare("SELECT `user_id` FROM `Users` WHERE `email` = :email LIMIT 1");
    $stmt->execute([':email' => $email]);
    $user = $stmt->fetch();

    if (!$user) {
        echo json_encode([
            'status' => 'error',
            'message' => 'Пользователь с таким email не найден'
        ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    $update_sql = "UPDATE `Users` SET password = :password WHERE email = :email";
    
    $update_stmt = $pdo->prepare($update_sql);

    $update_stmt->execute([
        ':password' => $new_password,
        ':email' => $email
    ]);

    // Проверяем, была ли обновлена хотя бы одна строка
    $rows_affected = $update_stmt->rowCount();

    if ($rows_affected > 0) {
        echo json_encode([
            'status' => 'success',
            'message' => 'Пароль успешно изменен'
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
        'message' => $e->getMessage()
    ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
}