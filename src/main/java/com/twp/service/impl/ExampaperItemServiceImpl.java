package com.twp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twp.dao.ExampaperItemDao;
import com.twp.entity.ExampaperItemEntity;
import com.twp.service.ExampaperItemService;

import java.util.List;
import java.util.Map;



@Service("exampaperItemService")
public class ExampaperItemServiceImpl implements ExampaperItemService {
	@Autowired
	private ExampaperItemDao exampaperItemDao;
	
	@Override
	public ExampaperItemEntity queryObject(Integer id){
		return exampaperItemDao.queryObject(id);
	}
	
	@Override
	public List<ExampaperItemEntity> queryList(Map<String, Object> map){
		return exampaperItemDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return exampaperItemDao.queryTotal(map);
	}
	
	@Override
	public void save(ExampaperItemEntity exampaperItem){
		exampaperItemDao.save(exampaperItem);
	}
	
	@Override
	public void update(ExampaperItemEntity exampaperItem){
		exampaperItemDao.update(exampaperItem);
	}
	
	@Override
	public void delete(Integer id){
		exampaperItemDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		exampaperItemDao.deleteBatch(ids);
	}
	
}
