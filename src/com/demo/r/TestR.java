package com.demo.r;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

/**
 * R语言的简单使用
 * @author zhangchx
 * @date 2017-12-22
 * @email njzcx@126.com
 */
public class TestR {

	public static void main(String[] args) {
		try {
			RConnection c = new RConnection();

			REXP x = c.eval("15>13");

			System.out.println(x.asString());
		} catch (RserveException e) {
			e.printStackTrace();
		} catch (REXPMismatchException e) {
			e.printStackTrace();
		}
	}

}
