package protocol.http.message;

import org.apache.http.ProtocolVersion;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.util.TextUtils;

import protocol.http.ProtocolVersionBuild;

public class AbstractHttpMessageMsg extends AbstractHttpMessage {
	
	public AbstractHttpMessageMsg(String headerStr) {
//		super();
		if(TextUtils.isEmpty(headerStr))
			return;
		String[] headers = headerStr.split("\r\n");
		for(String header:headers){
			if(TextUtils.isEmpty(header.trim())) continue;
			String name = header.split(": ")[0].trim();
			String value = header.split(": ")[1].trim();
			/**
			 * 解决以下异常
			 * Caused by: org.apache.http.ProtocolException: Content-Length header already present
			 * Caused by: org.apache.http.ProtocolException: Content-Length header already present
			 */
			if(!name.equals("Content-Length") && !name.equals("Transfer-Encoding"))
				super.addHeader(name, value);
		}
	}

	public ProtocolVersion getProtocolVersion() {
		return ProtocolVersionBuild.getDefault();
	}
	
	public ProtocolVersion getProtocolVersion(String proVer) {
		return ProtocolVersionBuild.create(proVer);
	}
}
