package protocol.http;

public enum HttpMethod {
	PUT("PUT", "功能跟post相似，用来将信息放到请求的URL（URI）上，put方法是幂等方法， post非幂等方法，put在请求时容易造成数据冗余， 而post则不然。"), 
	DELETE("DELETE", "与PUT对应，用于删除请求URL上的某个资源， 该请求返回状态有3种：（200：表示删除请求被成功执行，返回被删除的资源；202：表示删除请求被接受，但还没有被执行； 204：表示删除请求被执行，但没有返回被删除的资源"), 
	POST("POST", "主要是向指定的URL（URI）提交数据, 通常用于表单发送，psot所传递的数据或参数不是已明文形式存在的，而是封装后的，因此相对安全系数高，像注册、登录、提交表单都是用该方法实现的。"), 
	GET("GET", " 主要用于向指定的URL（URI）请求资源（资源文件或是数据均可）， 可以带参数也可以不带参数， 带参数时，参数是明文传递，你可以在浏览器的地址栏中看到参数名及参数值，get安全性不高，所以常用于安全性要求低的场合， 比如登录后请求数据。"), 
	OPTIONS("OPTIONS", "OPTIONS请求方法的主要用途有两个：1、获取服务器支持的HTTP请求方法；也是黑客经常使用的方法。2、用来检查服务器的性能。例如：AJAX进行跨域请求时的预检，需要向另外一个域名的资源发送一个HTTP OPTIONS请求头，用以判断实际发送的请求是否安全。"), 
	HEAD("HEAD", "HEAD方法跟GET方法相同，只不过服务器响应时不会返回消息体。"), 
	TRACE("TRACE", "TRACE_Method是HTTP（超文本传输）协议定义的一种协议调试方法，该方法会使服务器原样返回任意客户端请求的任何内容。"), 
	CONNECT("CONNECT", "CONNECT这个方法的作用就是把服务器作为跳板，让服务器代替用户去访问其它网页，之后把数据原原本本的返回给用户。");
	
	private String method;
	private String desc;
	
	private HttpMethod(String method, String desc){
		this.setMethod(method);
		this.setDesc(desc);
	}
	
	public static HttpMethod getHttpMethod(String method){
		for (HttpMethod m : HttpMethod.values()) {
            if(m.getMethod().equals(method))
            	return m;
        }
		return null;
	}
	
	public static String[] getMethods() {
		int count = HttpMethod.values().length;
		String[] methods = new String[count];
		for (int i = 0; i < count; i++) {
            methods[i] = HttpMethod.values()[i].getMethod();
        }
		return methods;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
