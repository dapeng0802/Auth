package com.rendp.auth.context;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.rendp.auth.entity.User;

public class LoginUserCache {

	private static Map<Long, LoginUser> cache = new HashMap<Long, LoginUser>();
	
	/**
	 * 
	 * @param user 用户
	 * @param expire 过期时间，单位秒，如果是 30 分钟过期，即：60 * 30 = 1800
	 */
	public static void put(User user, long expire) {
		long expireTime = Calendar.getInstance().getTime().getTime() + expire * 1000;
		LoginUser loginUser = new LoginUser();
		loginUser.setUser(user);
		loginUser.setExpire(expireTime);
		cache.put(user.getId(), loginUser);
	}
	
//	public static User get(Long userId) {
//		
//	}
	
	public static void remove() {
		
	}
	
	private static class LoginUser {
		private long expire;
		private User user;
		public long getExpire() {
			return expire;
		}
		public void setExpire(long expire) {
			this.expire = expire;
		}
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}
	}
	
}
