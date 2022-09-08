package project.bank;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class Display {

	public static void main(String[] args) {
		
		System.out.println("Welcome to Your Project Bank");
		
		BankAccount obj1 = new BankAccount();		
		obj1.showMenu();

	}

}

class BankAccount
{
	String customerName;
	String customerID;
	
	BankAccount(String cname,String cid)
	{
		customerName=cname;
		customerID=cid;
	}
	
	BankAccount()
	{
		customerName="";
		customerID="";
	}
	
	void BalanceCheck(String ip_account)
	{
		
		DbConnection obj = new DbConnection();
		Connection con = null;
		PreparedStatement p = null;
		ResultSet rs = null;
		
		con =  obj.connectDb();
		
		 try {
	            String sql = "select * from BANKACCOUNT WHERE ACCOUNT_NO = ?";
	            p = con.prepareStatement(sql);
	            p.setInt(1, Integer.valueOf(ip_account));
	            rs = p.executeQuery();
	 
	       
	            System.out.println("Customer ID\t\tNAME\t\tBALANCE\t\tACCOUNT NO.");
	 
	            
	            while (rs.next()) {
	 
	                int id = rs.getInt(2);
	                String name = rs.getString(1);
	                int balance = rs.getInt("BALANCE");
	                String account = rs.getString(4);
	                System.out.println(id + "\t\t" + name
	                                   + "\t\t" + balance + "\t\t" + account);
	                
	            }
	        }
	 
	        
	        catch (SQLException e) {
	 
	            
	            System.out.println(e);
	        }
		
	}
	
