package protocol.http.message.bean;

import protocol.http.message.AbstractHttpMessageMsg;
import protocol.http.message.BasicStatusLineMsg;

public class BasicResponseBean {

	private BasicStatusLineMsg statusLine;
	private AbstractHttpMessageMsg header;
	private String body;
	
	public BasicStatusLineMsg getStatusLine() {
		return statusLine;
	}
	
	public void setStatusLine(BasicStatusLineMsg statusLine) {
		this.statusLine = statusLine;
	}
	
	public AbstractHttpMessageMsg getHeader() {
		return header;
	}
	
	public void setHeader(AbstractHttpMessageMsg header) {
		this.header = header;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}

}
