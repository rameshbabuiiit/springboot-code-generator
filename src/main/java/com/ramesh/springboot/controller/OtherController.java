package com.ramesh.springboot.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.CaseFormat;
import com.ramesh.springboot.service.CodeGeneratorService;

@RestController
public class OtherController {
	@Autowired
	CodeGeneratorService codeGeneratorService;

	@GetMapping(value = { "/class/{str}" })
	public ArrayList<String> getInitCapital(@PathVariable(value = "str") String str) {
		/*
		 * SELECT GROUP_CONCAT(CONCAT(DATA_TYPE ,'@',COLUMN_NAME)) AS Constants from
		 * information_schema.columns WHERE TABLE_NAME = 'user_preference'; notepad++:
		 * ctrl+alt+shift+j
		 */
		final String BIG_INT = "bigint@";
		final String INT = "int@";
		final String VARCHAR = "varchar@";
		final String DATE_TIME = "datetime@";
		final String CHAR = "char@";
		final String TEXT = "text@";

		final String LONG = "Long ";
		final String STRING = "String ";
		final String LOCAL_DATE_TIME = "LocalDateTime ";
		final String INTEGER = "Integer ";

		String[] strings = str.split(",");
		ArrayList<String> strArray = new ArrayList<>();
		for (String str1 : strings) {
			String formattedStr = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, str1);
			if (formattedStr.startsWith(BIG_INT))
				formattedStr = formattedStr.replace(BIG_INT, LONG);
			else if (formattedStr.startsWith(INT))
				formattedStr = formattedStr.replace(INT, INTEGER);
			else if (formattedStr.startsWith(VARCHAR))
				formattedStr = formattedStr.replace(VARCHAR, STRING);
			else if (formattedStr.startsWith(CHAR))
				formattedStr = formattedStr.replace(CHAR, STRING);
			else if (formattedStr.startsWith(TEXT))
				formattedStr = formattedStr.replace(TEXT, STRING);
			else if (formattedStr.startsWith(DATE_TIME))
				formattedStr = formattedStr.replace(DATE_TIME, LOCAL_DATE_TIME);
			strArray.add("private " + formattedStr + ";");
		}
		return strArray;
	}
}
