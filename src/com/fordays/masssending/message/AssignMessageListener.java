package com.fordays.masssending.message;

import com.fordays.masssending.base.util.LogUtil;
import com.fordays.masssending.message.biz.AssignMessageBiz;

/**
 * 发帖任务监听
 */
public class AssignMessageListener implements Runnable {
	private AssignMessageBiz assignMessageBiz;
	private LogUtil myLog;
	private AssignMessageListForm amlf;

	/**
	 * 订单监听器（可自定义订单查询次数）
	 * 
	 * @param AssignMessageBiz
	 *            AssignMessageBiz
	 * @param int
	 *            customCount
	 */
	public AssignMessageListener(AssignMessageListForm amlf,
			AssignMessageBiz assignMessageBiz) {
		super();
		this.amlf = amlf;
		this.assignMessageBiz = assignMessageBiz;
	}

	public void run() {
		try {
			myLog = new LogUtil(false, false, AssignMessageListener.class);

			int sleepSecond = amlf.getSpaceTime() * 1000 * 60;

			int[] selectedItems = amlf.getSelectedItems();
			int amlfLength = selectedItems.length;

			for (int i = 0; i < amlfLength; i++) {
				int assignMessageId = selectedItems[i];

				MessageInfo messageInfo = assignMessageBiz
						.getMessageInfoByAssignMessageId(assignMessageId);

				messageInfo = assignMessageBiz.checkMessageInfo(messageInfo);

				if (messageInfo.isValidated()) {
					messageInfo = assignMessageBiz
							.executeSendMessage(messageInfo);
					if (messageInfo != null) {
						if (messageInfo.isValidated() == false) {
							messageInfo.setSendedStatus(new Long(3));
						}
						assignMessageBiz
								.updateAssignMessageASMessageInfo(messageInfo);
					}
				} else {
					System.out.println("messageInfo 格式校验错误");
				}
				if (i < (amlfLength - 1)) {
					Thread.sleep(sleepSecond);
				}
			}
			myLog.info("发帖任务监听线程结束....");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public AssignMessageListForm getAmlf() {
		return amlf;
	}

	public void setAmlf(AssignMessageListForm amlf) {
		this.amlf = amlf;
	}

	public AssignMessageBiz getAssignMessageBiz() {
		return assignMessageBiz;
	}

	public LogUtil getMyLog() {
		return myLog;
	}

	public void setAssignMessageBiz(AssignMessageBiz assignMessageBiz) {
		this.assignMessageBiz = assignMessageBiz;
	}

	public void setMyLog(LogUtil myLog) {
		this.myLog = myLog;
	}

}