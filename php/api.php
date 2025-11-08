<?php

file_put_contents("post.log", print_r($_POST, true));
file_put_contents("gpc.log", print_r($_REQUEST, true));

$conn = mysqli_connect("localhost","root","","android_app");

if($_SERVER['REQUEST_METHOD'] == 'POST')
{
		
	$name = mysqli_real_escape_string($conn, $_POST['name']);
	$email= mysqli_real_escape_string($conn,$_POST['email']);
	$password = mysqli_real_escape_string($conn,$_POST['password']);

	$check_query = "SELECT * FROM users WHERE email='$email'";
	$check_result_query = mysqli_fetch_array(mysqli_query($conn,$check_query));

	if (isset($check_result_query))
	{
		
		// User already exists
		$result["success"] = "0";
		$result["message"] = "Account already exits!";

		echo json_encode($result);
		echo $result[message];
	}

	else
	{
		
		// Create user
		$sql=mysqli_query($conn, 'INSERT INTO users(name,email,password) VALUES("'.$_POST['name'].'","'.$_POST['email'].'","'.$_POST['password'].'")');

		if (!$sql) 
		{
			die (mysqli_error($conn));
		}
		else
		{
			$result["success"] = "1";
			$result["message"] = "Account created successfully!";

			echo json_encode($result);
			echo $result;
		}
	}
}
else
{
	$result["success"] = "0";
	$result["message"] = "Registration failed, an error occurred";

	echo json_encode($result);
	echo $result;
}
?>