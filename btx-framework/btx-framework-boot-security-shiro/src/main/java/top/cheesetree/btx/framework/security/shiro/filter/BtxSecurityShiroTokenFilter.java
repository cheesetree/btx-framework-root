package top.cheesetree.btx.framework.security.shiro.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
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

    public BtxSecurityShiroTokenFilter(String tokenKey) {
        this.tokenKey = tokenKey;
    }

//    @Override
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//        return ((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name());
//    }

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        StatelessToken token = null;
        String t = getToken((HttpServletRequest) servletRequest);
        if (StringUtils.hasLength(t)) {
            token = new StatelessToken(t);
        }
        return token;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (!StringUtils.hasLength(getToken((HttpServletRequest) request))) {
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
        } else {
            return super.executeLogin(request, response);
        }
    }

    private String getToken(HttpServletRequest request) {
        return request.getHeader(tokenKey);
    }
}
