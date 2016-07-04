package com.sinxiao.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

public class Utils {
	/***
	 * 
	 */
	public static final int True = 1;
	/***
	 * 
	 */
	public static final int Flase = 0;
	/***
	 * 
	 */
	public static final String APP_FIRST_INIT = "app_first_init";
	/***
	 * 
	 */
	public static final String SPC_NAME = "data";
	/***
	 * 
	 */
	public static Context mContext;
	/***
	 * 
	 */
	public static boolean debug = true;
	/***
	 * 
	 */
	public static String INFOR = "advisteration";
	/***
	 * 
	 */
	public static String INFOR_GETOK = "com.dc.mobile.dcshare.utils.ADV_GETOK";
	/***
	 * 
	 */
	private final static String tag = "dcshare";
	private static FileWriter fw = null;

	/***
	 * 
	 * @param t
	 * @return
	 */
	public static File getFileByURL(String t) {
		// 存储路径
		File file = Environment.getExternalStorageDirectory();
		String url = t.toLowerCase();
		String[] spt = url.split("/");
		file = new File(file.getAbsolutePath() + "/dcback/");
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(file.getAbsolutePath(), spt[spt.length - 1].trim());

		return file;
	}

	/***
	 * 获得当前页码
	 * 
	 * @return 页码
	 */
	public static int getPageIdx(Context cxt) {
		return readIntValueFromSpc(cxt, PAGE_IDX, 0);
	}

	/***
	 * 页码 PAGE_IDX = "pg_idx";
	 * 
	 */
	public static String PAGE_IDX = "pg_idx";
	/***
	 * 每页的大�? PAGE_COUNT = 80;
	 * 
	 */
	public static final int PAGE_COUNT = 80;

	/***
	 * 保存当前的页�?
	 * 
	 * @param cxt
	 * @param idx
	 * @return
	 */
	public static boolean savePageIdx(Context cxt, int idx) {
		return saveIntValueIntoSpc(cxt, PAGE_IDX, idx);
	}

	/***
	 * 
	 * @return
	 */
	public static int getPageCount() {

		return PAGE_COUNT;
	}

	/**
	 * 返回的信息为 ../kanmeinv/xxx.xx
	 * 
	 * @param url
	 * @return
	 */
	public static String getDefaultPath(String url) {
		String[] urls = url.split("/");
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		path += "/kanmeinv/";
		if (urls != null && urls.length > 0) {

			path += urls[urls.length - 1];
		}

		return path;
	}

