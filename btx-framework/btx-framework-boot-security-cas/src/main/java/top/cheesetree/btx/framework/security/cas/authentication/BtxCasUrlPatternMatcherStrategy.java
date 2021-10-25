package top.cheesetree.btx.framework.security.cas.authentication;

import org.jasig.cas.client.authentication.UrlPatternMatcherStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Arrays;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2021/9/6 09:22
 * @Version: 1.0
 * @Description:
 */
public class BtxCasUrlPatternMatcherStrategy implements UrlPatternMatcherStrategy {
    private PathMatcher pathMatcher;
    private String[] pathPattern;

    public BtxCasUrlPatternMatcherStrategy() {
        this.pathMatcher = new AntPathMatcher();
    }

    public BtxCasUrlPatternMatcherStrategy(PathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
    }

    public BtxCasUrlPatternMatcherStrategy(String pathPattern) {
        this.pathPattern = pathPattern.split("\\|");
    }

    @Override
    public boolean matches(String url) {
        return Arrays.stream(pathPattern).anyMatch(p -> this.pathMatcher.match(p, url));
    }

    @Override
    public void setPattern(String s) {
        if (pathMatcher.isPattern(s)) {
            this.pathPattern = s.split("\\|");
        }
    }
}
