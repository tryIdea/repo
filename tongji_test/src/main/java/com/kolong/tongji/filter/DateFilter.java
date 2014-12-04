package com.kolong.tongji.filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.kolong.tongji.util.Constants;

public class DateFilter implements Filter {
	private static Logger logger = Logger.getLogger(DateFilter.class);

	@Override
	public void init(FilterConfig filterconfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterchain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		String monitorCircle = request.getParameter("monitorCircle");
		if(monitorCircle != null) {
			Constants.MONITORCIRCLE = Integer.parseInt(monitorCircle);
		}
		
		String remoteHost = request.getRemoteHost();
		logger.warn("remoteHost---->" + remoteHost);
		filterchain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}

}
