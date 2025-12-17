<?php
require 'db.php';
header('Content-Type: application/json; charset=utf-8');

try {
    if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
        echo json_encode(['status' => 'error', 'message' => 'Требуется POST'], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    $input = getRequestData();
    $name = trim($input['name'] ?? '');

    if ($name === '') {
        echo json_encode(['status' => 'error', 'message' => 'Поле ФИО обязательно'], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    $sql = "SELECT 
                p.payment_id, 
                p.amount, 
                p.payment_date, 
                p.payment_type,
                u.full_name AS student_name
            FROM `Payments` p
            INNER JOIN `Students` s ON p.student_id = s.student_id
            INNER JOIN `Users` u ON s.user_id = u.user_id
            WHERE u.full_name LIKE :name
            ORDER BY p.payment_date DESC
            LIMIT 100";
    
    $stmt = $pdo->prepare($sql);
    $stmt->execute([':name' => "%$name%"]);
    $payments = $stmt->fetchAll();

    if (empty($payments)) {
        echo json_encode([
            'status' => 'error',
            'message' => 'Платежи для студента не найдены'
        ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    $out = [];
    foreach ($payments as $p) {
        $out[] = [
            'student' => $p['student_name'],
            'operation' => 'Начисление',
            'operation_type' => $p['payment_type'],
            'amount' => (float)$p['amount'],
            'time' => $p['payment_date']
        ];
    }

    echo json_encode([
        'status' => 'success',
        'count' => count($out),
        'data' => $out
    ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);

} catch (Exception $e) {
    echo json_encode([
        'status' => 'error', 
        'message' => $e->getMessage()
    ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
}