package com.imooc.miaosha.service;

import com.imooc.miaosha.dao.UserDao;
import com.imooc.miaosha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Felix
 * @date 2019/5/4 22:12
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User getById(int id){
        return userDao.getById(id);
    }

    //@Transactional
    public boolean tx(){
        User u1 = new User();
        u1.setId(2);
        u1.setName("cshf");
        userDao.insert(u1);

        User u2 = new User();
        u2.setId(1);
        u2.setName("vjkfdghu");
        userDao.insert(u2);

        return true;
    }
}
