package cn.luminous.squab.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 没有，先去掉
 */
//@Component
//@WebFilter(urlPatterns = "/**", filterName = "disablejsession")
public class Disablejsession implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // System.out.println("3.过滤器MyDisableUrlSessionFilter");
        // skip non-http requests
        if (!(request instanceof HttpServletRequest)) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // clear session if session id in URL
        if (httpRequest.isRequestedSessionIdFromURL()) {
            HttpSession session = httpRequest.getSession();
            if (session != null)
                session.invalidate();
        }

        // wrap response to remove URL encoding
        HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper(httpResponse) {

            @Override
            public String encodeRedirectUrl(String url) {
                return url;
            }

            public String encodeRedirectURL(String url) {
                return url;
            }

            public String encodeUrl(String url) {
                return url;
            }

            public String encodeURL(String url) {
                return url;
            }
        };

        // process next request in chain
        chain.doFilter(request, wrappedResponse);

    }

    @Override
    public void destroy() {

    }
}
