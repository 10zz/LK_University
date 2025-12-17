<?php
// db.php - настройте параметры и подключайте в каждом скрипте
$db_host = '127.0.0.1';
$db_name = 'LKNNGASU';
$db_user = 'root';
$db_pass = '';
$dsn = "mysql:host={$db_host};dbname={$db_name};charset=utf8mb4";

try {
    $pdo = new PDO($dsn, $db_user, $db_pass, [
        PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
        PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
    ]);
} catch (PDOException $e) {
    header('Content-Type: application/json; charset=utf-8', true, 500);
    echo json_encode([
        'status' => 'error',
        'message' => 'Не удалось подключиться к БД',
        'error' => $e->getMessage()
    ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
    exit;
}

// helper: получить JSON-или-form данные
function getRequestData() {
    $data = [];
    $contentType = $_SERVER['CONTENT_TYPE'] ?? '';
    if (stripos($contentType, 'application/json') !== false) {
        $json = file_get_contents('php://input');
        $data = json_decode($json, true) ?? [];
    } else {
        $data = array_merge($_GET, $_POST);
    }
    return $data;
}
