package io.dtchain.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.dtchain.entity.LeaveTable;
import io.dtchain.entity.QueryLeave;
import io.dtchain.service.LeaveService;
import io.dtchain.utils.Result;

/**
 * 请假信息Controller
 *
 */
@Controller
@RequestMapping("/leave")
public class LeaveController {
	@Resource
	private LeaveService leaveService;

	@RequestMapping("/addLeave.io")
	@ResponseBody
	/*
	 * 添加请假信息
	 */
	public Result<Object> addLeave(LeaveTable leave) {
		Result<Object> result = leaveService.addLeave(leave);
		return result;
	}

	@RequestMapping("searchLeave.io")
	@ResponseBody
	/*
	 * 查询请假信息
	 */
	public Result<List<LeaveTable>> searchLeave(QueryLeave ql) {
		Result<List<LeaveTable>> result = leaveService.searchLeave(ql);
		return result;
	}
}
