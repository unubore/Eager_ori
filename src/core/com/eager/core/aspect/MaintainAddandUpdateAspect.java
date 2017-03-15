package com.eager.core.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.eager.core.domain.BaseDomain;
import com.eager.core.domain.Session;
import com.eager.core.util.ThreadVariable;

@Aspect
@Repository("maintainAddandUpdateAspect")
public class MaintainAddandUpdateAspect {
	final static Logger logger = LoggerFactory.getLogger(MaintainAddandUpdateAspect.class);

	@Before("execution(public * com.eager..*.*Dao.add*(..)) &&  args(baseDomain,..)")
	public void appendCreateInfo(BaseDomain baseDomain) throws Exception {
		/*
		 * if (baseDomain instanceof Session || baseDomain instanceof SystemLog)
		 * { return; }
		 */
		Session session = ThreadVariable.getSession();
		if (session == null || session.getUserName() == null) {
			logger.error("系统尝试在无登陆的情况下添加[{}]对象", baseDomain.getClass().getName());
			throw new Exception("Session不存在，系统不能正常工作!");
		}
		baseDomain.setCreateDate(session.getAccessTime());
		baseDomain.setCreateUser(session.getUserName());
	}

	@Before("execution(public * com.tianque..*.*Dao.update*(..)) &&  args(baseDomain,..)")
	public void appendUPdateInfo(BaseDomain baseDomain) throws Exception {
		/*
		 * if (baseDomain instanceof Session || baseDomain instanceof SystemLog)
		 * { return; }
		 */
		Session session = ThreadVariable.getSession();
		if (session == null || session.getUserName() == null) {
			logger.error("系统尝试在无登陆的情况下更新[{}]对象,对象ID为[{}]", baseDomain.getClass().getName(), baseDomain.getId());
			throw new Exception("Session不存在，系统不能正常工作!");
		}
		baseDomain.setUpdateDate(session.getAccessTime());
		baseDomain.setUpdateUser(session.getUserName());
	}

}
