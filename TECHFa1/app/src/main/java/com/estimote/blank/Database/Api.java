package com.estimote.blank.Database;

public class Api {
 
    public static final String ROOT_URL = "http://18.188.215.166/UserLocApi/v1/UserLocAPI.php?apicall=";
    public static final String TIME_URL = "http://18.188.215.166/TimeApi/v1/TimeAPI.php?apicall=";
 
    public static final String URL_CREATE_USER = ROOT_URL + "createUser";
    public static final String URL_READ_USER_LOC = ROOT_URL + "getUserLoc";
    public static final String URL_UPDATE_USER = ROOT_URL + "updateUser";
    public static final String URL_DELETE_USER = ROOT_URL + "deleteUser&USERID=";

    public static final String URL_GET_TIME = TIME_URL + "getTime";
    public static final String URL_CREATE_TIME = TIME_URL + "getTime";
    public static final String URL_UPDATE_TIME = TIME_URL + "getTime";
    public static final String URL_DELETE_TIME = TIME_URL + "getTime";
}