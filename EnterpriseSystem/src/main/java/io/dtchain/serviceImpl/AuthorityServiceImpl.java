package io.dtchain.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.dtchain.dao.AuthorityDao;
import io.dtchain.entity.EmpInfo;
import io.dtchain.service.AuthorityService;
import io.dtchain.utils.Result;
@Service("AuthorityService")
public class AuthorityServiceImpl implements AuthorityService{
	@Autowired
	private AuthorityDao authorityDao;
	@Override
	public Result<List<EmpInfo>> queryEmpInfo(Integer page) {
		Map<String,Object> map=new HashMap<String,Object>();
		int n=(page<=0?1:(page-1)*8);
		map.put("page", n);
		Result<List<EmpInfo>> result=new Result<List<EmpInfo>>();
		List<EmpInfo> list=authorityDao.queryEmpInfo(map);
		result.setData(list);
		result.setState(1);
		return result;
	}
	@Override
	public Result<Object> queryCount() {
		Result<Object> result=new Result<Object>();
		result.setData(authorityDao.queryCount());
		result.setState(1);
		return result;
	}

}
