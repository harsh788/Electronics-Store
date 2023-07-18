import java.sql.*;
import java.util.Scanner;

public class Electronics_store 
{
    //Set JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    //static final String DB_URL = "jdbc:mysql://localhost/companydb";
    static final String DB_URL = "jdbc:mysql://localhost/electronics_store?useSSL=false";
    //  Database credentials
    static final String USER = "root";// add your user 
    static final String PASS = "harsh1234";// add password
    

    public static void main(String[] args) {
    Connection conn = null;
    Statement stmt = null;
    Scanner sc = new Scanner(System.in);


// STEP 2. Connecting to the Database
    try{
        //STEP 2a: Register JDBC driver
        Class.forName(JDBC_DRIVER);
        //STEP 2b: Open a connection
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        //STEP 2c: Execute a query
        System.out.println("Creating statement...");
        stmt = conn.createStatement();

        int input;
        String sql;
        ResultSet rs = null;
        boolean flag = true;

        while(flag)
        {
            System.out.println();
            System.out.println("Login as: ");
            System.out.println("1. Admin");
            System.out.println("2. Customer");
            System.out.println("0. Exit");
            int cho = sc.nextInt();
            switch(cho)
            {
                case 0:
                flag = false;
                break;

                //Admin
                case 1:
                System.out.println();
                System.out.println("0. Quit");
                System.out.println("1. Insert new data");
                System.out.println("2. Total products left");
                System.out.println("3. Get total sales");
                System.out.println("4. Getting all the transactions of a particular customer");
                System.out.println("5. Total sales for a day");
                System.out.println("6. Getting total quantity and sales of a particular Product_type, grouped by the Brand");
                System.out.println("7. Getting total quantity and sales of a particular Brand, grouped by the Product_type"); 
                System.out.println("8. Update the mobile number of a Customer");
                System.out.println("9. Delete a Product");       
        
                while(true)
                {
                    System.out.println();
                    System.out.print("Enter the Query number: ");
                    input = sc.nextInt();
                    switch(input)
                    {
                        case 0:
                        flag = false;
                        break;
        
                        //Inserting new data
                        case 1:
                        System.out.println("Enetr the number corresponding to the table where new data is to be added");
                        System.out.println("1. Customer");
                        System.out.println("2. Product");
                        System.out.println("3. Transaction");
                        int num = sc.nextInt();
                        switch(num)
                        {
                            //Adding a new Customer
                            case 1:
                            System.out.println("Adding a new Customer");
                            System.out.print("First name: ");
                            String fname = sc.next();
                            System.out.print("Last name: ");
                            String lname = sc.next();
                            System.out.print("Mobile number: ");
                            long m = sc.nextLong();
                            System.out.print("Email ID: ");
                            String email = sc.next();
                            System.out.print("City: ");
                            String addr = sc.next();
                            sql="INSERT INTO Customer(Fname, Lname, Mobile_number, Email_ID, Address) values ('"+fname+"', '"+lname+"', "+m+", '"+email+"', '"+addr+"')";
                            stmt.executeUpdate(sql);
                            System.out.println("Customer added successfully!!");
                            break;
        
                            //Adding a new Product
                            case 2:
                            System.out.println("Adding a new Product");
                            System.out.print("Product type: ");
                            String prod = sc.next();
                            System.out.print("Brand: ");
                            String br = sc.next();
                            System.out.print("Price: ");
                            long pr = sc.nextLong();
                            System.out.print("Number of units added: ");
                            long unit = sc.nextLong();
                            sql="insert into Product(Product_type, Brand, Price, Units_left) values ('"+prod+"', '"+br+"', "+pr+", "+unit+")";
                            stmt.executeUpdate(sql);
                            System.out.println("Product added successfully!!");
                            break;
                            
                            //Adding a new Transaction
                            case 3:
                            System.out.println("Adding a new Transaction");
                            System.out.print("CustomerID: ");
                            int custid = sc.nextInt();
                            System.out.print("Total price: ");
                            int tot = sc.nextInt();
                            sql="insert into Transactions(Customer_ID, Total_price) values ("+custid+", "+tot+")";
                            stmt.executeUpdate(sql);
                            
                            System.out.println("Adding the items bought in this transaction");
                            //Can add numerous products in a single transaction
                            while(true)
                            {
                                System.out.print("ProductID: ");
                                int pro = sc.nextInt();
                                sql="select count(*) from Transactions";
                                rs=stmt.executeQuery(sql);
                                rs.next();
                                int transid = rs.getInt(1);
                                System.out.print("Quantity: ");
                                int qua = sc.nextInt();
                                System.out.print("Cost: ");
                                int cos = sc.nextInt();
                                sql="insert into Product_Transaction(Product_ID, Transaction_D, Quantity, Cost) values ("+pro+", "+transid+", "+qua+", "+cos+")";
                                stmt.executeUpdate(sql);
        
                                System.out.println("Enter one more product?(Y/N)");
                                String str = sc.next();
                                if(str.equalsIgnoreCase("N"))
                                {
                                    break;
                                }
                            }
                            System.out.println("Transaction added successfully!!");
                            break;
        
                            default:
                            System.out.println("Incorrect choice!!");
                            continue;
                        }
                        break;
        
                        //Total products left
                        case 2:
                        sql = "select sum(Units_left) from Product";
                        rs = stmt.executeQuery(sql);
                        int sum = 0, unitsleft;
                        while(rs.next())
                        {
                            unitsleft = rs.getInt(1);
                            sum = sum + unitsleft;
                        }
                        System.out.println("Total number of Products left: "+sum);
                        break;
        
                        //Get total sales
                        case 3:
                        sql="select sum(Total_price) from Transactions";
                        rs = stmt.executeQuery(sql);
                        sum = 0;
                        int totprice;
                        while(rs.next())
                        {
                            totprice = rs.getInt(1);
                            sum = sum + totprice; 
                        }
                        System.out.println("Total sales(in Rupees): "+sum);
                        break;
        
                        //Getting all the transactions of a particular Customer
                        case 4:
                        System.out.print("Enter the customer's mobile number whose transactions are to be retrieved: ");
                        long n = sc.nextLong();
                        sql="select p.Product_type AS prod, p.Brand AS p_brand, pt.Quantity AS pt_Quantity, pt.Cost AS pt_cost from Product_Transaction AS pt, Product AS p where pt.Transaction_D = (select Transaction_ID from Transactions where Transactions.Customer_ID = (select Customer_ID from Customer where Mobile_number = "+n+")) AND pt.Product_ID = p.Product_ID";
                        rs=stmt.executeQuery(sql);
                        int quantity, cost;
                        String p_type, brand;
                        while(rs.next())
                        {
                            p_type = rs.getString("prod");
                            brand = rs.getString("p_brand");
                            quantity = rs.getInt("pt_Quantity");
                            cost = rs.getInt("pt_cost");
                            System.out.println("Product Type: "+p_type+", Brand: "+brand+", Quantity: "+quantity+ ", Cost: "+cost);
                        }
                        break;
        
                        //Information regarding all transactions made on a particlar day
                        case 5:
                        System.out.print("Enter the date for which the total sales is to be calculated (Format: yyyy-mm-dd): ");
                        String date = sc.next();
                        //sql = "select (Total_price) from Transactions where cast(Transaction_date AS DATE) = '"+date+"'";
                        sql="select p.Product_type AS p_prodtype, p.Brand AS p_brand, t.Total_price AS t_tot from Transactions AS t INNER JOIN Product_Transaction AS pt ON t.Transaction_ID=pt.Transaction_D INNER JOIN Product AS p ON pt.Product_ID=p.Product_ID where cast(Transaction_date AS DATE) = '"+date+"'";
                        rs=stmt.executeQuery(sql);
                        sum = 0;
                        while(rs.next())
                        {
                            p_type =rs.getString("p_prodtype");
                            brand = rs.getString("p_brand");
                            totprice = rs.getInt("t_tot");
                            sum = sum + totprice;
                            System.out.println("Product_type: "+p_type+", Brand: "+brand+", Price: "+totprice);
                        }
                        System.out.println("Total sales on "+date+" = "+sum);
                        break;
        
                        //Getting total quantity and sales of a particular Product_type, grouped by the Brand
                        case 6:
                        System.out.println("Enter the Product type for which total quantity and sales are to be calculated: ");
                        sql="select DISTINCT Product_type from Product";
                        rs=stmt.executeQuery(sql);
                        while(rs.next())
                        {
                            System.out.println(rs.getString("Product_type"));
                        }
                        String choice = sc.next();
                        sql="select p.Brand AS p_brand, pt.Quantity AS pt_Quantity, pt.Cost AS pt_Cost, sum(pt.Cost*pt.Quantity) AS 'Total Sales' from Product_Transaction AS pt, Product AS p where pt.Product_ID IN (select p.Product_ID from Product where p.Product_type='"+choice+"') group by p.Brand, pt.Quantity, pt.Cost";
                        rs=stmt.executeQuery(sql);
                        while(rs.next())
                        {
                            brand = rs.getString("p_Brand");
                            quantity = rs.getInt("pt_Quantity");
                            cost = rs.getInt("pt_Cost");
                            System.out.println("Brand: "+brand+", Quantity: "+quantity+", Total Sales: "+cost*quantity);
                        }
                        break;
                        
                        //Getting total quantity and sales of a particular Brand, grouped by the Product_type
                        case 7:
                        System.out.println("Enter the Brand for which total quantity and sales are to be calculated:");
                        sql="select DISTINCT Brand from Product";
                        rs=stmt.executeQuery(sql);
                        while(rs.next())
                        {
                            System.out.println(rs.getString("Brand"));
                        }
                        choice = sc.next();
                        sql="select p.Product_type AS p_ptype, pt.Quantity AS pt_quantity, pt.Cost AS pt_cost, sum(pt.Cost*pt.Quantity) AS 'Total Sales' from Product_Transaction AS pt, Product AS p where pt.Product_ID IN (select p.Product_ID from Product where p.Brand='"+choice+"') group by p.Product_type, pt.Quantity, pt.Cost";
                        rs=stmt.executeQuery(sql);
                        while(rs.next())
                        {
                            p_type=rs.getString("p_ptype");
                            quantity=rs.getInt("pt_quantity");
                            cost=rs.getInt("pt_cost");
                            System.out.println("Product type: "+p_type+", Quantity: "+quantity+", Total sales: "+cost*quantity);
                        }
                        break;
        
                        //Updating the phone number of an existing Customer
                        case 8:
                        System.out.print("Enter the old mobile number: ");
                        long oldmob = sc.nextLong();
                        System.out.print("Enter the new mobile number: ");
                        long newmob = sc.nextLong();
                        sql="UPDATE Customer "+"SET Mobile_number = "+newmob+" WHERE Mobile_number = "+oldmob+"";
                        stmt.executeUpdate(sql);
                        System.out.println("Mobile number updated successfully!!");
                        break;
        
                        //Deleting a product from the database
                        case 9:
                        System.out.println("Enter the ProductID of the Product to be deleted");
                        int pr = sc.nextInt();

                        //All product_transaction rows pointing to this product_id will be set to NULL
                        sql = "Update Product_Transaction SET Product_ID=NULL WHERE Product_ID="+pr+"";
                        stmt.executeUpdate(sql);

                        sql="DELETE FROM Product "+"WHERE Product_ID = "+pr+"";
                        stmt.executeUpdate(sql);
                        System.out.println("Product record deleted successfully!!");
                        break;
        
                        default:
                        System.out.println("Incorrect choice!!");
                        continue;
                    }
        
                    if(input == 0)
                    {
                        flag = false;
                        break;
                    }
                }
                break;

                //Customer
                case 2:
                System.out.println("1. Get all the products of a particular Product type");
                System.out.println("0. Exit");
                int inp = sc.nextInt();
                if(inp == 1)
                {
                    System.out.println("Enter the Product type from the following: ");
                    sql="select DISTINCT Product_type from Product";
                    rs=stmt.executeQuery(sql);
                    while(rs.next())
                    {
                        System.out.println(rs.getString("Product_type"));
                    }
                    String st = sc.next();
                    String br;
                    int price;
                    sql="select * from Product where Product_type='"+st+"'";
                    rs=stmt.executeQuery(sql);
                    while(rs.next())
                    {
                        st=rs.getString("Product_type");
                        br=rs.getString("Brand");
                        price=rs.getInt("Price");
                        System.out.println("Product type: "+st+", Brand: "+br+", Price: "+price);
                    }
                }
                if(inp == 0)
                {
                    flag = false;
                }
            }
        }

    //STEP 5: Clean-up environment
    rs.close();
    stmt.close();
    conn.close();
        }catch(SQLException se){    	 //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){        	//Handle errors for Class.forName
        e.printStackTrace();
    }finally{				//finally block used to close resources
        try{
            if(stmt!=null)
                stmt.close();
        }catch(SQLException se2){
        }
        try{
            if(conn!=null)
                conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }					//end finally try
    }					//end try
    System.out.println("End of Code");
    }					//end main        
}
