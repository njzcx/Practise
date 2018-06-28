package zhangchx.spring.dao;

import zhangchx.spring.bean.User;

public interface UserDao {
	public User selectOne(String id);
}
