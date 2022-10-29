package oasip.backend.Exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {


        final Map<String, Object> errors = new LinkedHashMap<>();
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        errors.put("timestamp", ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        errors.put("status", response.getStatus());
        errors.put("error", HttpStatus.FORBIDDEN);
        errors.put("message", "Access Denied");
        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), errors);
        }catch (Exception e){
            throw new ServletException();
        }

    }
}
