import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class ExpViewDB extends HttpServlet{ 
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL="jdbc:mysql://localhost:3306/TIMS";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "Subhi@123";

    public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException{
        response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String title = "View Budget Details";
		
		String bid = request.getParameter("budg_id");

		out.println(
         "<html>\n" +
         "<head>"+
		 "<link rel=\"stylesheet\" href=\"PromTable.css\">"+
		 "<title>"+title+"</title></head>\n" +
         "<body style = \"background-image:url('images/painting-1682416389136-9172.jpg'); background-repeat:no-repeat; background-size:cover; opacity:0.8\">\n");

		   
         try{
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);
			// Open a connection
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement st = conn.createStatement();
            
            // Execute SQL query
            String sql = "SELECT * FROM Expenses WHERE budget_id='" + bid + "'";
		    //Execute SQL Query
			ResultSet rs = st.executeQuery(sql);
            out.print("<table>");
			out.print("<tr><th>Expense Number</th>");
            out.print("<tr><th>Budget ID</th>");
			out.print("<th>Expense Details</th>");
			out.print("<th>Amount Spent</th>");
			out.print("</tr>");

			if(rs.next())
			{
			  rs.previous(); 
			  while(rs.next()){
				int exp_no = rs.getInt("exp_no");
                String budg_id = rs.getString("budget_id");
				String exp_dets = rs.getString("exp_dets");
				float amt = rs.getFloat("amt_spent");
				
				//Display values
				out.print("<tr><td>" + exp_no + "</td>");
				out.print("<tr><td>" + budg_id + "</td>");
				out.print("<td>" + exp_dets + "</td>");
				out.print("<td>" + amt + "</td>");
			  }
			  out.println("</table>");
			}
			else{
			 out.print("</table>");
			 out.println("<p>No Records Found</p>");
			} 
            // Close all the connections
            rs.close();
			st.close();
            conn.close();
		} 
		catch(SQLException se) {
         //Handle errors for JDBC
            out.println("<p>Error</p>");
            se.printStackTrace();
		} 
        catch(Exception e){
            out.println("<p>Error</p>");
        }
        finally{
            out.println("</body></html>");
        }
      }
    
}

