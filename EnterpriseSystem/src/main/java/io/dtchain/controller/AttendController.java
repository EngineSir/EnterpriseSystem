package io.dtchain.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.dtchain.entity.QueryRecord;
import io.dtchain.entity.ResultProce;
import io.dtchain.service.AttendService;
import io.dtchain.utils.Result;

@Controller
@RequestMapping("/attend")
public class AttendController
{
	@Resource
	private AttendService attendService;
	@RequestMapping(value="/statistic.io",method=RequestMethod.POST)
	@ResponseBody
	/*
	 * 查询工时统计表
	 */
	public Result<List<ResultProce>> statistic(QueryRecord qr){
		Result<List<ResultProce>> result=attendService.statistic(qr);
		return result;
	}
}
