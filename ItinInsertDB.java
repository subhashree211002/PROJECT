import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
 
public class ItinInsertDB extends HttpServlet{
		// JDBC driver name and database URL
      static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
      static final String DB_URL="jdbc:mysql://localhost:3306/TIMS";

      //  Database credentials
      static final String USER = "root";
      static final String PASS = "Subhi@123";

	  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    
		// Set response content type
		response.setContentType("text/html"); 
		PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        String title = "Insert and View Itinerary";

        out.println(
         "<html>\n" +
         "<head>"+
		 "<link rel=\"stylesheet\" href=\"PromTable.css\">"+
		 "<title>"+title+"</title></head>\n" +
         "<body style = \"background-image:url('images/painting-1682416389136-9172.jpg'); background-repeat:no-repeat; background-size:cover; opacity:0.8\">\n");

		try{
                
            String trip_id = request.getParameter("trip_id");
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);
			// Open a connection
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //Finding No of Recommendations to decide Recno
            Statement st_c = conn.createStatement();
			String sql = "SELECT * FROM Itineraries where trip_id = '"+trip_id+"'";
			ResultSet rs1 = st_c.executeQuery(sql);
            int it_no = 1;
            while(rs1.next())
              it_no++;
            st_c.close();
            String it_id = Integer.toString(it_no);

            //Insert into Table   
			PreparedStatement st_ins = conn.prepareStatement("Insert into Itineraries values(?, ?, ?, ?, ?, ?)");
            st_ins.setString(1, request.getParameter("trip_id"));
			st_ins.setString(2, it_id);
			st_ins.setString(3, request.getParameter("tourist_spot"));
            st_ins.setString(4, request.getParameter("dov"));
			st_ins.setString(5, request.getParameter("st_time"));
			st_ins.setInt(6, Integer.parseInt(request.getParameter("duration")));
			st_ins.executeUpdate();
            st_ins.close();


           out.println(
           "<html>\n" + "<head>"+
		   "<link rel=\"stylesheet\" href=\"PromRecTable.css\">"+
		   "<title>"+title+"</title></head>\n" +
           "<body style = \"background-image:url('images/painting-1682416389136-9172.jpg'); background-repeat:no-repeat; background-size:cover; opacity:0.8\">\n");
            //Writing table entries into response
            Statement st = conn.createStatement();
			sql = "SELECT * FROM Itineraries where trip_id ="+trip_id;
			ResultSet rs = st.executeQuery(sql);
            out.print("<table>");
			out.print("<tr><th>Trip ID</th>");
			out.print("<th>Itinerary ID</th>");
			out.print("<th>Tourist Spot</th>");
			out.print("<th>Date of Visit</th>");
            out.print("<th>Start Time</th>");
            out.print("<th>Duration</th>");
			out.print("</tr>");

			
			while(rs.next()){
                trip_id = rs.getString("trip_id");
				it_id = rs.getString("it_id");
				String tourist_spot = rs.getString("tourist_spot");
                String dov = rs.getString("dov");
                String st_time = rs.getString("st_time");
                int dur = rs.getInt("duration");
				//Display values
				out.print("<tr><td>" + trip_id + "</td>");
				out.print("<td>" + it_id + "</td>");
				out.print("<td>" + tourist_spot + "</td>");
                out.print("<td>" + dov + "</td>");
                out.print("<td>" + st_time + "</td>");
				out.print("<td>" + dur + "</td></tr>");
			  }
			  out.println("</table>");
			
            st.close();
            rs.close();
            conn.close(); 
            out.println("<p>Insert Successful....</p>");
            // Get a writer pointer 
            // to display the successful result
          
        }
        catch (Exception e) {
            out.println(e);
            e.printStackTrace();
        }
    }
} 