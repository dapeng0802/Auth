package com.rendp.auth.context;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rendp.auth.dto.Accordion;
import com.rendp.auth.entity.User;


public class UserContextFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		ResponseContext.setCurrent(response);
		
		if(request.getRequestURI().contains("login")) {
			Cookie cookie = new Cookie("application_monitor", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}
		
		if(request.getRequestURI().contains("index.jsp")) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}
		
		if(request.getRequestURI().endsWith(".css") || request.getRequestURI().endsWith(".js")
				|| request.getRequestURI().endsWith(".jpg") || request.getRequestURI().endsWith(".gif")) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}
		
		String cookieValue = "";
		if(null != request.getCookies()) {
			for(Cookie cookie : request.getCookies()) {
				if(Objects.equals(cookie.getName(), "auth")) {
					cookieValue = cookie.getValue();
					break;
				}
			}
		}
		
		byte[] bytes = Base64.getDecoder().decode(cookieValue);
		String auth = new String(bytes);
		
		String[] array = auth.split("\\$");
		if(2 == array.length) {
			User user = LoginUserCache.get(array[0]);
			if(null == user) {
				LoginUserHelper helper = WebApplicationContext.getBean(LoginUserHelper.class);
				helper.executeLogin(array[0], array[1]);
				user = LoginUserCache.get(array[0]);
			}
			if(null != user && Objects.equals(user.getPwd(), array[1])) {
				List<Accordion> accordions = LoginUserCache.getAccordions(user.getName());
				request.setAttribute("accordions", accordions);
				UserContext.setCurrent(user);
				LoginUserCache.setCookie(user);
				filterChain.doFilter(servletRequest, servletResponse);
				return;
			} else {
				Cookie cookie = new Cookie("auth", null);
				cookie.setMaxAge(0);
				response.addCookie(cookie);
				response.sendRedirect("/index.jsp");
			}
		} else {
			Cookie cookie = new Cookie("auth", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
