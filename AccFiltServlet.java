import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
  
public class AccFiltServlet extends HttpServlet {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL="jdbc:mysql://localhost:3306/TIMS";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "Subhi@123";

    public void doGet(HttpServletRequest request, HttpServletResponse response){  
        try{            
            response.setContentType("text/html");  
            PrintWriter out = response.getWriter();


            String dest_city = (String)request.getParameter("dest_city");

            String c1 = (String)request.getParameter("c1");
            String c2 = (String)request.getParameter("c2");

            String fn = (String)request.getParameter("fn");


            //out.println(c1+" "+c2+" "+c3+" "+c4);
            out.println("<table>");
            out.println("<tr><th>Accomodation Type</th><th>Accomodation Name</th><th>Address</th><th>Cost</th><th>Amenities</th></tr>");

			Class.forName(JDBC_DRIVER);
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            Statement stmt = conn.createStatement();
            String sql1 = "";
            String sql2 = "";
            String sql3 = "";
            String sql4 = "";
            if(c1.equals("Hostel")||c1.equals("Hotel")||c1.equals("Villa")||c1.equals("Resort")||c1.equals("Homestay")){
                sql1 = c1;
            }
            else{
                sql1 = "%";
            }

            if(c2.equals("inc")){
                sql4 = ", cost ASC";
            }
            else if(c2.equals("dec")){
                sql4 = ", cost DESC";
            }


            
			//String sql = "SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM transit ORDER BY prom_priority"+sql4+")"+sql3+")"+sql2+")"+sql1;
			String sql = "SELECT * FROM accommodation NATURAL JOIN location WHERE city ='"+dest_city+"' AND accom_type LIKE '"+sql1+"' ORDER BY prom_priority"+sql4;
            //console.log(sql);
            //out.println(sql);

			ResultSet rs = stmt.executeQuery(sql);
            //out.println(sql);
            while(rs.next()){
                out.println("<tr id = '"+rs.getString("accom_id") + "' onclick='clear_all();"+fn+"("+rs.getString("accom_id")+");'><td>"+rs.getString("accom_type") + "</td><td>" + rs.getString("accom_name") + "</td><td>" + rs.getString("address") + "</td><td>" + rs.getString("cost") + "</td><td>" + rs.getString("amenities") +"</tr>");
            }

            out.println("</table>");
            
        }
        catch(Exception e){
            System.out.println(e);
        }  
  
    }  
}  

