package com.hundsun.hscar.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hundsun.hscar.dao.TokenDao;
import com.hundsun.hscar.entity.TokenEntity;
import com.hundsun.hscar.service.api.ITokenService;

/**
 * 用户Token
 * 
 * @author zhangmm
 * @email phoenix122411@126.com
 * @date 2017-07-16
 */
@Service("tokenService")
public class TokenService implements ITokenService {
	
	@Autowired
	private TokenDao tokenDao;
	
	private final static int EXPIRE = 3600 * 12; // 12小时后过期
	
	@Override
	public TokenEntity queryObjectById(Long userId){
		TokenEntity tokenEntity = new TokenEntity();
		tokenEntity.setUserId(userId);
		return tokenDao.queryObject(tokenEntity);
	}
	
	@Override
	public TokenEntity queryObjectByToken(String token) {
		TokenEntity tokenEntity = new TokenEntity();
		tokenEntity.setToken(token);
		return tokenDao.queryObject(tokenEntity);
	}
	
	@Override
	public TokenEntity queryObject(TokenEntity tokenEntity){
		return tokenDao.queryObject(tokenEntity);
	}
	
	@Override
	public List<TokenEntity> queryList(Map<String, Object> map){
		return tokenDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tokenDao.queryTotal(map);
	}
	
	@Override
	public void save(TokenEntity tokenEntity){
		tokenDao.save(tokenEntity);
	}
	
	@Override
	public void update(TokenEntity tokenEntity){
		tokenDao.update(tokenEntity);
	}
	
	@Override
	public void delete(Long userId){
		tokenDao.delete(userId);
	}
	
	@Override
	public void deleteBatch(Long[] userIds){
		tokenDao.deleteBatch(userIds);
	}
	
	@Override
	public Map<String, Object> createToken(long userId) {
		//生成一个token
		String token = UUID.randomUUID().toString();
		
		//当前时间
		Date now = new Date();

		//过期时间
		Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

		//判断是否生成过token
		TokenEntity tokenEntity = queryObjectById(userId);
		if(tokenEntity == null){
			tokenEntity = new TokenEntity();
			tokenEntity.setUserId(userId);
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			//保存token
			save(tokenEntity);
		}else{
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			//更新token
			update(tokenEntity);
		}

		Map<String, Object> map = new HashMap<>();
		map.put("token", token);
		map.put("expire", EXPIRE);

		return map;
	}
	
}
