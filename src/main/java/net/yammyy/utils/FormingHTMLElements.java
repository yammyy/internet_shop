package net.yammyy.utils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FormingHTMLElements
{
    public static void formingHeader(HttpServletRequest _request,HttpServletResponse _response, List<String> _csss)
            throws IOException
    {
        PrintWriter _writer=_response.getWriter();
        _writer.println("<head>"+
                         "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />"+
                         //Bootstrap CSS
                         "<link type=\"text/css\" rel=\"stylesheet\" href=\""+_request.getContextPath()+"/webjars/bootstrap/4.6.2/css/bootstrap.min.css\">"+
                         "<link type=\"text/css\" rel=\"stylesheet\" href=\""+_request.getContextPath()+"/webjars/font-awesome/6.1.2/css/all.min.css\">"+
                         //My styles
                         "<link type=\"text/css\" rel=\"stylesheet\" href=\""+_request.getContextPath()+"/css/styles.css\">");
        for (String css: _csss)
        {
            _writer.println("<link type=\"text/css\" rel=\"stylesheet\" href=\"css/"+css+".css\">");
        }
        _writer.println("</head>");
    }
    public static void formingJSSection(HttpServletRequest _request,HttpServletResponse _response,List<String> _jss)
            throws IOException
    {
        PrintWriter writer_l=_response.getWriter();
        writer_l.println("<script src=\"webjars/jquery/3.5.1/jquery.min.js\"></script>"+
                         "<script src=\"webjars/popper.js/1.16.0/umd/popper.min.js\"></script>"+
                         "<script src=\"webjars/bootstrap/4.6.2/js/bootstrap.min.js\"></script>");
        for (String js_l: _jss)
        {
            writer_l.println("<script src=\"js/"+js_l+".js\"></script>");
        }
    }
}
