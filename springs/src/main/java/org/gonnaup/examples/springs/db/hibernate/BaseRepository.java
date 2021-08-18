package org.gonnaup.examples.springs.db.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Repository 模板类
 *
 * @author gonnaup
 * @version created at 2021/8/18 15:28
 */
@Transactional
public class BaseRepository<T> {

    @SuppressWarnings("all")
    @Autowired
    protected HibernateTemplate hibernateTemplate;

    protected Class<T> entityClass;

    @SuppressWarnings("unchecked")
    protected BaseRepository() {
        //获取子类Repository对应的泛型实体类
        Type type = getClass().getGenericSuperclass();
        Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        entityClass = (Class<T>) actualTypeArguments[0];
    }

    /**
     * 根据主键查找实体类
     *
     * @param id
     * @return
     */
    public T findOne(Serializable id) {
        return hibernateTemplate.get(entityClass, id);
    }

    /**
     * 新增实体类
     *
     * @param entity
     * @return id
     */
    public Serializable save(T entity) {
        return hibernateTemplate.save(entity);
    }

    /**
     * 更新实体
     *
     * @param entity
     */
    public void update(T entity) {
        hibernateTemplate.update(entity);
    }

    /**
     * 根据id删除实体
     *
     * @param id
     */
    public void deleteOne(Serializable id) {
        T db = findOne(id);
        if (db != null) {
            hibernateTemplate.delete(db);
        }
    }

}
