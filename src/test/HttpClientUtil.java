package test;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import common.tools.FileTools;
import protocol.http.message.bean.BasicRequestBean;

public class HttpClientUtil {
	public static void main(String[] args) {
		String file = "requests.txt";
		String REQUEST_SEPARATOR = "\n🐵🙈🙉\n";
		String[] requests = FileTools.readFileToString(file, REQUEST_SEPARATOR);
		for(String request : requests){
			if(null == request || "".equals(request))
				continue;
			sendRequest(request);
		}
	}
	
	/**
	 * 发送http请求，并返回响应结果
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static HttpResponse sendRequest(String request){
		if(!request.trim().startsWith("GET") && !request.trim().startsWith("POST")) return null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(new BasicRequestBean(request).getHttpUriRequest());
			System.out.println("response---->" + response.toString());
			System.out.println("responseEntity---->" + EntityUtils.toString(response.getEntity()));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(null != response){
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("response is null...");
			}
		}
		return response;
	}
}