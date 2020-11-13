package com.fzufood.util;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author buzhouI
 * 作用：mybatis查询和插入时的，File类型与String类型之间的转换
 */
public class FileTypeHandler implements TypeHandler {

    /**
     * 此方法是在插入是进行设置参数
     * 参数：	PreparedStatement
     * 		int	i				为Jdbc预编译时设置参数的索引值
     * 		Object obj			要插入的参数值
     * 		JdbcType jdbcType	要插入JDBC的类型
     */
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Object object, JdbcType jdbcType) throws SQLException {
        String path = null;
        if(object != null) {
            File file = (File)object;
            path = file.toString();
        }
        preparedStatement.setString(i,path);
    }

    /**
     * 执行查询后
     * 参数：	ResultSet rs		查询当前列数据
     *			String columnName	查询当前列名称
     */
    @Override
    public File getResult(ResultSet resultSet, String columnName) throws SQLException {
        String path = resultSet.getString(columnName);
        File file = null;
        if(path != null) {
            file = new File(path);
        }
        return file;
    }

    @Override
    public File getResult(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

    @Override
    public File getResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
