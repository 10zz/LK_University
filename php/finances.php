<?php
require 'db.php';
header('Content-Type: application/json; charset=utf-8');

try {
    if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
        echo json_encode(['status' => 'неуспешно', 'message' => 'Требуется POST'], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    $input = getRequestData();
    $name = trim($input['name'] ?? '');

    if ($name === '') {
        echo json_encode(['status' => 'неуспешно', 'message' => 'Поле name обязательно'], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    // Найдём студента(ов) по имени (LIKE)
    $stmt = $pdo->prepare("SELECT student_id, full_name FROM Students WHERE full_name LIKE :name");
    $stmt->execute([':name' => "%$name%"]);
    $students = $stmt->fetchAll();

    if (count($students) === 0) {
        echo json_encode(['status' => 'неуспешно', 'message' => 'Студент не найден'], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }

    $studentIds = array_column($students, 'student_id');

    // Получим платежи для найденных студентов
    $in = implode(',', array_fill(0, count($studentIds), '?'));
    $sql = "SELECT p.payment_id, p.student_id, p.amount, p.payment_date, p.payment_type
            FROM Payments p
            WHERE p.student_id IN ($in)
            ORDER BY p.payment_date DESC
            LIMIT 500";
    $stmt2 = $pdo->prepare($sql);
    $stmt2->execute($studentIds);
    $payments = $stmt2->fetchAll();

    $out = [];
    foreach ($payments as $p) {
        $sname = '';
        foreach ($students as $s) {
            if ($s['student_id'] == $p['student_id']) { $sname = $s['full_name']; break; }
        }
        $out[] = [
            'student' => $sname,
            'operation_type' => $p['payment_type'],
            'amount' => (float)$p['amount'],
            'time' => $p['payment_date']
        ];
    }

    echo json_encode($out, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);

} catch (Exception $e) {
    echo json_encode(['status' => 'ошибка сервера', 'message' => $e->getMessage()], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
}
