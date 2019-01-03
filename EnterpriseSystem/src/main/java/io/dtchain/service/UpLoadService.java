package io.dtchain.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import io.dtchain.entity.QueryRecord;
import io.dtchain.entity.ResultProce;
import io.dtchain.utils.Result;

public interface UpLoadService
{
	//上传文件
	public void upLoad(HttpServletRequest req,HttpServletResponse res) throws Exception;
	//查询双休信息
	public Result<List<ResultProce>> queryWeekInfo(QueryRecord qr);
}
