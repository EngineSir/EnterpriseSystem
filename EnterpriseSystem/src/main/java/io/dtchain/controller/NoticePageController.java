package io.dtchain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@Api(description = "公告页面显示接口")
@CrossOrigin
public class NoticePageController {
	
	@ApiOperation(value = "显示公告详情页面")
	@GetMapping(value="/notice.io")
	public String blogPage(@ApiParam(value = "公告id", required = true) @RequestParam(value = "id") String id){
		return "noticePage";
	}
	
	@ApiOperation(value = "显示修改公告页面")
	@GetMapping(value = "/updateNotice.io")
	public String updateNotice(@ApiParam(value = "公告id",required = true) @RequestParam(value = "id") String id) {
		return "upNotice";
	}
}
