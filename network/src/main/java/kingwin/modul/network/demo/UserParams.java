package kingwin.modul.network.demo;

import com.google.gson.Gson;

/**
 * author: kingwin
 * created on: 2020/12/16 10:51 AM
 * description:
 */
public class UserParams {
    String username;
    String password;

    public UserParams(String username, String password){
        this.username = username;
        this.password = password;
    }

}
