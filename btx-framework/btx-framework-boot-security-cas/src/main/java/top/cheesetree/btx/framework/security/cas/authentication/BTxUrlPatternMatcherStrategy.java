package top.cheesetree.btx.framework.security.cas.authentication;

import org.jasig.cas.client.authentication.UrlPatternMatcherStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2021/9/6 09:22
 * @Version: 1.0
 * @Description:
 */
public class BTxUrlPatternMatcherStrategy implements UrlPatternMatcherStrategy {
    private PathMatcher pathMatcher;
    private String pathPattern;

    public BTxUrlPatternMatcherStrategy() {
        this.pathMatcher = new AntPathMatcher();
    }

    public BTxUrlPatternMatcherStrategy(PathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
    }

    public BTxUrlPatternMatcherStrategy(String pathPattern) {
        this.pathPattern = pathPattern;
    }

    @Override
    public boolean matches(String url) {
        return this.pathMatcher.match(pathPattern, url);
    }

    @Override
    public void setPattern(String s) {
        if (pathMatcher.isPattern(s)) {
            this.pathPattern = s;
        }
    }
}
