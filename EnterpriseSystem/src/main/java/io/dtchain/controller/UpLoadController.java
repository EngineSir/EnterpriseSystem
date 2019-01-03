package io.dtchain.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
	@RequestMapping(value="/upload.io",method=RequestMethod.POST)
	public void upLoad(HttpServletRequest req,HttpServletResponse res)throws Exception{
		upLoadService.upLoad(req, res);
	}
	@RequestMapping(value="/dataImport.io",method=RequestMethod.POST)
	@ResponseBody
	public Result<List<ResultProce>> queryWeekInfo(QueryRecord qr){
		return upLoadService.queryWeekInfo(qr);
	}
	
}
