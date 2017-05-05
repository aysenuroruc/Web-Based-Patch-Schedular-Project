package com.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.ResponseMessage;
import com.spring.config.AppConfig;
import com.spring.excel.ExcelUtil;
import com.spring.model.Test;

@Controller
public class TestController {
	@Autowired
	AppConfig config;
	
	@Autowired
	ExcelUtil excel;
	
	@RequestMapping(value="/test")
	public @ResponseBody Test test(@RequestBody Test test) {
		System.out.println(test.name);
		Test t = new Test();
		t.name="sezer";
		return t;
	}
	
	@RequestMapping(value="/index")
	public @ResponseBody Test index() {
		Test t = new Test();
		t.name="ayse";
		return t;
	}
	
	@RequestMapping(value="/save")
	public @ResponseBody Test save() {
		Test t = new Test();
		t.name="blablabla";
		return t;
    }
	
	@RequestMapping(value="/path/{test}")
	public @ResponseBody Test path(@PathVariable(value="test") String x) {
		Test t = new Test();
		t.name=x;
		return t;
	}
	
	@RequestMapping(value="/path")
	public @ResponseBody Test requestparam(@RequestParam(value="name") String x) {
		Test t = new Test();
		t.name=x;
		return t;
	}
	
	@RequestMapping(value="/")
	public String home() {
		return "index.html/";
	}
	
	@RequestMapping(value="/main")
	public String main() {
		return "main.html/";
	}
	
	@RequestMapping(value="/years")
	public @ResponseBody String[] years() {
		return config.years;
	}
	
	@RequestMapping(value="/export/excel")
	public @ResponseBody ResponseMessage exportExcel(@RequestBody String[] exportedYears) {
		ResponseMessage rm = new ResponseMessage();
		try {
			excel.importExcel(exportedYears);
			rm.success=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rm;
	}
}
