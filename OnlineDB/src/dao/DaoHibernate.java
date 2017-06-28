package dao;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import db_util.HibernateUtil;

import javax.persistence.TypedQuery;

/**
 * Created by lvsijian8 on 2017/6/19.
 */
public class DaoHibernate<T> implements IBaseDao<T> {

    @Override
    public int insert(T o) {
        Session s = null;
        Transaction tx = null;
        int result = 0;
        try {
            s = HibernateUtil.getThreadLocalSession();
            tx = s.beginTransaction();
            s.save(o);
            tx.commit();
            result=1;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();// 事物回滚
            }
        } finally {
            HibernateUtil.closeSession();
        }
        return result;
    }

    @Override
    public int insertList(List<T> list) {
        for (T t : list) {
            insert(t);
        }
        return list.size();
    }

    @Override
    public int update(T o) {
        Session s = null;
        int result = 0;
        Transaction tx = null;
        try {
            s = HibernateUtil.getThreadLocalSession();
            tx = s.beginTransaction();
            s.update(o);
            tx.commit();
            result = 1;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();// 事物回滚
            }
        } finally {
            HibernateUtil.closeSession();
        }
        return result;
    }

    @Override
    public int deleteList(Class<T> c, int... ids) {
        for (int id : ids) {
            delete(c, id);
        }
        return ids.length;
    }

    @Override
    public int delete(T o) {
        Session s = null;
        Transaction tx = null;
        int result = 0;
        try {
            s = HibernateUtil.getThreadLocalSession();
            tx = s.beginTransaction();
            s.delete(o);
            tx.commit();
            result = 1;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();// 事物回滚
            }
        } finally {
            HibernateUtil.closeSession();
        }
        return result;
    }

    @Override
    public int delete(Class<T> c, int id) {
        Session s = null;
        Transaction tx = null;
        int result = 0;
        try {
            s = HibernateUtil.getThreadLocalSession();
            tx = s.beginTransaction();
            s.delete(s.load(c, id));
            tx.commit();
            result = 1;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();// 事物回滚
            }
        } finally {
            HibernateUtil.closeSession();
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T findById(Class<T> c, int id) {
        Session s = null;
        T t = null;
        try {
            s = HibernateUtil.getThreadLocalSession();
            t = s.get(c, id);
        } finally {
            HibernateUtil.closeSession();
        }
        return t;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T findOne(String hql, String[] param) { // 查询单条记录
        T t = null;
        Session s = null;
        try {
            s = HibernateUtil.getThreadLocalSession();
            TypedQuery<T> query = s.createQuery(hql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    query.setParameter(i, param[i]);
                }
            }
            t = query.getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            HibernateUtil.closeSession();
        }
        return t;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> find(String hql, String[] param) {
        List<T> list = null;
        Session s = null;
        try {
            s = HibernateUtil.getThreadLocalSession();
            Query query = s.createQuery(hql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    query.setParameter(i, param[i]);
                }
            }
            list = query.list();
        } finally {
            HibernateUtil.closeSession();
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findPage(String hql, String[] param, int page, int size) { // 分页查找所有对象
        List<T> list = null;
        Session s = null;
        try {
            s = HibernateUtil.getThreadLocalSession();
            Query query = s.createQuery(hql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    query.setParameter(i, param[i]);
                }
            }
            // 筛选条数
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);
            list = query.list();
        } finally {
            HibernateUtil.closeSession();
        }
        return list;
    }

    @Override
    public int getCount(String hql, String[] param) {// 返回数据个数
        int resu = 0;
        Session s = null;
        try {
            s = HibernateUtil.getThreadLocalSession();
            Query q = s.createQuery(hql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    q.setString(i, param[i]);
                }
            }
            resu = Integer.valueOf(q.iterate().next().toString());
        } finally {
            HibernateUtil.closeSession();
        }
        return resu;
    }

    @Override
    //单字段模糊查询
    public List<T> findByFields(String hql, String fields[], String condition) {
        Session s = null;
        String findhql=hql;
        if(fields!=null && condition!=null && fields.length>0 && !condition.equals("") ){
            findhql =findhql + " where 1=1 and (";
            for(int i=0;i<fields.length-1;++i){
                findhql +=  fields[i]+" like '%" + condition + "%' or ";
            }
            findhql += fields[fields.length-1]+" like '%" + condition + "%') ";
        }
        try {
            s = HibernateUtil.getThreadLocalSession();
            Query query=s.createQuery(findhql);
            @SuppressWarnings("unchecked")
            List<T> list=query.list();
            return list;
        } finally {
            HibernateUtil.closeSession();
        }
    }

}