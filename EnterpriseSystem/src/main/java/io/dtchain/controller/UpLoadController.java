package io.dtchain.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.dtchain.entity.QueryRecord;
import io.dtchain.entity.ResultProce;
import io.dtchain.service.UpLoadService;
import io.dtchain.utils.Result;
/**
 * 上传文件controller类
 *
 */
@Controller
@RequestMapping("/File")
public class UpLoadController
{
	@Resource
	private UpLoadService upLoadService;
	/**
	 * 上传文件
	 */
	@RequestMapping("/upload.io")
	public void upLoad(HttpServletRequest req,HttpServletResponse res)throws Exception{
		upLoadService.upLoad(req, res);
	}
	@RequestMapping("/dataImport.io")
	@ResponseBody
	public Result<List<ResultProce>> queryWeekInfo(QueryRecord qr){
		Result<List<ResultProce>> result=upLoadService.queryWeekInfo(qr);
		return result;
	}
	
}
