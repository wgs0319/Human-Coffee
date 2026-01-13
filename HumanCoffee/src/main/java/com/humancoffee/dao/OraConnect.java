package com.humancoffee.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.humancoffee.model.QueryInfo;

public class OraConnect {
    public Connection conn;
    
    public List<String> queryEndsKey = new Vector<>();
    public List<String> queryInfosKey = new Vector<>();
    public Map<String, QueryInfo> queryInfos =
            Collections.synchronizedMap(new Hashtable<>());
    
    private String className;
    private String oraInfo;
    private String account;
    private String pwd;
    
    public OraConnect() {
        
    }
    
    public OraConnect(String className, String oraInfo, String account, String pwd) {
        this.conn = null;
        this.className = className;
        this.oraInfo = oraInfo;
        this.account = account;
        this.pwd = pwd;
        System.out.println("className : " + className);
        System.out.println("oraInfo : " + oraInfo);
        System.out.println("account : " + account);
        System.out.println("pwd : " + pwd);
    }
    
    public boolean connect() {
        try {
            
            Class.forName(this.className);
            conn = DriverManager.getConnection(
                    this.oraInfo,
                    this.account, this.pwd);
            System.out.println("Oracle Connected");
            
            // 스레드 생성 코드를 삭제
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(this.conn == null)
            return false;
        return true;
    }
/*    
    public void queryInfoExe() {
        synchronized(this.queryInfosKey) { // 동기화 블록 유지
            int loop;
            if(this.queryInfosKey == null || this.conn== null)
                return;
            if(this.queryInfosKey.size() <= 0)
                return;
            try {
                this.conn.setAutoCommit(false);
                while(this.queryInfosKey.size() > 0) {
                    String key = this.queryInfosKey.get(0);
                    System.out.println("queryInfoExe key : " + key);
                    QueryInfo qi = this.queryInfos.get(key);
                    if(qi.status == 0) {
                        PreparedStatement pstmt = this.conn.prepareStatement(qi.getSql());
                        if(qi.getParams() != null) {
                            for(loop = 0; loop < qi.getParams().length; loop++)
                                pstmt.setObject(loop + 1, qi.getParams()[loop]);
                        }
                        pstmt.addBatch();
                        pstmt.executeBatch();
                        qi.status = 1;
                        this.queryInfos.replace(key, qi);
                    }
                    this.queryEndsKey.add(key);
                    this.queryInfos.remove(key);
                    this.queryInfosKey.remove(key);
                }
                this.conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                if(this.conn != null) {
                    try {
                        this.conn.rollback();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }finally {
                if(this.conn != null) {
                    try {
                        this.conn.setAutoCommit(true);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
*/    
    public Object[][] exeSelect(QueryInfo qi) {
        Object[][] obj = new Object[0][0];
        ResultSet rs = null;
        if(qi != null) {
            PreparedStatement pstmt;
            try {
                pstmt = this.conn.prepareStatement(qi.getSql(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                if(qi.getParams() != null) {
                    for(int loop = 0; loop < qi.getParams().length; loop++)
                        pstmt.setObject(loop + 1, qi.getParams()[loop]);
                }
                rs = pstmt.executeQuery();
                if(!rs.last()) {
                    rs.close();
                    pstmt.close();
                    return obj;
                }
                int nRow = rs.getRow();
                int nColumn = rs.getMetaData().getColumnCount();
                obj = new Object[nRow][nColumn];
                rs.beforeFirst();
                int row = 0;
                while(rs.next()){ 
                    for(int col=0;col< nColumn; col++){
                        obj[row][col] = rs.getObject(col+1);
                    }
                    row++;
                }
                rs.close();
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }
    
    public int exeUpdate(PreparedStatement pstmt) {
        int row = -1;
        try {
            row = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return row;
    }
    
    public void close() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}