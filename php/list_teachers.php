<?php
require 'db.php';
header('Content-Type: application/json; charset=utf-8');

try {
    if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
        echo json_encode([
            'status' => 'неуспешно', 
            'message' => 'Требуется GET запрос'
        ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    $stmt = $pdo->query("SELECT teacher_id, full_name, department, user_id FROM Teachers ORDER BY full_name");
    $teachers = $stmt->fetchAll();

    echo json_encode([
        'status' => 'успешно',
        'count' => count($teachers),
        'data' => $teachers
    ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);

} catch (Exception $e) {
    echo json_encode([
        'status' => 'ошибка сервера', 
        'message' => $e->getMessage()
    ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
}