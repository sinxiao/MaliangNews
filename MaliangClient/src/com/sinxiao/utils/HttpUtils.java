package com.sinxiao.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.FormBody.Builder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import android.util.Log;

public class HttpUtils {
	private static OkHttpClient client;
	public static final MediaType JSON = MediaType
			.parse("application/json; charset=utf-8");
	public static final MediaType XML = MediaType
			.parse("application/xml; charset=utf-8");
	private static HashMap<String, Response> responses = new HashMap<String, Response>();
	private static HashMap<String, String> datas = new HashMap<String, String>();
	private static Lock lock = new ReentrantLock();
	private static Condition condition;
	static {
		if (client == null) {
			client = new OkHttpClient();
		}
		condition = lock.newCondition();
	};

	/**
	 * XML or JSON
	 * 
	 * @param url
	 * @param info
	 * @param isJson
	 * @return
	 */
	public static String httpPostRequest(String url, String info, boolean isJson) {
		RequestBody body = null;
		if (isJson) {
			body = RequestBody.create(JSON, info);
		} else {
			body = RequestBody.create(XML, info);
		}

		Request request = new Request.Builder().url(url).post(body).build();
		try {
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				return response.body().string();
			} else {
				return "error:" + response;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private static Request formateFormBody(String url,
			HashMap<String, String> values) {
		Set<String> keyset = values.keySet();

		Builder formBuilder = new FormBody.Builder();
		for (String key : keyset) {
			formBuilder.addEncoded(key, values.get(key));
			if (Utils.debug) {
				Log.e(TAG, "key >> " + key + " value >>　" + values.get(key));
			}

		}
		FormBody body = formBuilder.build();
		Request request = new Request.Builder().url(url).post(body).build();
		return request;
	}

	private static final String TAG = "HTTPUtils";

	/**
	 * �ύ ��ֵ��
	 * 
	 * @param url
	 *            �����ַ
	 * @param values
	 *            ��ֵ��
	 * @return
	 */
	public static String httpPostKeyValue(String url,
			HashMap<String, String> values) throws Exception {
		if (Utils.debug) {

			Log.e(TAG, "post url " + url);
		}
		Request request = formateFormBody(url, values);
		String data = null;
		final String uuid = UUID.randomUUID().toString();

		try {
			// Response response = client.newCall(request).execute();
			lock.lock();
			client.newCall(request).enqueue(new Callback() {

				public void onResponse(Call call, Response resp)
						throws IOException {
					String data = null;
					if (Utils.debug) {
						if (resp != null) {
							data = resp.body().string();
							Log.e(TAG, " onResponse >>  " + data);
						}
					}
					lock.lock();
					try {
//						 responses.put(uuid, resp);
						datas.put(uuid, data);
						condition.signal();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						lock.unlock();
					}
					// lock.unlock();
				}

				public void onFailure(Call call, IOException ioexception) {
					Log.e(TAG, " onFailure >>  ");
					lock.lock();
					try {
						condition.signal();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						lock.unlock();
					}
				}
			});
//			 Response response = null;

			if (Utils.debug) {

				Log.e(TAG, " uuid >>  " + uuid);
			}
			try {
				condition.await();
				if (datas.containsKey(uuid)) {
					data = datas.get(uuid);
				} else {
					if (Utils.debug) {

						Log.e(TAG, " response >> null ");
					}
				}
//				if (responses.containsKey(uuid)) {
//					response = responses.get(uuid);
//				} else {
//					if (Utils.debug) {
//
//						Log.e(TAG, " response >> null ");
//					}
//				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
			// lock.lock();

			if (data != null && !data.equals("")) {
//			if (response != null && response.isSuccessful()) {
//				 data = response.body().string();
//				 if (Utils.debug) {
//				
//				 Log.e(TAG, "data >>  " + data);
//				 }
				return data;
			} else {
				Log.e(TAG, " not sucess  ");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

		return null;
	}

	/**
	 * �첽�ύ���� ���ص�������CallBack��ʵ��
	 * 
	 * @param url
	 *            �����ַ
	 * @param data
	 *            �ύ����
	 * @param callback
	 *            �ص�����
	 * @return ��������ĵ��ã�����ȡ�����ò���
	 */
	public static Call postInforEnqueue(final String url, final String data,
			final OnResponseRev rev) {
		RequestBody body = RequestBody.create(
				MediaType.parse("application/x-www-form-urlencoded"), data);
		Request request = new Request.Builder().post(body).url(url).build();
		Call call = client.newCall(request);
		calls.put(url + data, call);
		call.enqueue(new Callback() {

			public void onResponse(Call arg0, Response resp) throws IOException {
				rev.onResponse(data, resp.body().string());
				calls.remove(url + data);
			}

			public void onFailure(Call call, IOException arg1) {
				rev.onFailure(arg1);
				calls.remove(url + data);
			}
		});

		return call;
	}

	public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType
			.parse("text/x-markdown; charset=utf-8");

	public static void uploadFire(String url, File file,
			Hashtable<String, String> headers, OnResponseRev rev) {

		Request request = new Request.Builder().url(url)
				.post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file)).build();

		Iterator<String> iterator = headers.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			String value = headers.get(key);
			request.headers().newBuilder().add(key, value);
		}

		try {
			Response response = client.newCall(request).execute();
			if (rev != null) {
				if (response.isSuccessful()) {
					rev.onResponse(url, "ok");
				} else {
					rev.onFailure(new IOException());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rev.onFailure(e);
		}

	}

	// private static final MediaType MEDIA_TYPE_PNG = MediaType
	// .parse("image/png");

	public static void downloadFile(String url, String path,
			DownloadLisenter rev) {
		Utils.showLog(TAG, "url >> " + url + " path >>" + path);
		Request request = new Request.Builder().url(url).build();

		Response response = null;
		try {
			response = client.newCall(request).execute();
			if (!response.isSuccessful()) {
				throw new IOException("Unexpected code " + response);
			}
			BufferedInputStream bufferInput = new BufferedInputStream(response
					.body().byteStream());
			rev.progress(1);

			File file = new File(path);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				// file.mkdir();
				file.createNewFile();
			} else {
				if (file.isDirectory()) {
					file.delete();
				} else {
					rev.progress(100);
					return;
				}

			}
			Utils.showLog(TAG, "path >> " + path);
			BufferedOutputStream writer = new BufferedOutputStream(
					new FileOutputStream(file));
			Utils.showLog(TAG, "111");
			int reachcount = 0;
			byte[] buffer = new byte[1024];
			int len = -1;
			len = bufferInput.read(buffer);
			reachcount += len;
			long sum = response.body().byteStream().available();
			sum = response.body().contentLength();
			Utils.showLog(TAG, " reachcount>> " + reachcount + " sum >> " + sum);
			while ((len) > 0) {
				writer.write(buffer, 0, len);
				if (rev != null) {
					long l = (long) ((float) reachcount / (float) sum * 100);
					Utils.showLog(TAG, " reachcount>> " + reachcount);
					if (l >= 100) {
						l = 99;
					}
					rev.progress(l);
				}
				len = bufferInput.read(buffer);
				reachcount += len;
			}

			bufferInput.close();
			writer.close();
			rev.progress(100);
			if (rev != null) {
				rev.onResponse(url, path);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rev.onFailure(e);
			Utils.showLog(TAG, "error " + e.getMessage());
		}

	}

	/**
	 * @param key
	 * @return
	 */
	public static boolean cancelCall(String key) {
		if (calls.containsKey(key)) {
			Call call = calls.remove(key);
			if (call != null && call.isCanceled() == false) {
				call.cancel();
				return true;
			}
		}
		return false;
	}

	private static Hashtable<String, Call> calls = new Hashtable<String, Call>();

	public static interface OnResponseRev {

		public void onFailure(IOException exception);

		public void onResponse(String data, String response);

	}

	public static interface DownloadLisenter {
		public void onFailure(IOException exception);

		public void onResponse(String data, String response);

		/**
		 * 完成了 count % 的下载
		 * 
		 * @param count
		 */
		public void progress(long count);
	}

}
