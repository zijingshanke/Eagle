package com.fordays.masssending.forum;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.neza.base.ListActionForm;

/**
 * 论坛列表
 */
public class ForumListForm extends ListActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String loginSite;
	private Long loginPort;
	private Long status;
	private Section section = new Section();
	private Account account = new Account();

	private boolean isShowHidden = false;

	public void reset(ActionMapping actionMapping,
			HttpServletRequest httpServletRequest) {
		thisAction = "";
		this.setIntPage(1);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginSite() {
		return loginSite;
	}

	public void setLoginSite(String loginSite) {
		this.loginSite = loginSite;
	}

	public Long getLoginPort() {
		return loginPort;
	}

	public void setLoginPort(Long loginPort) {
		this.loginPort = loginPort;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public boolean isShowHidden() {
		return isShowHidden;
	}

	public void setShowHidden(boolean isShowHidden) {
		this.isShowHidden = isShowHidden;
	}

	
}
