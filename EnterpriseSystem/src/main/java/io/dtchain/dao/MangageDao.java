package io.dtchain.dao;

import java.util.List;
import java.util.Map;

import io.dtchain.entity.EmpInfo;

public interface MangageDao {

	// 添加员工信息
	public int addEmp(EmpInfo emp);

	// 登录校验
	public EmpInfo login(String adminName);

	// 查询部门所有员工信息
	public List<EmpInfo> queryDeptEmpInfo(Map<String, Object> map);

	// 删除员工信息
	public int delEmpInfo(Map<String, Object> map);

	// 更新员工信息
	public int upEmpInfo(EmpInfo emp);

	// 查询员工部门信息
	public String queryDept(String empName);

	// 查询员工名字与工号
	public List<Map<String, Object>> queryNum();

	// 查询员工账号密码
	public EmpInfo queryInfo(String name);
	public List<EmpInfo> queryApprovalInfo(Map<String,Object> map);
	//查询员工总数
	public int queryEmpCount();
}
