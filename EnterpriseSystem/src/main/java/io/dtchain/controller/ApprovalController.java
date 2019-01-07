package io.dtchain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.dtchain.entity.EmpInfo;
import io.dtchain.entity.LeaveTable;
import io.dtchain.service.ApprovalService;
import io.dtchain.service.MangageService;
import io.dtchain.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/approval")
@Api(value = "/approval", description = "审批管理相关接口")
@CrossOrigin
public class ApprovalController {

	@Autowired
	private MangageService mangageService;
	@Autowired
	private ApprovalService approvalService;
	
	@ApiOperation(value = "查询审批人")
	@GetMapping(value = "/approval.io")
	@ResponseBody
	public Result<List<EmpInfo>> queryApprovalInfo(@ApiParam(value = "当前页数", required = true)  @RequestParam(value = "page") int page,
												   @ApiParam(value = "页面显示数据量", required = true) @RequestParam(value = "limit") int limit){
		return mangageService.queryApprovalInfo(page,limit);
	}
	
	@ApiOperation(value = "请假申请")
	@PostMapping(value = "/leaveApplication.io")
	@ResponseBody
	public Result<Object> leaveApplication(@ApiParam(value = "请假类型", required = true) @RequestParam(value = "leaveType") String leaveType,
										   @ApiParam(value = "开始时间", required = true) @RequestParam(value = "start") String start,
										   @ApiParam(value = "结束时间", required = true) @RequestParam(value = "end") String end,
										   @ApiParam(value = "请假天数", required = true) @RequestParam(value = "leaveNum") int leaveNum,
										   @ApiParam(value = "请假事由", required = true) @RequestParam(value = "leaveRegard") String leaveRegard,
										   @ApiParam(value = "审批人", required = true) @RequestParam(value = "approver") String approver){
		return approvalService.leaveApplication(leaveType, start, end, leaveNum, leaveRegard, approver);
		
	}
	
	@ApiOperation(value = "待审批总数")
	@GetMapping(value = "/queryCount.io")
	@ResponseBody
	public Result<Object> queryCount(@ApiParam(value = "审批状态  0:拒绝 1:同意 2:待审批") @RequestParam(value = "approverStatue") int approverStatue){
		return approvalService.queryCount(approverStatue);
	}
	
	@ApiOperation(value = "获取待审批记录")
	@GetMapping(value= "/getPendingApproval.io")
	@ResponseBody
	public Result<List<LeaveTable>> getPendingApproval(@ApiParam(value = "当前页数", required = true) @RequestParam(value = "page") int page,
													   @ApiParam(value = "审批状态  0:拒绝 1:同意 2:待审批  ", required = true) @RequestParam(value = "approverStatue") int approverStatue){
		return approvalService.getPendingApproval(page, approverStatue);
	}
	
}
