package action;

import bean.User;
import com.opensymphony.xwork2.ActionSupport;
import dao.user.UserDao;

/**
 * Created by lvsijian8 on 2017/6/18.
 */
public class UserAction extends ActionSupport {
    private User user;
    private UserDao userDao = new UserDao();

    public String login(){
        try {
            String forward = null;
            User user2 =null;
            user2 = userDao.findBynameAndPassword(user);
            if (user2 != null){
                user=user2;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            return SUCCESS;
        }
    }

    public String Register() throws Exception {
        int flag = 0;
        User user2 = userDao.findByname(user);
        if (user2==null) {
            flag = userDao.insert(user);
            if (flag == 1) {
                user=userDao.findByname(user);
            }
        }
        return SUCCESS;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
