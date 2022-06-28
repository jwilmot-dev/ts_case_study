package com.justinwilmot.capstone.app.dao;

//UserDAO

import com.justinwilmot.capstone.app.entity.User;

public interface UserDao {

    public User findByUserName(String userName);
    

    
    public void save(User user);
    
}
