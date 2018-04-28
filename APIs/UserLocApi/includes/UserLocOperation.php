<?php
class UserLocOperation
{
    private $con;
    function __construct()
    {
        require_once dirname(__FILE__) . '/DbConnect.php';
        $db = new DbConnect();
        $this->con = $db->connect();
    }
 
//createUser
 function createUser($USERNAME, $USERID, $PHONETYPE){
 $stmt = $this->con->prepare("INSERT INTO USERLOC (USERNAME, USERID, PHONETYPE) VALUES (?, ?, ?) ");
 $stmt->bind_param("sss", $USERNAME, $USERID, $PHONETYPE);
 if($stmt->execute())
 return true; 
 return false; 
 }

 //getUserLoc
 function getUserLoc(){
 $stmt = $this->con->prepare("SELECT USERNAME, USERID, PHONETYPE, USERLOCATION FROM USERLOC");
 $stmt->execute();
 $stmt->bind_result($USERNAME, $USERID, $PHONETYPE, $USERLOCATION);
 $USERLOC = array(); 
 while($stmt->fetch()){
 $USER  = array();
 $USER['USERNAME'] = $USERNAME; 
 $USER['USERID'] = $USERID; 
 $USER['PHONETYPE'] = $PHONETYPE; 
 $USER['USERLOCATION'] = $USERLOCATION; 
 array_push($USERLOC, $USER); 
 }
 return $USERLOC; 
 }
 
//updateUser
 function updateUser($USERID, $USERNAME, $PHONETYPE){
 $stmt = $this->con->prepare("UPDATE USERLOC SET USERNAME = ?, PHONETYPE = ? WHERE USERID = ?");
 $stmt->bind_param("sss", $USERNAME, $PHONETYPE, $USERID);
 if($stmt->execute())
 return true; 
 return false; 
 }
 
 
//deleteUser
 function deleteUser($USERID){
 $stmt = $this->con->prepare("DELETE FROM USERLOC WHERE USERID = ? ");
 $stmt->bind_param("s", $USERID);
 if($stmt->execute())
 return true; 
 return false; 
 }
}

