package zhangchx.rpc.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import zhangchx.spring.bean.User;

public interface IUserService extends Remote {
	String getUsername(String id) throws RemoteException;
	int countUser() throws RemoteException;
	User createUser(String id, String username) throws RemoteException;
}
