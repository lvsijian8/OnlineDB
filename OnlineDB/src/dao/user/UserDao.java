package dao.user;

import bean.User;
import dao.DaoHibernate;

/**
 * Created by lvsijian8 on 2017/6/19.
 */
public class UserDao extends DaoHibernate<User> {
    // 通过姓名、密码查询用户
    public User findBynameAndPassword(User user) {
        String hql = "from User u where u.userName=? and u.userPassword=?";
        String param[] = { user.getUserName(), user.getUserPassword() };
        User user1 = this.findOne(hql, param);
        return user1;
    }

    // 通过姓名查询用户
    public User findByname(User user) {
        String hql = "from User u where u.userName=?";
        String param[] = { user.getUserName() };
        User user1 = this.findOne(hql, param);
        return user1;
    }

    // 修改用户密码： 修改 对象use的密码为newPasswoed
    public int updatePassword(User user, String newPassword) {
        User user1 = this.findBynameAndPassword(user);
        user1.setUserPassword(newPassword);
        return this.update(user1);
    }
}
