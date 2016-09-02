package com.rendp.auth.context;

import com.rendp.auth.entity.User;

public class UserContext {

	private static ThreadLocal<UserContext> tl = new ThreadLocal<>();
	
	private User user;
	
	private UserContext(User user) {
		this.user = user;
	}
	
	static void setCurrent(User user) {
		tl.set(new UserContext(user));
	}
	
	public static UserContext getCurrent() {
		return tl.get();
	}
	
	public User getUser() {
		return user;
	}
}
