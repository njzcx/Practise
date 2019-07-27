package zhangchx.rpc.dubbo;

public class DubboDemoServiceImpl implements DubboDemoService {

	@Override
	public String sayHello(String name) {
		System.out.println(name);
		return "Hello" + name;
	}

}
