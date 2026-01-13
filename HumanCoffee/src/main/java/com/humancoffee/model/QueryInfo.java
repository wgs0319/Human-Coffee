package com.humancoffee.model;

import java.util.Arrays;

public class QueryInfo {
	private String sql;
	private Object[] params;	//	PreparedStatement.setObject()에 전달된 파라미터들
	public byte	status = 0;		//	초기값 0, 처리완료 1, 에러 2
	
	public QueryInfo(String sql, Object... params) {
		this.sql = sql;
		this.params = params;
	}
	
	public String getSql() {
		return this.sql;
	}
	
	public Object[] getParams() {
		return params;
	}
	
	@Override
	public String toString() {
		return "QueryInfo{" + 
				"sql='" + sql + '\'' +
				", params=" + Arrays.toString(params) +
				'}';
	}
}
