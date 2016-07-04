package com.sinxiao.mvp.bean;

import java.util.List;

public class HttpResult<T> {
	public int method_id;
	public int result_status;
	public List<T> objs;
}
