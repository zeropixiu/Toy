package ph.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ph.dao.VetDao;
import ph.dao.VisitDao;
import ph.entity.Vet;
import ph.entity.Visit;

/**
 * Servlet implementation class VisitServlet
 */
@WebServlet("/VisitServlet")
public class VisitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String m = request.getParameter("m");
		if("toAdd".equals(m)) {
			toAdd(request,response);
		}else if("showHistory".equals(m)) {
			showHistory(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String m = request.getParameter("m");
		if("add".equals(m)) {
			add(request,response);
		}
	}
//	转到玩具修理情况添加页visitadd.jsp
	private void toAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			VetDao vetDao = new VetDao();
			List<Vet> vets = vetDao.getAll();
			request.setAttribute("vets", vets);
			request.getRequestDispatcher("/visitadd.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/customersearch.jsp").forward(request, response);
		}
		  
	}
	private void showHistory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			VisitDao visitDao = new VisitDao();
			List<Visit> visits = visitDao.getVisitsByPetId(Integer.parseInt(request.getParameter("petId")));
			request.setAttribute("visits", visits);
			if (visits.size()==0) {
				request.setAttribute("msg", "没有找到历史修理情况");
			}
			request.getRequestDispatcher("/visitsearch_result.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/customersearch.jsp").forward(request, response);
		}
		
	}
	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Visit visit = new Visit();
			visit.setPetId(Integer.parseInt(request.getParameter("petId")));
			visit.setVetId(Integer.parseInt(request.getParameter("vetId")));
			visit.setDescription(request.getParameter("description"));
			visit.setTreatment(request.getParameter("treatment"));
			VisitDao visitDao = new VisitDao();
			visitDao.save(visit);
			request.setAttribute("msg", "修理情况添加成功");
			response.sendRedirect(request.getContextPath()+"/CustomerServlet?m=showDetail&cid=" + request.getParameter("cid"));
			
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/customersearch.jsp").forward(request, response);
		}
	}


}
