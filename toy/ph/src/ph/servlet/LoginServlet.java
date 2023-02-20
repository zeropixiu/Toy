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
//		�ӵ�¼ҳ��ȡ�û��������֤��
//		�ж���֤���Ƿ���ȷ
//		����ת��index.jsp������
//		��ȷ���жϵ�¼�û�����������Ƿ���ȷ
//			�����ڣ�ת��index.jsp������
//			���ڣ��ж������Ƿ���ȷ
//				���벻��ȷ��ת��index.jsp
//				������ȷ����user�浽session���ж��û���role
//					roleΪadmin,ת��vetsearch.jsp
//					roleΪcustomer,ת��cutindex.jsp
		
		
		String msg = null;//�汨����Ϣ
		String url = null;//��תĿ�ĵ�ַ
		HttpSession session = request.getSession(true);
		String inputcode = request.getParameter("checkcode");
		String realcode = (String) session.getAttribute("realcode");
		if(realcode.equalsIgnoreCase(inputcode)) {
//			��֤����ȷ
//			�ж��û��Ƿ����
			try {
				User user = new UserDao().getByName(request.getParameter("name"));
				if(user == null) {
					msg="�û���������!";
					url="/index.jsp";
				}else if(!user.getPwd().equals(request.getParameter("pwd"))) {
//					���벻��
					msg="���벻��ȷ!";
					url="/index.jsp";
				}else {
//					������ȷ,��½�ɹ�,�û��洢session
					session.setAttribute("user",user );
//					�ж��û���role
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
//			��֤�����
			msg="��֤���������";
			url="/index.jsp";
		}
		request.setAttribute("msg", msg);
		request.getRequestDispatcher(url).forward(request, response);;
	}

}
