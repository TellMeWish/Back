package jpabook.jpashop.common.exception;

public enum Status {
    GOOD(200, "UYMBP", ":)"),
    BAD(400, "NBBDV", ":("),
    
    CANT_WRITE_RE_RE_COMMENT(400, "JEO8S", "대댓글까지만 작성가능."),
    
    NOT_FOUND(404, "OFHQM", "Not Found"),
    METHOD_NOT_ALLOWED(405, "N3KG0", "Method Not Allowed"),
    ACCESS_DENIED(403, "KDQKN", "접근불가"),
    EMPTY_STRING(400, "EPFQT", "공백입력불가"),
    EMPTY_FILE(400, "HFDCA", "파일미등록"),
    REGISTERED_PHONE_NUMBER(400, "DDHJU", "등록된번호"),
    UNREGISTERED_PHONE_NUMBER(400, "KAZCZ", "미등록번호"),
    VERIFICATION_CODE_MISMATCH(400, "QMAEH", "인증번호불일치"),
    PASSWORD_MISMATCH(400, "SMWFG", "비밀번호불일치"),
    CURRENT_PASSWORD_MISMATCH(400, "EDYCG", "현재비밀번호불일치"),
    LOGINED_ANOTHER_DIVICE(400, "PZZGX", "다른기기에서로그인됨"),
    INVALID_TOKEN(401, "MXQAV", "유효하지않은토큰"),
    EXPIRED_TOKEN(401, "ISMUD", "만료된토큰"),
    IMAGE_FILE_ONLY(400, "DLSXV", "png, jpg, jpeg, gif만 업로드 가능"),
    DELETE_ALREADY(400, "MCSRQ", "이미삭제되었음"),
    NO_CONTENT(404, "XCIAI", "존재하지않는글"),
    PHONE_DENIED(400, "BWCVM", "전화번호유효성검사불일치"),
    PASSWORD_DENIED(400, "PRDNJ", "비밀번호유효성검사불일치"),
    NAME_DENIED(400, "MEVIA", "이름유효성검사불일치"),
    NOT_EXIST_PEOPLE(404, "YFOJK", "존재하지않는회원"),
    CANT_SELF(400, "DFAFX", "나와의대화불가"),
    OVER_ONE_MINUTES(403, "IXJPL", "토큰만료1분이상재발급불가"),
    NON_SIGN_IN(401, "TCJAN", "비로그인"),
    VALIDATION_ERROR(400, "PWHQG", "VALIDATION_ERROR"),
    SERVER_ERROR(500, "LE7W0", "Internal Server Error"),
    STOPPED_PEOPLE(403, "CN9WQ", "이용정지"),
    TERMINATED_PEOPLE(403, "L8F2X", "이용해지"),
    MAX_UPLOAD_SIZE_EXCEEDED(400, "K9KN1", "최대 업로드 사이즈 초과");

    private int httpStatusCode;
    private String code;
    private String message;

    Status(int httpStatusCode, String code, String message) {
        this.httpStatusCode = httpStatusCode;
        this.code = code;
        this.message = message;
    }

    public int httpStatusCode() {
        return this.httpStatusCode;
    }

    public String code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
}