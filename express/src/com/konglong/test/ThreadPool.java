package com.konglong.test;


 

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;





public class ThreadPool extends ThreadGroup {

    /**
     * 线程池关闭标志
     */
	private boolean isClose=false;
	/**
	 * doClsoe 方法执行标记
	 */
	private boolean doClose=false;
	/**
	 * 加锁标记
	 */
	private  Object lockObj=new Object();
	/**
	 * 任务集合
	 */
	private Queue<Runnable> taskList=new LinkedBlockingQueue<Runnable>();
	/**
	 * 线程池结束 处理接口
	 */
	private  PoolEndHandler   poolEndHandler=null;
	public  ThreadPool(String name,int poolSize,PoolEndHandler   poolEndHandler) {
		super(name);		
		this.setDaemon(true);	
		this.poolEndHandler=poolEndHandler;
		//开启工作线程
		for(int p=0;p<poolSize;p++){
			Thread t=new WorkThread();
			t.setPriority(Thread.MAX_PRIORITY);
			t.start();
		}
	}
	/**
	 * 添加任务线程
	 * @param task
	 */
	public void addTask(Runnable task){
		synchronized(lockObj){
			if(!isClose){
				taskList.offer(task);
			}
		}
	}
	
	/**
	 * 线程池 关闭
	 */
	public void close(){	
		    isClose=true;		
	}
	
	/**
	 * 线程池关闭 执行 doClose 
	 */
	public void  doClose(){
		synchronized(lockObj){
			if(poolEndHandler!=null && taskList.size()==0){				
				poolEndHandler.doEnd();
				doClose=true;
			}
		}
	}
	
	/**
	 * 工作线程
	 * @author Administrator
	 *
	 */
	private class WorkThread extends Thread{
	 	     
	    @SuppressWarnings("unused")
		private int count;
	    public WorkThread(){}
			       
		public void run(){
			while(true){
				synchronized(lockObj){
				if (taskList != null && !taskList.isEmpty()) {
						taskList.poll().run();
						count++;					 
					} else if (isClose) {// 如果线程池已经关闭 销毁工作线程
						// 线程池关闭后 执行 doClose 方法
						if(!doClose){
							doClose();
						}
						break;
					}
				}
			}
		}
		
	}
	
	
}
