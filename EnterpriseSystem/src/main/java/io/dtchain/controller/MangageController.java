package io.dtchain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.dtchain.entity.EmpInfo;
import io.dtchain.service.AuthorityService;
import io.dtchain.service.MangageService;
import io.dtchain.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value="/mangage")
@Api(value = "/mangage", description = "员工管理相关接口")
@CrossOrigin
public class MangageController {

	@Autowired
	private MangageService mangageService;
	@Autowired
	private AuthorityService authorityService;
	
	@ApiOperation(value = "登陆校验")
	@PostMapping(value="/login.io")
	@ResponseBody
	public Result<Object> login(@ApiParam(value = "用户名", required = true) @RequestParam(value = "username")String username,
					   @ApiParam(value = "密码", required = true) @RequestParam(value = "pass")String pass) {
		return mangageService.login(username, pass);
	}
	 
	 
	@ApiOperation(value = "添加员工")
	@PostMapping(value="/addEmp.io")
	@ResponseBody
	public Result<Object> addEmp(@ApiParam(value = "名字", required = true) @RequestParam(value = "empName") String empName,
			 					 @ApiParam(value = "部门", required = true) @RequestParam(value = "empDept") String empDept,
			 					 @ApiParam(value = "工号", required = true) @RequestParam(value = "empNum") String empNum,
			 					 @ApiParam(value = "手机号码", required = true) @RequestParam(value = "empPhone") String empPhone,
			 					 @ApiParam(value = "性别", required = true) @RequestParam(value = "empSex") String empSex,
			 					 @ApiParam(value = "邮箱", required = true) @RequestParam(value = "empMail") String empMail) {
		EmpInfo emp=new EmpInfo();
		emp.setEmpDept(empDept);
		emp.setEmpMail(empMail);
		emp.setEmpName(empName);
		emp.setEmpNum(empNum);
		emp.setEmpPhone(empPhone);
		emp.setEmpSex(empSex);
		return mangageService.addEmp(emp);
	}

	@ApiOperation(value = "查询部门员工信息")
	@GetMapping(value="/allSearch.io")
	@ResponseBody
	public Result<List<EmpInfo>> queryDeptEmpInfo(@ApiParam(value = "部门名称", required = true) @RequestParam(value = "deptName") String deptName,
											      @ApiParam(value = "当前页数", required = true) @RequestParam(value = "page") int page) {
		return mangageService.queryDeptEmpInfo( deptName, page);
	}

	@ApiOperation(value = "删除员工信息")
	@DeleteMapping(value = "/delEmpInfo.io")
	@ResponseBody
	public Result<Object> delEmpInfo(@ApiParam(value = "员工id", required = true)  @RequestBody String empId) {
		return mangageService.delEmpInfo(empId);
	}

	@ApiOperation(value = "修改员工信息")
	@PutMapping(value="/upEmpInfo.io")
	@ResponseBody
	public Result<Object> upEmpInfo(@ApiParam(value = "名字", required = false) @RequestParam(value = "empName") String empName,
									@ApiParam(value = "部门", required = false) @RequestParam(value = "empDept") String empDept,
									@ApiParam(value = "手机号码", required = false) @RequestParam(value = "empPhone") String empPhone,
									@ApiParam(value = "性别", required = false) @RequestParam(value = "empSex") String empSex,
									@ApiParam(value = "工号", required = false) @RequestParam(value = "empNum") String empNum,
									@ApiParam(value = "邮箱", required = false) @RequestParam(value = "empMail") String empMail,
									@ApiParam(value = "员工id", required = true) @RequestParam(value = "empId") String empId) {
		
		
		EmpInfo emp=new EmpInfo();
		emp.setEmpDept(empDept);
		emp.setEmpMail(empMail);
		emp.setEmpName(empName);
		emp.setEmpNum(empNum);
		emp.setEmpId(empId);
		emp.setEmpPhone(empPhone);
		emp.setEmpSex(empSex);
		return  mangageService.upEmpInfo(emp);
	}
	
	@ApiOperation(value = "查询是否拥有员工管理权限")
	@GetMapping(value="/authorityUrl.io")
	@ResponseBody
	public Result<Object> authorityUrl(@ApiParam(value = "员工管理资源url", required = true)  @RequestParam(value = "url") String url){
		return authorityService.authorityUrl(url);
	}
	
	@ApiOperation(value = "查询部门员工总数")
	@GetMapping(value = "/queryCount.io")
	@ResponseBody
	public Result<Object> queryCount(@ApiParam(value = "部门名称", required = true) @RequestParam(value = "deptName") String deptName){
		return mangageService.queryCount(deptName);
	}
}
