<?php
require 'db.php';
header('Content-Type: application/json; charset=utf-8');

try {
    if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
        echo json_encode([], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    $sql = "SELECT m.material_id, m.title, m.material_type, m.file_path, m.upload_date, t.full_name AS teacher_name
            FROM StudyMaterials m
            LEFT JOIN Teachers t ON m.teacher_id = t.teacher_id
            ORDER BY m.upload_date DESC
            LIMIT 200";
    $stmt = $pdo->query($sql);
    $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);

    $out = [];
    foreach ($rows as $r) {
        $out[] = [
            'title' => $r['title'],
            'description' => $r['material_type'],
            'file_path' => $r['file_path'],
            'teacher' => $r['teacher_name'] ?? '',
            'upload_date' => $r['upload_date']
        ];
    }

    echo json_encode($out, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);

} catch (Exception $e) {
    echo json_encode(['status' => 'ошибка сервера', 'message' => $e->getMessage()], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
}
