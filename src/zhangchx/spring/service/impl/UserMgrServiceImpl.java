package zhangchx.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import zhangchx.spring.bean.User;
import zhangchx.spring.dao.UserDao;
import zhangchx.spring.service.IUserMgrServcie;

public class UserMgrServiceImpl implements IUserMgrServcie {
	
	@Autowired
	UserDao userdao;

	@Override
	public void list() {

	}

	@Override
	public User query(String id) {
		return userdao.selectOne(id);
	}

	@Override
	public boolean delete(String id) {
		return false;
	}

	@Override
	public boolean add(User user) {
		return false;
	}

	@Override
	public boolean update(String id, User user) {
		return false;
	}

}
