package io.dtchain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NoticPageController {
	@RequestMapping(value="/notice.io",method=RequestMethod.GET)
	public String blogPage(@RequestParam(value="id")String id){
		return "noticePage";
	}
}
