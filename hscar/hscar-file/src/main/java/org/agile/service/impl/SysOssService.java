package org.agile.service.impl;

import java.util.List;
import java.util.Map;

import org.agile.dao.SysOssDao;
import org.agile.entity.SysOssEntity;
import org.agile.service.api.ISysOssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysOssService")
public class SysOssService implements ISysOssService {
	@Autowired
	private SysOssDao sysOssDao;
	
	@Override
	public SysOssEntity queryObject(Long id){
		return sysOssDao.queryObject(id);
	}
	
	@Override
	public List<SysOssEntity> queryList(Map<String, Object> map){
		return sysOssDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysOssDao.queryTotal(map);
	}
	
	@Override
	public void save(SysOssEntity sysOss){
		sysOssDao.save(sysOss);
	}
	
	@Override
	public void update(SysOssEntity sysOss){
		sysOssDao.update(sysOss);
	}
	
	@Override
	public void delete(Long id){
		sysOssDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		sysOssDao.deleteBatch(ids);
	}
	
}
