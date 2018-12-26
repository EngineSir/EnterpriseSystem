package io.dtchain.controller;

import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.dtchain.entity.QueryRecord;
import io.dtchain.entity.RecordTable;
import io.dtchain.service.QueryRecordService;
import io.dtchain.utils.Result;

/**
 * 考勤记录查询controller类
 *
 */
@Controller
@RequestMapping("/record")
public class RecordController {
	@Resource
	private QueryRecordService queryRecordService;

	@RequestMapping("/queryRecord.io")
	@ResponseBody
	/**
	 * 查询考勤记录
	 */
	public Result<List<RecordTable>> queryRecord(QueryRecord qr) {
		Result<List<RecordTable>> result = queryRecordService.queryRecord(qr);
		System.out.println("result: "+result);
		return result;
	}

	@RequestMapping("/detailed.io")
	@ResponseBody
	/*
	 * 查询迟到，早退
	 */
	public Result<List<RecordTable>> queryDetailed(QueryRecord qr) {
		Result<List<RecordTable>> result = queryRecordService.queryDetailed(qr);
		return result;
	}

	/*
	 * 查询迟到早退加班明细
	 */
	@RequestMapping("/detailedInfo.io")
	@ResponseBody
	public Result<List<RecordTable>> queryDetailedInfo(QueryRecord qr) {
		Result<List<RecordTable>> result = queryRecordService.queryDetailedInfo(qr);
		return result;
	}
	/**
	 * 查询符合查询条件的记录总数
	 */
	@RequestMapping("/queryCount.io")
	@ResponseBody
	public Result<Object> queryCount(QueryRecord qr){
		return queryRecordService.quertCount(qr);
	}
	/**
	 * 点击其他分页，查询记录
	 */
	@RequestMapping("/otherPage.io")
	@ResponseBody
	public Result<List<RecordTable>> queryOtherPage(QueryRecord qr){
		
		return queryRecordService.queryOtherPage(qr);
	}
	
	@RequestMapping("/downloadExcel.io")
	public void downloadExcel(HttpServletRequest req,HttpServletResponse res) throws Exception{
		
			QueryRecord qr=new QueryRecord();
			qr.setEmpName(req.getParameter("name"));
			qr.setEmpDept(req.getParameter("dept"));
			qr.setEnd(req.getParameter("end"));
			qr.setStart(req.getParameter("start"));
			HSSFWorkbook wb=queryRecordService.download(qr);
	        res.setContentType("application/vnd.ms-excel");
	        res.setHeader("Content-disposition", "attachment;filename=AttendRecord.xls" );
	        OutputStream ouputStream = res.getOutputStream();
	        wb.write(ouputStream);
	        ouputStream.flush();
	        ouputStream.close();
	}
}