package io.dtchain.dao;

import java.util.List;
import java.util.Map;

import io.dtchain.entity.DeptInfo;

public interface DeptDao
{
	/*
	 * 添加部门
	 */
	public int addDept(Map<String,String> map);
	/*
	 * 删除部门
	 */
	public int delDept(String id);
	/*
	 * 查询部门
	 */
	public List<DeptInfo> queryDept();
	/*
	 * 修改部门   部门表
	 */
	public int upDept(DeptInfo dept);
	
}
