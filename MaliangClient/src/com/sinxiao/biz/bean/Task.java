package com.sinxiao.biz.bean;

import java.util.concurrent.Future;

public class Task {
	private Future<String> future;

	public void cancel() {
		if (future.isCancelled() && future.isDone() == false) {
			future.cancel(true);
		}
	}

	public void setFuture(Future<String> future) {
		this.future = future;
	}

}
