<?php
require 'db.php';
header('Content-Type: application/json; charset=utf-8');

if($_SERVER['REQUEST_METHOD'] == 'GET') {
    $statement = $pdo->query("SELECT * FROM Study");
    $rows = $statement->fetchAll(PDO::FETCH_ASSOC);

    if (count($rows) > 0) {
        echo json_encode($rows, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
    } else {
        echo json_encode(['message' => 'Нет данных'], JSON_UNESCAPED_UNICODE);
    }
} else {
    echo json_encode(['message' => 'Требуется GET запрос'], JSON_UNESCAPED_UNICODE);
}
?>
