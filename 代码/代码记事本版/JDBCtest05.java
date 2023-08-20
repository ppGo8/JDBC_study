/*
	处理查询结果集
*/
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;


public class JDBCtest05{
	public static void main(String[] args) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null; // 查询结果封闭到结果集对象；释放资源的时候从这里开始向上关闭
		try {
			// 1.注册驱动
			String driver = "com.mysql.cj.jdbc.Driver";
			Class.forName(driver);
			// 2.获取连接
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpowernode","root","1234");
			// 3.获取数据库操作对象
			stmt = conn.createStatement();
			// 4.执行查询
			String sql = "select deptno,dname,loc from dept";
			// 执行各种语句的方法:
			// int executeUpdate(insert/delete/update) 返回处理的行数
			// ResultSet executeQuery(select)          返回查询到的结果集
			rs = stmt.executeQuery(sql); // executeQuery专门执行DQL语句的方法,执行数据库查询语句
			// 5.处理结果
/* 			boolean flags = rs.next();   // 指向下一行;一开始的指向是表头；rs.next()数据的第一行，如果有的话
			System.out.println(flags);   
			if(flags){
				// 光标指向的行有数据
				// 取数据
				// getString()方法的特点：不管数据库中的数据类型是什么，都以String的形式取出
				// getString里面的参数对应数据库中列的下标；JDBC中下标从1开始，不是0
				String deptno = rs.getString(1);  
				String dname = rs.getString(2);
				String loc = rs.getString(3);
				System.out.println(deptno+ "," +dname+ "," +loc);
		
			} */
			while(rs.next()){
				String deptno = rs.getString(1);  
				String dname = rs.getString(2);
				String loc = rs.getString(3);
				System.out.println(deptno+ "," +dname+ "," +loc);
			}
			
			
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			// 6.释放资源
			if(rs != null){
				try{
					rs.close();
				}catch (SQLException e){
					e.printStackTrace();
				}
				
			}
			if(stmt != null){
				try{
					stmt.close();
				}catch (SQLException e){
					e.printStackTrace();
				}
				
			}
			if(conn != null){
				try{
					conn.close();
				}catch (SQLException e){
					e.printStackTrace();
				}
				
			}
		}
		
		
	}
}