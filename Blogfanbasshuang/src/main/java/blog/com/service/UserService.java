package blog.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.com.model.dao.UserDao;
import blog.com.model.entity.UserEntity; // 导入 UserEntity 类

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    // 保存用户信息的方法
    public boolean createUser(String name, String email, String password) {
        // 检查是否已存在具有相同邮箱的用户
        UserEntity existingUser = userDao.findByEmail(email);
        if (existingUser == null) {
            // 如果不存在相同邮箱的用户，则保存新用户信息
            userDao.save(new UserEntity(email, name, password, null));
            return true;
        } else {
            return false;
        }
    }

    // 登录验证的方法
    public UserEntity checkLogin(String email, String password) {
        // 根据邮箱和密码查询用户
        UserEntity user = userDao.findByEmailAndPassword(email, password);
        return user; // 如果找到匹配的用户则返回，否则返回 null
    }
}
