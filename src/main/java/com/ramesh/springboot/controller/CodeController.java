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
	@GetMapping(value = { "schema/{schema}/table/{table}" })
	public String getData(@PathVariable(value = "schema") String schema,@PathVariable(value = "table") String table) {

		if (table == null || table.isEmpty())
			return null;
		else
			return codeGeneratorService.getColumns(table,schema);
	}
}
