package backend.useraccess.exception;

import backend.useraccess.enums.ErrorCode;

public class InvalidChartFieldNameException extends InvalidInputException{
    public InvalidChartFieldNameException() {
        super(ErrorCode.NOT_FOUND_CHART_FIELD_NAME);
    }
}
