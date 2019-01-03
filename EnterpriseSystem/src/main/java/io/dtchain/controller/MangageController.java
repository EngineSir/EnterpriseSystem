package io.dtchain.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.dtchain.entity.EmpInfo;
import io.dtchain.service.MangageService;
import io.dtchain.service.ResourceService;
import io.dtchain.utils.Result;

/**
 * 
 * 员工管理
 */
@Controller
@RequestMapping("/mangage")
public class MangageController {

	@Autowired
	private MangageService mangageService;
	
		/**
		 * 登录校验
		 */
	@RequestMapping(value="/login.io",method=RequestMethod.POST)
	public String login(String username,String pass ,HttpServletResponse res,HttpServletRequest req) {
		if(username==null||username.length()==0||pass==null||pass.length()==0) {
			return "redirect:login";
		}
		Result<Object> result=new Result<Object>();
		Subject subject = SecurityUtils.getSubject();
		 UsernamePasswordToken token=new UsernamePasswordToken(username,pass);
		 try {
	            subject.login(token);
	            result.setMsg("登录成功");
				result.setState(1);
	        }catch (LockedAccountException lae) {
	            token.clear();
	            result.setMsg( "用户已经被锁定不能登录，请与管理员联系！");
	            result.setState(0);
	        } catch (AuthenticationException e) {
	            token.clear();
	            result.setMsg("用户或密码不正确！");
	            result.setState(2);
	        }
		return "redirect:../index";
	}
	/**
	 * 添加员工
	 */
	@RequestMapping(value="/addEmp.io",method=RequestMethod.POST)
	@ResponseBody
	public Result<Object> addEmp(EmpInfo emp) {
		return mangageService.addEmp(emp);
	}

	// 查询员工信息
	@RequestMapping(value="/allSearch.io",method=RequestMethod.POST)
	@ResponseBody
	public Result<List<EmpInfo>> queryEmpInfo( String value, int page) {
		return mangageService.queryEmpInfo( value, page);
	}

	// 删除员工信息
	@RequestMapping(value="/delEmpInfo.io",method=RequestMethod.POST)
	@ResponseBody
	public Result<Object> delEmpInfo(String empId) {
		return mangageService.delEmpInfo(empId);
	}

	// 更新员工信息
	@RequestMapping(value="/upEmpInfo.io",method=RequestMethod.POST)
	@ResponseBody
	public Result<Object> upEmpInfo(EmpInfo emp) {
		return  mangageService.upEmpInfo(emp);
	}
	@RequestMapping(value="/authorityUrl.io",method=RequestMethod.POST)
	@ResponseBody
	public Result<Object> authorityUrl(String url){
		return mangageService.authorityUrl(url);
	}
}
