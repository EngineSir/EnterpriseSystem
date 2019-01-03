package io.dtchain.dao;

import java.util.List;

import io.dtchain.entity.DataProceTable;
import io.dtchain.entity.QueryRecord;

public interface AttendDao {
	/**
	 * 查询全部的工时统计表
	 * 
	 * @return
	 */
	public List<DataProceTable> searchAllProce(QueryRecord qr);

	/**
	 * 查询部门的工时统计表
	 * 
	 * @param qr
	 * @return
	 */
	public List<DataProceTable> searchDeptProce(QueryRecord qr);

	/**
	 * 查询个人的工时统计表
	 * 
	 * @param qr
	 * @return
	 */
	public List<DataProceTable> searchProce(QueryRecord qr);
}
