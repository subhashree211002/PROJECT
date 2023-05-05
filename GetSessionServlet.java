import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
  
  

public class GetSessionServlet extends HttpServlet{
      
    public void doGet(HttpServletRequest request, HttpServletResponse response){  
        try{
            HttpSession session = request.getSession(false);
            //session.setAttribute("uid", request.getParameter("uid"));
        
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println(session.getAttribute("uid"));
        }
        catch(Exception e){
            System.out.println(e);
        }   
    }  
}
