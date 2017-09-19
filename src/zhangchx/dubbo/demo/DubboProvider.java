package zhangchx.dubbo.demo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.sun.net.ssl.internal.ssl.Provider;

public class DubboProvider {
	ServiceConfig<DubboDemoService> serviceInstance = null;
	private void init() {
		
		// 服务实现
		DubboDemoService dubboService = new DubboDemoServiceImpl();
		
		// 当前应用配置
		ApplicationConfig application = new ApplicationConfig();
		application.setName("provider");
		
		// 连接注册中心配置
		RegistryConfig registry = new RegistryConfig();
		registry.setAddress("127.0.0.1:9090");
		registry.setUsername("admin");
		registry.setPassword("admin");
		
		// 服务提供者协议配置
		ProtocolConfig protocol = new ProtocolConfig();
		protocol.setName("dubbo");
		protocol.setPort(12345);
		protocol.setThreads(200);
		
		// 注意：ServiceConfig为重对象，内部封装了与注册中心的连接，以及开启服务端口
		ServiceConfig<DubboDemoService> service = new ServiceConfig<DubboDemoService>(); // 此实例很重，封装了与注册中心的连接，请自行缓存，否则可能造成内存和连接泄漏
		service.setApplication(application);
		service.setRegistry(registry); // 多个注册中心可以用setRegistries()
		service.setProtocol(protocol); // 多个协议可以用setProtocols()
		service.setInterface(DubboDemoService.class);
		service.setRef(dubboService);
		service.setVersion("1.0.0");

		// 暴露及注册服务
		service.export();
		serviceInstance = service;
	}
	
	public ServiceConfig<DubboDemoService> getServiceInstance() {
		return serviceInstance;
	}
	
	 public static void main(String[] args) throws Exception {
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
//                new String[] {"META-INF/spring/dubbo-demo-provider.xml"});
//        context.start();
        DubboProvider provider = new DubboProvider();
        provider.init();
        System.in.read(); // press any key to exit
    }
}
