package top.cheesetree.btx.framework.security.shiro.support.cas;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.jasig.cas.client.Protocol;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.authentication.AttributePrincipalImpl;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.StringUtils;
import top.cheesetree.btx.framework.core.json.CommJSON;
import top.cheesetree.btx.framework.security.IBtxSecurityPermissionService;
import top.cheesetree.btx.framework.security.IBtxSecurityUserService;
import top.cheesetree.btx.framework.security.model.SecurityFuncDTO;
import top.cheesetree.btx.framework.security.model.SecurityRoleDTO;
import top.cheesetree.btx.framework.security.shiro.matcher.BtxNoAuthCredentialsMatcher;
import top.cheesetree.btx.framework.security.shiro.model.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author van
 * @date 2022/2/21 09:20
 * @description TODO
 */

@Slf4j
public class BtxSecurityCasAuthorizingRealm extends AuthorizingRealm {
    private TicketValidator ticketValidator;
    private String serverName;
    private Protocol protocol;

    @Autowired
    @Lazy
    IBtxSecurityPermissionService<? extends BtxShiroSecurityMenuDTO, ? extends BtxShiroSecurityFuncDTO, ?
            extends BtxShiroSecurityRoleDTO> btxSecurityPermissionService;
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;

    @Autowired
    @Lazy
    private IBtxSecurityUserService<? extends BtxShiroSecurityUserDTO> btxSecurityUserService;
    @Autowired
    private BtxShiroCasProperties btxShiroCasProperties;

    public BtxSecurityCasAuthorizingRealm(BtxNoAuthCredentialsMatcher btxNoAuthCredentialsMatcher,
                                          String casServerUrlPrefix,
                                          Protocol validationType, String serverName) {
        super(btxNoAuthCredentialsMatcher);
        switch (validationType) {
            case SAML11:
                this.ticketValidator = new Saml11TicketValidator(casServerUrlPrefix);
                break;
            case CAS1:
            case CAS2:
                this.ticketValidator = new Cas20ServiceTicketValidator(casServerUrlPrefix);
                break;
            case CAS3:
            default:
                this.ticketValidator = new Cas30ServiceTicketValidator(casServerUrlPrefix);
                break;
        }

        this.serverName = serverName;
        this.setAuthenticationTokenClass(CasToken.class);
        this.protocol = validationType;

        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token != null && token.getClass().isAssignableFrom(CasToken.class);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        Object principal = principalCollection.getPrimaryPrincipal();
        String uid;
        if (principal instanceof BtxShiroSecurityAuthUserDTO) {
            BtxShiroSecurityAuthUserDTO p = (BtxShiroSecurityAuthUserDTO) principal;
            if (p.getUser() != null) {
                uid = p.getUser().getUid();
            } else {
                uid = ((AttributePrincipal) p.getAuthinfo()).getName();
            }
            List<? extends SecurityFuncDTO> funcs;
            List<? extends SecurityRoleDTO> roles;

            BtxShiroSecurityAuthUserDTO u = (BtxShiroSecurityAuthUserDTO) principalCollection.getPrimaryPrincipal();

            if (u.getUser().getFuncs() != null && !u.getUser().getFuncs().isEmpty()) {
                funcs = u.getUser().getFuncs();
            } else {
                funcs = btxSecurityPermissionService.getFunc(u.getUser().getLoginName());
            }

            if (u.getUser().getRoles() != null && !u.getUser().getRoles().isEmpty()) {
                roles = u.getUser().getRoles();
            } else {
                roles = btxSecurityPermissionService.getRole(u.getUser().getLoginName());
            }

            if (funcs != null) {
                Set<String> stringPermissions = new HashSet<>();
                funcs.forEach((SecurityFuncDTO f) -> {
                    stringPermissions.add(f.getFuncCode());
                });
                info.setStringPermissions(stringPermissions);
            }

            if (roles != null) {
                Set<String> stringRoles = new HashSet<>();
                roles.forEach((SecurityRoleDTO f) -> {
                    stringRoles.add(f.getRoleCode());
                });
                info.setRoles(stringRoles);
            }

        }

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        CasToken casToken = (CasToken) token;
        if (token == null) {
            log.warn("cas token is null");
            return null;
        }

        AttributePrincipal casPrincipal = null;
        String ticket = (String) casToken.getCredentials();

        log.debug("cas ticket->{}", ticket);

        if (StringUtils.hasText(ticket) && !btxShiroCasProperties.getSkipTicketValidation()) {
            try {
                Assertion casAssertion = ticketValidator.validate(ticket, CommonUtils.constructServiceUrl(request,
                        response, null, this.serverName, this.protocol.getServiceParameterName(),
                        this.protocol.getArtifactParameterName(), true));
                casPrincipal = casAssertion.getPrincipal();
                log.debug("cas ticket validate success->{}", JSON.toJSON(casAssertion));
            } catch (TicketValidationException e) {
                throw new CasAuthenticationException("Unable to validate ticket [" + ticket + "]", e);
            }
        } else if (StringUtils.hasText(casToken.getUserId())) {
            casPrincipal = new AttributePrincipalImpl(casToken.getUserId());
        } else {
            casPrincipal = new AttributePrincipalImpl(btxShiroCasProperties.getDevUserName());
        }

        if (casPrincipal != null && StringUtils.hasLength(casPrincipal.getName())) {
            String userId = casPrincipal.getName();

            if (btxSecurityUserService != null) {
                CommJSON<? extends BtxShiroSecurityUserDTO> ret = btxSecurityUserService.getUserInfo(userId);
                log.debug("cas ticket to userinfo->", JSON.toJSON(ret));
                if (ret.checkSuc()) {
                    BtxShiroSecurityAuthUserDTO u = new BtxShiroSecurityAuthUserDTO();
                    u.setUser(ret.getResult());
                    return new SimpleAuthenticationInfo(new SimplePrincipalCollection(u, "user"),
                            ticket);
                } else {
                    throw new AuthenticationException(ret.getMsg());
                }
            } else {
                BtxShiroSecurityAuthUserDTO u = new BtxShiroSecurityAuthUserDTO();
                u.setAuthinfo(casPrincipal);
                return new SimpleAuthenticationInfo(new SimplePrincipalCollection(u, "user"),
                        ticket);
            }
        } else {
            return null;
        }

    }
}
