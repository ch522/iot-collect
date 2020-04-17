package com.goldcard.iot.collect.protocol.handler.dao;

import com.github.pagehelper.PageInfo;
import com.goldcard.iot.collect.protocol.handler.model.bo.BaseCondition;

import java.util.List;

/**
 * Dao层基础操作
 *
 * @Author ChenLong
 * @Date 2019/3/23 12:30
 */
public interface IBaseDao<T> {

    /**
     * 增加操作
     *
     * @param item 需要新增的实体
     * @Author ChenLong
     * @Date 2019/3/23 12:43
     */
    void insert(T item);

    /**
     * 根据SqlId插入数据
     *
     * @param statement Mapper中的SqlId
     * @param item      需要插入的数据
     * @Author ChenLong
     * @Date 2019/3/23 12:55
     */
    void insert(String statement, T item);

    /**
     * 更新操作
     *
     * @param item 需要更新的实体
     * @Author ChenLong
     * @Date 2019/3/23 12:43
     */
    void update(T item);

    /**
     * 根据主键删除
     *
     * @param pk 主键
     * @Author ChenLong
     * @Date 2019/3/23 17:08
     */
    void delete(Object pk);

    /**
     * 根据主键查找相应实体
     *
     * @param pk 主键
     * @return 实体或null
     * @Author ChenLong
     * @Date 2019/3/23 12:44
     */
    T findByPk(Object pk);

    /**
     * 查询
     *
     * @param condition 相关查询条件
     * @return 列表或null
     * @Author ChenLong
     * @Date 2019/3/23 13:13
     */
    List<T> query(BaseCondition condition);

    /**
     * 分页查询
     *
     * @param condition 查询条件
     * @Author ChenLong
     * @Date 2019/3/23 15:56
     */
    PageInfo<T> queryWithPage(BaseCondition condition);

    /**
     * 分页查询
     *
     * @param condition 查询条件
     * @param statement Mapper中的sqlId
     * @Author ChenLong
     * @Date 2019/3/23 16:04
     */
    PageInfo<T> queryWithPage(String statement, BaseCondition condition);

    /**
     * 批量插入
     *
     * @param list 批量插入集合
     * @Author ChenLong
     * @Date 2019/3/29 9:41
     */
    void insertBatch(List<T> list);

    /**
     * 批量更新
     *
     * @param list
     * @Author baolj
     */
    void updateBatch(List<T> list);
}
