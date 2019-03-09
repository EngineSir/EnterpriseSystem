package io.dtchain.service;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import io.dtchain.entity.EmpInfo;
import io.dtchain.utils.Result;

public interface MangageService {
	/**
	 * 添加员工信息
	 * 
	 * @param emp			员工信息
	 * @return
	 */
	public Result<Object> addEmp(EmpInfo emp);

	/**
	 * 查询员工信息
	 * 
	 * @param deptName		部门名称	
	 * @param page			当前页数
	 * @return
	 */
	public Result<List<EmpInfo>> queryDeptEmpInfo(String deptName, int page);

	/**
	 * 删除员工信息
	 * 
	 * @param empId			员工id
	 * @return
	 */
	public Result<Object> delEmpInfo(String empId);

	/**
	 * 更新员工信息
	 * 
	 * @param emp			员工信息
	 * @return
	 */
	public Result<Object> upEmpInfo(EmpInfo emp);

	
	
	/**
	 * 登陆校验
	 * 
	 * @param username		用户名
	 * @param pass			密码
	 * @return
	 */
	public Result<Object> login(String username,String pass);
	
	/**
	 * 查询审批人
	 * 
	 * @param page			当前页数
	 * @param limit			每页多少条
	 * @return
	 */
	public Result<List<EmpInfo>> queryApprovalInfo(int page,int limit);
	
	/**
	 * 查询部门员工总数
	 * 
	 * @param deptName		部门名称
	 * @return
	 */
	public Result<Object> queryCount(String deptName);
	
	/**
	 * 修改密码
	 * 
	 * @param oriPass		原密码
	 * @param newPass		新密码
	 * @return
	 */
	public Result<Object> updatePass(String oriPass,String newPass);
}
