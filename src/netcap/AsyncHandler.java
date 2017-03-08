package netcap;

import common.util.LogUtil;

/**
 * <pre> 简单异步处理示例. </pre>
 * 
 * @author zhouyelin
 * @version $Id: AsyncHandler.java, v 0.1 2017年1月17日 下午8:47:54 zhouyelin Exp $
 */
public class AsyncHandler implements Runnable {
	private static Class<?> cl = AsyncHandler.class;

    private JFrameMain frame;

    public void setFrame(JFrameMain frame){
    	this.frame = frame;
    }

	public void run() {
		while(true){
            synchronized (HttpDataQueues.queue) {
                while(HttpDataQueues.queue.isEmpty()){ //
                    try {
                    	HttpDataQueues.queue.wait(); //队列为空时，使线程处于等待状态
                    } catch (InterruptedException e) {
                    	LogUtil.err(cl, e);
                    }
                    LogUtil.debug(cl, "wait...");
                }
                HttpDataQueues.Task t= HttpDataQueues.queue.remove(0); //得到第一个
                t.setFrame(this.frame);
                t.dataProcess(); //执行该任务
                System.out.println("end");
            }
        }
	}


}
