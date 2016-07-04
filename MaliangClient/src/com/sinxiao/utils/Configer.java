package com.sinxiao.utils;

public class Configer {
	
	public static final int NETERROR = 10001;
	public static final int TIMEOUT = 10002;
	public static final int SERVERERROR = 10003;
	public static final int KILLED = 10004;
	public static final int DOWNLOADOK = 10005;
	public static final int NOCONNECT = 10006;
	public static final int TIMEOUTCONTROL = 10007;
	public static final int ERROR = 10008;

	public static final String COUNT = "3";
	
	public static boolean connection = false;
	public static boolean usewifi = true;

	public static float battery_remain = 0.0f;
	public static boolean pluged = false;

	/***
	 * 
	 */
	public static int REQ_POST_DATA = 1;
	/***
	 * 
	 */
	public static int REQ_CHK_VERSION = 2;
	
	/***
	 * 
	 */
	public static int REQ_POST_LOGIN = 5;

	/***
	 * 
	 */
	public static int STATUS_OK = 200;

}
