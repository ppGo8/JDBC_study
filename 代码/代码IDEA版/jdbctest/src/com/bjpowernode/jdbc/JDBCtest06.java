package com.bjpowernode.jdbc;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @时间: 2023-05-13
 * @作者： ppgo8
 * @描述： 案例用户登录验证，存在bug(sql注入)
 **/

public class JDBCtest06 {
    public static void main(String[] args) throws Exception {
            // 初始化界面
            Map<String, String> userLoginInfo = initUI();
            // 验证用户名和密码
            boolean loginSuccessFlag = login(userLoginInfo);
            // 输出结果
            System.out.println(loginSuccessFlag ? "登录成功" : "登陆失败");
    }

    /**
     * @功能： 初始化用户界面
     * @return： 用户输入的用户名和密码等信息
     **/
    private static Map<String, String> initUI() {
        Scanner scanner  = new Scanner(System.in);

        System.out.println("用户名:");
        String loginName = scanner.nextLine();

        System.out.print("密码：");
        String loginPwd= scanner.nextLine();

        Map<String, String> userLoginInfo = new HashMap<>();
        userLoginInfo.put("loginName",loginName);
        userLoginInfo.put("loginPwd",loginPwd);

        return userLoginInfo;
    }


    /**
     * @功能： 验证用户账号和密码是否正确
     * @return: true false
     **/
    private static boolean login(Map<String,String> userLoginInfo){
        // JDBC代码
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        boolean successLoginFlag = false; // 打标记,一开始默认登录不成功
        try {
            // 1.建立驱动
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
            // 2.获取连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpowernode","root","1234");
            // 3.获取数据库对象
            stmt = conn.createStatement();
            // 4.执行查询:把变量拼进sql查询语句
            String sql = "select * from t_user where loginName= '"+ userLoginInfo.get("loginName")+"' and loginPwd = '"+ userLoginInfo.get("loginPwd") +"'"; // 在sql语句中 字符串需要使用单引号抱起来
            rs = stmt.executeQuery(sql);
            System.out.println(userLoginInfo.get("loginName"));
            System.out.println(userLoginInfo.get("loginPwd"));
            // 5.处理结果:不需要循环,因为最多一条数据
            successLoginFlag = rs.next();

        } catch (ClassNotFoundException e) {
            System.out.println("Error Occurs!!!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error Occurs!!!");
            e.printStackTrace();
        } finally {
            // 6.释放资源
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(stmt!=null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return successLoginFlag;
    }

}
