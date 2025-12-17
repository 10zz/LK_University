<?php
require 'db.php';
header('Content-Type: application/json; charset=utf-8');

try {
    if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
        echo json_encode([], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    $sql = "SELECT m.material_id, m.title, m.description, m.material_type, m.icon_path, m.link
            FROM StudyMaterials m
            LIMIT 200";
    $stmt = $pdo->query($sql);
    $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);

    $out = [];
    foreach ($rows as $r) {
        $out[] = [
            'icon_path' => $r['icon_path'],
            'title' => $r['title'],
            'material_type' => $r['material_type'],
            'description' => $r['description']
        ];
    }

    echo json_encode(['status' => 'success', 'data' => $out], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);

} catch (Exception $e) {
    echo json_encode(['status' => 'error', 'message' => $e->getMessage()], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
}
