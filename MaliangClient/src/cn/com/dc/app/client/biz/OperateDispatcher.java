package cn.com.dc.app.client.biz;

import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import com.sinxiao.biz.bean.TaskModule;
import com.sinxiao.mvp.bean.Rsponse2BeanCallBack;

public class OperateDispatcher {

	private ThreadFactory threadFactory = new ThreadFactory() {
		public Thread newThread(Runnable r) {
			Thread thread = new Thread(r);
			thread.setPriority(Thread.MAX_PRIORITY);
			if (timeoutcheckThread.isAlive() == false) {
				timeoutcheckThread.setName("OperateDispatcherTimeOutWatchDog");
				timeoutcheckThread.start();
			}
			return thread;
		}
	};
	private static OperateDispatcher dispatcher = new OperateDispatcher();

	private OperateDispatcher() {
	}

	public static ExecutorService getExecutor() {
		return dispatcher.executor;
	}

	private ExecutorService executor = Executors
			.newCachedThreadPool(threadFactory);

	private Vector<TaskModule> tasks = new Vector<TaskModule>();

	private Object mlock = new Object();
	/**
	 * 测超
	 */
	public Thread timeoutcheckThread = new Thread() {
		public void run() {

			synchronized (mlock) {
				while (true) {
					try {
						mlock.wait(5 * 1000);

						for (int i = tasks.size() - 1; i >= 0; i--) {
							TaskModule task = tasks.get(i);
							if (task.isTimeOut()) {
								if (task.future.isCancelled() == false
										&& task.future.isDone() == false) {
									task.future.cancel(true);
								}
								tasks.remove(task);
							}

						}
						// for (TaskModule task : tasks) {
						// if (task.isTimeOut()) {
						// if (task.future.isCancelled() == false
						// && task.future.isDone() == false) {
						// task.future.cancel(true);
						// }
						// tasks.remove(task);
						// }
						// }

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		};
	};

	private Hashtable<String, TaskModule> taskshash = new Hashtable<String, TaskModule>();

	public static void putOperator(String idString, Future<String> future,
			Rsponse2BeanCallBack rsp) {
		TaskModule tm = new TaskModule(future, rsp);
		dispatcher.taskshash.put(idString, tm);
		dispatcher.tasks.add(tm);
	}
	public static void removeOperator(String idString)
	{
		dispatcher.tasks.remove(dispatcher.taskshash.get(idString));
		dispatcher.taskshash.remove(idString);
	}
}
