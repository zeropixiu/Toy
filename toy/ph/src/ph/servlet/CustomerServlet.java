package ph.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.internal.compiler.env.IUpdatableModule.UpdateKind;

import ph.dao.PetDao;
import ph.dao.UserDao;
import ph.entity.Pet;
import ph.entity.User;

/**
 * Servlet implementation class CustomerServlet
 */
@WebServlet("/CustomerServlet")
public class CustomerServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String m = request.getParameter("m");
//		��ѯ�ͻ�����
		if("showDetail".equals(m)) {
			showDetail(request,response);
		}else if ("search".equals(m)) {
//			��ѯ�ͻ�
			search(request, response);
		}else if ("toUpdate".equals(m)) {
//			ת���ͻ�����ҳ
			toUpdate(request,response);
		}else if ("delete".equals(m)) {
//			ɾ���ͻ�
			delete(request,response);
		}
	}




	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int id = Integer.parseInt(request.getParameter("cid"));
			new UserDao().delete(id);
			response.sendRedirect(request.getContextPath() + "/CustomerServlet?m=search&customerName=;");
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/customersearch.jsp").forward(request, response);
		}
		
	}








	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String m = request.getParameter("m");
		if("search".equals(m)) {
//			��ѯ�ͻ�
			search(request,response);
		}else if ("add".equals(m)) {
//			������û�
			add(request,response);
		}else if ("update".equals(m)) {
//			�ͻ���Ϣ���µ�DB
			update(request,response);
		}else if ("showDetail".equals(m)) {
//			�鿴�ͻ�����
			showDetail(request,response);
		}
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User customer = new User();
			int id = Integer.parseInt(request.getParameter("id"));
			customer.setId(id);
			customer.setTel(request.getParameter("tel"));
			customer.setAddress(request.getParameter("address"));
			new UserDao().modify(customer);//���µ����ݿ�
			request.getRequestDispatcher("/CustomerServlet?m=showDetail&cid="+ id).forward(request, response);
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/customersearch.jsp").forward(request, response);
		}
	}




	private void toUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		����cid��ѯ�ͻ�
//		�ͻ��������ͻ�����ҳcustomerupd.jsp
		try {
			User customer = new UserDao().getById(Integer.parseInt(request.getParameter("cid")));
			request.setAttribute("customer", customer);
			request.getRequestDispatcher("/customerupd.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("customersearch.jsp").forward(request, response);
		}
		
	}
	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User customer = new User();
			customer.setName(request.getParameter("name"));
			customer.setTel(request.getParameter("tel"));
			customer.setAddress(request.getParameter("address"));
			customer.setPwd("123456");
			customer.setRole("customer");
			new UserDao().save(customer);
			request.setAttribute("msg", "����û��ɹ�");
			request.getRequestDispatcher("/customersearch.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/customersearch.jsp").forward(request, response);
		}
	}




	private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<User> users = new UserDao().searchCustomer(request.getParameter("customerName"));
			if(users.size()==0) {//δ�鵽
				request.setAttribute("msg", "û���ҵ�����û���Ϣ");
				request.getRequestDispatcher("/customersearch.jsp").forward(request, response);
			}else {//�鵽��
				request.setAttribute("users", users);
				request.getRequestDispatcher("/customersearch_result.jsp").forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/customersearch.jsp").forward(request, response);
		}
	}
	
	
	private void showDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			UserDao userDao = new UserDao();
			PetDao petDao = new PetDao();
//			�ӳ����ӻ�ȡ�鿴������û�ID
			int ownerId = Integer.valueOf(request.getParameter("cid"));
//			���ݿͻ�ID��ѯ�ͻ�
			User customer = userDao.getById(ownerId);
//			���ݿͻ�ID��ѯ�����
			List<Pet> pets = petDao.getPetsByOwnerId(ownerId);
//			�󶨵��ͻ���pets��
			customer.setPets(pets);
			request.setAttribute("customer", customer);
			request.getRequestDispatcher("/customerdetail.jsp").forward(request, response);
			
		} catch (Exception e) {
			request.setAttribute("msg",e.getMessage());
			request.getRequestDispatcher("/customersearch.jsp").forward(request, response);
		}
		
	}

}
