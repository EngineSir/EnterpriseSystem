package io.dtchain.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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

	public Result<Object> addEmp(EmpInfo emp) {
		Result<Object> result = new Result<Object>();
		String empId = Utils.createId();
		emp.setEmpId(empId);
		emp.setEmpPass(Utils.Md5(emp.getEmpName(), emp.getEmpNum()));
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

	public Result<List<EmpInfo>> queryDeptEmpInfo(String deptName, int page) {
		Result<List<EmpInfo>> result = new Result<List<EmpInfo>>();
		List<EmpInfo> list = new ArrayList<EmpInfo>();
		Map<String, Object> map = new HashMap<String, Object>();
		int begin = (page - 1) * 6;
		map.put("begin", begin);
		map.put("info", deptName);
		list = mangageDao.queryDeptEmpInfo(map);

		result.setData(list);
		result.setMsg("查询成功");
		result.setState(1);
		return result;
	}

	public Result<Object> delEmpInfo(String empId) {
		Result<Object> result = new Result<Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("empId", empId.substring(empId.indexOf("=")+1));
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

	public Result<Object> upEmpInfo(EmpInfo emp) {
		Result<Object> result = new Result<Object>();
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

	@Override
	public Result<Object> authorityUrl(String url) {
		Result<Object> result = new Result<Object>();
		String getAuthotity = (String) SecurityUtils.getSubject().getSession().getAttribute(url);
		if (getAuthotity != null && getAuthotity.length() > 0) {
			result.setMsg("拥有该权限");
			result.setState(1);
		} else {
			result.setMsg("没有改权限");
			result.setState(0);
		}
		return result;
	}

	@Override
	public String login(String username, String pass) {
		if(username==null||username.length()==0||pass==null||pass.length()==0) {
			return "redirect:login";
		}
		Subject subject = SecurityUtils.getSubject();
		 UsernamePasswordToken token=new UsernamePasswordToken(username,pass);
		 try {
	            subject.login(token);
	        }catch (LockedAccountException lae) {
	            token.clear();
	        } catch (AuthenticationException e) {
	            token.clear();
	        }
		return "redirect:../index";
	}

	@Override
	public Result<List<EmpInfo>> queryApprovalInfo(int page,int limit) {
		page=page<1?1:page;
		limit=mangageDao.queryEmpCount();
		
		int pageNum=(page-1)*limit;
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("page", pageNum);
		map.put("limit", limit);
		List<EmpInfo> list=mangageDao.queryApprovalInfo(map);
		Result<List<EmpInfo>> result=new Result<List<EmpInfo>>();
		result.setData(list);
		result.setState(1);
		result.setCount(limit);
		return result;
	}
}
