package com.wlld.myjecs.handler;

import com.wlld.myjecs.entity.mes.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常捕获
 * @author
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler({ BindException.class })
	@ResponseStatus(HttpStatus.OK)
	public Response handleBusinessException(BindException exception) {
		log.warn("参数验证异常,ex = {}", exception.getBindingResult().getAllErrors().get(0).getDefaultMessage());
		return Response.fail(400,exception.getBindingResult().getAllErrors().get(0).getDefaultMessage());
	}
}
