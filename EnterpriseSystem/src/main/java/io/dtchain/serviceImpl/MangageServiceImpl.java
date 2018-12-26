package io.dtchain.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import io.dtchain.dao.MangageDao;
import io.dtchain.entity.EmpInfo;
import io.dtchain.service.MangageService;
import io.dtchain.utils.Result;
import io.dtchain.utils.Utils;

@Service("mangageService")
public class MangageServiceImpl implements MangageService {
	@Resource
	private MangageDao mangageDao;

	/**
	 * 添加员工信息
	 */
	public Result<Object> addEmp(EmpInfo emp) {
		Result<Object> result = new Result<Object>();
		String empId = Utils.createId();
		emp.setEmpId(empId);
		// 设置人力资源部 初始密码123456
		if (emp.getEmpDept().equals("人力资源部")) {
			emp.setEmpPass("123456");
		}
		int n = mangageDao.addEmp(emp);
		if (n == 1) {
			result.setMsg("添加员工成功");
			result.setState(1);
			result.setData(empId);
		} else {
			result.setMsg("添加员工失败");
			result.setState(0);
		}
		return result;
	}

	/**
	 * 登录校验
	 */
	public Result<Object> login(String adminName, String adminPassword, HttpServletResponse res) {
		Result<Object> result = new Result<Object>();

		/* 超级管理员帐户
		if (adminName.equals("admin") && adminPassword.equals("123456")) {
			result.setMsg("登录成功");
			result.setState(1);
			return result;
		}*/
		EmpInfo emp = mangageDao.login(adminName);
		if (emp == null) {
			result.setMsg("用户名错误");
			result.setState(0);
		} else if (emp.getEmpPass().equals(adminPassword)) {
			result.setMsg("登录成功");
			result.setState(1);
			result.setData(emp.getEmpId());
			// cookie=new Cookie("name",adminName);
			// res.addCookie(cookie);
		} else {
			result.setMsg("密码错误");
			result.setState(2);
		}
		return result;
	}

	/**
	 * 查询员工信息
	 */
	public Result<List<EmpInfo>> queryEmpInfo(String value, int page) {
		Result<List<EmpInfo>> result = new Result<List<EmpInfo>>();
		List<EmpInfo> list = new ArrayList<EmpInfo>();
		Map<String, Object> map = new HashMap<String, Object>();
		int begin = (page - 1) * 6;
		map.put("begin", begin);
		map.put("info", value);
		list = mangageDao.queryDeptEmpInfo(map);
	
		result.setData(list);
		result.setMsg("查询成功");
		result.setState(1);
		return result;
	}

	/**
	 * 删除员工信息
	 */
	public Result<Object> delEmpInfo(String empId) {
		Result<Object> result = new Result<Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("empId", empId);
		int n = mangageDao.delEmpInfo(map);
		if (n > 0) {
			result.setMsg("删除信息成功");
			result.setState(1);
		} else {
			result.setMsg("删除信息是失败");
			result.setState(0);
		}
		return result;
	}

	/**
	 * 更新员工信息
	 */
	public Result<Object> upEmpInfo(EmpInfo emp) {
		Result<Object> result = new Result<Object>();
		if (emp.getEmpDept().equals("人力资源部")) {
			emp.setEmpPass("123456");
		} else {
			emp.setEmpPass("");
		}
		int n = mangageDao.upEmpInfo(emp);
		if (n > 0) {
			result.setMsg("更新信息成功");
			result.setState(1);
		} else {
			result.setMsg("更新信息失败");
			result.setState(0);
		}
		return result;
	}
}
