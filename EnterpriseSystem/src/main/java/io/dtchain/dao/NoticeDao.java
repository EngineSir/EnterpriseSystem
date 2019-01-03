package io.dtchain.dao;

import java.util.List;
import java.util.Map;

import io.dtchain.entity.Notice;

public interface NoticeDao {
	public void saveNotice(Notice notice);

	public List<Notice> queryNoticeTitle(Map<String, Object> map);

	public Notice queryNoticeContent(String id);

	public int queryNoticeCount();
}
