package common.tools;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import org.json.JSONException;
import org.json.JSONObject;

import common.util.ConstsUtil;

public class StringTools {

	/**
	 * json格式的字符串转换成json对象
	 * @param jsonStr
	 * @return
	 */
	public static JSONObject convertToJson(String jsonStr) {
		// contactList =
		// "{\"result\":{\"contact_count\":\"1\",\"contact_list\":[{\"lastModifiedTime\":\"2014-06-30
		// 13:43:40\",\"createTime\":\"2014-06-30
		// 13:43:40\",\"status\":0,\"dataFromFlag\":\"1\",\"groupMap\":[],\"givenName\":\"\u738b\u4fca\u5b87\",\"contactUserId\":\"1031853202\",\"contactId\":\"6612128302\",\"lastContactTime\":null,\"userId\":1031853202,\"name\":\"\u738b\u4fca\u5b87\",\"syncMobileFlag\":\"1\",\"groups\":[],\"mobile\":[\"18701257471\"]}]},\"id\":\"1404194084187\",\"jsonrpc\":\"2.0\"}";
		JSONObject json = null;
		try {
			json = new JSONObject(jsonStr);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return (null == str || str.length() < 1) ? true : false;
	}

	/**
	 * 判断字符串是否null
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		return null == str ? true : false;
	}

	/**
	 * 将字符串数组使用指定的字符串（joinSymbol）连接起来
	 * @param joinSymbol
	 * @param strings
	 * @return
	 */
	public static String joinStr(String joinSymbol, String... strings) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strings.length; ++i) {
			if (i == strings.length - 1) {
				sb.append(strings[i]);
			} else {
				sb.append(strings[i]).append(joinSymbol);
			}
		}
		return sb.toString();
	}

	/**
	 * 将Map使用指定的字符（joinSymbol）连接起来
	 * @param joinSymbol
	 * @param params
	 * @return
	 */
	public static String joinStr(String joinSymbol, Map<String, String> params) {
		return joinStr(joinSymbol, params, true);
	}

	/**
	 * 将Map使用指定的字符（joinSymbol）连接起来
	 * @param join
	 * @param params
	 * @param urlEncode 是否采用urlEncode编码
	 * @return
	 */
	public static String joinStr(String join, Map<String, String> params, boolean urlEncode) {
		StringBuilder sb = new StringBuilder();

		for (Entry<String, String> entry : params.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			if (value == null) {
				continue;
			}

			if (sb.length() > 0) {
				sb.append(join);
			}

			try {
				if (urlEncode) {
					sb.append(String.format("%s=%s", key, URLEncoder.encode(value, ConstsUtil.UTF8)));
				} else {
					sb.append(String.format("%s=%s", key, value));
				}
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e.getCause());
			}
		}

		return sb.toString();
	}
	
	public static String removeExcessSpaces(String str){
		//将字符串中的一个或多个空格用一个空格替换
		Pattern p = Pattern.compile("\\s+");
		Matcher m = p.matcher(str);
		String newStr = m.replaceAll(" ").trim();
		return newStr;
	}
	
	public static String convertStreamToString(InputStream is) {      
        /*  
          * To convert the InputStream to String we use the BufferedReader.readLine()  
          * method. We iterate until the BufferedReader return null which means  
          * there's no more data to read. Each line will appended to a StringBuilder  
          * and returned as String.  
          */     
         BufferedReader reader = new BufferedReader(new InputStreamReader(is));      
         StringBuilder sb = new StringBuilder();      
     
         String line = null;      
        try {      
            while ((line = reader.readLine()) != null) {      
                 sb.append(line + "\n");      
             }      
         } catch (IOException e) {      
             e.printStackTrace();      
         } finally {      
            try {      
                 is.close();      
             } catch (IOException e) {      
                 e.printStackTrace();      
             }      
         }      
     
        return sb.toString();      
     }
	
	/**
	 * java 合并两个byte数组
	 * @param byte_1
	 * @param byte_2
	 * @return
	 */
	public static byte[] byteMerger(List<byte[]> byteList) {
		int len = 0;
		for(byte[] b:byteList){
			len += b.length;
		}
		byte[] byteArray = new byte[len];
		int destPos = 0;
		for(int i = 0; i<byteList.size(); i++){
			System.arraycopy(byteList.get(i), 0, byteArray, destPos, byteList.get(i).length);
			destPos += byteList.get(i).length;
		}
		return byteArray;
	}
	
	public static String gzipToString(byte[] data) {
		String result = "";
        try {  
            ByteArrayInputStream bis = new ByteArrayInputStream(data);  
            GZIPInputStream gzip = new GZIPInputStream(bis);  
            byte[] buf = new byte[1024];  
            int num = -1;  
            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
            while ((num = gzip.read(buf, 0, buf.length)) != -1) {  
                baos.write(buf, 0, num);  
            }  
            byte[] b = baos.toByteArray();  
            baos.flush();  
            baos.close();  
            gzip.close();  
            bis.close(); 
            result = new String(b);
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
		return result;
	}

	// public static void main(String[] args) {
	// // TODO Auto-generated method stub
	//
	// }

}
