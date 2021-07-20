package com.aurora.encrypt.core.advice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.aurora.encrypt.core.annotation.ApiCryptBody;
import com.aurora.encrypt.core.annotation.ApiEncryptBody;
import com.aurora.encrypt.core.handler.CryptManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;

/**
 * 对响应内容进行加密
 * @author xzbcode
 */
@Slf4j
@ControllerAdvice
@SuppressWarnings("all")
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    private CryptManager cryptManager;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        // 当且仅当有 EncryptRspBody 注解时才加密响应体
        Method method = methodParameter.getMethod();
        if (method != null) {
            return AnnotationUtils.findAnnotation(method, ApiEncryptBody.class) != null
                    ||  AnnotationUtils.findAnnotation(method, ApiCryptBody.class) != null;
        }
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object apiResponse, MethodParameter methodParameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // 处理基本数据类型的null, 以及时间格式
        String data = JSON.toJSONString(apiResponse, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullBooleanAsFalse, SerializerFeature.WriteNullListAsEmpty);
        HttpHeaders headers = serverHttpResponse.getHeaders();
        String respBody = JSON.toJSONString(apiResponse);
        // 加密
        String encryptData = cryptManager.encrypt(headers, respBody);
        // 开启调试
        if (log.isDebugEnabled()) {
            log.debug("response body before encrypted： {}，response body after encrypted： {}", data, encryptData);
        }
        // 返回加密后的数据
        return encryptData;
    }

    /**
     * <h2>加密失败的处理</h2>
     * @param e
     * @param serverHttpResponse
     * @return
     */
//    private ErrorResponse handlerError(EncryptException e, ServerHttpResponse serverHttpResponse) {
//        // 记录错误信息
//        log.error("系统加密响应内容时发生错误： {}, 详情： {}", e.getMessage(), e);
//        // 返回一个加密错误提示
//        serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
//        return new ErrorResponse(500, "系统服务出现异常，请稍后再试");
//    }

}
