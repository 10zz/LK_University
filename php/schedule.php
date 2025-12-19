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

    // Получаем параметры из GET запроса
    $schedule_date = $_GET['schedule_date'] ?? null;
    $start_time = $_GET['start_time'] ?? null;
    $end_time = $_GET['end_time'] ?? null;
    $group_name = $_GET['group_name'] ?? null;
    $full_name = $_GET['full_name'] ?? null;
    
    // Формируем SQL запрос с JOIN
    $sql = "SELECT
                es.external_schedule_id,
                es.schedule_subject,
                es.schedule_date,
                es.start_time,
                es.end_time,
                es.room,
                u.full_name AS teacher_name,
                g.group_name
            FROM ExternalSchedule es
            LEFT JOIN `Groups` g ON es.group_id = g.group_id
            LEFT JOIN `Teachers` t ON es.teacher_id = t.teacher_id
            LEFT JOIN `Users` u ON t.user_id = u.user_id
            WHERE 1=1";
    
    $params = [];
    
    // Добавляем условия фильтрации
    if ($schedule_date !== null) {
        $sql .= " AND es.schedule_date = :schedule_date";
        $params[':schedule_date'] = $schedule_date;
    }
    
    if ($start_time !== null) {
        $sql .= " AND es.start_time >= :start_time";
        $params[':start_time'] = $start_time;
    }
    
    if ($end_time !== null) {
        $sql .= " AND es.end_time <= :end_time";
        $params[':end_time'] = $end_time;
    }
    
    if ($group_name !== null) {
        $sql .= " AND g.group_name LIKE :group_name";
        $params[':group_name'] = "%$group_name%";
    }
    
    if ($full_name !== null) {
        $sql .= " AND u.full_name LIKE :full_name";
        $params[':full_name'] = "%$full_name%";
    }
    
    // Сортировка
    $sql .= " ORDER BY es.schedule_date, es.start_time";
    
    $stmt = $pdo->prepare($sql);
    $stmt->execute($params);
    $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);
    
    // Если результатов нет
    if (empty($rows)) {
        echo json_encode([
            'status' => 'success',
            'message' => 'Расписание не найдено',
            'data' => []
        ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }
    
    // Форматируем результат
    $results = [];
    foreach ($rows as $row) {
        $results[] = [
            'subject' => $row['schedule_subject'],
            'date' => $row['schedule_date'],
            'start_time' => $row['start_time'],
            'end_time' => $row['end_time'],
            'room' => $row['room'],
            'teacher' => $row['teacher_name'],
            'group' => $row['group_name']
        ];
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
?>