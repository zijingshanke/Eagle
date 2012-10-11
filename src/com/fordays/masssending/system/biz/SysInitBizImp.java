package com.fordays.masssending.system.biz;

import com.fordays.masssending.base.MainTask;
import com.neza.exception.AppException;

public class SysInitBizImp implements SysInitBiz {

	public void initPlatConfig() throws AppException {
		initThreadPool();
	}

	public void initThreadPool() throws AppException {
		try {
			MainTask task = MainTask.getInstance();
			Thread thread = new Thread(task);
			thread.start();
		} catch (Exception ex) {
			System.out.println("init MainTask fails... " + ex.getMessage());
		}
	}
}
