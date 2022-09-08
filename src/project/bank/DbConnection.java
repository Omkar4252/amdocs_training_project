package project.bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import java.sql.Statement;

public class DbConnection {

	public Connection connectDb() {
		
		
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e1) {
				
				e1.printStackTrace();
			}
			
			String url = "jdbc:oracle:thin:@localhost:1521/orcl.iiht.tech";
			
			String user = "scott";
			
			String pwd = "tiger";
			
			try {
				Connection con = DriverManager.getConnection(url, user, pwd);
				if(con != null) {
					System.out.println("connection successfull");
				}
				
				
				return con;
					
				
				
				
				
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			
		

	}

}
