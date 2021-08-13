package com.toy.publisher.entity.common;

import java.util.List;

public class ResultData {
    private String code;
    private Object data;
    private String message;

    public static class Builder {
        private String code;
        private Object data;
        private String message;

        public Builder code(ResultCodeEnum code) {
            this.code = code.getCode();
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public Builder data(List<Object> data) {
            this.data = data;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public ResultData builder() {return new ResultData(this);}
    }

    private ResultData(Builder builder) {
        this.code = builder.code;
        this.data = builder.data;
        this.message = builder.message;
    }

    public enum ResultCodeEnum {
        SUCCESS("success"),
        FAIL("fail"),
        NOT_SERVICE_KEY("not valid service key");

        private String code;

        ResultCodeEnum(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

}
