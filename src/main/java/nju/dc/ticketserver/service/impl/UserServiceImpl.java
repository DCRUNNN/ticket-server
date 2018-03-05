package nju.dc.ticketserver.service.impl;

import nju.dc.ticketserver.dao.UserDao;
import nju.dc.ticketserver.dao.utils.UserDaoUtils;
import nju.dc.ticketserver.po.UserPO;
import nju.dc.ticketserver.service.UserService;
import nju.dc.ticketserver.utils.ActiveCodeHelper;
import nju.dc.ticketserver.utils.EncryptHelper;
import nju.dc.ticketserver.utils.MailHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserDaoUtils userDaoUtils;

    @Override
    public int addUser(UserPO userPO) {
        String password = userPO.getPassword();
        password = EncryptHelper.getShaEncryption(password);
        userPO.setPassword(password);
        userPO.setUserID(userDaoUtils.createUserID());
        return userDao.addUser(userPO);
    }

    @Override
    public int activeUser(String activeCode) {
        return userDao.activeUser(activeCode);
    }

    @Override
    public boolean doRegister(UserPO userPO, String email) {
        // 这里可以验证各字段是否为空

        //利用正则表达式（可改进）验证邮箱是否符合邮箱的格式
        if(!email.matches("^\\w+@(\\w+\\.)+\\w+$")){
            return false;
        }
        //生成激活码
        String activeCode= ActiveCodeHelper.generateUniqueCode();
        userPO.setActiveCode(activeCode);
        userPO.setState("未激活");

        //保存成功则通过线程的方式给用户发送一封邮件
        if(addUser(userPO)>0){
            new Thread(new MailHelper(email, activeCode)).start();;
            return true;
        }
        return false;
    }
}