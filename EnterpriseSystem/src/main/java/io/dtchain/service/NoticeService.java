package io.dtchain.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import io.dtchain.entity.Notice;
import io.dtchain.utils.Result;

public interface NoticeService {
	// 公告内容中上传图片
	public String uploadImage(MultipartFile file, HttpServletRequest req) throws Exception;

	public String noticeContent(Notice notice);

	public Result<List<Notice>> queryNoticeTitle(Integer page);

	public Result<Notice> queryNoticeContent(String id);

	public Result<Object> queryNoticeCount();
}
