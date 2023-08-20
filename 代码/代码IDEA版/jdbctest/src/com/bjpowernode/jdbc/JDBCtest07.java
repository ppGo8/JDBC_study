package com.bjpowernode.jdbc;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @时间: 2023-05-13
 * @作者： ppgo8
 * @描述： 解决sql注入问题
 * @解决方法： 只要用户提供的信息不参与SQL语句的编译,就不起作用。
 *              使用java.sql.PreparedStatement,该接口继承了了java.sql.Statement
 *              PreparedStatement是预编译的数据库操作对象
 *              原理 ：预先对sql语句的框架进行编译，然后再给sql语句传 “值”
 **/
public class JDBCtest07 {
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
        PreparedStatement ps = null;      // 使用预编译的数据库对象,有d
        ResultSet rs = null;
        boolean successLoginFlag = false; // 打标记,一开始默认登录不成功
        String loginName = userLoginInfo.get("loginName");
        String loginPwd = userLoginInfo.get("loginPwd");
        try {
            // 1.建立驱动
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
            // 2.获取连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpowernode","root","1234");
            // 3.获取预编译的数据库对象
            String sql = "select * from t_user where loginName= ? and loginPwd = ?";  // ? 占位符不需要写引号；这是sql语句的框架
            // 程序执行到这里会发送sql给dbms,dbms进行预编译
            ps = conn.prepareStatement(sql); // 没d
            // 给占位符?传值 第一个问号小标是1,第二个问号下标是2.
            ps.setString(1,loginName);  // 这时候就算有数据库的关键字也不会参加编译
            ps.setString(2,loginPwd);
            // 其他方法：setInt()、setDouble()、

            // 4.执行查询:把变量拼进sql查询语句
            rs = ps.executeQuery();          // 里面不需要写sql东西
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
            if(ps!=null){
                try {
                    ps.close();
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
