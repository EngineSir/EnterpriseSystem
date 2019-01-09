package io.dtchain.service;

import java.util.List;

import io.dtchain.entity.DeptInfo;
import io.dtchain.utils.Result;

public interface DeptService {
	
	/**
	 * 添加部门
	 * 
	 * @param deptName		部门名称
	 * @return
	 */
	public Result<Object> addDept(String deptName);

	/**
	 * 删除部门 
	 * @param id			部门id
	 * @return
	 */
	public Result<Object> delDept(String id);

	/**
	 * 查询部门信息
	 * @return
	 */
	public Result<List<DeptInfo>> queryDept();

	/**
	 * 修改部门信息
	 * @param dept			部门信息
	 * @return
	 */
	public Result<Object> upDept(DeptInfo dept);
}
