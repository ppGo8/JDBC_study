/*
	将数据的所有信息配置到配置文件;
	优点：不需要改动代码,只需要修改属性资源文件的内容;(方便用户使用)
	建议：实际开发中不建议把连接数据库的信息写死到java程序中;(方便自己开发,如果是给别人写的产品那他们不会告诉你密码)
*/
import java.sql.*;
import java.util.*;

public class JDBCtest04 {
	public static void main(String[] args) throws Exception{
		
		// 使用资源绑定器绑定属性配置文件
		ResourceBundle bundle = ResourceBundle.getBundle("jdbc"); // 注意：不需要写属性配置文件的后缀
		String driver  = bundle.getString("driver");
		String url = bundle.getString("url");
		String user = bundle.getString("user");
		String password = bundle.getString("password");
		
		Connection conn = null;
		Statement stmt = null;
		try{
			//1、注册驱动
			Class.forName(driver);
			//2、获取连接	
			conn = DriverManager.getConnection(url,user,password);
			//3、获取数据库操作对象
			stmt = conn.createStatement();
			//4、执行SQL语句			
			String sql = "update dept set dname = '销售部', loc = '天津' where deptno = 20";
			int count = stmt.executeUpdate(sql);
			System.out.println(count == 1 ? "修改成功" : "修改失败");
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			//6、释放资源
			if(stmt != null){
				try{
					stmt.close();
				}catch(SQLException e){
					e.printStackTrace();
				}	
			}
			if(conn != null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
				}	
			}
		}
		
	}
}