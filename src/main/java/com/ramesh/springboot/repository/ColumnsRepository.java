package com.ramesh.springboot.repository;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ramesh.springboot.model.ColumnsTable;
@Service
public interface ColumnsRepository {
	 List<ColumnsTable> findByTableName(String name, String tableSchema);
}
