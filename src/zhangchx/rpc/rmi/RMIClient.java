package zhangchx.rpc.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {
	public static void main(String[] args) throws Exception {
		/**当两个不同机器之间通信时，会涉及安全问题，因此需要使用SecurityManager，并在命令行执行java -Djava.security.manager**/
		/*if(System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }*/
		
		String name = "rmi.service.test";
        /***************** 以下为查找服务方法一 ************/
        // 获取注册表
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        // 查找对应的服务
        IUserService service = (IUserService) registry.lookup(name);
      
        /***************** 以下为查找服务方法二 ************/
        // DemoService service = (DemoService) Naming.lookup(name);
      
        /***************** 以下为查找服务方法三 ************/
        //Context namingContext = new InitialContext();
        //DemoService service = (DemoService) namingContext.lookup("rmi:" + name);
      
        // 调用服务
        System.out.println(service.createUser("001", "zhangchx"));
	}
}
