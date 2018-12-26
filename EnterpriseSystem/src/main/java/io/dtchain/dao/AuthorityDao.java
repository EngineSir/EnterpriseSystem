package io.dtchain.dao;

import java.util.List;
import java.util.Map;

import io.dtchain.entity.EmpInfo;

public interface AuthorityDao {
	public List<EmpInfo> queryEmpInfo(Map<String,Object> map);
	public int queryCount();
}
