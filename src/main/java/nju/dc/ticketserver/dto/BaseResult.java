package nju.dc.ticketserver.dto;

public class BaseResult <T> {

    private int errorCode;
    private T data;

    public BaseResult(int errorCode, T data) {
        this.errorCode = errorCode;
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
