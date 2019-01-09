package io.dtchain.dao;

import java.util.List;
import java.util.Map;

import io.dtchain.entity.DeptInfo;

public interface DeptDao {
	
	/**
	 * 添加部门
	 * 
	 * @param map		部门信息
	 * @return
	 */
	public int addDept(Map<String, String> map);

	/**
	 * 删除部门
	 * 
	 * @param id		部门id
	 * @return
	 */
	public int delDept(String id);

	/**
	 * 查询部门信息
	 * 
	 * @return
	 */
	public List<DeptInfo> queryDept();

	/**
	 * 修改部门信息
	 * 
	 * @param dept
	 * @return
	 */
	public int upDept(DeptInfo dept);

}
