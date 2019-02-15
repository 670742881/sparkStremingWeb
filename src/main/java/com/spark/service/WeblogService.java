package com.spark.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Administrator on 2017/10/17.
 */
public class WeblogService {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
//    static {
//        String url = "jdbc:mysql://10.20.4.222:3306/test";
//        String username = "bigdata";
//        String password = "bigdata";
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
    static String url ="jdbc:mysql://10.20.4.222:3306/test";
    static String username="bigdata";
    static String password="bigdata";
    String day=   TimeUtil.parseLong2String(new Date().getTime(),DATE_FORMAT);
    public  Map<String,Object> queryWeblogs() {
        Connection conn = null;
        PreparedStatement pst = null;
        List<String> titleNames = new ArrayList();
        List<String> titleCounts = new ArrayList();
        Map<String,Object> retMap = new HashMap<String, Object>();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url,username,password);
            String query_sql = "select provence,amt from area_user_amt where 1=1 and `date`=? order by amt desc ";
            pst = conn.prepareStatement(query_sql);
            pst.setString(1,day);
            ResultSet rs = pst.executeQuery();
            int i = 0;
            while (rs.next()){
                String provence = rs.getString("provence");
                String amt = rs.getString("amt");
               titleNames.add(provence);
               titleCounts.add(amt);
            }
            retMap.put("titleName", titleNames);
            retMap.put("titleCount", titleCounts);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }


            }catch(Exception e){
                e.printStackTrace();
            }
        }
             return retMap;
    }

    public  String[] titleCount() {
        Connection conn = null;
        PreparedStatement pst = null;
        String[] titleSums = new String[1];
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url,username,password);



            String query_sql = "select sum(amt) amtSum from area_user_amt where `date`=?";
            pst = conn.prepareStatement(query_sql);
            pst.setString(1,day);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                String titleSum = rs.getString("amtSum");
                titleSums[0] = titleSum;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return titleSums;
    }

}
