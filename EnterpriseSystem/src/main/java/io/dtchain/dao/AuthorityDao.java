package io.dtchain.dao;

import java.util.List;
import java.util.Map;

import io.dtchain.entity.EmpInfo;

public interface AuthorityDao {
	
	/**
	 * 查询员工信息
	 * 
	 * @param map		当前页数
	 * @return
	 */
	public List<EmpInfo> queryEmpInfo(Map<String, Object> map);

	/**
	 * 查询员工总数
	 * 
	 * @return
	 */
	public int queryCount();

	/**
	 * 添加权限
	 * 
	 * @param map		资源id,员工id
	 * @return
	 */
	public int addAuthority(Map<String, Object> map);

	/**
	 * 删除权限
	 * 
	 * @param map		资源id,员工id
	 * @return
	 */
	public int delAuthority(Map<String, Object> map);
}
