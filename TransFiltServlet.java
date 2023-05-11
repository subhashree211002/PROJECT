import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
  
public class TransFiltServlet extends HttpServlet {
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

            String st_city = (String)request.getParameter("st_city");
            String dest_city = (String)request.getParameter("dest_city");

            String c1 = (String)request.getParameter("c1");
            String c2 = (String)request.getParameter("c2");
            String c3 = (String)request.getParameter("c3");
            String c4 = (String)request.getParameter("c4");

            String fn = request.getParameter("fn");


            //out.println(c1+" "+c2+" "+c3+" "+c4);
            out.println("<table>");
            out.println("<tr><th>Transit Type</th><th>Source</th><th>Destination</th><th>Start Time</th><th>EndTime</th><th>Cost</th></tr>");

			Class.forName(JDBC_DRIVER);
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            Statement stmt = conn.createStatement();
            String sql1 = "";
            String sql2 = "";
            String sql3 = "";
            String sql4 = "";
            if(c1.equals("false")){
                st_city = "%";
            }
            if(c2.equals("false")){
                dest_city = "%";
            }
            if(c3.equals("Flight")||c3.equals("Train")){
                ;
            }
            else{
                c3 = "%";
            }
            if(c4.equals("inc")){
                sql4 = ",cost ASC";
            }
            else if(c4.equals("dec")){
                sql4 = ",cost DESC";
            }
			//String sql = "SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM transit ORDER BY prom_priority"+sql4+")"+sql3+")"+sql2+")"+sql1;
			String sql = "SELECT * FROM transit WHERE source LIKE '"+st_city+"' AND dest LIKE '"+dest_city+"' AND trans_type LIKE '"+c3+"' ORDER BY prom_priority"+sql4;
            //console.log(sql);
            //out.println(sql);

			ResultSet rs = stmt.executeQuery(sql);
            //out.println(sql);
            while(rs.next()){
                out.println("<tr id = '"+rs.getString("transit_id") + "' onclick='clear_all();"+fn+"("+rs.getString("transit_id")+");'><td>"+rs.getString("trans_type") + "</td><td>" + rs.getString("source") + "</td><td>" + rs.getString("dest") + "</td><td>" + rs.getString("start_time") + "</td><td>" + rs.getString("end_time")+"</td><td>" + rs.getString("cost")+"</td></tr>");
                //out.println("<tr id = '"+rs.getString("transit_id") + "' onclick='clear_all();choose_onw("+rs.getString("transit_id")+");'><td>"+rs.getString("trans_type") + "</td><td>" + rs.getString("source") + "</td><td>" + rs.getString("dest") + "</td><td>" + rs.getString("start_time") + "</td><td>" + rs.getString("end_time")+"</td><td>" + rs.getString("cost")+"</td></tr>");
            }

            out.println("</table>");
            
        }
        catch(Exception e){
            System.out.println(e);
        }  
  
    }  
}  

