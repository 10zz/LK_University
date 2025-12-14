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

    $stmt = $pdo->query("SELECT group_id, group_name, faculty FROM Groups ORDER BY group_name");
    $groups = $stmt->fetchAll();

    echo json_encode([
        'status' => 'успешно',
        'count' => count($groups),
        'data' => $groups
    ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);

} catch (Exception $e) {
    echo json_encode([
        'status' => 'ошибка сервера', 
        'message' => $e->getMessage()
    ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
}