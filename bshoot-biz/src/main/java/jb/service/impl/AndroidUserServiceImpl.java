package jb.service.impl;

import component.redis.Namespace;
import component.redis.service.RedisServiceImpl;
import component.redis.service.RedisUserServiceImpl;
import jb.interceptors.TokenManage;
import jb.interceptors.TokenWrap;
import jb.service.UserServiceI;
import jb.util.IpUtil;
import org.androidpn.server.model.User;
import org.androidpn.server.service.UserExistsException;
import org.androidpn.server.service.UserNotFoundException;
import org.androidpn.server.service.UserService;
import org.androidpn.server.xmpp.session.ClientSession;
import org.androidpn.server.xmpp.session.SessionManager;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AndroidUserServiceImpl implements UserService {
	private UserServiceI userService;
	protected SessionManager sessionManager;
	private RedisUserServiceImpl redisUserService;
	public AndroidUserServiceImpl(){
		sessionManager = SessionManager.getInstance();
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		userService = wac.getBean(UserServiceI.class);
		redisUserService = wac.getBean(RedisUserServiceImpl.class);
	}

	@Override
	public User getUser(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByUsername(String u) throws UserNotFoundException {
		User user = new User();
		user.setUsername(u);
		user.setPassword("123456");
		return user;
	}
	
	
	public User getUserByUsername(String u,String psd) throws UserNotFoundException {		
		jb.pageModel.User user1 = new jb.pageModel.User();
		user1.setName(u);
		user1.setPwd(psd);
		User user = new User();
		user.setUsername(u);
		if(TokenManage.DEFAULT_TOKEN.equals(psd)){
			user.setPassword(psd);
			return user;
		}
		TokenWrap tokenWrap = redisUserService.getToken(psd);
		if (tokenWrap != null && tokenWrap.getUid().equals(u)) {
			user.setPassword(psd);
			tokenWrap.setServerHost(IpUtil.getLocalHost());
			redisUserService.setUserConnect(u,IpUtil.getLocalHost());
			return user;
		}
		return user;
		
	}
	
	@SuppressWarnings("unused")
	@Override
	public List<User> getUsers() {
		Collection<ClientSession> sessions = sessionManager.getSessions();
		List<User> list = new ArrayList<User>();
		/**/
		return list;
	}

	
	@Override
	public void removeUser(Long arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public User saveUser(User u) throws UserExistsException {
		// TODO Auto-generated method stub
		//System.out.println("11111111111111");
		return null;
	}
}
