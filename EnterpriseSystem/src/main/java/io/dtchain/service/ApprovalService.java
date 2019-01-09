package io.dtchain.service;

import java.util.List;

import io.dtchain.entity.LeaveTable;
import io.dtchain.utils.Result;

public interface ApprovalService {
	/**
	 * 请假申请
	 * 
	 * @param leaveType			请假类型
	 * @param startTime			开始时间
	 * @param endTime			结束时间
	 * @param leaveNum			请假天数
	 * @param leaveRegard		请假事由
	 * @param approver			审批人
	 * @return
	 */
	public Result<Object> leaveApplication(String leaveType,String startTime, String endTime,int leaveNum, String leaveRegard, String approver);
	
	/**
	 * 待审批总数
	 * 
	 * @param approverStatue 	审批状态
	 * @return
	 */
	public Result<Object> queryCount(int approverStatue);
	
	/**
	 * 获取待审批记录
	 * 
	 * @param page				当前页数		
	 * @param approverStatue	审批状态
	 * @return
	 */
	public Result<List<LeaveTable>> getPendingApproval(int page,int approverStatue);
}
