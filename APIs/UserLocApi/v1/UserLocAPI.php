<?php 
 require_once '../includes/UserLocOperation.php';
 function isTheseParametersAvailable($params){
 $available = true; 
 $missingparams = ""; 
 foreach($params as $param){
 if(!isset($_POST[$param]) || strlen($_POST[$param])<=0){
 $available = false; 
 $missingparams = $missingparams . ", " . $param; 
 }
 }
 if(!$available){
 $response = array(); 
 $response['error'] = true; 
 $response['message'] = 'Parameters ' . substr($missingparams, 1, strlen($missingparams)) . ' missing';
 echo json_encode($response);
 die();
 }
 }
 $response = array();
 if(isset($_GET['apicall'])){
 switch($_GET['apicall']){
 
 //CREATEUSER
 case 'createUser':
 isTheseParametersAvailable(array('USERNAME','USERID', 'PHONETYPE'));
 $db = new UserLocOperation();
 $result = $db->createUser(
 $_POST['USERNAME'],
 $_POST['USERID'],
 $_POST['PHONETYPE']
 );
 if($result){
 $response['error'] = false; 
 $response['message'] = 'User successfully added to system';
 $response['USERLOC'] = $db->getUserLoc();
 }else{
 $response['error'] = true; 
 $response['message'] = 'Error, try again';
 }
 break; 
 
//getUserLoc
 case 'getUserLoc':
 $db = new UserLocOperation();
 $response['error'] = false; 
 $response['message'] = 'User locations pulled from database';
 $response['USERLOC'] = $db->getUserLoc();
 break; 
 
 
 //updateUser
 case 'updateUser':
 isTheseParametersAvailable(array('USERID', 'USERNAME' ,'PHONETYPE'));
 $db = new UserLocOperation();
 $result = $db->updateUser(
 $_POST['USERID'],
 $_POST['USERNAME'],
 $_POST['PHONETYPE']);
 if($result){
 $response['error'] = false; 
 $response['message'] = 'Username and/or Phone updated';
 $response['USERLOC'] = $db->getUserLoc();}
 else{
 $response['error'] = true; 
 $response['message'] = 'Error, try again';}
 break; 
 
//deleteUser
 case 'deleteUser':
 if(isset($_GET['USERID'])){
 $db = new UserLocOperation();
 if($db->deleteUser($_GET['USERID'])){
 $response['error'] = false; 
 $response['message'] = 'User deleted successfully';
 $response['USERLOC'] = $db->getUserLoc();}
 else{
 $response['error'] = true; 
 $response['message'] = 'Some error occurred please try again';}
 }else{
 $response['error'] = true; 
 $response['message'] = 'Nothing to delete';}
 break;}
 }else{
 $response['error'] = true; 
 $response['message'] = 'Invalid API Call';}
 echo json_encode($response);