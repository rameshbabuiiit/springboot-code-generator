package com.ramesh.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ramesh.springboot.service.CodeGeneratorService;

@RestController
public class CodeController {
	@Autowired
	CodeGeneratorService codeGeneratorService;
	@GetMapping(value = { "/table-name/{details}" })
	public String getData(@PathVariable(value = "details") String str) {
		if (str == null || str.isEmpty())
			return null;
		else
			return codeGeneratorService.getColumns(str);
	}
}
