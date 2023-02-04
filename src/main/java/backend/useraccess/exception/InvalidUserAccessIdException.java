package backend.useraccess.exception;

public class InvalidUserAccessIdException extends InvalidInputException{
    public InvalidUserAccessIdException() {
        super("존재하지 않는 유저 접근 데이터 아이디 입니다.");
    }
}
