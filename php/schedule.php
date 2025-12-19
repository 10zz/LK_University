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
    $start_date = $_GET['start_date'] ?? null;
    $end_date = $_GET['end_date'] ?? null;
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
    if ($start_date !== null && $end_date !== null) {
        $sql .= " AND es.schedule_date BETWEEN :start_date AND :end_date";
        $params[':start_date'] = $start_date;
        $params[':end_date'] = $end_date;
    } elseif ($start_date !== null) {
        $sql .= " AND es.schedule_date >= :start_date";
        $params[':start_date'] = $start_date;
    } elseif ($end_date !== null) {
        $sql .= " AND es.schedule_date <= :end_date";
        $params[':end_date'] = $end_date;
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
    $sql .= " ORDER BY es.schedule_date";
    
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