	/****
	 * 
	 * @param mContext
	 * @return
	 */
	public static boolean getNetWorkAvailable(Context mContext) {
		ConnectivityManager manager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] all = manager.getAllNetworkInfo();
		boolean available = false;
		for (NetworkInfo networkInfo : all) {
			if (networkInfo.getState() == android.net.NetworkInfo.State.CONNECTED) {
				available = true;
			}
		}
		return available;
	}

	/***
	 * 
	 */
	public static final String IMAGE_VALUE = "imgavalue";

	/***
	 * 
	 * @param msg
	 * @param strings
	 */
	public static void showLog(String msg, String... strings) {
		StringBuffer sbf = new StringBuffer();
		if (debug) {
			sbf.append(msg);
			for (int i = 0; i < strings.length; i++) {
				sbf.append(strings[i]);
			}
			Log.e(tag, sbf.toString());
		} else {
			if (fw == null) {
				if (mContext != null) {
					String path = mContext.getCacheDir().getAbsolutePath()
							+ "/log.txt";
					try {
						fw = new FileWriter(new File(path));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			if (fw != null) {
				Date d = new Date();

				sbf.append(d.toGMTString()).append(" === ").append(msg);
				try {

					fw.append(sbf.toString());
					fw.flush();
					fw.close();
					fw = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 根据key获取保存的int�??
	 * 
	 * @param cxt
	 * @param key
	 * @param def
	 * @return
	 */
	public static int readIntValueFromSpc(Context cxt, String key, int def) {
		if (null == cxt) {
			cxt = mContext;
		}
		return cxt.getSharedPreferences(SPC_NAME, 0).getInt(key, def);
	}

	/***
	 * add by lynchxu ,fix the bug above the android4.0 show error
	 * 
	 * @param layoutId
	 * @return the show Popupwindow
	 */
	public static PopupWindow getPopupWindow(Context cxt, int layoutId) {
		LayoutInflater mLayoutInfalter = LayoutInflater.from(cxt);
		View menuView = mLayoutInfalter.inflate(layoutId, null);
		PopupWindow popupWindow = new PopupWindow(cxt);
		popupWindow = new PopupWindow(menuView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, false);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setOutsideTouchable(true);
		return popupWindow;
	}

	/***
	 * 根据key获取保存的String�??
	 * 
	 * @param cxt
	 * @param key
	 * @param def
	 * @return
	 */
	public static String readStringValueFormSpc(Context cxt, String key,
			String def) {
		if (null == cxt) {
			cxt = mContext;
		}
		return cxt.getSharedPreferences(SPC_NAME, 0).getString(key, def);
	}

	/***
	 * 根据key保存int�??
	 * 
	 * @param cxt
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean saveIntValueIntoSpc(Context cxt, String key, int value) {
		if (null == cxt) {
			cxt = mContext;
		}

		return cxt.getSharedPreferences(SPC_NAME, 0).edit().putInt(key, value)
				.commit();
	}

	/***
	 * 保存String�??
	 * 
	 * @param cxt
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean saveStringValueIntoSpc(Context cxt, String key,
			String value) {
		if (null == cxt) {
			cxt = mContext;
		}
		return cxt.getSharedPreferences(SPC_NAME, 0).edit()
				.putString(key, value).commit();
	}

	/***
	 * 把dip转换成px
	 * 
	 * @param context
	 * @param nValue
	 * @return
	 */
	public static int dip2px(Context context, int nValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (nValue * scale + 0.5f);
	}

	// 加密 解密 认证的算�??

	//
	/* Hash "SHA-1" */
	/**
	 * 获得SHA-1 得到的是 byte[]
	 * 
	 * @param inBuffer
	 *            获得hash value 的数�??
	 * @return hash value byte[]
	 */
	public static byte[] GetHashValue(byte[] inBuffer) {
		byte[] hashValue = null;

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.reset();
			digest.update(inBuffer);
			hashValue = digest.digest();
		} catch (Exception e) {
			System.out.println("Hash SHA-1 error:" + e.getMessage());
			return "0000000000".getBytes();
		}

		return hashValue;
	}

	/***
	 * 获得数据的摘要，以字符串形式显示
	 * 
	 * @param input
	 *            数据
	 * @return 数据摘要
	 */
	public static String getSha1(byte[] input) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA1");
		} catch (Exception e) {
			return "error in encrypting";
		}
		md.reset();
		md.update(input);
		byte[] encodedPassword = md.digest();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < encodedPassword.length; i++) {
			if ((encodedPassword[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
		}
		return buf.toString();
	}

	/***
	 * 
	 * @param secret
	 * @param text
	 * @return
	 */
	public static byte[] hmac_sha1(byte[] secret, byte[] text) {
		byte[] hmacValue = null;
		byte ipad = 0x36;
		byte opad = 0x5c;

		try {
			int secretlen = secret.length;
			byte[] tmpkeybyte = null;
			if (secretlen > 64) {
				MessageDigest H = MessageDigest.getInstance("SHA-1");
				tmpkeybyte = H.digest(secret);
			} else {
				tmpkeybyte = new byte[secretlen];
				for (int i = 0; i < secretlen; i++) {
					tmpkeybyte[i] = secret[i];
				}
			}

			int keylen = tmpkeybyte.length;
			byte[] keybyte = new byte[64];
			for (int i = 0; i < 64; i++) {
				if (i < keylen)
					keybyte[i] = tmpkeybyte[i];
				else
					keybyte[i] = 0;
			}

			byte[] tmpkeyipad = new byte[64];
			for (int i = 0; i < 64; i++) {
				tmpkeyipad[i] = (byte) (keybyte[i] ^ ipad);
			}

			byte[] keyopad = new byte[64];
			for (int i = 0; i < 64; i++) {
				keyopad[i] = (byte) (keybyte[i] ^ opad);
			}

			byte[] keyipad = new byte[64 + text.length];
			for (int i = 0; i < 64 + text.length; i++) {
				if (i < 64)
					keyipad[i] = tmpkeyipad[i];
				else
					keyipad[i] = text[i - 64];
			}

			MessageDigest shaipad = MessageDigest.getInstance("SHA-1");
			byte[] shaipadout = shaipad.digest(keyipad);

			byte[] hmacin = new byte[64 + shaipadout.length];
			for (int i = 0; i < 64 + shaipadout.length; i++) {
				if (i < 64)
					hmacin[i] = keyopad[i];
				else
					hmacin[i] = shaipadout[i - 64];
			}

			MessageDigest hmacout = MessageDigest.getInstance("SHA-1");
			hmacValue = hmacout.digest(hmacin);

		} catch (Exception e) {
			System.out.println("hmac_sha1 error:" + e.getMessage());
			return "0000000000".getBytes();
		}
		return hmacValue;
	}

	/**
	 * Hex 转换�??string
	 * 
	 * @param b
	 * @return
	 */
	public static String getHexString(byte[] b) {
		String result = "";
		if (b == null) {
			System.out.println("error: getHexString is null");
			return result;
		}
		for (int i = 0; i < b.length; i++) {
			String tstr = Integer.toString((b[i] & 0xff) + 0x100, 16);
			if (tstr != null)
				result += tstr.substring(1);
		}
		return result;
	}

	/***
	 * 字符�??获得 字节数据
	 * 
	 * @param s
	 * @return
	 */
	public static byte[] getHexBytes(String s) {
		byte[] result = new byte[s.length() / 2];
		char[] enc = s.toCharArray();
		for (int i = 0; i < result.length; i++) {
			StringBuilder curr = new StringBuilder(2);
			curr.append(enc[i * 2]).append(enc[i * 2 + 1]);
			result[i] = (byte) Integer.parseInt(curr.toString(), 16);
		}
		return result;
	}

	/* 3DES ECB */
	/***
	 * Des 加密
	 * 
	 * @param key
	 * @param data
	 * @return
	 */
	public static byte[] Des3EncodeECB(byte[] key, byte[] data) {
		Key deskey = null;
		byte[] bOut = null;

		byte[] k = new byte[24];
		if (key.length == 16) {
			System.arraycopy(key, 0, k, 0, 16);
			System.arraycopy(key, 0, k, 16, 8);
		} else {
			System.arraycopy(key, 0, k, 0, 24);
		}

		try {
			DESedeKeySpec spec = new DESedeKeySpec(k);
			SecretKeyFactory keyfactory = SecretKeyFactory
					.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);

			Cipher cipher = Cipher.getInstance("desede" + "/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, deskey);
			bOut = cipher.doFinal(data);
		} catch (Exception e) {
			System.out.println("Des3 Encode ECB error:" + e.getMessage());
		}

		return bOut;
	}

	/***
	 * Des 解密
	 * 
	 * @param key
	 * @param data
	 * @return
	 */
	public static byte[] Des3DecodeECB(byte[] key, byte[] data) {
		Key deskey = null;
		byte[] bOut = null;

		byte[] k = new byte[24];
		if (key.length == 16) {
			System.arraycopy(key, 0, k, 0, 16);
			System.arraycopy(key, 0, k, 16, 8);
		} else {
			System.arraycopy(key, 0, k, 0, 24);
		}

		try {
			DESedeKeySpec spec = new DESedeKeySpec(k);
			SecretKeyFactory keyfactory = SecretKeyFactory
					.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);

			Cipher cipher = Cipher.getInstance("desede" + "/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, deskey);
			bOut = cipher.doFinal(data);
		} catch (Exception e) {
			System.out.println("Des3 Decode ECB error:" + e.getMessage());
		}

		return bOut;
	}

	// private static byte[] MAIN_KEY =
	// {0x45,0x72,0x74,0x29,(byte)0xa8,(byte)0xbe,(byte)0xa1,0x34,(byte)0xe5,0x5d,(byte)0xaa,0x06,(byte)0xff,0x4d,(byte)0xa5,(byte)0xac};
	// factor key: 8bytes
	public static void main(String[] args) {
		// byte[] b = new String("ssssssssssss").toString().getBytes();
		// String hex = getHexString(b);
		// System.out.println("hex is "+hex);
		//
		// String hex1 = getHexString(b);
		// System.out.println("hex1 is "+hex1);
	}

}
