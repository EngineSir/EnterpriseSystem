package io.dtchain.serviceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.dtchain.dao.ApprovalDao;
import io.dtchain.entity.LeaveTable;
import io.dtchain.service.ApprovalService;
import io.dtchain.utils.Result;
@Service("ApprovalService")
public class ApprovalServiceImpl implements ApprovalService{

	@Autowired
	private ApprovalDao approvalDao;
	@Override
	public Result<Object> leaveApplication(String leaveType, String startTime, String endTime, int leaveNum, String leaveRegard,
			String approver) {
		Result<Object> result=new Result<Object>();
		String applicant=(String)SecurityUtils.getSubject().getPrincipal();
		Timestamp createTime = new Timestamp(System.currentTimeMillis());
		String id = UUID.randomUUID().toString().replace("-", "");
		LeaveTable leave=new LeaveTable();
		leave.setApplicant(applicant);
		leave.setApprover(approver);
		leave.setApproverStatue(2);
		leave.setCreateTime(createTime);
		leave.setEndTime(endTime);
		leave.setId(id);
		leave.setLeaveNum(leaveNum);
		leave.setLeaveRegard(leaveRegard);
		leave.setLeaveType(leaveType);
		leave.setStartTime(startTime);
		int n=approvalDao.leaveApplication(leave);
		if(n<1) {
			result.setMsg("请求失败");
			result.setState(0);
		}else {
			result.setMsg("请求成功");
			result.setState(1);
		}
		return result;
	}
	@Override
	public Result<Object> queryCount(int approverStatue) {
		String applicant=(String)SecurityUtils.getSubject().getPrincipal();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("applicant", applicant);
		map.put("approverStatue", approverStatue);
		Result<Object> result=new Result<Object>();
		int count=0;
		if(applicant.equals("admin")) {
			count=approvalDao.queryAdminCount(map);
		}else {
			count=approvalDao.queryCount(map);
		}
		result.setCount(count);
		result.setMsg("请求成功");
		result.setState(1);
		return result;
	}
	@Override
	public Result<List<LeaveTable>> getPendingApproval(int page, int approverStatue) {
		Result<List<LeaveTable>> result=new Result<List<LeaveTable>>();
		List<LeaveTable> list=new ArrayList<LeaveTable>();
		page=page<1?0:(page-1)*10;
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("page", page);
		map.put("approverStatue", approverStatue);
		if(((String)SecurityUtils.getSubject().getPrincipal()).equals("admin")) {
			list=approvalDao.getAdminPendingApproval(map);
		}else {
			list=approvalDao.getPendingApproval(map);
		}
		result.setData(list);
		result.setMsg("请求成功");
		result.setState(1);
		return result;
	}

}
