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

    if ($email === '' || $password === '') {
        echo json_encode(['status' => 'неуспешно', 'message' => 'email и пароль обязательны'], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    $stmt = $pdo->prepare("SELECT * FROM Users WHERE email = :email LIMIT 1");
    $stmt->execute([':email' => $email]);
    $user = $stmt->fetch();

    if (!$user) {
        echo json_encode(['status' => 'неуспешно', 'message' => 'Неверный email или пароль'], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    if (!password_verify($password, $user['password'])) {
        echo json_encode(['status' => 'неуспешно', 'message' => 'Неверный email или пароль'], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    // Найдём полное имя: сначала в Students, потом в Teachers
    $full_name = '';
    $user_type = $user['user_type'] ?? '';

    if ($user['user_id']) {
        $stmtS = $pdo->prepare("SELECT full_name FROM Students WHERE user_id = :uid LIMIT 1");
        $stmtS->execute([':uid' => $user['user_id']]);
        $s = $stmtS->fetch();
        if ($s && !empty($s['full_name'])) {
            $full_name = $s['full_name'];
        } else {
            $stmtT = $pdo->prepare("SELECT full_name FROM Teachers WHERE user_id = :uid LIMIT 1");
            $stmtT->execute([':uid' => $user['user_id']]);
            $t = $stmtT->fetch();
            if ($t && !empty($t['full_name'])) {
                $full_name = $t['full_name'];
            }
        }
    }

    // Если имени нет — подставим email как fallback
    if ($full_name === '') $full_name = $user['email'];

    echo json_encode([
        'status' => 'успешно',
        'full_name' => $full_name,
        'user_type' => $user_type
    ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);

} catch (Exception $e) {
    echo json_encode(['status' => 'ошибка сервера', 'message' => $e->getMessage()], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
}
