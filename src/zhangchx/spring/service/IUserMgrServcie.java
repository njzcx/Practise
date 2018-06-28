package zhangchx.spring.service;

import zhangchx.spring.bean.User;

public interface IUserMgrServcie {
	public void list();
	
	public User query(String id);
	
	public boolean delete(String id);
	
	public boolean add(User user);
	
	public boolean update(String id, User user);
}
