package zhangchx.rpc.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import zhangchx.spring.bean.User;

public class UserServiceImpl extends UnicastRemoteObject implements
		IUserService {

	protected UserServiceImpl() throws RemoteException {
//		super();
	}

	@Override
	public String getUsername(String id) {
		return "username-zhangchx";
	}

	@Override
	public int countUser() {
		return 711;
	}

	@Override
	public User createUser(String id, String username) {
		User user = new User();
		user.setId(id);
		user.setName(username);
		return user;
	}

}
