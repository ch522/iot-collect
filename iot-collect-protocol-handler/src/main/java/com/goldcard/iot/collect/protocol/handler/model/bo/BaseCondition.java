package com.goldcard.iot.collect.protocol.handler.model.bo;

import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 基础查询条件
 *
 * @Author ChenLong
 * @Date 2019/3/23 13:12
 */
public class BaseCondition {

    private Class<?> clazz;

    public BaseCondition() {
        clazz = this.getClass();
    }

    private Object pk;

    private Integer page = 1;
    private Integer limit = 10;

    private List<String> dataAuths;

    public Object getPk() {
        return pk;
    }

    public void setPk(Object pk) {
        this.pk = pk;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    private String orgCode;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    private Byte state;

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public List<String> getDataAuths() {
        return dataAuths;
    }

    public void setDataAuths(List<String> dataAuths) {
        this.dataAuths = dataAuths;
    }

    private Field getField(String propertyName) {
        Field field = null;

        List<Field> list = new ArrayList<>();

        Field[] fields = clazz.getDeclaredFields();

        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != null) {
            list.addAll(Arrays.asList(superClazz.getDeclaredFields()));
        }
        list.addAll(Arrays.asList(fields));

        Optional<Field> optional = list.stream().filter(p -> p.getName().equals(propertyName)).findFirst();
        if (optional.isPresent()) {
            field = optional.get();
        }
        return field;


    }

    public boolean containsKey(String propertyName) {
        Boolean flag = false;
        try {
            Field field = getField(propertyName);
            return field != null;
        } catch (Exception e) {
            flag = false;
        }
        return false;
    }

    /**
     * 验证List是有值
     *
     * @Author ChenLong
     * @Date 2019/4/1 16:37
     */
    public boolean isListNotEmpty(String propertyName) {
        Boolean flag = false;
        try {

            Field field = getField(propertyName);
            if (field != null) {
                field.setAccessible(true);
                List list = (List) field.get(this);
                flag = CollectionUtils.isNotEmpty(list);
            }

        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public boolean isBooleanVal(String propertyName) {
        Boolean flag = false;
        try {

            Field field = getField(propertyName);
            if (field != null) {
                field.setAccessible(true);
                Boolean object = (Boolean) field.get(this);

                flag = object == null ? Boolean.FALSE : object;
            }

        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

}
