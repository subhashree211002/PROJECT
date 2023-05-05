import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
  
public class GetTripDetailsServlet extends HttpServlet {
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
            String tid = (String)request.getParameter("tid");

            //out.println("<p>Driver Access Initialising....</p>");
			Class.forName(JDBC_DRIVER);
			//out.println("<p>Driver Access Succesful....</p>");
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			//out.println("<p>Database Connection Successful....</p>");

            Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM trips WHERE trip_id='"+tid+"'";
			ResultSet rs = stmt.executeQuery(sql);

            if(rs.next()){
                out.println(rs.getString("st_place"));
                out.println(rs.getString("dest"));
                out.println(rs.getString("st_date"));
                out.println(rs.getString("end_date"));
            }

            

            
        }
        catch(Exception e){
            System.out.println(e);
        }  
  
    }  
}  