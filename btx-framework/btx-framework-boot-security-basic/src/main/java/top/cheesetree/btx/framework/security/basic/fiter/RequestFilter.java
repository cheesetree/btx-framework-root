package top.cheesetree.btx.framework.security.basic.fiter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.filter.OncePerRequestFilter;
import top.cheesetree.btx.framework.security.basic.interceptor.WebHttpServletRequestWrapper;
import top.cheesetree.btx.framework.security.basic.interceptor.WebHttpServletResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author van
 * @version 1.0
 * @Description TODO
 * @date 创建时间：2018年5月22日 下午3:21:48
 * @since
 */
@ConditionalOnProperty(name = "btx.security.enable_req_wrap", havingValue = "true")
@WebFilter(urlPatterns = {"/**"})
public class RequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        WebHttpServletRequestWrapper mParametersWrapper = new WebHttpServletRequestWrapper(request);
        WebHttpServletResponseWrapper resWrapper = new WebHttpServletResponseWrapper(response);
        filterChain.doFilter(mParametersWrapper, resWrapper);
        byte[] content = resWrapper.getResponseData();
        ServletOutputStream out = response.getOutputStream();
        out.write(content);
        out.flush();
    }

}
