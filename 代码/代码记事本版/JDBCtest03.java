/*
	注册驱动的常用方式
*/
import java.sql.*;

public class JDBCtest03{
	public static void main(String[] args) throws Exception{
		try{
			// 1.注册驱动 
			// (1)第一种写法
			// DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			
			// (2) 常用写法:反射机制加载类
			// 为什么这种方式常用？因为参数是一个字符串,字符串可以写到xxx.properties文件中。
			// 以下方法不需要接受返回值,因为我们只想用它的加载动作
			Class.forName("com.mysql.cj.jdbc.Driver");  // 注意第一个字母大写
			
			// 2.获取连接
			Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bjpowernode","root","1234");
			System.out.println(conn);
			
		}catch(SQLException e){
			e.printStackTrace();
		} 
	}
	
}