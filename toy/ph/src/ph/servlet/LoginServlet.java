package ph.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ph.dao.UserDao;
import ph.entity.User;
import ph.utils.CheckCode;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		从登录页获取用户输入的验证码
//		判断验证码是否正确
//		错误：转到index.jsp并报错
//		正确：判断登录用户输入的密码是否正确
//			不存在，转到index.jsp并报错
//			存在：判断密码是否正确
//				密码不正确：转到index.jsp
//				密码正确：将user存到session，判断用户的role
//					role为admin,转到vetsearch.jsp
//					role为customer,转到cutindex.jsp
		
		
		String msg = null;//存报错信息
		String url = null;//跳转目的地址
		HttpSession session = request.getSession(true);
		String inputcode = request.getParameter("checkcode");
		String realcode = (String) session.getAttribute("realcode");
		if(realcode.equalsIgnoreCase(inputcode)) {
//			验证码正确
//			判断用户是否存在
			try {
				User user = new UserDao().getByName(request.getParameter("name"));
				if(user == null) {
					msg="用户名不存在!";
					url="/index.jsp";
				}else if(!user.getPwd().equals(request.getParameter("pwd"))) {
//					密码不对
					msg="密码不正确!";
					url="/index.jsp";
				}else {
//					密码正确,登陆成功,用户存储session
					session.setAttribute("user",user );
//					判断用户的role
					if("admin".equals(user.getRole())) {
						url="/vetsearch.jsp";
					}else if("customer".equals(user.getRole())) {
						url="/customer.jsp";
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
//			验证码错误
			msg="验证码输入错误";
			url="/index.jsp";
		}
		request.setAttribute("msg", msg);
		request.getRequestDispatcher(url).forward(request, response);;
	}

}
