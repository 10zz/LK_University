<?php
require 'db.php';
header('Content-Type: application/json; charset=utf-8');

try {
    if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
        echo json_encode([
            'status' => 'error', 
            'message' => 'Требуется POST'
        ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    $input = getRequestData();
    $teacher_name = trim($input['teacher_name'] ?? '');

    if ($teacher_name === '') {
        echo json_encode([
            'status' => 'error',
            'message' => 'Имя преподавателя обязательно'
        ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

        $sql = "SELECT 
                f.fqw_id, 
                f.department, 
                f.theme,
                t.teacher_id,
                u_teacher.full_name AS teacher_name,
                u_student.full_name AS student_name,
                g.group_name
            FROM `FQW` f
            INNER JOIN `Teachers` t ON f.teacher_id = t.teacher_id
            INNER JOIN `Users` u_teacher ON t.user_id = u_teacher.user_id
            LEFT JOIN `Students` s ON f.student_id = s.student_id
            LEFT JOIN `Users` u_student ON s.user_id = u_student.user_id
            LEFT JOIN `Groups` g ON f.group_id = g.group_id
            WHERE u_teacher.full_name LIKE :teacher_name
            ORDER BY f.fqw_id DESC";

    $stmt = $pdo->prepare($sql);
    $stmt->execute([':teacher_name' => "%$teacher_name%"]);
    $rows = $stmt->fetchAll();

    if (empty($rows)) {
        echo json_encode([
            'status' => 'error',
            'message' => 'Работы для преподавателя не найдены'
        ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    $out = [];
    foreach ($rows as $r) {
        $out[] = [
            'teacher' => $r['teacher_name'],
            'student' => $r['student_name'],
            'group' => $r['group_name'] ?? '-',
            'department' => $r['department'],
            'theme' => $r['theme']
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