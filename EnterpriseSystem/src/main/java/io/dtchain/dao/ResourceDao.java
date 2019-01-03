package io.dtchain.dao;

import java.util.List;
import java.util.Map;

import io.dtchain.entity.Resource;

public interface ResourceDao {
	public List<Resource> loadUserResources(Map<String, Object> map);
	public List<Resource> queryAll();
	public List<Integer> queryId(String userId);
	public List<String> queryResourceName(String userId);
}  
