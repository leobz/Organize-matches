package matches.organizer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MatchNotFoundException extends RuntimeException{

    public MatchNotFoundException() {
        super();
    }
    public MatchNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public MatchNotFoundException(String message) {
        super(message);
    }
    public MatchNotFoundException(Throwable cause) {
        super(cause);
    }

}