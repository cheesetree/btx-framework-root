package top.cheesetree.btx.framework.security.shiro.pam;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authc.pam.ShortCircuitIterationException;
import org.apache.shiro.realm.Realm;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author van
 * @date 2022/11/17 14:21
 * @description TODO
 */
@Slf4j
public class BtxModularRealmAuthenticator extends ModularRealmAuthenticator {

    @Override
    protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms, AuthenticationToken token) {
        AuthenticationStrategy strategy = this.getAuthenticationStrategy();
        AuthenticationInfo aggregate = strategy.beforeAllAttempts(realms, token);
        if (log.isTraceEnabled()) {
            log.trace("Iterating through {} realms for PAM authentication", realms.size());
        }

        Iterator var5 = realms.iterator();

        while (var5.hasNext()) {
            Realm realm = (Realm) var5.next();

            try {
                aggregate = strategy.beforeAttempt(realm, token, aggregate);
            } catch (ShortCircuitIterationException var11) {
                break;
            }

            if (realm.supports(token)) {
                log.trace("Attempting to authenticate token [{}] using realm [{}]", token, realm);
                AuthenticationInfo info = null;
                Throwable t = null;

                try {
                    info = realm.getAuthenticationInfo(token);
                } catch (Throwable var12) {
                    t = var12;
                    if (log.isDebugEnabled()) {
                        String msg = "Realm [" + realm + "] threw an exception during a multi-realm authentication " +
                                "attempt:";
                        log.debug(msg, var12);
                    }
                }

                if (t instanceof AccountException) {
                    throw (AuthenticationException) t;
                } else if (t instanceof AuthenticationException) {
                    throw (AuthenticationException) t;
                }

                aggregate = strategy.afterAttempt(realm, token, info, aggregate, t);
            } else {
                log.debug("Realm [{}] does not support token {}.  Skipping realm.", realm, token);
            }
        }

        aggregate = strategy.afterAllAttempts(token, aggregate);
        return aggregate;
    }

}
