package matches.organizer.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AddPlayerException extends RuntimeException {

    public AddPlayerException() {
        super();
    }
    public AddPlayerException(String message, Throwable cause) {
        super(message, cause);
    }
    public AddPlayerException(String message) {
        super(message);
    }
    public AddPlayerException(Throwable cause) {
        super(cause);
    }
}