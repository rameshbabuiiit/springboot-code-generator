package com.ramesh.springboot.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ramesh.springboot.model.ColumnsTable;
@Repository
public class ColumnsRepositoryImpl  implements ColumnsRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<ColumnsTable> findByTableName(String tableName, String tableSchema) {
		return jdbcTemplate.query("select * from columns where table_name = ? and table_schema = ? order by ordinal_position asc", new Object[] { tableName, tableSchema },
				(rs, rowNum) -> new ColumnsTable(rs.getString("table_schema"), rs.getString("table_name"),
						rs.getString("column_name"), rs.getLong("ordinal_position"), rs.getString("column_default"),
						rs.getString("is_nullable"), rs.getString("data_type"), rs.getString("column_type")));
	}

}
