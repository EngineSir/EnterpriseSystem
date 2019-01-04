package io.dtchain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import io.dtchain.entity.Notice;
import io.dtchain.service.NoticeService;
import io.dtchain.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping("/notice")
@Api(value = "/notice", description = "公告管理相关接口")
@CrossOrigin
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;

	@ApiOperation(value = "写公告时上传的图片")
	@PostMapping(value="/image.io")
	@ResponseBody
	public String uploadImage(@ApiParam(value = "上传图片", required = true) @RequestParam(value = "file") MultipartFile file) throws Exception{
		return noticeService.uploadImage(file);
	}
	
	@ApiOperation(value = "发布公告")
	@PostMapping(value="/noticeContent.io")
	@ResponseBody
	public String noticeContent(@ApiParam(value = "标题", required = true) @RequestParam(value = "title") String title,
								@ApiParam(value = "公告内容", required = true) @RequestParam(value = "content") String content){
		Notice notice=new Notice();
		notice.setContent(content);
		notice.setTitle(title);
		return noticeService.noticeContent(notice);
	}
	
	@ApiOperation(value = "查询公告标题列表")
	@GetMapping(value="/queryNoticeTitle.io")
	@ResponseBody
	public Result<List<Notice>> queryNoticeTitle(@ApiParam(value = "当前页面", required = true) @RequestParam(value = "page") Integer page){
		return noticeService.queryNoticeTitle(page);
	}
	
	@ApiOperation(value = "查询公告内容")
	@GetMapping(value="/queryNoticeContent.io")
	@ResponseBody
	public Result<Notice> queryNoticeContent(@ApiParam(value = "公告id", required = true)  @RequestParam(value = "id") String id){
		return noticeService.queryNoticeContent(id);
	}
	
	@ApiOperation(value = "查询公告总数")
	@GetMapping(value="/queryNoticeCount.io")
	@ResponseBody
	public Result<Object> queryNoticeCount(){
		return noticeService.queryNoticeCount();
	}
}