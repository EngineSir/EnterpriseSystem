package io.dtchain.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import io.dtchain.dao.AttendDao;
import io.dtchain.entity.DataProceTable;
import io.dtchain.entity.QueryRecord;
import io.dtchain.entity.ResultProce;
import io.dtchain.service.AttendService;
import io.dtchain.utils.Result;

@Service("AttendService")
public class AttendServiceImpl implements AttendService {
	@Resource
	private AttendDao attendDao;

	/**
	 * 查询工时统计表
	 */
	public Result<List<ResultProce>> statistic(QueryRecord qr) {
		Result<List<ResultProce>> result = new Result<List<ResultProce>>();
		List<DataProceTable> list = new ArrayList<DataProceTable>();
		List<ResultProce> rp = new ArrayList<ResultProce>();
		if (qr.getEmpName() == "") {
			// 查询部门
			list = attendDao.searchDeptProce(qr);
			rp = proce(list);
		} else {
			// 查询个人
			list = attendDao.searchProce(qr);
			rp = proce(list);
		}
		if (!rp.isEmpty()) {
			result.setData(rp);
			result.setMsg("查询成功");
			result.setState(1);
		} else {
			result.setMsg("没有查询到该数据");
			result.setState(0);
		}
		return result;
	}

	/**
	 * 处理员工工时统计表
	 */
	private List<ResultProce> proce(List<DataProceTable> list) {
		List<ResultProce> rpList = new ArrayList<ResultProce>();
		ResultProce rp;
		DataProceTable dp1;
		DataProceTable dp2;
		for (int i = list.size() - 1; i >= 0;) {
			int index = 1;
			rp = new ResultProce();
			dp1 = list.get(i);
			float days = dp1.getDays();
			int hours = dp1.getHours();
			int late = dp1.getLate();
			int earlyRetr = dp1.getEarlyRetr();
			int overTime = dp1.getOverTime();
			for (int j = list.size() - 2; j >= 0; j--) {
				dp2 = list.get(j);
				// 名字相同,日期不同
				if (dp1.getEmpName().equals(dp2.getEmpName()) && !dp1.getDates().equals(dp2.getDates())) {
					days += dp2.getDays();
					hours += dp2.getHours();
					late += dp2.getLate();
					earlyRetr += dp2.getEarlyRetr();
					overTime += dp2.getOverTime();
					index++;
					list.remove(j);
				}
			}
			rp.setDays(days);
			rp.setDept(dp1.getDept());
			rp.setEarlyRetr(earlyRetr);
			rp.setEmpName(dp1.getEmpName());
			rp.setHours(hours);
			rp.setLate(late);
			rp.setOverTime(overTime);
			rpList.add(rp);
			i -= index;
		}
		return rpList;
	}
}
