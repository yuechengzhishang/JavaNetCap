package protocol.http.message;

import org.apache.http.ProtocolVersion;
import org.apache.http.message.BasicRequestLine;
import org.apache.http.util.TextUtils;

import protocol.http.ProtocolVersionBuild;

public class BasicRequestLineMsg extends BasicRequestLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BasicRequestLineMsg(String method, String uri, ProtocolVersion version) {
		super(method, uri, version);
	}

	public static BasicRequestLineMsg getRequestLine(String requestLine){
		if(!TextUtils.isEmpty(requestLine)){
			String method = requestLine.trim().split(" ")[0];
			String uri = requestLine.trim().split(" ")[1];
			ProtocolVersion version = ProtocolVersionBuild.create(requestLine.trim().split(" ")[2]);
			return new BasicRequestLineMsg(method, uri, version);
		} else {
			System.out.println("requestLine may not be empty...");
		}
		return null;
	}
}
