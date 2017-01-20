package dao;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.TextUtils;

import common.util.ConstsUtil;
import protocol.http.HttpMethod;
import protocol.http.message.AbstractHttpMessageMsg;
import protocol.http.message.BasicRequestLineMsg;
import protocol.http.message.BasicStatusLineMsg;

public class HttpDataBean {
	
	private long id;
	
	private BasicRequestLineMsg requestLine;
	private String requestHeader;
	private String requestParams;
	
	private BasicStatusLineMsg responseStatusLine;
	private String responseHeader;
	private String responseBody;

	public HttpDataBean(long id, byte[] reqData, byte[] rspData) {
		this.setId(id);
		initRequestData(new String(reqData));
		initResponseData(new String(rspData));
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	private BasicRequestLineMsg getRequestLine() {
		return requestLine;
	}

	private void setRequestLine(String requestLine) {
		this.requestLine = BasicRequestLineMsg.getRequestLine(requestLine);
	}

	public String getMethod() {
		if(null != this.requestLine)
			return this.requestLine.getMethod();
		return null;
	}

	public String getUri() {
		if(null != this.requestLine)
			return this.requestLine.getUri();
		return null;
	}
	
	public String getRequestHeader() {
		return this.requestHeader;
	}

	public void setRequestHeader(String requestHeader) {
		this.requestHeader = requestHeader;
	}

	public String getRequestParamsStr() {
		return this.requestParams;
	}

	public void setRequestParams(String requestParams) {
		this.requestParams = requestParams;
	}
	
	private void setResponseStatusLine(String responseStatusLine) {
		this.responseStatusLine = BasicStatusLineMsg.getStatusLine(responseStatusLine);
	}
	
	public int getResponseCode() {
		if(null != this.responseStatusLine)
			return this.responseStatusLine.getStatusCode();
		return -1;
	}
	
	public String getResponseMsg() {
		if(null != this.responseStatusLine)
			return this.responseStatusLine.getReasonPhrase();
		return null;
	}
	
	public String getResponseHeader() {
		return this.responseHeader;
	}
	
	public void setResponseHeader(String responseHeader) {
		this.responseHeader = responseHeader;
	}

	public String getResponseBody() {
		return this.responseBody;
	}
	
	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	public String getUrl() {
		if(null != this.getRequestHeader() && null != this.requestLine)
			return "http://" + new AbstractHttpMessageMsg(this.requestHeader).getFirstHeader("Host").getValue().trim() + this.getUri();
		return null;
	}
	
	private void initRequestData(String requestData){
		int index = requestData.indexOf(ConstsUtil.CRLF);
		this.setRequestLine(requestData.trim().substring(0, index));
		String[] data = requestData.trim().substring(index).split(ConstsUtil.CRLF+ConstsUtil.CRLF);
		this.setRequestHeader(data[0]);
		String params = "";
		switch(HttpMethod.getHttpMethod(this.getMethod())){
		case GET:
			String[] temp = this.getUri().split(ConstsUtil.QUES);
			if(temp.length > 2)
				params = temp[1];
			break;
		case POST:
			if(data.length > 1)
				params = data[1];
			break;
		case PUT:
		case DELETE:
		case OPTIONS:
		case HEAD:
		case TRACE:
		case CONNECT:
		default:
			break;
		}
		this.setRequestParams(params);
		this.setRequestHeader(data[0]);
	}
	
	private void initResponseData(String rspData){
		String rspBody = "";
		int index = rspData.indexOf(ConstsUtil.CRLF);
		this.setResponseStatusLine(rspData.trim().substring(0, index));
//		String[] data = initData(rspData);
		String[] data = rspData.trim().substring(index).split(ConstsUtil.CRLF+ConstsUtil.CRLF);
		this.setResponseHeader(data[0]);
//			if(getHeader(this.responseHeader, "Content-Encoding").getValue().contains("gzip")){
//				rspBody = StringTools.gzipToString(packet.data);
		if(getHeader(this.responseHeader, "Content-Type").getValue().contains("image")){
			rspBody = "Image";
		} else{
			rspBody = data[1];
		}
		this.setResponseBody(rspBody);
	}
	
	public Header getHeader(String header, String name) {
		int begin = header.indexOf(name) + name.length() + 2;
		String temp = header.substring(begin);
		int end = temp.indexOf(ConstsUtil.CRLF);
		Header h = new BasicHeader(name, temp.substring(0, end));
		return h;
	}

//	private String[] initData(String data){
//		String header = "", body = "";
//		String[] dataArray = data.split(ConstsUtil.CRLF);
//		for(int i = 1; i < dataArray.length; i++){
//			if(!TextUtils.isEmpty(dataArray[i])){
//				header = header + (dataArray[i] + ConstsUtil.CRLF);
//			} else {
//				for(int j = i; j < dataArray.length; j++){
//					if(!TextUtils.isEmpty(dataArray[j]))
//						body = dataArray[j];
//				}
//				break;
//			}
//		}
//		return new String[]{header, body};
//	}
	
	/**
	 * 获取HttpUriRequest请求，在调用该方法前
	 * @return
	 */
	public HttpUriRequest getHttpUriRequest(){
		RequestBuilder rb = null;
		try {
			rb = RequestBuilder.create(this.getRequestLine()
					.getMethod())
					.setUri(this.getUri())
					.setVersion(this.getRequestLine().getProtocolVersion())
					.setEntity(new UrlEncodedFormEntity(this.getRequestParams()))
					.addParameters( this.getRequestParams().toArray(new NameValuePair[this.getRequestParams().size()]));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		for(Header h : new AbstractHttpMessageMsg(this.requestHeader).getAllHeaders()){
			rb.addHeader(h);
		}
		return rb.build();
	}

	public List<NameValuePair> getRequestParams() {
		return getParamsList(this.requestParams);
	}
	
	private List<NameValuePair> getParamsList(String kvs){
		List<NameValuePair> parameterList = new ArrayList<NameValuePair>();
		if(!TextUtils.isEmpty(kvs)){
			String[] kvsArray = kvs.split(ConstsUtil.AND);
			for(String kv : kvsArray){
				if(!TextUtils.isEmpty(kv))
					parameterList.add(new BasicNameValuePair(kv.split(ConstsUtil.EQUAL)[0].trim(), kv.split(ConstsUtil.EQUAL)[1].trim()));
			}
		}
		return parameterList;
	}
	
	public static void main(String[] args) {
		String str = "abc";
		str += ("def" + "123");
		System.out.println(str);
	}
}
