package zhangchx.rpc.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * rmi.policy文件
 * grant {
 * 	permission java.security.AllPermission;
 * };
 * @author Snow
 *
 */
public class RMIServer {
	
	public static void main(String[] args) throws Exception {
		/**当两个不同机器之间通信时，会涉及安全问题，因此需要使用SecurityManager，并在命令行执行java -Djava.security.policy=rmi.policy**/
		/*if(System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }*/
		
		// 服务名称
		String name = "rmi.service.test";
		
		// 创建本机 1099 端口上的 RMI 注册表
		Registry registry = LocateRegistry.createRegistry(1099);
		
		// 创建服务
		IUserService service = new UserServiceImpl();
		
		/***************** 以下为注册方法一 ************/
        // 将服务绑定到注册表中
		registry.bind(name, service);
      
        /***************** 以下为注册方法二 ************/
        // Naming.bind(name, service);
      
        /***************** 以下为注册方法三 ************/
        //Context namingContext = new InitialContext();
        //namingContext.bind("rmi:" + name, service); // 此方式 name 需要以 rmi: 开头
	}
}
