package io.dtchain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.dtchain.entity.EmpInfo;
import io.dtchain.service.AuthorityService;
import io.dtchain.utils.Result;

@Controller
@RequestMapping("/authority")
public class AuthorityController {

	@Autowired
	private AuthorityService authorityService;
	@RequestMapping(value="/empInfo.io",method=RequestMethod.POST)
	@ResponseBody
	public Result<List<EmpInfo>> queryEmpInfo(Integer page){
		return authorityService.queryEmpInfo(page);
	}
	@RequestMapping(value="/queryCount.io",method=RequestMethod.POST)
	@ResponseBody
	public Result<Object> queryCount(){
		return authorityService.queryCount();
	}
	@RequestMapping(value="/addAuthority.io",method=RequestMethod.POST)
	@ResponseBody
	public Result<Object> addAuthority(@RequestParam(value="resourceId[]") Integer[] resourceId,String empId){
		
		return authorityService.addAuthority(resourceId, empId);
	}
	@RequestMapping(value="/queryAuthorityId.io",method=RequestMethod.POST)
	@ResponseBody
	public Result<List<Integer>> queryAuthorityId(String empId){
		return authorityService.queryAuthorityId(empId);
	}
}
