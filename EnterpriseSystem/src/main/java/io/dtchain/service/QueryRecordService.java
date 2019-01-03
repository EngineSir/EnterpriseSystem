package io.dtchain.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import io.dtchain.entity.QueryRecord;
import io.dtchain.entity.RecordTable;
import io.dtchain.utils.Result;

public interface QueryRecordService {
	/**
	 * 根据检索条件查询考勤记录
	 * 
	 * @param qr
	 * @return
	 */
	public Result<List<RecordTable>> queryRecord(QueryRecord qr);

	// 查询迟到早退
	public Result<List<RecordTable>> queryDetailed(QueryRecord qr);

	// 页面个人早退，迟到加班信息
	public Result<List<RecordTable>> queryDetailedInfo(QueryRecord qr);

	// 查询记录总数
	public Result<Object> quertCount(QueryRecord qr);

	//
	public Result<List<RecordTable>> queryOtherPage(QueryRecord qr);

	public HSSFWorkbook download(QueryRecord qr);

}
