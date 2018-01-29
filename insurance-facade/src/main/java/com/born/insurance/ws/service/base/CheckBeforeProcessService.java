package com.born.insurance.ws.service.base;

import javax.jws.WebService;

/**
 * Order 检查后 在事务启动前先检查业务是否可以执行
 * 
 * 
 * @author qch
 * 
 */
@WebService
public interface CheckBeforeProcessService {
	void check();
}
