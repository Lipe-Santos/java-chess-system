package boardGame.exception;

import java.io.Serial;

public class BoardException extends RuntimeException {
    //I want this exception to be optional to handle
    @Serial
    private static final long serialVersionUID = 1L;
    public BoardException(String msg) {
        super(msg);
    }
}
