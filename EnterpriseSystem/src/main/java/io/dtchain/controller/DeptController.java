package io.dtchain.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.dtchain.entity.DeptInfo;
import io.dtchain.service.DeptService;
import io.dtchain.service.MangageService;
import io.dtchain.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping("/dept")
@Api(value = "/dept", description = "部门管理相关接口")
@CrossOrigin
public class DeptController
{
	@Resource
	private DeptService deptService;
	@Autowired
	private MangageService mangageService;
	
	@ApiOperation(value = "添加部门")
	@PostMapping(value="/addDept.io")
	@ResponseBody
	public Result<Object> addDept(@ApiParam(value = "部门名称", required = true) @RequestParam(value="deptName") String deptName){
		return deptService.addDept(deptName);
	}
	 
	@ApiOperation(value = "删除部门")
	@DeleteMapping(value="/delDept.io")
	@ResponseBody
	public Result<Object> delDept(@ApiParam(value = "部门id",required=true) @RequestBody String id){
		return deptService.delDept(id);
	}
	 
	@ApiOperation(value = "查询部门")
	@GetMapping(value="/queryDept.io")
	@ResponseBody
	public Result<List<DeptInfo>> queryDept(){
		return deptService.queryDept();
	}
	 
	@ApiOperation(value = "更新部门名称")
	@PutMapping(value="/upDept.io")
	@ResponseBody
	public Result<Object> upDept(@ApiParam(value = "部门id",required=true)  @RequestParam(value="id") String id,
								 @ApiParam(value = "部门名称",required=true)  @RequestParam(value="deptName") String deptName){
		System.out.println(deptName);
		 DeptInfo dept=new DeptInfo();
		 dept.setDeptName(deptName);
		 dept.setId(id);
		return deptService.upDept(dept);
	}
	 
	@ApiOperation(value = "查询是否拥有部门权限")
	@GetMapping(value="/authorityUrl.io")
	@ResponseBody
	public Result<Object> authorityUrl(@ApiParam(value = "部门资源url",required=true)  @RequestParam(value="url") String url){
		return mangageService.authorityUrl(url);
	}
}
