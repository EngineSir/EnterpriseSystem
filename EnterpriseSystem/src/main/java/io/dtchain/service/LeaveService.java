package io.dtchain.service;

import java.util.List;

import io.dtchain.entity.LeaveTable;
import io.dtchain.entity.QueryLeave;
import io.dtchain.utils.Result;

public interface LeaveService
{
	//添加请假信息
	public Result<Object> addLeave(LeaveTable leave);
	//查询请假信息
	public Result<List<LeaveTable>> searchLeave(QueryLeave ql);
}
