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

    $params = getRequestData();
    
    // Формируем SQL запрос с JOIN
    $sql = "SELECT 
                es.external_schedule_id,
                es.schedule_date,
                es.schedule_subject,
                u.full_name AS teacher_name,
                g.group_name,
                es.start_time,
                es.end_time,
                es.room
            FROM ExternalSchedule es
            LEFT JOIN `Groups` g ON es.group_id = g.group_id
            LEFT JOIN `Teachers` t ON es.teacher_id = t.teacher_id
            LEFT JOIN `Users` u ON t.user_id = u.user_id
            WHERE 1=1";
    
    $conds = [];
    
    // Сортировка по дате
    $sql .= " ORDER BY es.schedule_date DESC";
    
    $stmt = $pdo->prepare($sql);
    $stmt->execute($conds);
    $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);
    
    // Форматируем результат
    $results = [];
    foreach ($rows as $row) {
        $results[] = [
            'subject' => $row['schedule_subject'],
            'date' => $row['schedule_date'],
            'teacher' => $row['teacher_name'] ?? null,
            'group' => $row['group_name'] ?? null,
            'start_time' => $row['start_time'],
            'end_time' => $row['end_time'],
            'room' => $row['room']
        ];
    }
    
    // Если результатов нет
    if (empty($results)) {
        echo json_encode([
            'status' => 'error',
            'message' => 'Расписание не найдено',
            'data' => []
        ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }
    
    // Возвращаем результат
    echo json_encode([
        'status' => 'success',
        'count' => count($results),
        'data' => $results
    ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
    
} catch (Exception $e) {
    echo json_encode([
        'status' => 'error',
        'message' => $e->getMessage()
    ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
}