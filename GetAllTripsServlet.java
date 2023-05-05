import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
  
public class GetAllTripsServlet extends HttpServlet {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL="jdbc:mysql://localhost:3306/TIMS";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "Subhi@123";

    public void doGet(HttpServletRequest request, HttpServletResponse response){  
        try{
            HttpSession session = request.getSession(false);
            
            response.setContentType("text/html");  
            PrintWriter out = response.getWriter(); 
            
            String uid = (String)session.getAttribute("uid");
            //out.println(uid);

            //out.println("<p>Driver Access Initialising....</p>");
			Class.forName(JDBC_DRIVER);
			//out.println("<p>Driver Access Succesful....</p>");
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			//out.println("<p>Database Connection Successful....</p>");

            Statement stmt = conn.createStatement();
			String sql = "SELECT trip_id, dest, st_date FROM trips WHERE traveler_id='"+uid+"' ORDER BY st_date";
			ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                out.print(rs.getString("dest") + " (" + rs.getDate("st_date") + ") " + rs.getString("trip_id")  + ",");
            }
            
        }
        catch(Exception e){
            System.out.println(e);
        }  
  
    }  
}  

