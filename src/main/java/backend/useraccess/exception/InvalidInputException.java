package backend.useraccess.exception;

import backend.useraccess.enums.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidInputException extends RuntimeException{
    private final ErrorCode errorCode;
    public InvalidInputException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
