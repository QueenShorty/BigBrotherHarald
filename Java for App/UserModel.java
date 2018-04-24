import java.io.Serializable;
 

class UserLoc {
    private String USERNAME; 
    private String USERID; 
    private String PHONETYPE; 
    private String USERLOCATION; 
 
    public UserLoc(String USERNAME, String USERID, String PHONETYPE, String USERLOCATION) {
        this.USERNAME = USERNAME; 
        this.USERID = USERID; 
        this.PHONETYPE = PHONETYPE; 
        this.USERLOCATION = USERLOCATION; 
    }
 
    public String getUSERNAME() {
        return USERNAME; 
    }
 
    public String getUSERID() {
        return USERID;
    }
 
    public String getPHONETYPE() {
        return PHONETYPE;
    }
 
    public int getUSERLOCATION() {
        return USERLOCATION;
    }

}