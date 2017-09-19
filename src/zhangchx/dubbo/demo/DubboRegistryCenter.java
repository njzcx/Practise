package zhangchx.dubbo.demo;

import java.io.IOException;

import org.springframework.core.SimpleAliasRegistry;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.registry.RegistryService;
import com.alibaba.dubbo.registry.multicast.MulticastRegistry;

public class DubboRegistryCenter {
	private void init() {
		
		URL url = URL.valueOf("192.168.1.112");
		RegistryService registry = new MulticastRegistry(url);
		
		ApplicationConfig application = new ApplicationConfig();
		application.setName("registrycenter");
		
		ProtocolConfig protocol = new ProtocolConfig();
		protocol.setPort(9090);
		
		ServiceConfig<RegistryService> service = new ServiceConfig<RegistryService>();
		service.setApplication(application);
		service.setProtocol(protocol);
		service.setInterface(RegistryService.class);
		service.setRef(registry);
		
		service.export();

	}
	
	public static void main(String[] args) throws IOException {
		new DubboRegistryCenter().init();
		System.in.read();
	}
}
