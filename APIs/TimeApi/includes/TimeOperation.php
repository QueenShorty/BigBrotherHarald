<?php
 
class TimeOperation
{
    //Database connection link
    private $con;
 
    //Class constructor
    function __construct()
    {
        //Getting the DbConnect.php file
        require_once dirname(__FILE__) . '/DbConnect.php';
 
        //Creating a DbConnect object to connect to the database
        $db = new DbConnect();
 
        //Initializing our connection link of this class
        //by calling the method connect of DbConnect class
        $this->con = $db->connect();
    }
 
 /*
 * The create operation
 * When this method is called a new record is created in the database
 */
 function createTime($USER, $ROOM, $CLOCK){
 $stmt = $this->con->prepare("INSERT INTO CTIME (USER, ROOM, CLOCK) VALUES (?, ?, ?)");
 $stmt->bind_param("sss", $USER, $ROOM, $CLOCK);
 if($stmt->execute())
 return true; 
 return false; 
 }
 
 /*
 * The read operation
 * When this method is called it is returning all the existing record of the database
 */
 function getTime(){
 $stmt = $this->con->prepare("SELECT USER, ROOM, CLOCK FROM CTIME");
 $stmt->execute();
 $stmt->bind_result($USER, $ROOM, $CLOCK);
 $CTIME = array(); 
 while($stmt->fetch()){
 $CCLOCK  = array();
 $CCLOCK['USER'] = $USER; 
 $CCLOCK['ROOM'] = $ROOM; 
 $CCLOCK['CLOCK'] = $CLOCK; 
 array_push($CTIME, $CCLOCK); 
 }
 return $CTIME; 
 }
 
 /*
 * The update operation
 * When this method is called the record with the given id is updated with the new given values
 */
 function updateTime($ROOM, $CLOCK, $USER){
 $stmt = $this->con->prepare("UPDATE CTIME SET ROOM = ?, CLOCK = ? WHERE USER = ?");
 $stmt->bind_param("sss", $ROOM, $CLOCK, $USER);
 if($stmt->execute())
 return true; 
 return false; 
 }
 
 
 /*
 * The delete operation
 * When this method is called record is deleted for the given id 
 */
 function deleteTime($USER){
 $stmt = $this->con->prepare("DELETE FROM CTIME WHERE USER = ?");
 $stmt->bind_param("s", $USER);
 if($stmt->execute())
 return true; 
 
 return false; 
 } 
}
