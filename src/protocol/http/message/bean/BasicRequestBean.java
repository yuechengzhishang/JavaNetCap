package protocol.http.message.bean;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.TextUtils;

import common.util.UrlUtil;
import protocol.http.message.AbstractHttpMessageMsg;
import protocol.http.message.BasicRequestLineMsg;

public class BasicRequestBean {

	private BasicRequestLineMsg reqLine;
	private AbstractHttpMessageMsg header;
	private HttpEntity entity;
	private List<NameValuePair> parameters;
	private URI uri;

	public BasicRequestBean(String request) {
		String[] reqData = request.split("\n");
		this.setReqLine(reqData[0]);
		this.setParameters();
		
		String header = "", entity = "";
		for(int i = 1; i < reqData.length; i++){
			if(TextUtils.isEmpty(reqData[i])){
				if(i + 1 < reqData.length)
					entity = reqData[i+1];
				break;
			}
			header += reqData[i];
			header += "\r\n";
		}
		
		this.setHeader(header);
		this.setEntity(entity);
	}
	
	public BasicRequestLineMsg getReqLine() {
		return reqLine;
	}

	public void setReqLine(String reqLine) {
		this.reqLine = BasicRequestLineMsg.getRequestLine(reqLine);
	}

	public AbstractHttpMessageMsg getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = new AbstractHttpMessageMsg(header);
	}

	public HttpEntity getEntity() {
		return entity;
	}

	public void setEntity(HttpEntity entity) {
		this.entity = entity;
	}
	
	public void setEntity(String entity) {
		String[] kvs = UrlUtil.getURLDecoderString(entity).split("&");
		List<NameValuePair> postparams = new ArrayList<NameValuePair>();
		for(String kv : kvs){
			if(!TextUtils.isEmpty(kv)){
				String[] temp = kv.trim().split("=");
				if(temp.length > 1){
					String key = kv.trim().split("=")[0];
					String value = kv.trim().split("=")[1];
					postparams.add(new BasicNameValuePair(key, value));
				}
			}
		}
		this.entity = new UrlEncodedFormEntity(postparams, Consts.UTF_8);
	}

	public List<NameValuePair> getParameters() {
		return parameters;
	}

	public void setParameters(List<NameValuePair> parameters) {
		this.parameters = parameters;
	}
	
	public void setParameters() {
		List<NameValuePair> parameterList = new ArrayList<NameValuePair>();
		String[] temp = reqLine.getUri().split("\\?");
		if(temp.length > 1){
			if(!TextUtils.isEmpty(temp[1])){
				String[] kvs = temp[1].split("&");
				for(String kv : kvs){
					if(TextUtils.isEmpty(kv))
						parameterList.add(new BasicNameValuePair(kv.split("=")[0].trim(), kv.split("=")[1].trim()));
				}
			}
		}
		this.parameters = parameterList;
	}

	public URI getUri() {
		if (null == this.uri){
			try {
				String scheme = reqLine.getProtocolVersion().getProtocol().toLowerCase();
				String path = reqLine.getUri();
				this.uri = new URIBuilder()
						.setScheme(scheme)
						.setHost(this.getHeader().getFirstHeader("Host").getValue())
						.setPath(path)
						.build();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return this.uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}
	
	public void setUri(String uri) throws URISyntaxException {
		this.uri = new URI(uri);
	}
	
	/**
	 * 获取HttpUriRequest请求，在调用该方法前
	 * @return
	 */
	public HttpUriRequest getHttpUriRequest(){
		RequestBuilder rb = RequestBuilder.create(this.getReqLine()
				.getMethod())
				.setUri(this.getUri())
				.setVersion(this.getReqLine().getProtocolVersion())
				.setEntity(this.getEntity())
				.addParameters( this.getParameters().toArray(new NameValuePair[this.getParameters().size()]));
		for(Header h : this.getHeader().getAllHeaders()){
			rb.addHeader(h);
		}
		return rb.build();
	}

}
