package zhangchx.dubbo.demo;

public class DubboDemoServiceImpl implements DubboDemoService {

	@Override
	public String sayHello(String name) {
		return "Hello" + name;
	}

}
