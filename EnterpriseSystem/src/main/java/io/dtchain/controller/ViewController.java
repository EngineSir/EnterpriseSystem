package io.dtchain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ViewController {

	@RequestMapping(value="/login",method=RequestMethod.GET)
	
	public String login() {
		return "login";
	}
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index() {
		return "index";
	}
	@RequestMapping(value="/stassMang",method=RequestMethod.GET)
	public String mangage() {
		return "mangage";
	}
	@RequestMapping(value="/record",method=RequestMethod.GET)
	public String record() {
		return "record";
	}
	@RequestMapping(value="/attend",method=RequestMethod.GET)
	public String attend() {
		return "attend";
	}
	@RequestMapping(value="/notice",method=RequestMethod.GET)
	public String notice() {
		return "notice";
	}
	@RequestMapping(value="/dataImport",method=RequestMethod.GET)
	public String dataImport() {
		return "dataImport";
	}
	@RequestMapping(value="/jurisdiction",method=RequestMethod.GET)
	public String jurisdiction() {
		return "jurisdiction";
	}
	@RequestMapping(value="/myApproval",method=RequestMethod.GET)
	public String myApproval() {
		return "myApproval";
	}
	@RequestMapping(value="/com",method=RequestMethod.GET)
	public String com() {
		return "com";
	}
	@RequestMapping(value="/dept",method=RequestMethod.GET)
	public String dept() {
		return "dept";
	}
	@RequestMapping(value="/approval",method=RequestMethod.GET)
	public String approval() {
		return "approval";
	}
	@RequestMapping(value="/info",method=RequestMethod.GET)
	public String info() {
		return "info";
	}
	@RequestMapping(value="/editeNotice",method=RequestMethod.GET)
	public String editeNotice() {
		return "editeNotice";
	}
}
