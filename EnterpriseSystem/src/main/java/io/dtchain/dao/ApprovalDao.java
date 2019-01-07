package io.dtchain.dao;

import java.util.List;
import java.util.Map;

import io.dtchain.entity.LeaveTable;

public interface ApprovalDao {
	public int leaveApplication(LeaveTable leave);
	public int queryCount(Map<String,Object> map);
	/*
	 * 管理员账号查的总数
	 */
	public int queryAdminCount(Map<String,Object> map);	
	public List<LeaveTable> getPendingApproval(Map<String,Object>map);
	public List<LeaveTable> getAdminPendingApproval(Map<String,Object>map);
}
