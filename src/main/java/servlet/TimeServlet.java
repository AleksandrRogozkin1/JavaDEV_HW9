package servlet;
import org.thymeleaf.TemplateEngine;

import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;




@WebServlet(value ="/time")
public class TimeServlet extends HttpServlet {

private TemplateEngine engine;
@Override
public void init() throws ServletException {
    engine = new TemplateEngine();

    FileTemplateResolver resolver = new FileTemplateResolver();
    resolver.setPrefix("D:/IT/IdeaProjects2/JavaDEV_HW9/templates/");
    resolver.setSuffix(".html");
    resolver.setTemplateMode("HTML5");
    resolver.setOrder(engine.getTemplateResolvers().size());
    resolver.setCacheable(false);
    engine.addTemplateResolver(resolver);
}
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String timezoneParam = request.getParameter("timezone");
        if (timezoneParam == null){
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("lastTimezone")) {
                        timezoneParam=(cookie.getValue());
                    }
                }

            }
        }else{
            timezoneParam = timezoneParam.replaceAll(" ", "+");
        }
        response.addCookie(new Cookie("lastTimezone", timezoneParam));

        String currentTime=ZonedDateTime.now(ZoneId.of(timezoneParam))
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss 'UTC'X"));
        response.setContentType("text/html");
        Context context=new Context();
        context.setVariable("time", currentTime);
        engine.process("time", context, response.getWriter());
        response.getWriter().close();

    }
}


