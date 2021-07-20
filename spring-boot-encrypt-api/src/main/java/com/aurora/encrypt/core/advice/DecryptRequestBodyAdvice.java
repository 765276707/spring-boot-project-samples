package com.aurora.encrypt.core.advice;

import com.aurora.encrypt.core.annotation.ApiCryptBody;
import com.aurora.encrypt.core.annotation.ApiDecryptBody;
import com.aurora.encrypt.core.exception.extd.DecryptBodyException;
import com.aurora.encrypt.core.handler.CryptManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * 对请求体内容进行增强
 * @author xzbcode
 */
@Slf4j
@ControllerAdvice
@SuppressWarnings("all")
public class DecryptRequestBodyAdvice implements RequestBodyAdvice {

    @Autowired
    private CryptManager cryptManager;


    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> clazz) {
        // 当且仅当有 DecryptReqBody 注解时才解密请求体
        Method method = methodParameter.getMethod();
        if (method != null) {
            return AnnotationUtils.findAnnotation(method, ApiDecryptBody.class) != null
                    ||  AnnotationUtils.findAnnotation(method, ApiCryptBody.class) != null;
        }
        return false;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type,
                                           Class<? extends HttpMessageConverter<?>> clazz) throws IOException {
        // 判断方法是否有解密注解
        return new DecryptHttpInputMessage(httpInputMessage);
    }

    @Override
    public Object afterBodyRead(Object object, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type,
                                Class<? extends HttpMessageConverter<?>> clazz) {
        // TODO Auto-generated method stub
        return object;
    }

    @Override
    public Object handleEmptyBody(Object object, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type,
                                  Class<? extends HttpMessageConverter<?>> clazz) {
        // TODO Auto-generated method stub
        return object;
    }

    /**
     * 实现并重写HttpInputMessage
     * @author xzb
     */
    class DecryptHttpInputMessage implements HttpInputMessage {
        HttpHeaders headers;
        InputStream body;

        DecryptHttpInputMessage(HttpInputMessage httpInputMessage) throws IOException {
            this.headers = httpInputMessage.getHeaders();
            this.body = httpInputMessage.getBody();
        }

        @Override
        public InputStream getBody() throws IOException {
            // 输入流不为空的时候
            if (null != body) {
                // 替换非url安全字符（jdk8之后replaceAll性能优于replace）
                String strBody = IOUtils.toString(body)
                        .replaceAll("%2B", "+")
                        .replaceAll("%3D", "=");
                // 开启调试
                if (log.isDebugEnabled()) {
                    log.debug("request body before decrypted： {}", strBody);
                }
                // 解密参数
                String decryptStrBody = "";
                try {
                    decryptStrBody = cryptManager.decrypt(headers, strBody);
                } catch (Exception e) {
                    throw new DecryptBodyException("decrypted failure, may be cause by invalid crypt text in request body.");
                }
                // 开启调试
                log.info("request body after decrypted： {}", decryptStrBody);
                if (log.isDebugEnabled()) {
                    log.debug("request body after decrypted： {}", decryptStrBody);
                }
                // 返回解密后的数据流
                return IOUtils.toInputStream(decryptStrBody, "UTF-8");
            }
            // 返回原始数据
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }

}
