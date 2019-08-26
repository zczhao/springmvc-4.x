package zzc.springmvc;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class Application {

	public static void main(String[] args) throws LifecycleException {

		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8080);

		File file = new File(Application.class.getResource("/").getPath());
		String projectPath = file.getAbsoluteFile().toString().replace("\\", "/").replace("/target/classes", "");

		String webappDirLocation = projectPath + "/src/main/webapp/";
		tomcat.addWebapp("/", webappDirLocation);

		System.out.println("configuring app with basedir: " + webappDirLocation);
		tomcat.start();
		tomcat.getServer().await();

	}
}
