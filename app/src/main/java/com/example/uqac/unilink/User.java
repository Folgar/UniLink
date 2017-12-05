package com.example.uqac.unilink;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Folgar on 05/12/2017.
 */

public class User {
    private static final User ourInstance = new User();

    public FirebaseUser getUser() {
        return user;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }

    private FirebaseUser user ;

    public static User getInstance() {
        return ourInstance;
    }

    private User() {
    }
}
