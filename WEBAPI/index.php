
<?php
header('Access-Control-Allow-Origin: *');
/*
Establishing the configuration for connecting 
to our online database;
*/
define("dbhost", "mysql4.gear.host");
define("dbuser", "sampldb");
define("dbpass", "Wb6xe1_c4?Em");
define("dbname", "sampldb");

//function for making a connection to the database
function connect(){
    $con = mysqli_connect(dbhost, dbuser, dbpass, dbname);
    if(!$con){
        return false;
    }
    else{
        $query = "CREATE TABLE IF NOT EXISTS Users(id int primary key auto_increment,name varchar(250) not null,username varchar(250) not null unique,email varchar(250) not null unique,password varchar(20) not null);";
        mysqli_query($con, $query);
        return $con;
    }
}

//closing the connection of the database
function close(){
    mysqli_close();
}

//register a new user
function register($name, $username, $email, $password){
    $conn = connect();
    $query = "INSER INTO User(name, username, email, password) VALUES('$name', '$username', '$email', '$password')";
    $result = mysqli_query( $conn, $query);
    if($result && mysqli_num_rows($result) > 0){
        $select = mysqli_query( $conn, "SELECT * FROM Users WHERE email = '$email'");
        $rows = array();
        while($row = mysqli_fetch_array($select, MYSQLI_ASSOC)){
            $rows = row;
        }
        
        return $rows;
    }else{
        return false;
    }
    
    close();
}

//Sign user into their account
function Signin($email, $password){
    $conn = connect();
    $query = "SELECT * FROM Users WHERE email = '$email' AND password = '$password'";
    $result = mysqli_query($conn, $query);
    if($result && mysqli_num_rows($result) > 0){
        $rows = array();
        while($row = mysqli_fetch_array($result, MYSQLI_ASSOC)){
            $rows = row;
        }
        
        return $rows;
    }else{
        return false;
    }
    
    close();
}

if(isset($_POST['tag']) && $_POST['tag'] != ''){
    $tag = $_POST['tag'];
    
    $response = array("tag" => $tag, "error" => false);
    
    $name = $_POST['name'];
	$username = $_POST['username'];
	$email  = $_POST['email'];
	$password  = $_POST['password'];
    
    if($tag == 'register'){
        $do_register = register($name, $username, $email, $password);
        
        if($do_register != false){
            $response["error"] = false;
            $response['data'] = $do_register;
            echo json_encode($response);
        }
        else{
            $response["error"] = TRUE;
            $response["msg"] = "Register Failed, User might already exist";
            echo json_encode($response);
        }
    }else{
        $response["error"] = TRUE;
        $response["msg"] = "Tag not reconized";
        echo json_encode($response);
    }
    
}
else if(isset($_GET['tag']) && $_GET['tag'] != ''){
    $tag = $_GET['tag'];
    
    $response = array("tag" => $tag, "error" => false);
    
    $email  = $_GET['email'];
	$password  = $_GET['password'];
    
    if($tag == 'login'){
        $do_login = Signin($email, $password);
        
        if($do_login != false){
            $response["error"] = false;
            $response['data'] = $do_login;
            echo json_encode($response);
        }else{
            $response["error"] = TRUE;
            $response["msg"] = "Login Failed, please check your details";
            echo json_encode($response);
        }
		
    }else{
        $response["error"] = TRUE;
        $response["msg"] = "Tag not reconized";
        echo json_encode($response);
    }
}
else{
    $response["error"] = TRUE;
    $response["msg"] = "Required parameter 'tag' is missing!";
    echo json_encode($response);
}


?>