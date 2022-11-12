package top.cheesetree.btx.framework.security.shiro.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.SneakyThrows;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import top.cheesetree.btx.framework.core.constants.BtxConsts;
import top.cheesetree.btx.framework.core.json.CommJSON;
import top.cheesetree.btx.framework.security.constants.BtxSecurityMessage;
import top.cheesetree.btx.framework.security.shiro.subject.StatelessToken;
import top.cheesetree.btx.framework.web.util.RequestUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @author van
 * @date 2022/2/17 15:09
 * @description TODO
 */
public class BtxSecurityShiroTokenFilter extends AuthenticatingFilter {
    private String tokenKey;

    private boolean ignoreToken;

    public BtxSecurityShiroTokenFilter(String tokenKey, boolean ignoreToken) {
        this.tokenKey = tokenKey;
        this.ignoreToken = ignoreToken;
    }

    @SneakyThrows
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return (ignoreToken || StringUtils.hasLength(getToken((HttpServletRequest) request))) && super.executeLogin(request, response);
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        StatelessToken token = null;
        String t = getToken((HttpServletRequest) servletRequest);
        if (ignoreToken || StringUtils.hasLength(t)) {
            token = new StatelessToken(t, "");
        }
        return token;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        if (RequestUtil.isAjaxRequest(req)) {
            HttpServletResponse rep = (HttpServletResponse) response;
            rep.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            rep.setContentType(MediaType.APPLICATION_JSON_VALUE);
            rep.setCharacterEncoding(BtxConsts.DEF_ENCODE.toString());
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(JSON.toJSONBytes(new CommJSON(BtxSecurityMessage.SECURIT_UNLOGIN_ERROR),
                    SerializerFeature.WriteMapNullValue));
        }
        return false;
    }


    private String getToken(HttpServletRequest request) {
        String tk = request.getHeader(tokenKey);
        if (!StringUtils.hasText(tk)) {
            tk = request.getParameter(tokenKey);
        }
        return tk;

    }
}
