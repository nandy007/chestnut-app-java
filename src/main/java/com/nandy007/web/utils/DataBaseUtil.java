package com.nandy007.web.utils;

import com.nandy007.web.core.ServiceException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.OutParameter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库操作工具
 */
public class DataBaseUtil {

    private static DataSource dataSource;
    private static String databaseType; // 数据库类型，如果有需要根据数据库类型区别sql，可以使用

    public static void setDataSource(DataSource dataSource){
        DataBaseUtil.dataSource = dataSource;
    }
    public static void setDatabaseType(String databaseType) {
        DataBaseUtil.databaseType = databaseType;
    }

    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            throw new ServiceException(e.getMessage(), e);
        }
        return conn;
    }

    /**
     * 日志
     */
    private static Logger log = LoggerFactory.getLogger(DataBaseUtil.class);


    /*
	 *  定义方法,使用QueryRunner类的方法delete将数据表的数据删除
	 */
	public static boolean execute(String sql, Object []params){
        boolean rs = false;
        try{
            //创建QueryRunner类对象
            QueryRunner qr = new QueryRunner(DataBaseUtil.dataSource);	
            //调用QueryRunner方法update
            int row = qr.update(sql, params);
            /*
            *  判断insert,update,delete执行是否成功
            *  对返回值row判断
            *  if(row>0) 执行成功
            */
            rs = row>0?true:false;
        }catch(SQLException e){
            throw new ServiceException(e.getMessage(), e);
        }

        return rs;
		
    }
    
    public static boolean execute(String sql){
        return execute(sql, null);
	}
	
	public static List<Map<String, Object>> query(String sql, Object []params){
        try{
            //创建QueryRunner类对象
            QueryRunner qr = new QueryRunner(DataBaseUtil.dataSource);	
            List<Map<String, Object>> list = qr.query(sql, new MapListHandler(), params);

            return list;
        }catch(SQLException e){
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public static List<Map<String, Object>> query(String sql){
        return query(sql);
    }

    public static Map<String, Object> queryRow(String sql, Object []params){
        List<Map<String, Object>> list = query(sql, params);
        if(list==null) return null;
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    public static Map<String, Object> queryRow(String sql){
        return queryRow(sql, null);
    }

    public static void setParam(PreparedStatement prStmt, Object[] params) throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                if (params[i] == null) {
                    prStmt.setNull(i + 1, Types.NULL);
                } else if (params[i] instanceof String) {
                    prStmt.setString(i + 1, (String)params[i]);
                } else if (params[i] instanceof Integer) {
                    prStmt.setInt(i + 1, ((Integer)params[i]).intValue());
                } else if (params[i] instanceof Long) {
                    prStmt.setLong(i + 1, ((Long)params[i]).longValue());
                } else if (params[i] instanceof java.sql.Date) {
                    prStmt.setDate(i + 1, (java.sql.Date)params[i]);
                } else if (params[i] instanceof java.util.Date) {
                    java.util.Date udate = (java.util.Date)params[i];
                    java.sql.Timestamp t = new java.sql.Timestamp(udate.getTime());
                    prStmt.setTimestamp(i + 1, t);
                } else if (params[i] instanceof java.sql.Timestamp) {
                    java.sql.Timestamp t = (java.sql.Timestamp)params[i];
                    if (t.getTime() == 0) {
                        prStmt.setNull(i + 1, Types.NULL);
                    } else {
                        prStmt.setTimestamp(i + 1, (java.sql.Timestamp)params[i]);
                    }
                } else if (params[i] instanceof byte[]) {
                    prStmt.setBytes(i + 1, (byte[])params[i]);
                } else if (params[i] instanceof Float) {
                    prStmt.setFloat(i + 1, ((Float)params[i]).floatValue());
                } else if (params[i] instanceof Double) {
                    prStmt.setDouble(i + 1, ((Double)params[i]).doubleValue());
                } else if (params[i] instanceof Character) {
                    prStmt.setString(i + 1, params[i].toString());
                } else {
                    throw new ServiceException("暂不支持此类型的数据更新 " + params[i].getClass());
                }
            }
        }
    }

    /**
     * 批处理调用
     * @param sql
     * @param params
     * @return int[]
     * 用法：
        List<Map<String, Object>> sqlObjList = new ArrayList();

        Map<String, Object> map1 = new HashMap();
        map1.put("sql", "update user set sex=? where username=?");
        map1.put("params", new Object[]{1, "test"});
        sqlObjList.add(map1);

        init[] rs = DatabaseUtil.batch(sqlObjList);

     */
    public static int[] batch(List<Map<String, Object>> sqlObjList) {
        int size = sqlObjList.size();
        int[] result = new int[size];
        Connection con = getConnection();
        try{
            con.setAutoCommit(false);//设置禁用自动提交
            for (int i = 0; i < size; i++) {
                PreparedStatement prStmt = null;
                try {
                    Map<String, Object> sqlObj = sqlObjList.get(i);
                    String sql = (String)sqlObj.get("sql");
                    Object []params = (Object [])sqlObj.get("params");
                    
                    prStmt = con.prepareStatement(sql);
                    setParam(prStmt, params);
                    result[i] = prStmt.executeUpdate();
                    
                } catch (SQLException ex) {
                    throw ex;
                } finally {
                    if (prStmt != null) {
                        prStmt.close();
                    }
                }
            }
            con.commit();
        }catch(SQLException e){
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    throw new ServiceException(ex.getMessage(), ex);
                }
            }
            throw new ServiceException(e.getMessage(), e);
        }finally{
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                } catch (SQLException e) {
                    new ServiceException(e.getMessage(), e);
                }
                
                try {
                    con.close();
                } catch (SQLException e) {
                    new ServiceException(e.getMessage(), e);
                }
            }
        }

        return result;
    }

    public static boolean batchSimple(List<Map<String, Object>> sqlObjList){
        int[] rs = batch(sqlObjList);
        return rs.length==sqlObjList.size();
    }

    /**
     * 批处理调用
     * @param sql
     * @param params
     * @return int[]
     */
    public static int[] batch(String sql, Object[][] params) {
        try{
            QueryRunner qr = new QueryRunner(DataBaseUtil.dataSource);
            int[] rs = qr.batch(sql, params);
            return rs;
        }catch(SQLException e){
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public static boolean batchSimple(String sql, Object[][] params) {
        int[] rs = batch(sql, params);
        return rs.length==params.length;
    }


    /**
     * 存储过程调用
     * @param sql
     * @param params
     * 
     * 用法：
     * 
        OutParameter<Integer> op = new OutParameter<Integer>(Types.INTEGER, Integer.class);

        DataBaseUtil.call("call myFourth_proc(?, ?)", new Object[]{3, op});

        System.err.println(op);
     */
    public static void call(String sql, Object[] params) {
        QueryRunner qr = new QueryRunner(DataBaseUtil.dataSource);
        try {
            qr.execute(sql, params);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
}
