package io.dtchain.service;

import java.util.List;

import io.dtchain.entity.DeptInfo;
import io.dtchain.utils.Result;

public interface DeptService {
	// 添加部门
	public Result<Object> addDept(String deptName);

	// 删除部门
	public Result<Object> delDept(String id);

	// 查询
	public Result<List<DeptInfo>> queryDept();

	// 修改部门
	public Result<Object> upDept(DeptInfo dept);
}
