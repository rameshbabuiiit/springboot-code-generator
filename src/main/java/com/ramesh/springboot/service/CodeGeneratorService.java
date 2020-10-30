package com.ramesh.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.CaseFormat;
import com.ramesh.springboot.model.ColumnsTable;
import com.ramesh.springboot.repository.ColumnsRepositoryImpl;
import com.ramesh.springboot.utils.Constants;

@Service
public class CodeGeneratorService {
	@Autowired
	ColumnsRepositoryImpl repo;

	public String getColumns(String tableName) {
		List<ColumnsTable> columnsTableRecs = repo.findByTableName(tableName);
		StringBuilder result = new StringBuilder();

		result.append(buildConstantsClass(columnsTableRecs, tableName) + "\r\n\r\n\r\n");
		result.append(buildEntityClass(columnsTableRecs, tableName) + "\r\n\r\n\r\n");
		result.append(buildRepositoryClass(columnsTableRecs, tableName) + "\r\n\r\n\r\n");
		result.append(buildJavaClass(columnsTableRecs, tableName) + "\r\n\r\n\r\n");
		return result.toString();
	}

	public String buildJavaClass(List<ColumnsTable> recs, String tableName) {
		StringBuilder str = new StringBuilder("public class " + getCapInit(tableName) + "{\r\n");
		for (ColumnsTable rec : recs) {
			str.append("private " + getJavaDataType(rec.getDataType()) + " " + getInitCapital(rec.getColumnName())
					+ ";\r\n");
		}
		str.append("}");
		return str.toString();
	}

	public String buildEntityClass(List<ColumnsTable> recs, String tableName) {
		StringBuilder str = new StringBuilder("@Entity \r\n" + "@Table(name = Constants." + tableName.toUpperCase()
				+ ")\r\n public class " + getCapInit(tableName)
				+ " implements Serializable { \r\n private static final long serialVersionUID = 1l;\r\n");
		int i = 0;
		for (ColumnsTable rec : recs) {
			if (i == 0) {
				str.append("@Id \r\n @GeneratedValue(strategy = GenerationType.IDENTITY) \r\n");
				str.append("private " + getJavaDataType(rec.getDataType()) + " " + getInitCapital(rec.getColumnName())
						+ ";\r\n");
			} else {
				str.append("@Column(name = Constants." + rec.getColumnName().toUpperCase() + ")\r\n");
				str.append("private " + getJavaDataType(rec.getDataType()) + " " + getInitCapital(rec.getColumnName())
						+ ";\r\n");
			}
			i++;
		}
		str.append("}");
		return str.toString();
	}

	public String buildConstantsClass(List<ColumnsTable> recs, String tableName) {
		StringBuilder str = new StringBuilder("public interface Constants { \r\n public static final String "
				+ tableName.toUpperCase() + " = " + "\"" + tableName.toUpperCase() + "\"" + ";\r\n");
		for (ColumnsTable rec : recs) {
			str.append("public static final String " + rec.getColumnName().toUpperCase() + " = " + "\""
					+ rec.getColumnName().toLowerCase() + "\"" + ";\r\n");
		}
		str.append("}");
		return str.toString();
	}

	public String buildRepositoryClass(List<ColumnsTable> recs, String tableName) {
		StringBuilder str = new StringBuilder("@Repository \r\n public interface " + getCapInit(tableName)
				+ "Repository  extends JpaRepository<" + getCapInit(tableName) + "Entity, " + "#" + "> {\r\n");
		String pkeyDataType = "";
		for (ColumnsTable rec : recs) {
			if (rec.getOrdinalPosition() == 1) {
				pkeyDataType = getJavaDataType(rec.getDataType());
				break;
			}
		}

		str.append("}");
		return str.toString().replace("#", pkeyDataType);
	}

	public String getJavaDataType(String dataType) {
		String formattedStr = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, dataType);
		if (formattedStr.startsWith(Constants.BIG_INT))
			formattedStr = formattedStr.replace(Constants.BIG_INT, Constants.LONG);
		else if (formattedStr.startsWith(Constants.INT))
			formattedStr = formattedStr.replace(Constants.INT, Constants.INTEGER);
		else if (formattedStr.startsWith(Constants.VARCHAR))
			formattedStr = formattedStr.replace(Constants.VARCHAR, Constants.STRING);
		else if (formattedStr.startsWith(Constants.CHAR))
			formattedStr = formattedStr.replace(Constants.CHAR, Constants.STRING);
		else if (formattedStr.startsWith(Constants.TEXT))
			formattedStr = formattedStr.replace(Constants.TEXT, Constants.STRING);
		else if (formattedStr.startsWith(Constants.DATE_TIME))
			formattedStr = formattedStr.replace(Constants.DATE_TIME, Constants.LOCAL_DATE_TIME);
		return formattedStr;
	}

	public String getInitCapital(String str) {
		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, str);
	}

	public String getCapInit(String str) {
		return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, str);
	}
}