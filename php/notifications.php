<?php
require 'db.php';
header('Content-Type: application/json; charset=utf-8');

try {
    if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
        echo json_encode(['status' => 'неуспешно', 'message' => 'Требуется GET'], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    // Берём последние уведомления (ExternalEvents)
    $stmt = $pdo->query("
        SELECT event_id, event_title, event_description 
        FROM ExternalEvents
        LIMIT 100
    ");
    $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);

    $out = [];
    foreach ($rows as $r) {
        $out[] = [
            'event_id' => $r['event_id'],
            'title' => $r['event_title'],
            'description' => $r['event_description'],
        ];
    }

    echo json_encode([
        'status' => 'успешно',
        'count' => count($out),
        'data' => $out
    ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);

} catch (Exception $e) {
    echo json_encode([
        'status' => 'ошибка сервера', 
        'message' => $e->getMessage()
    ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
}