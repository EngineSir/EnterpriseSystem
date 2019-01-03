package io.dtchain.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import io.dtchain.entity.Notice;
import io.dtchain.service.NoticeService;
import io.dtchain.utils.Result;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	/*
	 * 写通知时上传的图片
	 */
	@RequestMapping(value="/image.io",method=RequestMethod.POST)
	@ResponseBody
	public String uploadImage(@RequestParam("file") MultipartFile file,HttpServletRequest req) throws Exception{
		return noticeService.uploadImage(file, req);
	}
	@RequestMapping(value="/noticeContent.io",method=RequestMethod.POST)
	@ResponseBody
	public String noticeContent(Notice notice){
		return noticeService.noticeContent(notice);
	}
	@RequestMapping(value="/queryNoticeTitle.io",method=RequestMethod.POST)
	@ResponseBody
	public Result<List<Notice>> queryNoticeTitle(Integer page){
		return noticeService.queryNoticeTitle(page);
	}
	@RequestMapping(value="/queryNoticeContent.io",method=RequestMethod.POST)
	@ResponseBody
	public Result<Notice> queryNoticeContent(String id){
		return noticeService.queryNoticeContent(id);
	}
	@RequestMapping(value="/queryNoticeCount.io",method=RequestMethod.POST)
	@ResponseBody
	public Result<Object> queryNoticeCount(){
		return noticeService.queryNoticeCount();
	}
}