package jpabook.jpashop.common.exception;

public class SowonException extends RuntimeException {
    static final long serialVersionUID = -1989121633042145585L;

    private int httpStatusCode;
    private String code;
    private String message;

    public SowonException(Status status) {
        super(status.name());
        this.httpStatusCode = status.httpStatusCode();
        this.code = status.code();
        this.message = status.message();
    }

    public SowonException(String message) {
        super(Status.BAD.name());
        this.httpStatusCode = Status.BAD.httpStatusCode();
        this.code = Status.BAD.code();
        this.message = message;
    }

    public SowonException(String code, String message) {
        super(Status.BAD.name());
        this.httpStatusCode = Status.BAD.httpStatusCode();
        this.code = code;
        this.message = message;
    }

    public int getHttpStatusCode() {
        return this.httpStatusCode;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}