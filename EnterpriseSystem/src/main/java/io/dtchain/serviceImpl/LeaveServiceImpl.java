package io.dtchain.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import io.dtchain.dao.LeaveDao;
import io.dtchain.entity.LeaveTable;
import io.dtchain.entity.QueryLeave;
import io.dtchain.service.LeaveService;
import io.dtchain.utils.Result;

@Service("LeaveService")
public class LeaveServiceImpl implements LeaveService {
	@Resource
	private LeaveDao leaveDao;

	/**
	 * 添加请假信息
	 */
	public Result<Object> addLeave(LeaveTable leave) {
		Result<Object> result = new Result<Object>();
		int n = leaveDao.addLeave(leave);
		if (n > 0) {
			result.setState(1);
			result.setMsg("添加请假信息成功");
		} else {
			result.setState(0);
			result.setMsg("添加请假信息失败");
		}
		return result;
	}

	/**
	 * 查询请假信息
	 */
	public Result<List<LeaveTable>> searchLeave(QueryLeave ql) {
		Result<List<LeaveTable>> result = new Result<List<LeaveTable>>();
		List<LeaveTable> list = new ArrayList<LeaveTable>();
		ql.setPage((ql.getPage() - 1) * 9);
		if (ql.getValue().equals("全部")) {
			list = leaveDao.searchAllLeave(ql);
		} else {
			list = leaveDao.searchLeave(ql);
		}
		result.setData(list);
		result.setMsg("查询成功");
		result.setState(1);
		return result;
	}

}
