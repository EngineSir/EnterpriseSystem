package io.dtchain.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.dtchain.entity.EmpInfo;
import io.dtchain.utils.Result;

public interface MangageService {
	/**
	 * 添加员工信息
	 * 
	 * @param emp
	 * @return
	 */
	public Result<Object> addEmp(EmpInfo emp);

	/**
	 * 查询员工信息
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public Result<List<EmpInfo>> queryDeptEmpInfo(String value, int page);

	/**
	 * 删除员工信息
	 * 
	 * @param empName
	 * @param empDept
	 * @return
	 */
	public Result<Object> delEmpInfo(String empId);

	/**
	 * 更新员工信息
	 */
	public Result<Object> upEmpInfo(EmpInfo emp);

	public Result<Object> authorityUrl(String url);
	
	public String login(String username,String pass);
}
