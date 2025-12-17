<?php

$pdo = new PDO('mysql:host=localhost;dbname=android_app', 'root', '');

if($_SERVER['REQUEST_METHOD'] == 'GET')
{
	$statement = $pdo->query("SELECT * FROM Study");
	$rows = $statement->fetchAll(PDO::FETCH_ASSOC);

	if (count($rows) > 0)
	{
		echo json_encode($rows, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
	}
}

/*$conn = mysqli_connect("localhost","root","","android_app");

if($_SERVER['REQUEST_METHOD'] == 'GET')
{
	$check_query = "SELECT * FROM Study";
	$result = mysqli_query($conn,$check_query);
	$check_result_query = mysqli_fetch_array($result);

	echo fetchAll($check_result_query);
	if (isset($check_result_query))
	{
		$rows = fetchAll(
		while($row = 
	}
	else
	{
		echo "failure";
	}
}
else
	echo "failed, an error occurred";
}*/
?>