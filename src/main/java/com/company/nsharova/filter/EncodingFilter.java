package com.company.nsharova.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter
public class EncodingFilter implements Filter {

    private String encoding;

    public void destroy() {
//		LOG.debug("Filter destruction starts");
        // no op
//		LOG.debug("Filter destruction finished");
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
//		LOG.debug("Filter starts");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
//		LOG.trace("Request uri --> " + httpRequest.getRequestURI());

        String requestEncoding = request.getCharacterEncoding();
        if (requestEncoding == null) {
//			LOG.trace("Request encoding = null, set encoding --> " + encoding);
            request.setCharacterEncoding(encoding);
        }

        chain.doFilter(request, response);

//		LOG.debug("Filter finished");
    }

    public void init(FilterConfig fConfig) throws ServletException {
//		LOG.debug("Filter initialization starts");
        encoding = fConfig.getInitParameter("encoding");
//		LOG.trace("Encoding from web.xml --> " + encoding);
//		LOG.debug("Filter initialization finished");
    }

}