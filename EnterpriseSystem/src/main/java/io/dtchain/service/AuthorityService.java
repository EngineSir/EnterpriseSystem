package io.dtchain.service;

import java.util.List;

import io.dtchain.entity.EmpInfo;
import io.dtchain.utils.Result;

public interface AuthorityService {
	public Result<List<EmpInfo>> queryEmpInfo(Integer page);
	public Result<Object> queryCount();
}
