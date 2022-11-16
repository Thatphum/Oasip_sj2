package oasip.backend.Exception;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class ErrorResponse
{
    private String timestamp;
    private Integer status;
    private String errors;
    private String message;
//    private String path;

    public ErrorResponse(HttpStatus status, String message) {
        super();
        this.timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(new Date());
        this.status = status.value();
        this.errors = status.name();
        this.message = message;
//        this.path =
    }

}
