package io.dtchain.dao;

import java.util.List;

import io.dtchain.entity.EnterTable;
import io.dtchain.entity.OutTable;
import io.dtchain.entity.QueryRecord;
import io.dtchain.entity.RecordTable;
import io.dtchain.utils.Result;

public interface RecordDao {
	// 将数据写入worktimetable
	public int workTable(List<RecordTable> list);

	// 清空都为null的记录
	public int emptyNull();

	// 查询迟到早退明细
	public List<RecordTable> queryDetailed(QueryRecord qr);

	// 查询部门早退明细
	public List<RecordTable> queryDeptDetailed(QueryRecord qr);

	// 查询全部员工的早退明细
	public List<RecordTable> queryAllDetailed(QueryRecord qr);

	// 插入迟到早退记录
	public int detailed(List<RecordTable> list);

	// 个人早退，迟到，加班
	public List<RecordTable> queryDetailedInfo(QueryRecord qr);

	// 查询总员工记录总数
	public int queryAllCount(QueryRecord qr);

	// 查询部门员工记录数
	public int queryDeptCount(QueryRecord qr);

	// 查询个人记录数
	public int queryCount(QueryRecord qr);

	// 个人分页的记录数
	public List<RecordTable> queryOtherPage(QueryRecord qr);

	// 部门分页的记录数
	public List<RecordTable> queryPage(QueryRecord qr);

	// 查询总记录用于下载
	public List<RecordTable> queryAllRecord(QueryRecord qr);

	// 查询部门员工记录
	public List<RecordTable> queryDeptPage(QueryRecord qr);

	// 查询个人记录数用于下载
	public List<RecordTable> queryRecord(QueryRecord qr);
}
