package com.aurora.security.core.filter.xss_attack;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * XSS过滤后的HttpServletRequest
 * @author xzbcode
 */
@Slf4j
@SuppressWarnings("all")
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private HttpServletRequest request;
    public static final String UTF_8 = "UTF-8";

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    @Override
    public String getHeader(String name) {
        String header = super.getHeader(name);
        if (!StringUtils.isEmpty(header)) {
            header = this.escapeXSS(header);
        }
        return header;
    }


    @Override
    public String[] getParameterValues(String name) {
        String[] parameterValues = super.getParameterValues(name);
        for (int i=0; i<parameterValues.length; i++) {
            if (!StringUtils.isEmpty(parameterValues[i])) {
                parameterValues[i] = this.escapeXSS(parameterValues[i]);
            }
        }
        return parameterValues;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 获取body的值
        ServletInputStream inputStream = super.getInputStream();
        if (inputStream == null) {
            return inputStream;
        }
        String body = StreamUtils.copyToString(inputStream, Charset.forName(UTF_8));
        // 过滤
        if (log.isDebugEnabled()) {
            log.debug("body getInputStream before in XssHttpServletRequestWrapper: {}", body);
        }
        if (!StringUtils.isEmpty(body)) {
            body = this.escapeXSS(body);
        }
        // 返回流
        if (log.isDebugEnabled()) {
            log.debug("body getInputStream after in XssHttpServletRequestWrapper: {}", body);
        }
        final ByteArrayInputStream stream = new ByteArrayInputStream(body.getBytes(UTF_8));
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return stream.read();
            }
        };
    }

    /**
     * 过滤XSS字符
     * @param value
     * @return
     */
    private String escapeXSS(String value) {
        value = StringEscapeUtils.escapeHtml4(value);
        value = value.replace("&quot;","\"");
        return value;
    }
}
