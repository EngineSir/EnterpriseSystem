package io.dtchain.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.dtchain.entity.EmpInfo;
import io.dtchain.utils.Result;

public interface MangageService
{
	/**
	 * 添加员工信息
	 * @param emp
	 * @return
	 */
	public Result<Object> addEmp(EmpInfo emp);
	/**
	 * 登录校验
	 * @param adminName
	 * @param adminPassword
	 * @return
	 */
	public Result<Object> login(String adminName,String adminPassword,HttpServletResponse res);
	/**
	 * 查询员工信息
	 * @param name
	 * @param value
	 * @return
	 */
	public Result<List<EmpInfo>> queryEmpInfo(String value,int page);
	/**
	 * 删除员工信息
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
}
