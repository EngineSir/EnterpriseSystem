package io.dtchain.service;

import java.util.List;

import io.dtchain.entity.EmpInfo;
import io.dtchain.utils.Result;

public interface AuthorityService {
	
	/**
	 * 查询员工信息
	 * 
	 * @param page			当前页面
	 * @return
	 */
	public Result<List<EmpInfo>> queryEmpInfo(Integer page);

	/**
	 * 查询员工总数
	 * @return
	 */
	public Result<Object> queryCount();

	/**
	 * 添加权限
	 * 
	 * @param resourceId	资源id
	 * @param empId			员工id
	 * @return
	 */
	public Result<Object> addAuthority(Integer[] resourceId, String empId);

	/**
	 * 查询权限
	 * 
	 * @param empId			员工id
	 * @return
	 */
	public Result<List<Integer>> queryAuthorityId(String empId);

}
