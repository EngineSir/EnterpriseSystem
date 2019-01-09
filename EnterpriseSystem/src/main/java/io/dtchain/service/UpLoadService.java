package io.dtchain.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import io.dtchain.entity.QueryRecord;
import io.dtchain.entity.ResultProce;
import io.dtchain.utils.Result;

public interface UpLoadService {
	
	/**
	 * 上传excel文件
	 * 
	 * @param req
	 * @param res
	 * @throws Exception
	 */
	public void upLoad(MultipartFile file,HttpServletRequest req, HttpServletResponse res) throws Exception;

	/**
	 * 查询双休信息
	 * 
	 * @param qr		检索信息
	 * @return
	 */
	public Result<List<ResultProce>> queryWeekInfo(QueryRecord qr);
}
