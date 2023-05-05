import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
  
  

public class SessionInitiateServlet extends HttpServlet{
      
    public void doGet(HttpServletRequest request, HttpServletResponse response){  
        try{
            HttpSession session = request.getSession(true);
            session.setAttribute("uid", request.getParameter("uid"));
        
            RequestDispatcher rd = request.getRequestDispatcher("trav_hmpg.html");
            rd.forward(request, response);
        }
        catch(Exception e){
            System.out.println(e);
        }   
    }  
}
