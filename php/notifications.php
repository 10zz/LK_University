<?php
require 'db.php';
header('Content-Type: application/json; charset=utf-8');

try {
    if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
        echo json_encode([], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    // Берём последние уведомления (ExternalEvents)
    $stmt = $pdo->query("SELECT event_id, event_title, event_description, event_date, organizer, created_at FROM ExternalEvents ORDER BY event_date DESC, created_at DESC LIMIT 100");
    $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);

    $out = [];
    foreach ($rows as $r) {
        $out[] = [
            'title' => $r['event_title'],
            'description' => $r['event_description'],
            // Если у вас есть таблица иконок — подставьте поле. Пока плейсхолдер:
            'icon_path' => '/icons/events/default.png',
            'event_date' => $r['event_date'],
            'organizer' => $r['organizer']
        ];
    }

    echo json_encode($out, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);

} catch (Exception $e) {
    echo json_encode(['status' => 'ошибка сервера', 'message' => $e->getMessage()], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
}
