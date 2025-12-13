<?php
require 'db.php';
header('Content-Type: application/json; charset=utf-8');

try {
    if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
        echo json_encode([], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    $params = getRequestData();
    $group = trim($params['group'] ?? '');
    $teacher = trim($params['teacher'] ?? '');
    $start_date = $params['start_date'] ?? null;
    $end_date = $params['end_date'] ?? null;
	
	$start_date = date("Y-m-d", strtotime($start_date));
	$end_date = date("Y-m-d", strtotime($end_date));

    // Подставим значения по умолчанию, если даты не заданы (последние 30 дней)
    if (!$start_date || !$end_date) {
        $end_date = date('Y-m-d');
        $start_date = date('Y-m-d', strtotime('-30 days'));
    }

    $results = [];

    // 1) ExternalSchedule
    $sql = "SELECT schedule_date AS day, NULL AS subject, teacher_name AS teacher, group_name AS `group`,
                   start_time, end_time, room, 'external' AS source
            FROM ExternalSchedule
            WHERE schedule_date BETWEEN :start_date AND :end_date";
    $conds = [':start_date' => $start_date, ':end_date' => $end_date];

    if ($group !== '') {
        $sql .= " AND group_name LIKE :group";
        $conds[':group'] = "%$group%";
    }
    if ($teacher !== '') {
        $sql .= " AND teacher_name LIKE :teacher";
        $conds[':teacher'] = "%$teacher%";
    }
    $sql .= " ORDER BY schedule_date, start_time";

    $stmt = $pdo->prepare($sql);
    $stmt->execute($conds);
    $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);
    foreach ($rows as $r) {
        $results[] = [
            'day' => $r['day'],
            'subject' => $r['subject'],
            'teacher' => $r['teacher'],
            'group' => $r['group'],
            'start_time' => $r['start_time'],
            'end_time' => $r['end_time'],
            'room' => $r['room'],
            'source' => $r['source']
        ];
    }

    // 2) ExamsRetakes (связать group_id и teacher_id с названиями)
    $sql2 = "SELECT e.exam_date AS day, e.exam_type AS subject, t.full_name AS teacher, g.group_name AS `group`,
                    e.exam_time AS start_time, NULL AS end_time, e.room, 'exam' AS source
             FROM ExamsRetakes e
             LEFT JOIN `Teachers` t ON e.teacher_id = t.teacher_id
             LEFT JOIN `Groups` g ON e.group_id = g.group_id
             WHERE e.exam_date BETWEEN :s2 AND :e2";
    $conds2 = [':s2' => $start_date, ':e2' => $end_date];

    if ($group !== '') {
        $sql2 .= " AND g.group_name LIKE :group2";
        $conds2[':group2'] = "%$group%";
    }
    if ($teacher !== '') {
        $sql2 .= " AND t.full_name LIKE :teacher2";
        $conds2[':teacher2'] = "%$teacher%";
    }
    $sql2 .= " ORDER BY e.exam_date, e.exam_time";

    $stmt2 = $pdo->prepare($sql2);
    $stmt2->execute($conds2);
    $rows2 = $stmt2->fetchAll(PDO::FETCH_ASSOC);
    foreach ($rows2 as $r) {
        $results[] = [
            'day' => $r['day'],
            'subject' => $r['subject'],
            'teacher' => $r['teacher'],
            'group' => $r['group'],
            'start_time' => $r['start_time'],
            'end_time' => $r['end_time'], // null — если нет
            'room' => $r['room'],
            'source' => $r['source']
        ];
    }

    // Отдаём объединённый массив (можно затем сортировать по дате/времени на клиенте)
    echo json_encode($results, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);

} catch (Exception $e) {
    echo json_encode(['status' => 'ошибка сервера', 'message' => $e->getMessage()], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
}
