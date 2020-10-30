package com.ramesh.springboot.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ColumnsTable {
	private String tableSchema;
	private String tableName;
	private String columnName;
	private Long ordinalPosition;
	private String columnDefault;
	private String isNullable;
	private String dataType;
	private String columnType;

}
