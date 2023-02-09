package backend.useraccess.exception;

import backend.useraccess.enums.ErrorCode;

public class InvalidUserAccessIdException extends InvalidInputException{
    public InvalidUserAccessIdException() {
        super(ErrorCode.NOT_FOUND_USER_ACCESS_ID);
    }
}
