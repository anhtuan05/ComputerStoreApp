package com.example.computerstoreapp;

public class UserSessionManager {
    private static UserSessionManager instance;
    private boolean isLoggedIn;
    private String username;
    private int userid;
    private int userrole;

    private String usergmail;

    private UserSessionManager() {
        isLoggedIn = false;
        username = "";
    }

    public static synchronized UserSessionManager getInstance() {
        if (instance == null) {
            instance = new UserSessionManager();
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getUserrole() {
        return userrole;
    }

    public void setUserrole(int userrole) {
        this.userrole = userrole;
    }

    public String getUsergmail() {
        return usergmail;
    }

    public void setUsergmail(String usergmail) {
        this.usergmail = usergmail;
    }

}

