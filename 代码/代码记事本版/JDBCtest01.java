/*
	JDBC编程六步
*/
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;



public class JDBCtest01{
	public static void main(String[] args){
		// 这两个对象为全局对象:在try和finally模块中均需要使用，所以定义提前。
		Connection conn = null;
		Statement stmt = null;
		try{
			// 1.注册驱动
			Driver driver = new com.mysql.cj.jdbc.Driver();	// 多态：父类型引用指向子类型
			DriverManager.registerDriver(driver);
			// 2.建立连接
			/*
				url:统一资源定位符(网络中某个资源的绝对路径)
				URL包括那几部分？
					协议
					IP（域名）
					端口
					资源名
					
				jdbc:mysql：//127.0.0.1:3306/bjpowernode
					jdbc:mysql:// 协议
					127.0.0.1 IP地址
					3306 端口号
					bjpowernode 具体的数据库实例名
					
					说明：localhost和127.0.0.1都是本地IP地址，写哪个都行
	
			*/
			String url = "jdbc:mysql://127.0.0.1:3306/bjpowernode";
			String user = "root";
			String password = "1234";
			
			// 数据库连接对象=com.mysql.cj.jdbc.ConnectionImpl@1a84f40f
			conn = DriverManager.getConnection(url,user,password);
			System.out.println("数据库连接对象=" + conn);
				
			// 3.获得数据库操作对象(statement专门执行sql语句)
			stmt = conn.createStatement();
			
			// 4.执行sql语句
			String sql = "insert into dept(deptno,dname,loc) values(50,\"人事部\",\"北京\")";
			// 专门执行DML语句的(insert delete update)
			// 返回值是“影响数据库库的记条数”
			int count = stmt.executeUpdate(sql);
			System.out.println(count == 1? "保存成功":"保存释放");
			
			// 5.处理查询结果集
			
		} catch(SQLException e){
			System.out.println("Error occur!!!");
			e.printStackTrace();
		} finally{
			// 6.释放资源
			// 为了保证资源一定释放，在finally语句中关闭资源
			// 并且要遵循从小到大依次关闭
			// 分别对齐try...catch:因为如果第一个报错，那么后面的代码也无法执行会被影响
			try{
				if(stmt != null){		
					stmt.close();
				}
			} catch(SQLException e){
				e.printStackTrace();	
			}
			try{
				if(conn != null){
					conn.close();
				}
			} catch(SQLException e){
				e.printStackTrace();	
			}
		}
	
	}
}
