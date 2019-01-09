package io.dtchain.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import io.dtchain.entity.QueryRecord;
import io.dtchain.entity.ResultProce;
import io.dtchain.service.UpLoadService;
import io.dtchain.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value="/File")
@Api(value = "/File", description = "导入excel考勤记录相关接口")
@CrossOrigin
public class UpLoadController
{
	@Resource
	private UpLoadService upLoadService;
	
	
	@ApiOperation(value = "上传excel文件")
	@PostMapping(value="/upload.io")
	public void upLoad(@ApiParam(value = "上传excel", required = true) @RequestParam(value = "file") MultipartFile file,HttpServletRequest req,HttpServletResponse res)throws Exception{
		upLoadService.upLoad(file,req, res);
	}
	
	@ApiOperation(value = "查询双休工时统计")
	@GetMapping(value="/dataImport.io")
	@ResponseBody
	public Result<List<ResultProce>> queryWeekInfo(@ApiParam(value = "名字", required = false) @RequestParam(value = "empName") String empName,
											       @ApiParam(value = "部门", required = true) @RequestParam(value = "empDept") String empDept,
											       @ApiParam(value = "开始日期", required = true) @RequestParam(value = "start") String start,
											       @ApiParam(value = "结束日期", required = true) @RequestParam(value = "end") String end){
		QueryRecord qr=new QueryRecord();
		qr.setEmpDept(empDept);
		qr.setEmpName(empName);
		qr.setEnd(end);
		qr.setStart(start);
		return upLoadService.queryWeekInfo(qr);
	}
	
}
