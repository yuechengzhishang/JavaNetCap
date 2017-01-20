package protocol.http;

import org.apache.http.ProtocolVersion;
import org.apache.http.util.TextUtils;

public class ProtocolVersionBuild extends ProtocolVersion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProtocolVersionBuild(String protocol, int major, int minor) {
		super(protocol, major, minor);
	}
	
	public static ProtocolVersion getDefault() {
		return new ProtocolVersionBuild("HTTP", 1, 1);
	}
	
	public static ProtocolVersion create(String proVer) {
		if(!TextUtils.isEmpty(proVer)){
			String protocol = proVer.trim().split("/")[0];
			String version = proVer.trim().split("/")[1];
			int major = Integer.valueOf(version.trim().split("\\.")[0]);
			int minor = Integer.valueOf(version.trim().split("\\.")[1]);
			return new ProtocolVersionBuild(protocol, major, minor);
		} else {
			System.out.println("proVer may not be empty...");
			return new ProtocolVersionBuild("HTTP", 1, 1);
		}
	}

//	public static void main(String[] args) {
//		String str = "1.1";
//		System.out.println(str.split("\\.")[0]);
//		System.out.println(str.trim().split("\\.")[1]);
//	}
}
