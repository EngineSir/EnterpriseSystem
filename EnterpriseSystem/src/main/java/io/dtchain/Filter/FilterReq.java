package io.dtchain.Filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FilterReq implements Filter {
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String[] path = { "/login.html" };
		String currPath = request.getServletPath();
		int num = currPath.lastIndexOf("/");
		String Paths = currPath.substring(num);
		for (String s : path) {
			if (s.equals(Paths)) {
				chain.doFilter(request, response);
				return;
			}
		}
		boolean flog = false;
		Cookie[] cookie = request.getCookies();
		if (cookie == null) {
			response.sendRedirect("login.html");
			return;
		} else {
			for (Cookie c : cookie) {
				if (c.getName().equals("name") && c.getValue() != null) {
					flog = true;
				}
			}
		}
		if (flog) {
			chain.doFilter(request, response);
		} else {
			response.sendRedirect("login.html");
			return;
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
