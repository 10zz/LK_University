<?php
require 'db.php';
header('Content-Type: application/json; charset=utf-8');

try {
    if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
        echo json_encode([
            'status' => 'неуспешно', 
            'message' => 'Требуется POST запрос'
        ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    $input = getRequestData();
    $email = trim($input['email'] ?? '');
    $current_password = $input['current_password'] ?? ''; // Текущий пароль для проверки
    $new_password = $input['new_password'] ?? '';
    $confirm_password = $input['confirm_password'] ?? ''; // Подтверждение нового пароля

    // Валидация входных данных
    if (empty($email) || empty($new_password)) {
        echo json_encode([
            'status' => 'неуспешно',
            'message' => 'Все поля обязательны'
        ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    if (!empty($current_password)) {
        // Проверяем существование пользователя и текущий пароль
        $stmt = $pdo->prepare("SELECT user_id, password FROM Users WHERE email = :email LIMIT 1");
        $stmt->execute([':email' => $email]);
        $user = $stmt->fetch();

        if (!$user) {
            echo json_encode([
                'status' => 'неуспешно',
                'message' => 'Пользователь с таким email не найден'
            ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
            exit;
        }

        // Проверка текущего пароля
        if ($current_password !== $user['password']) {
            echo json_encode([
                'status' => 'неуспешно',
                'message' => 'Текущий пароль неверен'
            ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
            exit;
        }
    }

    // Проверка сложности нового пароля
    if (strlen($new_password) < 6) {
        echo json_encode([
            'status' => 'неуспешно',
            'message' => 'Пароль должен содержать минимум 6 символов'
        ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    // Проверка совпадения нового пароля и подтверждения
    if (!empty($confirm_password) && $new_password !== $confirm_password) {
        echo json_encode([
            'status' => 'неуспешно',
            'message' => 'Новый пароль и подтверждение не совпадают'
        ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    // Обновление пароля
    $update_sql = "UPDATE Users SET password = :password WHERE email = :email";
    $params = [
        ':password' => $new_password,
        ':email' => $email
    ];

    $update_stmt = $pdo->prepare($update_sql);
    $update_stmt->execute($params);

    $rows_affected = $update_stmt->rowCount();

    if ($rows_affected > 0) {
        echo json_encode([
            'status' => 'успешно',
            'message' => 'Пароль успешно изменен'
        ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
    } else {
        echo json_encode([
            'status' => 'неуспешно',
            'message' => 'Не удалось изменить пароль'
        ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
    }

} catch (PDOException $e) {
    error_log("Password change error: " . $e->getMessage());
    
    echo json_encode([
        'status' => 'ошибка сервера', 
        'message' => $e->getMessage()
    ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
} catch (Exception $e) {
    error_log("General error in password change: " . $e->getMessage());
    
    echo json_encode([
        'status' => 'ошибка сервера', 
        'message' => $e->getMessage()
    ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
}