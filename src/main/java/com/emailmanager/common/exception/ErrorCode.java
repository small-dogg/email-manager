package com.emailmanager.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    SERVER_NOT_FOUND(HttpStatus.NOT_FOUND, "메일 서버를 찾을 수 없습니다."),
    MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "메일 메시지를 찾을 수 없습니다."),
    MAIL_CONNECTION_FAILED(HttpStatus.SERVICE_UNAVAILABLE, "메일 서버 연결에 실패했습니다."),
    UNSUPPORTED_PROTOCOL(HttpStatus.BAD_REQUEST, "지원하지 않는 메일 프로토콜입니다."),
    MAIL_OPERATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "메일 처리 중 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
