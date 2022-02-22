package top.cheesetree.btx.framework.security.shiro.support.cas;

import org.apache.shiro.authc.*;
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
import top.cheesetree.btx.framework.security.model.SecurityAuthUserDTO;
import top.cheesetree.btx.framework.security.model.SecurityFuncDTO;
import top.cheesetree.btx.framework.security.model.SecurityMenuDTO;
import top.cheesetree.btx.framework.security.model.SecurityUserDTO;
import top.cheesetree.btx.framework.security.shiro.matcher.BtxNoAuthCredentialsMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * @author van
 * @date 2022/2/21 09:20
 * @description TODO
 */
public class BtxSecurityCasAuthorizingRealm extends AuthorizingRealm {
    private TicketValidator ticketValidator;
    private String serverName;
    private Protocol protocol;

    @Autowired
    @Lazy
    IBtxSecurityPermissionService<SecurityMenuDTO, SecurityFuncDTO> btxSecurityPermissionService;
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;

    @Autowired(required = false)
    @Lazy
    private IBtxSecurityUserService btxSecurityUserService;

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
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token != null && token.getClass().isAssignableFrom(CasToken.class);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> stringPermissions = new HashSet<>();

        Object principal = principalCollection.getPrimaryPrincipal();
        String uid;
        if (principal instanceof SecurityAuthUserDTO) {
            SecurityAuthUserDTO p = (SecurityAuthUserDTO) principal;
            if (p.getUser() != null) {
                uid = p.getUser().getUid();
            } else {
                uid = ((AttributePrincipal) p.getAuthinfo()).getName();
            }
            btxSecurityPermissionService.getFunc(uid).forEach((SecurityFuncDTO f) -> {
                stringPermissions.add(f.getFuncCode());
            });
            info.setStringPermissions(stringPermissions);
        }

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        CasToken casToken = (CasToken) token;
        if (token == null) {
            return null;
        }

        AttributePrincipal casPrincipal = null;
        String ticket = (String) casToken.getCredentials();
        if (StringUtils.hasText(ticket)) {
            try {
                Assertion casAssertion = ticketValidator.validate(ticket, CommonUtils.constructServiceUrl(request,
                        response, null, this.serverName, this.protocol.getServiceParameterName(),
                        this.protocol.getArtifactParameterName(), true));
                casPrincipal = casAssertion.getPrincipal();
            } catch (TicketValidationException e) {
                throw new CasAuthenticationException("Unable to validate ticket [" + ticket + "]", e);
            }
        } else if (StringUtils.hasText(casToken.getUserId())) {
            casPrincipal = new AttributePrincipalImpl(casToken.getUserId());
        }

        if (casPrincipal != null) {
            String userId = casPrincipal.getName();

            if (btxSecurityUserService != null) {
                CommJSON<SecurityUserDTO> ret = btxSecurityUserService.getUserInfo(userId);
                if (ret.checkSuc()) {
                    SecurityAuthUserDTO u = new SecurityAuthUserDTO();
                    u.setUser(ret.getResult());
                    return new SimpleAuthenticationInfo(new SimplePrincipalCollection(u, "user"),
                            ticket);
                } else {
                    throw new AccountException(ret.getMsg());
                }
            } else {
                SecurityAuthUserDTO u = new SecurityAuthUserDTO();
                u.setAuthinfo(casPrincipal);
                return new SimpleAuthenticationInfo(new SimplePrincipalCollection(u, "user"),
                        ticket);
            }
        } else {
            return null;
        }

    }
}
