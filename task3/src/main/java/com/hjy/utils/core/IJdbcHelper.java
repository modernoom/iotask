package com.hjy.utils.core;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author  haungjinyong
 */
public interface IJdbcHelper {
    /**
     * 查询返回List集合
     * @param sql sql语句
     * @param tClass 封装实体类的字节码对象
     * @param objs ?对应的值
     * @param <T> 泛型
     * @return 结果
     */
    <T> List<T> query(String sql, Class<T> tClass, Object... objs);

    /**
     * 增删改方法
     * @param sql sql语句
     * @param objs ?对应的值
     */
    void update(String sql, Object... objs);

    /**
     * 关闭资源
     * @throws SQLException 关闭异常
     */
    void close() throws SQLException;

}
