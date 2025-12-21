<?php
require 'db.php';
header('Content-Type: application/json; charset=utf-8');

try {
    if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
        echo json_encode([
            'status' => 'error', 
            'message' => 'Требуется POST запрос'
        ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }
	
	$input = getRequestData();
	$email = trim($input['email'] ?? '');
	
	if (empty($email)) {
        echo json_encode([
            'status' => 'error',
            'message' => 'Нет email'
        ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        exit;
    }
	
	$dir = "profile_pictures/" . basename($_FILES['image']['name']);
	if (move_uploaded_file($_FILES['image']['tmp_name'],$dir))
	{
		$stmt = $pdo->prepare("SELECT user_id FROM `Users` WHERE email = :email LIMIT 1");
		$stmt->execute([':email' => $email]);
		$user = $stmt->fetch();
		
		if (!$user) {
			echo json_encode([
				'status' => 'error',
				'message' => 'Пользователь не найден'
			], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
			exit;
		}
		
		$update_stmt = $pdo->prepare("UPDATE `Users` SET user_icon = :user_icon WHERE email = :email");
		
		$update_stmt->execute([
			':user_icon' => $dir,
			':email' => $email
		]);

		// Проврочка был ли обновлён пароль - не работает, ибо строка может не меняется, а файл при этом перезаписывается
		/*$rows_affected = $update_stmt->rowCount();

		if ($rows_affected > 0) {
			echo json_encode([
				'status' => 'success',
				'message' => 'Изображение успешно изменено'
			], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
		} else {
			echo json_encode([
				'status' => 'error',
				'message' => 'Не удалось изменить изображение'
			], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
		}*/
		echo json_encode([
				'status' => 'success',
				'message' => 'Изображение успешно изменено'
			], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
	}
	else
	{
		echo json_encode([
				'status' => 'error',
				'message' => 'Ошибка в загрузке изображения'
			], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
	}
		

} catch (Exception $e) {
    echo json_encode([
        'status' => 'error',
        'message' => 'Ошибка сервера: ' . $e->getMessage()
    ], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
}

?>