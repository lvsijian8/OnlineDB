package db_util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by lvsijian8 on 2017/6/19.
 */
public final class HibernateUtil {
    private static SessionFactory sessionFactory;
    private static ThreadLocal<Session> session = new ThreadLocal<Session>();

    private HibernateUtil() {
    }

    static {
        // 第一步:读取Hibernate的配置文件 hibernamte.cfg.xml文件
        try {
            Configuration configuration = new Configuration().configure();
            sessionFactory = configuration.buildSessionFactory();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static Session getThreadLocalSession() {
        Session s = session.get();
        if (s == null) {
            s = sessionFactory.openSession();
            session.set(s);
        }
        return s;
    }

    public static void closeSession() {
        Session s = session.get();
        if (s != null) {
            s.close();
            session.set(null);
        }
    }

}
