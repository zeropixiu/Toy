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
//		查询客户详情
		if("showDetail".equals(m)) {
			showDetail(request,response);
		}else if ("search".equals(m)) {
//			查询客户
			search(request, response);
		}else if ("toUpdate".equals(m)) {
//			转到客户更新页
			toUpdate(request,response);
		}else if ("delete".equals(m)) {
//			删除客户
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
//			查询客户
			search(request,response);
		}else if ("add".equals(m)) {
//			添加新用户
			add(request,response);
		}else if ("update".equals(m)) {
//			客户信息更新到DB
			update(request,response);
		}else if ("showDetail".equals(m)) {
//			查看客户详情
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
			new UserDao().modify(customer);//更新到数据库
			request.getRequestDispatcher("/CustomerServlet?m=showDetail&cid="+ id).forward(request, response);
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/customersearch.jsp").forward(request, response);
		}
	}




	private void toUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		根据cid查询客户
//		客户对象传至客户更新页customerupd.jsp
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
			request.setAttribute("msg", "添加用户成功");
			request.getRequestDispatcher("/customersearch.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/customersearch.jsp").forward(request, response);
		}
	}




	private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<User> users = new UserDao().searchCustomer(request.getParameter("customerName"));
			if(users.size()==0) {//未查到
				request.setAttribute("msg", "没有找到相关用户信息");
				request.getRequestDispatcher("/customersearch.jsp").forward(request, response);
			}else {//查到了
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
//			从超链接获取查看详情的用户ID
			int ownerId = Integer.valueOf(request.getParameter("cid"));
//			根据客户ID查询客户
			User customer = userDao.getById(ownerId);
//			根据客户ID查询其玩具
			List<Pet> pets = petDao.getPetsByOwnerId(ownerId);
//			绑定到客户的pets上
			customer.setPets(pets);
			request.setAttribute("customer", customer);
			request.getRequestDispatcher("/customerdetail.jsp").forward(request, response);
			
		} catch (Exception e) {
			request.setAttribute("msg",e.getMessage());
			request.getRequestDispatcher("/customersearch.jsp").forward(request, response);
		}
		
	}

}
