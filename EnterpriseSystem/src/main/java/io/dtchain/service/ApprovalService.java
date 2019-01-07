package io.dtchain.service;

import java.util.List;

import io.dtchain.entity.LeaveTable;
import io.dtchain.utils.Result;

public interface ApprovalService {
	public Result<Object> leaveApplication(String leaveType,String startTime, String endTime,int leaveNum, String leaveRegard, String approver);
	public Result<Object> queryCount(int approverStatue);
	public Result<List<LeaveTable>> getPendingApproval(int page,int approverStatue);
}
