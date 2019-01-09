package io.dtchain.dao;

import java.util.List;
import java.util.Map;

import io.dtchain.entity.LeaveTable;

public interface ApprovalDao {
	
	/**
	 * 请假申请
	 * 
	 * @param leave			请假信息
	 * @return
	 */
	public int leaveApplication(LeaveTable leave);
	
	/**
	 * 获取待审批总数(非管理员)
	 * 
	 * @param map			待审批参数
	 * @return
	 */
	public int queryCount(Map<String,Object> map);
	
	/**
	 * 获取待审批总数(管理员)
	 * 
	 * @param map			待审批参数
	 * @return
	 */
	public int queryAdminCount(Map<String,Object> map);	
	
	/**
	 * 获取待审批记录(非管理员)
	 * 
	 * @param map			待审批参数
	 * @return
	 */
	public List<LeaveTable> getPendingApproval(Map<String,Object>map);
	
	/**
	 * 获取待审批记录(管理员)
	 * 
	 * @param map			待审批参数
	 * @return
	 */
	public List<LeaveTable> getAdminPendingApproval(Map<String,Object>map);
}
