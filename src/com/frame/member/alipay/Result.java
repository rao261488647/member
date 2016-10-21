package com.frame.member.alipay;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import android.util.Log;

public class Result {

	private static final Map<String, String> sResultStatus;
	public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	public static final String PRIVATE = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMAdWn2mSpSTc+ARmYMu0NgFjAGjyPaPwLMIAU5Mu7oM3WqtvDZtfOuIuAXceKYjXjTaOiIVx71El2r06ySrISQLtCuiuuWOG3nftGcjLz9JR6IIUd7CpSa4Ld2JpDv/eUJm0iIshQc3FfinKUgd9SdSHPynZ7MQ3yelIXnPOI7FAgMBAAECgYAE9b0MWWq7g0MOmeW4uVCzAxGmSTA7DWMQPDisaNa/6u0cf/fH//OlsRXvMM38nBUmkCvIDX/mpwqxvDkF7VUhqYqUPi80IC26pbAttYAURkzHktkH/ROTIFcILXxWVXiIHoUjAMSbOAGwfwpVyvyzeqUotfesvmkcOzkZurO9AQJBAP+bgv80Eu/R3QubniLsNFuYUs+DyaYoJwAFSLUhoLeoaX7Zj8fvpSmXhNA/Um8rWrQXdNDcFvziqVehqboLb/ECQQDAaOFpgteNqp9WZIbHtSg5LOhk5KalVGt1QobVCZf7IH5E+14OtEARl8t7tJNwrjEt9n9/VowG/x6ha7J1LmAVAkA6x6ldRL1iFxHNHJFGGKmFssbAX98cbbliQNTSipQqB/mSqTduVAbYRLbPKPCtDfxC0+4SJbrHltw4gb3FQeIRAkABNmf9PzrWhLW+WKHUzlpzicnpINu//Pk0Yvfwjb/scDb7fk4Ib7BbrAY3QFsRgbeDn6MMDWKShLcPs1sLjABFAkBVZMdZ37QNmzNzc71OFQFbQd3iDW+bbKBnUhhmt/BooPnhXUNZ9VKyvjqQ1OCR285e3+8jevkavFVckaD3MCo/";
	public static final String DEFAULT_PARTNER = "2088311520075721";
	public static final String DEFAULT_SELLER = "onlinepayments@vip.sina.com";
	private String mResult;

	String resultStatus = null;
	String memo = null;
	String result = null;
	boolean isSignOk = false;

	public Result(String result) {
		this.mResult = result;
	}

	static {
		sResultStatus = new HashMap<String, String>();
		sResultStatus.put("9000", "操作成功");
		sResultStatus.put("4000", "系统异常");
		sResultStatus.put("4001", "数据格式不正确");
		sResultStatus.put("4003", "该用户绑定的支付宝账户被冻结或不允许支付");
		sResultStatus.put("4004", "该用户已解除绑定");
		sResultStatus.put("4005", "绑定失败或没有绑定");
		sResultStatus.put("4006", "订单支付失败");
		sResultStatus.put("4010", "重新绑定账户");
		sResultStatus.put("6000", "支付服务正在进行升级操作");
		sResultStatus.put("6001", "用户中途取消支付操作");
		sResultStatus.put("7001", "网页支付失败");
	}

	public String getResult() {
		String src = mResult.replace("{", "");
		src = src.replace("}", "");
		return getContent(src, "memo=", ";result");
	}

	public void parseResult() {

		try {
			String src = mResult.replace("{", "");
			src = src.replace("}", "");
			String rs = getContent(src, "resultStatus=", ";memo");
			if (sResultStatus.containsKey(rs)) {
				resultStatus = sResultStatus.get(rs);
			} else {
				resultStatus = "其他错误";
			}
			resultStatus += "(" + rs + ")";

			memo = getContent(src, "memo=", ";result");
			result = getContent(src, "result=", null);
			isSignOk = checkSign(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean checkSign(String result) {
		boolean retVal = false;
		try {
			JSONObject json = string2JSON(result, "&");

			int pos = result.indexOf("&sign_type=");
			String signContent = result.substring(0, pos);

			String signType = json.getString("sign_type");
			signType = signType.replace("\"", "");

			String sign = json.getString("sign");
			sign = sign.replace("\"", "");

			if (signType.equalsIgnoreCase("RSA")) {
				retVal = Rsa.doCheck(signContent, sign, PUBLIC);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("Result", "Exception =" + e);
		}
		Log.i("Result", "checkSign =" + retVal);
		return retVal;
	}

	public JSONObject string2JSON(String src, String split) {
		JSONObject json = new JSONObject();

		try {
			String[] arr = src.split(split);
			for (int i = 0; i < arr.length; i++) {
				String[] arrKey = arr[i].split("=");
				json.put(arrKey[0], arr[i].substring(arrKey[0].length() + 1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return json;
	}

	private String getContent(String src, String startTag, String endTag) {
		String content = src;
		int start = src.indexOf(startTag);
		start += startTag.length();

		try {
			if (endTag != null) {
				int end = src.indexOf(endTag);
				content = src.substring(start, end);
			} else {
				content = src.substring(start);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return content;
	}
}
