package com.tried.common.mail.service;

import com.tried.base.service.BaseService;

public interface MailService extends BaseService<Object>{

	/**
	 * 
		 * @Description 检查邮箱是否有新的邮件
		 * @author liuxd
		 * @date 2019-1-7 上午9:54:46
		 * @version V1.0
	 */
	public void checkMail()throws Exception;
}
