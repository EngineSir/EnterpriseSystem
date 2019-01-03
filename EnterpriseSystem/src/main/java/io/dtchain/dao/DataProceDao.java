package io.dtchain.dao;

import java.util.List;

import io.dtchain.entity.DataProceTable;

public interface DataProceDao {
	// 数据处理结果写入shujuk
	public int dataProce(List<DataProceTable> list);

	public int emptyZero();

	public int empzero();

	// 写入双休上班信息
	public int weekInfo(List<DataProceTable> list);
}
