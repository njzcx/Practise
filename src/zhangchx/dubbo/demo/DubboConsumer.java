package zhangchx.dubbo.demo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;

public class DubboConsumer {
	
	ReferenceConfig<DubboDemoService> referenceInstance = null;
	
	private void init() {
		// 当前应用配置
		ApplicationConfig application = new ApplicationConfig();
		application.setName("customer");

		// 连接注册中心配置
		RegistryConfig registry = new RegistryConfig();
		registry.setAddress("127.0.0.1:9090");
		registry.setUsername("admin");
		registry.setPassword("admin");

		// 注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接

		// 引用远程服务
		ReferenceConfig<DubboDemoService> reference = new ReferenceConfig<DubboDemoService>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
		reference.setApplication(application);
		reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
		reference.setInterface(DubboDemoService.class);
		reference.setVersion("1.0.0");

		// 和本地bean一样使用xxxService
		DubboDemoService dubboService = reference.get(); // 注意：此代理对象内部封装了所有通讯细节，对象较重，请缓存复用
		referenceInstance = reference;
	}
	
	public ReferenceConfig getReference() {
		return referenceInstance;
	}
	
	public static void main(String[] args) throws Exception {
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
//                new String[]{"META-INF/spring/dubbo-demo-consumer.xml"});
//        context.start();
		DubboConsumer consumer = new DubboConsumer();
		consumer.init();
		
		DubboDemoService demoService = (DubboDemoService) consumer.getReference().get(); // obtain proxy object for remote invocation
        String hello = demoService.sayHello("world"); // execute remote invocation
        System.out.println(hello); // show the result
    }
}
