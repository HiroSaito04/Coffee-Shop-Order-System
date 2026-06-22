//src\database\LoginHandler.java
package database;

public class LoginHandler {
    public static LoginHandler Instance;
    
    private String lastLoginUsername = "";
    
    private LoginHandler(){
        
    }
    
    public static synchronized LoginHandler getLoginHandler(){
        if (Instance == null){
            Instance = new LoginHandler();
        }
        return Instance;
    }
    
    public void setLastLogin(String loginUsername){
        lastLoginUsername = loginUsername;
    }
    
    public boolean checkIfSameLogin(String loginUsername){
        return lastLoginUsername.equals(loginUsername);
    }
    
     public String getUsername(){
        return lastLoginUsername;
    }
}
