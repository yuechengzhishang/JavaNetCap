package netcap;

import jpcap.packet.Packet;
import protocol.http.HttpMethod;

@SuppressWarnings("restriction")
public class PacketUtils {
	
	public static boolean isHttpRequest(String data){
		if(data.trim().startsWith("GET") || data.trim().startsWith("POST")){
			return true;
		}
		return false;
	}

	public static boolean isHttpFirstResponse(String data){
		if(data.trim().startsWith("HTTP/")){
			return true;
		}
		return false;
	}
	
	public static boolean isHttpRequest(Packet packet){
		boolean flag = false;
		String data = new String(packet.data);
		for(String method : HttpMethod.getMethods()){
			if(data.trim().startsWith(method)){
				flag = true;
				break;
			}
		}
		return flag;
	}

	public static boolean isHttpFirstResponse(Packet packet){
		String data = new String(packet.data);
		if(data.trim().startsWith("HTTP/")){
			return true;
		}
		return false;
	}
}