	void CreditMoney(String ip_account, int money)
	{
		DbConnection obj = new DbConnection();
		Connection con = null;
		PreparedStatement p = null;
		
		System.out.println("This will add " + money +" to your account with account number " + ip_account );
		
		ResultSet rs = null;
		
		con =  obj.connectDb();
		
		String sql = "select BALANCE from BANKACCOUNT WHERE ACCOUNT_NO = ?";
		try {
			p = con.prepareStatement(sql);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		try {
			p.setInt(1, Integer.valueOf(ip_account));
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		try {
			rs = p.executeQuery();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		try {
			while (rs.next()) {
				int balance = rs.getInt("BALANCE");
				int new_balance = money + balance;
				String sql1 = "UPDATE BANKACCOUNT SET BALANCE = ? WHERE ACCOUNT_NO = ?";
				
				PreparedStatement p1 = con.prepareStatement(sql1);
				p1.setInt(1, new_balance);
				p1.setInt(2, Integer.valueOf(ip_account));
				
				
				int rs1 = p1.executeUpdate();
				
				
				if( rs1 == 1) {
					System.out.println("Account has been crdited! and your current balance is " + new_balance);
				}
				
				
				
				
			
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*try {
		System.out.println("flag 1");
        String sql = "UPDATE BANKACCOUNT SET BALANCE = (select BALANCE from BANKACCOUNT WHERE ACCOUNT_NO = ?)+ ? WHERE ACCOUNT_NO = ?";
        p = con.prepareStatement(sql);
        p.setInt(1, Integer.valueOf(ip_account));
        p.setInt(2, money);
        p.setInt(3, Integer.valueOf(ip_account));
        System.out.println("flag 2");
        rs = p.executeUpdate();
        System.out.println("flag 3");
        
        
        //rs.next();
        //System.out.println(rs.toString());
        if (rs ==1)
        {
        	System.out.println("flag done");
        }
        else {
        	System.out.println("galat");
        }*/
    
    

    // Catch block to handle exception
    /*catch (SQLException e) {

        // Print exception pop-up on screen
        System.out.println(e);
    }*/
		
	void DebitMoney(String ip_account, int money) {
		
		DbConnection obj = new DbConnection();
		Connection con = null;
		PreparedStatement p = null;
		
		System.out.println("This will debit " + money +" from your account with account number " + ip_account );
		
		ResultSet rs = null;
		
		con =  obj.connectDb();
		
		String sql = "select BALANCE from BANKACCOUNT WHERE ACCOUNT_NO = ?";
		try {
			p = con.prepareStatement(sql);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		try {
			p.setInt(1, Integer.valueOf(ip_account));
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		try {
			rs = p.executeQuery();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		try {
			while (rs.next()) {
				int balance = rs.getInt("BALANCE");
				if (money <= balance)
				{
					int new_balance = balance-money;
				
				String sql1 = "UPDATE BANKACCOUNT SET BALANCE = ? WHERE ACCOUNT_NO = ?";
				
				PreparedStatement p1 = con.prepareStatement(sql1);
				p1.setInt(1, new_balance);
				p1.setInt(2, Integer.valueOf(ip_account));
				
				
				int rs1 = p1.executeUpdate();
				
				
				if( rs1 == 1) {
					System.out.println("Account has been debited! and your current balance is " + new_balance);
				}
				}
				else {
					System.out.println("insufficient funds please enter less amount");
				}
				
				
				
				
			
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	void NewAccount(String name, int balance)
	{
		DbConnection obj = new DbConnection();
		Connection con = null;
		PreparedStatement p = null;
		ResultSet rs = null;
		
		con =  obj.connectDb();
		String sql = "select cust_id, account_no from bankaccount where account_no = (SELECT MAX(account_no) FROM bankaccount)";
		
		try {
			p = con.prepareStatement(sql);
			rs = p.executeQuery();
			
			while(rs.next()) {
				int latest_account_no = Integer.valueOf(rs.getString("ACCOUNT_NO"));
				int latest_cust_id = Integer.valueOf(rs.getString("CUST_ID"));
				
				
				int new_account_no = latest_account_no + 1;
				
				int new_cust_id = latest_cust_id + 1;
				
				//System.out.println(new_account_no);
				//System.out.println(new_cust_id);
				
				
				String sql1 = "INSERT INTO BANKACCOUNT (CUST_NAME, CUST_ID, BALANCE, ACCOUNT_NO) VALUES (?, ?, ?, ?)";
				PreparedStatement p1 = con.prepareStatement(sql1);
				p1.setString(1, name);
		        p1.setInt(2, new_cust_id);
		        p1.setInt(3, balance);
		        p1.setInt(4, new_account_no);
		       
		        
		        int rs1 = p1.executeUpdate();
		        
		        if(rs1 == 1) {
		        	System.out.println("The data has been added successfully and your account number is "+ new_account_no + " and your customer ID is " + new_cust_id );
		        	}
		        else {
		        	System.out.println("Inset data is not updated.");
		        }
		     	
			}
			
		} 
		
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	
	void showMenu()
	{
		
		
		
		char option='\0';
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("\nPlease State the Purpose of your visit and "
					+ "select a option from the menu:");
			System.out.println("A. Create New Account:");
			System.out.println("B. Check Balance:");
			System.out.println("C. Withdraw Money:");
			System.out.println("D. Deposit Money:");
			System.out.println("E. Exit:");
			
			do
			{
				System.out.println("==================================");
				System.out.println("Select an option");
				System.out.println("==================================");
				option = scanner.next().charAt(0);
				System.out.println("\n");
				
				switch(option)
				{
				
				case 'A':
					String ip_name;
					int ini_balance;
					System.out.println("------------------------------");
					System.out.println("Thankyou for choosing our bank please enter your name:\n\n");
					ip_name = scanner.next();
					System.out.println("We are creating account number and customerID for you please hold...\n\n");
					System.out.println("Please deposit initial money...\nPlease enter amount that is been added..\n\n");
					ini_balance = scanner.nextInt();
					NewAccount(ip_name, ini_balance);
					System.out.println("------------------------------");
					break;
					
				case 'B':
					String ip_account;
					System.out.println("------------------------------");
					System.out.println("Please enter your Account Number for Balance Check:\n\n");
					ip_account = scanner.next();
					System.out.println("Your Account number is "+ ip_account);
					BalanceCheck(ip_account);
					System.out.println("------------------------------");
					break;
					
				case 'C':
					int money;
					System.out.println("------------------------------");
					System.out.println("Please enter your Account Number:\n\n");
					ip_account = scanner.next();
					System.out.println("Please enter the money that you wish to withdraw:\n\n");
					money = scanner.nextInt();
					System.out.println("Your Account number is "+ ip_account);
					DebitMoney(ip_account, money);
					System.out.println("------------------------------");
					break;
					
				case 'D':				
					System.out.println("------------------------------");
					System.out.println("Please enter your Account Number:\n\n");
					ip_account = scanner.next();
					System.out.println("Please enter the money that should be credited:\n\n");
					money = scanner.nextInt();
					System.out.println("Your Account number is "+ ip_account);
					CreditMoney(ip_account, money);
					System.out.println("------------------------------");
					break;
					
				case 'E':
					System.out.println("------------------------------");
					System.out.println("You have selected to Exit the menu:\n\n");
					System.out.println("------------------------------");
					break;
				
					
				default:
					System.out.println("------------------------------");
					System.out.println("Invalid Option!!. Please enter a valid option.");
					break;
					
				}
			}while(option != 'E');
		}
		System.out.println("ThankYou! for using our Bank.");
		
	}
	
}

