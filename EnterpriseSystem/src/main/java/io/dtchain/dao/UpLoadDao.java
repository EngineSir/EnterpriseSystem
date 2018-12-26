package io.dtchain.dao;

import java.util.List;
import java.util.Map;

import io.dtchain.entity.AttendTable;
import io.dtchain.entity.QueryRecord;
import io.dtchain.entity.ResultProce;

public interface UpLoadDao
{
	//插入考勤记录()到数据库
	public int insertAttendRecord(List<AttendTable> list); 
	//查询下班之后的进出门时间
	public List<AttendTable> queryOverTime(Map<String,String> map);
	//查询全部双休信息
	public List<ResultProce> queryAllWeekInfo(QueryRecord qr);
	//部门双休信息
	public List<ResultProce> queryDeptWeekInfo(QueryRecord qr);
	//个人双休信息
	public List<ResultProce> queryWeekInfo(QueryRecord qr);
	//排除正常上班时间出去的时间
	public List<AttendTable> queryOutTime(Map<String,String> map);
}
 