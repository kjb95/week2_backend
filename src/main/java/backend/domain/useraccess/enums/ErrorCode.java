package backend.domain.useraccess.enums;


public enum ErrorCode {

    INVALID_REQUEST_PARAMETER_BIND(400, "COMMON-001", "유효하지 요청 파라미터 바인딩"),
    METHOD_NOT_ALLOWED(405, "COMMON-002", "허용하지 않는 HTTP 메소드"),
    NO_SUCH_ELEMENT(404, "COMMON-003", "존재하지 않는 값을 가져오려 함"),
    UNEXPECTED_EXCEPTION(400, "COMMON-004", "예상하지 못한 예외"),

    UNAUTHORIZED_USER(401, "SECURITY-001", "인증되지 않은 사용자");

    private final int status;
    private final String code;
    private final String description;

    ErrorCode(int status, String code, String description) {
        this.status = status;
        this.code = code;
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}