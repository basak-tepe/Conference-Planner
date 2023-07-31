package com.conferences.schedule.controller.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleGlobalException extends RuntimeException {

	@Serial
    private static final long serialVersionUID = 3190484861219737415L;
    private String message;
	private String code;

    public SimpleGlobalException(String message) {
        super(message);
    }

    public ErrorResponse toErrorResponse() {
        return new ErrorResponse(this.code,this.message);
    }
}
