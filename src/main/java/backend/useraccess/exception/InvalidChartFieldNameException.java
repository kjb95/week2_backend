package backend.useraccess.exception;

public class InvalidChartFieldNameException extends InvalidInputException{
    public InvalidChartFieldNameException() {
        super("존재하지 않는 차트 데이터의 필드 이름 입니다.");
    }
}
