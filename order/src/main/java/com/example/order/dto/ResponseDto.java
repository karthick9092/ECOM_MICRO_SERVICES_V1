package com.example.order.dto;

import lombok.Data;

@Data
public class ResponseDto<T> {

    private String status;

    private String msg;

    private T data;

    public static <T> ResponseDtoBuilder<T> builder() {
        return new ResponseDtoBuilder<>();
    }

    public static class ResponseDtoBuilder<T> {

        private String status;
        private String msg;
        private T data;

        public ResponseDtoBuilder<T> status(String status) {
            this.status = status;
            return this;
        }

        public ResponseDtoBuilder<T> msg(String msg) {
            this.msg = msg;
            return this;
        }

        public ResponseDtoBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ResponseDto<T> build() {
            ResponseDto<T> dto = new ResponseDto<>();
            dto.status = this.status;
            dto.msg = this.msg;
            dto.data = this.data;
            return dto;
        }
    }
}

