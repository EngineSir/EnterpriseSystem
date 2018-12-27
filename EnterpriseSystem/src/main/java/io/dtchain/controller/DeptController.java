package io.dtchain.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.dtchain.entity.DeptInfo;
import io.dtchain.service.DeptService;
import io.dtchain.service.MangageService;
import io.dtchain.utils.Result;
/**
 * 部门的增删改查Controller
 *
 */
@Controller
@RequestMapping("/dept")
public class DeptController
{
	@Resource
	private DeptService deptService;
	@Autowired
	private MangageService mangageService;
	
	@RequestMapping("/addDept.io")
	@ResponseBody
	public Result<Object> addDept(String deptName){
		System.out.println(deptName);
		Result<Object> result=deptService.addDept(deptName);
		return result;
	}
	@RequestMapping("/delDept.io")
	@ResponseBody
	public Result<Object> delDept(String id){
		Result<Object> result=deptService.delDept(id);
		return result;
	}
	@RequestMapping("/queryDept.io")
	@ResponseBody
	public Result<List<DeptInfo>> queryDept(){
		Result<List<DeptInfo>> result=deptService.queryDept();
		return result;
	}
	@RequestMapping("/upDept.io")
	@ResponseBody
	public Result<Object> upDept(DeptInfo dept){
		Result<Object> result=deptService.upDept(dept);
		return result;
	}
	@RequestMapping("/authorityUrl.io")
	@ResponseBody
	public Result<Object> authorityUrl(String url){
		return mangageService.authorityUrl(url);
	}
}
