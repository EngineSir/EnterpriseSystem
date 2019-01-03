package io.dtchain.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.dtchain.dao.AuthorityDao;
import io.dtchain.dao.ResourceDao;
import io.dtchain.entity.EmpInfo;
import io.dtchain.service.AuthorityService;
import io.dtchain.utils.Result;

@Service("AuthorityService")
public class AuthorityServiceImpl implements AuthorityService {
	@Autowired
	private AuthorityDao authorityDao;
	@Autowired
	private ResourceDao resourcesDao;

	@Override
	public Result<List<EmpInfo>> queryEmpInfo(Integer page) {
		Map<String, Object> map = new HashMap<String, Object>();
		int n = (page <= 0 ? 1 : (page - 1) * 8);
		map.put("page", n);
		Result<List<EmpInfo>> result = new Result<List<EmpInfo>>();
		List<EmpInfo> list = authorityDao.queryEmpInfo(map);
		result.setData(list);
		result.setState(1);
		return result;
	}

	@Override
	public Result<Object> queryCount() {
		Result<Object> result = new Result<Object>();
		result.setData(authorityDao.queryCount());
		result.setState(1);
		return result;
	}

	@Override
	public Result<Object> addAuthority(Integer[] resourceIdArray, String empId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Result<Object> result = new Result<Object>();
		List<Integer> listId = resourcesDao.queryId(empId);
		if ((listId == null || listId.size() == 0) && (resourceIdArray.length == 1)) {
			result.setMsg("无权限分配");
			result.setState(0);
			return result;
		}
		// 123 12 1234 134
		// 123
		//
		if (listId == null || listId.size() == 0) {
			for (int i = 1; i < resourceIdArray.length; i++) {
				map.put("userId", empId);
				map.put("resourceId", resourceIdArray[i]);
				authorityDao.addAuthority(map);
				map.clear();
			}
			result.setMsg("权限分配成功");
			result.setState(1);
			return result;
		}
		//
		if (resourceIdArray.length == 1) {
			for (int i = 0; i < listId.size(); i++) {
				map.put("userId", empId);
				map.put("resourceId", listId.get(i));
				authorityDao.delAuthority(map);
				map.clear();
			}
			result.setMsg("清空权限成功");
			result.setState(2);
			return result;
		}
		//
		for (int i = 1; i < resourceIdArray.length; i++) {
			if (!listId.contains(resourceIdArray[i])) {
				map.put("userId", empId);
				map.put("resourceId", resourceIdArray[i]);
				authorityDao.addAuthority(map);
				map.clear();
			}
		}
		
		for (int i = 0; i < listId.size(); i++) {
			boolean flog = true;
			for (int j = 1; j < resourceIdArray.length; j++) {
				if (listId.get(i) == resourceIdArray[j]) {
					flog = false;
					break;
				}
			}
			if (flog) {
				map.put("userId", empId);
				map.put("resourceId", listId.get(i));
				authorityDao.delAuthority(map);
				map.clear();
				flog = true;
			}
		}
		result.setMsg("修改权限成功");
		result.setState(3);
		return result;
	}

	@Override
	public Result<List<Integer>> queryAuthorityId(String empId) {
		List<Integer> listId = resourcesDao.queryId(empId);
		Result<List<Integer>> result=new Result<List<Integer>>();
		result.setData(listId);
		return result;
	}

	

}