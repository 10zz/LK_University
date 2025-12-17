<?php
require 'db.php';
header('Content-Type: application/json; charset=utf-8');

try {
    if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
        echo json_encode([
            'status' => 'error', 
            'message' => 'Требуется GET запрос'
        ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    $stmt = $pdo->query("SELECT 
                    u.full_name AS teacher_name, t.department 
                FROM Teachers t 
                LEFT JOIN `Users` u ON t.user_id = u.user_id
                ORDER BY teacher_name");
    $teachers = $stmt->fetchAll();

    echo json_encode([
        'status' => 'success',
        'count' => count($teachers),
        'data' => $teachers
    ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);

} catch (Exception $e) {
    echo json_encode([
        'status' => 'error', 
        'message' => $e->getMessage()
    ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
}