package top.cheesetree.btx.framework.security.shiro.subject;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.CollectionUtils;

import java.util.Set;

/**
 * @author van
 * @date 2022/2/17 10:02
 * @description TODO
 */
public class BtxSimplePrincipalCollection extends SimplePrincipalCollection {
    private String cachedToString;

    public BtxSimplePrincipalCollection(Object principal, String realmName) {
        super(principal, realmName);
    }

    @Override
    public String toString() {
        if (this.cachedToString == null) {
            Set<Object> principals = this.asSet();
            if (!CollectionUtils.isEmpty(principals)) {
                this.cachedToString = JSON.toJSONString(principals);
            } else {
                this.cachedToString = "[]";
            }
        }

        return this.cachedToString;
    }
}
