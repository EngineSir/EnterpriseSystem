package io.dtchain.service;

import java.util.List;
import java.util.Map;

import io.dtchain.entity.Resource;
import io.dtchain.utils.Result;

public interface ResourceService {
	public List<Resource> loadUserResources(Map<String, Object> map);

	public List<Resource> queryAll();
}
