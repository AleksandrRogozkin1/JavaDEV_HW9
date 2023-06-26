package servlet;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.ZoneId;


@WebFilter("/time")
public class TimeZoneValidateFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {

        String timeZone = req.getParameter("timezone");

        if(timeZone == null || validTimeZone(timeZone.replaceAll(" ", "+"))){
            chain.doFilter(req,resp);
        }else {
            resp.setContentType("text/html");
            resp.getWriter().write("ERROR 400: Invalid timezone");
            resp.setStatus(400);
            resp.getWriter().close();
        }
    }
    private boolean validTimeZone(String timeZone) {
        try {
            ZoneId.of(timeZone);
            return true;
        }catch (DateTimeException e){
            return false;
        }
    }
}
