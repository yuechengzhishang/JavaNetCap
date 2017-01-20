package protocol.http.message;

import org.apache.http.ProtocolVersion;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.util.TextUtils;

import protocol.http.ProtocolVersionBuild;

public class BasicStatusLineMsg extends BasicStatusLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BasicStatusLineMsg(ProtocolVersion version, int statusCode, String reasonPhrase) {
		super(version, statusCode, reasonPhrase);
	}

	public static BasicStatusLineMsg getStatusLine(String statusLine){
		if(!TextUtils.isEmpty(statusLine)){
			String[] temp = statusLine.trim().split(" ");
			ProtocolVersion version = ProtocolVersionBuild.create(temp[0]);
			int statusCode = Integer.valueOf(temp[1]);
			String reasonPhrase = "";
			if(temp.length > 2)
				reasonPhrase = temp[2];
			return new BasicStatusLineMsg(version, statusCode, reasonPhrase);
		} else {
			System.out.println("statusLine may not be empty...");
		}
		return null;
	}
}
