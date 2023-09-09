package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MyConnection {
    private static final String username = "root";
    private static final String password = "";
    private static final String dataConn ="jdbc:mysql://localhost:3307/Georges";
    public Connection con;
    
    public Connection getConnection() throws SQLException, ClassNotFoundException
    {
       
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(dataConn, username, password);
        return con;
    } 
}
