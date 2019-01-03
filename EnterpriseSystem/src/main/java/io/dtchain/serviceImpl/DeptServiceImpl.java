package io.dtchain.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import io.dtchain.dao.DeptDao;
import io.dtchain.entity.DeptInfo;
import io.dtchain.service.DeptService;
import io.dtchain.utils.Result;
import io.dtchain.utils.Utils;

/**
 * 部门的增删改查ServiceImpl
 *
 */
@Service("DeptService")
public class DeptServiceImpl implements DeptService {
	@Resource
	private DeptDao deptDao;

	/**
	 * 添加部门
	 */
	public Result<Object> addDept(String deptName) {
		Result<Object> result = new Result<Object>();
		Map<String, String> map = new HashMap<String, String>();
		String id = Utils.createId();
		map.put("id", id);
		map.put("deptName", deptName);
		System.out.println(map);

		int n = deptDao.addDept(map);
		System.out.println(n);
		if (n > 0) {
			result.setData(id);
			result.setMsg("添加部门成功");
			result.setState(1);
		}
		return result;
	}

	/**
	 * 删除部门
	 */
	public Result<Object> delDept(String id) {
		Result<Object> result = new Result<Object>();
		int n = deptDao.delDept(id);
		if (n > 0) {
			result.setState(1);
			result.setMsg("删除成功");
		}
		return result;
	}

	/**
	 * 查询部门
	 */
	public Result<List<DeptInfo>> queryDept() {
		Result<List<DeptInfo>> result = new Result<List<DeptInfo>>();
		List<DeptInfo> list = new ArrayList<DeptInfo>();
		list = deptDao.queryDept();
		if (!list.isEmpty()) {
			result.setData(list);
			result.setMsg("查询成功");
			result.setState(1);
		}
		return result;
	}

	/**
	 * 修改部门
	 */
	public Result<Object> upDept(DeptInfo dept) {
		Result<Object> result = new Result<Object>();
		deptDao.upDept(dept);
		result.setMsg("修改成功");
		result.setState(1);

		return result;
	}

}
