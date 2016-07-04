package cn.com.dc.app.client.biz;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android.util.Log;

public class Utils {
	private static final String TAG ="firepush";
	public static void showLog(String tag,String log)
	{
		Log.e(TAG,"firepush "+ tag+"  "+ log);;
	}
	
	 public static String getResult(String[] cmds){  
         ShellExecute cmdexe = new ShellExecute ( );  
         String result="";  
         try {
            result = cmdexe.execute(cmds, "/");  
        } catch (IOException e) {  
            Log.e(TAG, "IOException");  
            e.printStackTrace();  
        }  
        return result;  
    }  
    private static class ShellExecute {  
        /* 
         * args[0] : shell 命令  �??"ls" �??"ls -1"; 
         * args[1] : 命令执行路径  �??"/" ; 
         */  
        public String execute ( String [] cmmand,String directory)  
        throws IOException {  
        String result = "" ;  
        try {  
        ProcessBuilder builder = new ProcessBuilder(cmmand);  
          
        if ( directory != null )  
        builder.directory ( new File ( directory ) ) ;  
        builder.redirectErrorStream (true) ;  
        Process process = builder.start ( ) ;  
          
        //得到命令执行后的结果   
        InputStream is = process.getInputStream ( ) ;  
        byte[] buffer = new byte[1024] ;  
        while ( is.read(buffer) != -1 ) {  
        result = result + new String (buffer) ;  
        }  
        is.close ( ) ;  
        } catch ( Exception e ) {  
            e.printStackTrace ( ) ;  
        }  
        return result ;  
        }  
    }  
}
