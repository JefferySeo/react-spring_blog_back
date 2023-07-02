package net.musecom.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebFilter("/*")
public class AuthFilter implements Filter {
	public AuthFilter() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		String action = req.getServletPath();
		
		if("/".equals(action) || "/login".equals(action) || "/login.jsp".equals(action) || "/blist".equals(action) ) {
			chain.doFilter(request, response);
		}else {
			Object isLoggedObj = req.getSession().getAttribute("isLogged");
			if(isLoggedObj != null) {
				boolean isLogged = (boolean) isLoggedObj;
				if(isLogged) {
					chain.doFilter(request, response);
					return;
				}
				String path = req.getContextPath() + "/";
				res.sendRedirect(path);
			}
		}
		
		
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
