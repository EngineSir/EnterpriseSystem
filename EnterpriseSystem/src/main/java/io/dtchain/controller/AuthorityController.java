package io.dtchain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.dtchain.entity.EmpInfo;
import io.dtchain.service.AuthorityService;
import io.dtchain.utils.Result;

@Controller
@RequestMapping("/authority")
public class AuthorityController {

	@Autowired
	private AuthorityService authorityService;
	@RequestMapping("/empInfo.io")
	@ResponseBody
	public Result<List<EmpInfo>> queryEmpInfo(Integer page){
		return authorityService.queryEmpInfo(page);
	}
	@RequestMapping("/queryCount.io")
	@ResponseBody
	public Result<Object> queryCount(){
		return authorityService.queryCount();
	}
	
}
