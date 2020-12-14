package fancybox.lib.io;

/**
 * out methods for SideBox platform
 * provided a series of methods to proxy std-out.
 * @author Rock Chin
 * @create 2020/12/14
 * @edit 2020/12/14
 */
public class Out {
	public static void stdPrint(String msg){
		System.out.println(msg);
	}
	public static void stdPrintln(String msg){
		stdPrint(msg+"\n");
	}
	public static void stdPrintln(String sub,String msg){
		stdPrintln("["+sub+"]"+msg);
	}
}
