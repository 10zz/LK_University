<?php
require 'db.php';
header('Content-Type: application/json; charset=utf-8');

try {
    if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
        echo json_encode([], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    $p = getRequestData();
    $conds = [];
    $params = [];

    $sql = "SELECT f.fqw_id, f.department, f.theme,
                   t.teacher_id, t.full_name AS teacher_name,
                   s.student_id, s.full_name AS student_name,
                   g.group_id, g.group_name
            FROM FQW f
            LEFT JOIN `Teachers` t ON f.teacher_id = t.teacher_id
            LEFT JOIN `Students` s ON f.student_id = s.student_id
            LEFT JOIN `Groups` g ON f.group_id = g.group_id
            WHERE 1=1";

    if (!empty($p['group_id'])) {
        $sql .= " AND f.group_id = :group_id";
        $params[':group_id'] = $p['group_id'];
    }
    if (!empty($p['teacher_id'])) {
        $sql .= " AND f.teacher_id = :teacher_id";
        $params[':teacher_id'] = $p['teacher_id'];
    }
    if (!empty($p['student_id'])) {
        $sql .= " AND f.student_id = :student_id";
        $params[':student_id'] = $p['student_id'];
    }
    if (!empty($p['department'])) {
        $sql .= " AND f.department LIKE :department";
        $params[':department'] = "%{$p['department']}%";
    }
    if (!empty($p['theme'])) {
        $sql .= " AND f.theme LIKE :theme";
        $params[':theme'] = "%{$p['theme']}%";
    }

    $sql .= " ORDER BY f.fqw_id DESC LIMIT 500";

    $stmt = $pdo->prepare($sql);
    $stmt->execute($params);
    $rows = $stmt->fetchAll();

    $out = [];
    foreach ($rows as $r) {
        $out[] = [
            'fqw_id' => $r['fqw_id'],
            'teacher' => $r['teacher_name'] ?? '',
            'student' => $r['student_name'] ?? '',
            'group' => $r['group_name'] ?? '',
            'department' => $r['department'],
            'theme' => $r['theme']
        ];
    }

    echo json_encode($out, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);

} catch (Exception $e) {
    echo json_encode(['status' => 'ошибка сервера', 'message' => $e->getMessage()], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
}
