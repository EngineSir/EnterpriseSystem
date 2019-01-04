package io.dtchain.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.dtchain.entity.QueryRecord;
import io.dtchain.entity.ResultProce;
import io.dtchain.service.AttendService;
import io.dtchain.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/attend")
@Api(value = "/attend", description = "考勤管理相关接口")
@CrossOrigin
public class AttendController {
	@Resource
	private AttendService attendService;

	@ApiOperation(value = "查询工时统计表")
	@GetMapping(value = "/statistic.io")
	@ResponseBody
	public Result<List<ResultProce>> statistic(@ApiParam(value = "员工名字", required = false) String empName,
											   @ApiParam(value = "部门名称", required = true) String empDept,
											   @ApiParam(value = "开始日期", required = true) String start,
											   @ApiParam(value = "结束日期", required = true) String end) {
		QueryRecord qr=new QueryRecord();
		qr.setEmpDept(empDept);
		qr.setEmpName(empName);
		qr.setEnd(end);
		qr.setStart(start);
		return attendService.statistic(qr);
	}
}
