package jb.interceptors;

import component.redis.service.RedisUserServiceImpl;
import jb.absx.F;
import jb.absx.UUID;
import jb.pageModel.SessionInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class TokenManage {
	
	public static String TOKEN_FIELD = "tokenId";
	
	private ConcurrentHashMap<String, TokenWrap> tokenMap = new ConcurrentHashMap<String, TokenWrap>();
	
	public static String DEFAULT_TOKEN = "123456789";
	/**
	 * 数据源回收，空闲期
	 */
	private long freeTime = 1000*60*60*24;

	/**
	 * 开启redis token管理
	 */
	protected boolean enableRedis = true;

	@Resource
	private RedisUserServiceImpl redisUserService;
	
	public void init(){
		new Thread("token 回收"){
			public void run(){
				while(true){
					try {
						sleep(10*1000);
						try {
							collection();
						} catch (Exception e) {
							e.printStackTrace();
						}						
					} catch (InterruptedException e) {
						break;
					}
				}
			}
		}.start();
	}
	
	public boolean validToken(String tid){
		return tokenMap.get(tid)==null?false:true;
	}
	
	public String getName(String tid){
		TokenWrap token = tokenMap.get(tid);
		String name = null;
		if(token!=null){
			name = token.getName(); 
		}
		return name;
	}
	
	public String getUid(String tid){
		TokenWrap token = tokenMap.get(tid);
		String uid = null;
		if(token!=null){
			uid = token.getUid(); 
		}
		return uid;
	}
	
	public SessionInfo getSessionInfo(HttpServletRequest request){
		String tokenId = request.getParameter(TokenManage.TOKEN_FIELD);	
		if(F.empty(tokenId)) return null;
		TokenWrap token;
		if(enableRedis){
			if(TokenManage.DEFAULT_TOKEN.equals(tokenId)){
				token = new TokenWrap(tokenId,DEFAULT_TOKEN,"测试超级管理员",this);

			}else {
				token = redisUserService.getToken(tokenId);
			}
		}else{
			token = tokenMap.get(tokenId);
		}
		if(token == null) return null;
		SessionInfo s = new SessionInfo();		
		s.setId(token.getUid());
		s.setName(token.getName());
		return s;		
	}
	
	private void initDefaultToken(){
		TokenWrap wrap = new TokenWrap(DEFAULT_TOKEN,DEFAULT_TOKEN,"测试管理员",this);
		wrap.retime();
		tokenMap.putIfAbsent(DEFAULT_TOKEN, wrap);

	}
	public String buildToken(String uid,String name){
		String tokenId = UUID.uuid();
		TokenWrap wrap = new TokenWrap(tokenId,uid,name,this);
		if(enableRedis){
			redisUserService.setToken(wrap);
		}else {
			wrap.retime();
			tokenMap.putIfAbsent(tokenId, wrap);
		}
		return tokenId;
	}

	public void refreshRedisToken(String token){
		redisUserService.refresh(token);
	}



	/**
	 * 回收空闲数据源
	 */
	private void collection() {
		synchronized (TokenManage.class) {
			long ntime = System.currentTimeMillis();
			Iterator<String> iter = tokenMap.keySet().iterator();
			String key = null;
			TokenWrap ds = null;
			while (iter.hasNext()) {
				key = iter.next();
				ds = tokenMap.get(key);
				if (ds != null) {
					if (ntime - ds.getCtime() > freeTime) {
						if (key.equals(DEFAULT_TOKEN)) continue;
						tokenMap.remove(key);
						iter.remove();
					}
				}
			}
		}
	}
}
