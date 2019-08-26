package zzc.springmvc.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import zzc.springmvc.dao.DepartmentDao;
import zzc.springmvc.dao.EmployeeDao;
import zzc.springmvc.entities.Employee;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

@Controller
public class EmployeeHandler {

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private DepartmentDao departmentDao;


	@ModelAttribute
	public void getEmployee(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
		if (null != id) {
			map.put("employee", employeeDao.get(id));
		}
	}

	/**
	 * 列表页
	 */
	@RequestMapping(value = "/emps", method = RequestMethod.GET)
	public String list(Map<String, Object> map) {
		map.put("employees", employeeDao.getEmployees());
		return "list";
	}

	/**
	 * 跳转到添加页
	 */
	@RequestMapping(value = "/emp", method = RequestMethod.GET)
	public String input(Map<String, Object> map) {
		map.put("departments", departmentDao.getDepartments());
		map.put("employee", new Employee());
		return "input";
	}

	/**
	 * 新增员工信息
	 */
	@RequestMapping(value = "/emp", method = RequestMethod.POST)
	public String save(Employee employee) {
		employeeDao.save(employee);
		return "redirect:/emps";
	}

	/**
	 * 删除员工
	 */
	@RequestMapping(value = "/emp/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Integer id) {
		employeeDao.delete(id);
		return "redirect:/emps";
	}

	/**
	 * 跳转到编辑页
	 */
	@RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
	public String input(@PathVariable("id") Integer id, Map<String, Object> map) {
		map.put("employee", employeeDao.get(id));
		map.put("departments", departmentDao.getDepartments());
		return "input";
	}

	/**
	 * 修改员工信息
	 */
	@RequestMapping(value = "/emp", method = RequestMethod.PUT)
	public String update(@ModelAttribute("employee") Employee employee) {
		employeeDao.save(employee);
		return "redirect:/emps";
	}


	/**
	 * 通过流的方式上传文件
	 *
	 * @param desc
	 * @param file
	 * @return
	 */
	@RequestMapping("/fileUpload1")
	public String fileUpload1(@RequestParam("desc") String desc, @RequestParam("file") MultipartFile file) throws IOException {
		System.out.println("desc:" + desc);
		System.out.println("OriginalFilename: " + file.getOriginalFilename());

		//用来检测程序运行时间
		long startTime = System.currentTimeMillis();

		try {
			//获取输出流
			OutputStream os = new FileOutputStream("/Users/zczhao/Documents/logs/" + new Date().getTime() + file.getOriginalFilename());
			//获取输入流 CommonsMultipartFile 中可以直接得到文件的流
			InputStream is = file.getInputStream();
			int temp;
			//一个一个字节的读取并写入
			while ((temp = is.read()) != (-1)) {
				os.write(temp);
			}
			os.flush();
			os.close();
			is.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("方法一的运行时间：" + String.valueOf(endTime - startTime) + "ms");

		return "redirect:/emps";
	}

	/**
	 * 采用file.Transto 来保存上传的文件
	 *
	 * @param desc
	 * @param file
	 * @return
	 */
	@RequestMapping("/fileUpload2")
	public String fileUpload2(@RequestParam("desc") String desc, @RequestParam("file") MultipartFile file) throws IOException {
		System.out.println("desc:" + desc);
		System.out.println("OriginalFilename: " + file.getOriginalFilename());
		long startTime = System.currentTimeMillis();
		String path = "/Users/zczhao/Documents/logs/" + new Date().getTime() + file.getOriginalFilename();

		File newFile = new File(path);
		//通过CommonsMultipartFile的方法直接写文件（注意这个时候）
		file.transferTo(newFile);
		long endTime = System.currentTimeMillis();
		System.out.println("方法二的运行时间：" + String.valueOf(endTime - startTime) + "ms");
		return "redirect:/emps";
	}

	/**
	 * 采用spring提供的上传文件的方法
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/fileUpload3")
	public String fileUpload3(HttpServletRequest request) throws IOException {
		long startTime = System.currentTimeMillis();
		//将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		//检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			//将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			//获取multiRequest 中所有的文件名
			Iterator iter = multiRequest.getFileNames();

			while (iter.hasNext()) {
				//一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				if (file != null) {
					String path = "/Users/zczhao/Documents/logs/" + file.getOriginalFilename();
					//上传
					file.transferTo(new File(path));
				}

			}

		}
		long endTime = System.currentTimeMillis();
		System.out.println("方法三的运行时间：" + String.valueOf(endTime - startTime) + "ms");
		return "redirect:/emps";
	}

}
