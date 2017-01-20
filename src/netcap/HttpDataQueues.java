package netcap;

import java.util.LinkedList;
import java.util.List;

import common.util.LogUtil;
import dao.HttpDataBean;
import dataprocess.DataSaveHandler;

public class HttpDataQueues {

	public static List<Task> queue = new LinkedList<Task>();
	
    /**
     * 假如 参数t 为任务
     * @param t
     */
    public static void add (Task t){
        synchronized (HttpDataQueues.queue) {
        	HttpDataQueues.queue.add(t); //添加任务
        	HttpDataQueues.queue.notifyAll();//激活该队列对应的执行线程，全部Run起来
        }
    }
    
    static class Task{
    	private static Class<?> cl = Task.class;
    	
    	private long id;
    	private byte[] reqData;
    	private byte[] rspData;
    	private JFrameMain frame;

        public void setFrame(JFrameMain frame){
        	this.frame = frame;
        }
    	
    	public Task(long id, byte[] reqData, byte[] rspData){
    		this.id = id;
    		this.reqData = reqData;
    		this.rspData = rspData;
    	}
    	
        public void dataProcess(){
        	HttpDataBean dataBean = new HttpDataBean(id, reqData, rspData);
        	if (dataBean != null && isValidReq(dataBean.getUri())) {
            	LogUtil.debug(cl, dataBean);
            	if(null != this.frame)
            		frame.updateView(dataBean);
            	DataSaveHandler.saveToExcel(dataBean);
            }
        }
    }
    
    public static boolean isValidReq(String url){
    	if(null == url || url.endsWith(".js") || url.endsWith(".css") || url.endsWith(".png"))
    		return false;
    	return true;
    }

}
