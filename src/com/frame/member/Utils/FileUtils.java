package com.frame.member.Utils;

import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class FileUtils {
	
	
	public static File getCacheDiskPath(Context paramContext, String paramString)
	  {
	    String str = "/mnt/sdcard/Android/data/com.frame.member/cache";
	    if (paramContext != null) {
	      if ("mounted".equals(Environment.getExternalStorageState())) {
	        try
	        {
	          str = paramContext.getExternalCacheDir().getPath();
	        }
	        catch (Exception localException1)
	        {
	          Log.e("cache", "[getDiskCacheDir]", localException1);
	          try
	          {
	            str = paramContext.getCacheDir().getPath();
	          }
	          catch (Exception e)
	          {
	            Log.e("cache", "[getDiskCacheDir]", e);
	          }
	        }
	      } else {
	        try
	        {
	          str = paramContext.getCacheDir().getPath();
	        }
	        catch (Exception localException2)
	        {
	          Log.e("cache", "[getDiskCacheDir]", localException2);
	        }
	      }
	    }
	    return new File(str + File.separator + paramString);
	  }

}
