package com.goldcard.iot.collect.dao.base.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.goldcard.iot.collect.dao.base.IBaseDao;
import com.goldcard.iot.collect.dao.model.bo.BaseCondition;
import org.apache.commons.collections.CollectionUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Dao层基础操作
 *
 * @Author ChenLong
 * @Date 2019/3/23 12:34
 */
@Component
public class BaseDaoImpl<T> implements IBaseDao<T> {

    @Autowired
    protected SqlSessionTemplate sqlSessionTemplate;


    private static final String INSERT = "insert";
    private static final String UPDATE = "update";
    private static final String QUERY = "query";
    private static final String DELETE = "delete";
    private static final String INSERT_BATCH = "insertBatch";
    private static final String UPDATE_BATCH = "updateBatch";
    private Class<?> entityClazz;

    public BaseDaoImpl() {
        this.entityClazz = getGenericClazz();
    }

    private Class<?> getGenericClazz() {
        return getSuperGenericClazz(this.getClass(), 0);
    }

    private Class<?> getSuperGenericClazz(Class<?> clazz, Integer index) {

        Type superType = clazz.getGenericSuperclass();
        if (!(superType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) superType).getActualTypeArguments();
        return (Class<?>) params[index];
    }

    /**
     * 增加操作
     *
     * @param item 需要新增的实体
     * @Author ChenLong
     * @Date 2019/3/23 12:43
     */
    @Override
    public void insert(T item) {
        sqlSessionTemplate.insert(getStatement(INSERT), item);
    }

    /**
     * 根据SqlId插入数据
     *
     * @param statement Mapper中的SqlId
     * @param item      需要插入的数据
     * @Author ChenLong
     * @Date 2019/3/23 12:55
     */
    @Override
    public void insert(String statement, T item) {
        sqlSessionTemplate.insert(statement, item);
    }

    /**
     * 更新操作
     *
     * @param item 需要更新的实体
     * @Author ChenLong
     * @Date 2019/3/23 12:43
     */
    @Override
    public void update(T item) {
        sqlSessionTemplate.update(getStatement(UPDATE), item);
    }

    @Override
    public void delete(Object pk) {
        BaseCondition condition = new BaseCondition();
        condition.setPk(pk);
        this.delete(condition);
    }

    protected void delete(BaseCondition condition) {
        sqlSessionTemplate.delete(getStatement(DELETE), condition);
    }

    /**
     * 根据主键查找相应实体
     *
     * @param pk 主键
     * @return 实体或null
     * @Author ChenLong
     * @Date 2019/3/23 12:44
     */
    @Override
    public T findByPk(Object pk) {
        if (null != pk) {
            BaseCondition condition = new BaseCondition();
            condition.setPk(pk);
            List<T> list = this.query(condition);
            if (CollectionUtils.isNotEmpty(list)) {
                return list.get(0);
            }
        }
        return null;
    }

    /**
     * 查询
     *
     * @param condition 相关查询条件
     * @return 列表或null
     * @Author ChenLong
     * @Date 2019/3/23 13:13
     */
    @Override
    public List<T> query(BaseCondition condition) {
        if (condition == null) {
            condition = new BaseCondition();
        }
        return sqlSessionTemplate.selectList(getStatement(QUERY), condition);
    }

    @Override
    public PageInfo<T> queryWithPage(BaseCondition condition) {
        PageHelper.startPage(condition.getPage(), condition.getLimit());
        List<T> list = this.query(condition);
        PageInfo<T> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public PageInfo<T> queryWithPage(String statement, BaseCondition condition) {
        PageHelper.startPage(condition.getPage(), condition.getLimit());
        List<T> list = sqlSessionTemplate.selectList(getStatement(statement), condition);
        PageInfo<T> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public void insertBatch(List<T> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            sqlSessionTemplate.insert(getStatement(INSERT_BATCH), list);
        }
    }

    protected String getStatement(String statement) {
        return this.entityClazz.getSimpleName() + "Mapper." + statement;

    }

    @Override
    public void updateBatch(List<T> list) {
        sqlSessionTemplate.update(getStatement(UPDATE_BATCH), list);
    }
}
