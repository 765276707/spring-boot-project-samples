package com.aurora.security.core.filter.steal_link;


import com.aurora.security.core.model.ListType;
import com.aurora.security.core.response.ErrorResponse;
import com.aurora.security.core.response.ErrorStatus;
import com.aurora.security.core.util.ResponseUtil;
import com.aurora.security.core.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * 简单的防盗链过滤器
 * @author xzbcode
 */
@Slf4j
public class StealLinkFilter extends OncePerRequestFilter {

    private final IStealLinkListProvider stealLinkListProvider;

    public StealLinkFilter(IStealLinkListProvider stealLinkListProvider) {
        this.stealLinkListProvider = stealLinkListProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String refererURL = request.getHeader("Referer");
        if (StringUtils.isEmpty(refererURL)) {
            this.handleError(response);
            return;
        }

        // 根据名单进行判断
        String domainURL = WebUtil.getDomainName(refererURL);
        if (ListType.BLACK_LIST.equals(stealLinkListProvider.getType())) {
            // 黑名单
            Set<String> blackList = stealLinkListProvider.getBlackList();
            if (blackList.size()==0
                    || blackList.contains(domainURL)) {
                this.handleError(response);
                return;
            }
        } else {
            // 白名单，未配置名单类型的情况下默认使用白名单
            Set<String> whiteList = stealLinkListProvider.getWhiteList();
            if (whiteList.size()==0
                    || !whiteList.contains(domainURL)) {
                this.handleError(response);
                return;
            }
        }

        // 放行
        chain.doFilter(request, response);
    }

    /**
     * <h2>盗用连接则响应403</h2>
     * @param response
     * @throws IOException
     */
    private void handleError(HttpServletResponse response) throws IOException {
        HttpStatus forbidden = HttpStatus.FORBIDDEN;
        ResponseUtil.writeJSON(response, forbidden,
                new ErrorResponse(ErrorStatus.FORBIDDEN, "请勿盗用连接资源"));
    }
}
