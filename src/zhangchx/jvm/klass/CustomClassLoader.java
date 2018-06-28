package zhangchx.jvm.klass;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * 自定义类加载器
 * @author Snow
 *
 */
public class CustomClassLoader extends URLClassLoader {

	public CustomClassLoader(URL[] urls) {
		super(urls);
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		return super.findClass(name);
	}

}
