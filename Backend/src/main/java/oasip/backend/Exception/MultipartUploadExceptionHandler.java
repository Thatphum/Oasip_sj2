package oasip.backend.Exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class MultipartUploadExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public void handleFileUploadException(MaxUploadSizeExceededException exception, HttpServletRequest httpServletRequest,
                                            HttpServletResponse response) throws IOException {

        final Map<String, Object> errors = new LinkedHashMap<>();
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        errors.put("timestamp", ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        errors.put("status", response.getStatus());
        errors.put("error", HttpStatus.BAD_REQUEST);
        errors.put("message","The file size cannot be larger than 10 MB.");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), errors);
    }
}