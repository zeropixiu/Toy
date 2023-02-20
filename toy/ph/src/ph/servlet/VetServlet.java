package ph.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ph.dao.SpecialityDao;
import ph.dao.VetDao;
import ph.entity.Speciality;
import ph.entity.Vet;

/**
 * Servlet implementation class VetServlet
 */
@WebServlet("/VetServlet")
public class VetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VetServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String m=request.getParameter("m");
		if ("toAdd".equals(m)) {
			toAdd(request,response);
		}
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		接受表单参数
		try {
			String m=request.getParameter("m");
			if("add".equals(m)) {
				add(request,response);
			}else if("search".equals(m)) {
				search(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	private void toAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			SpecialityDao specDao = new SpecialityDao();
			request.setAttribute("specs", specDao.getAll());
			request.getRequestDispatcher("/vetadd.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/vetsearch.jsp").forward(request, response);
		}
		  
	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String vetName = request.getParameter("name");
//			判断输入的修理师傅姓名是否为空
			if(vetName==null || "".equals(vetName)) {
				throw new Exception("请输入修理师傅姓名");
			}
			String specIds[] = request.getParameterValues("specId");
//			判断输入的修理师傅专业是否为空
			if(specIds==null || specIds.length==0) {
				throw new Exception("请选择至少一项专业特长");
			}
//			创建Vet储存信息
			Vet vet = new Vet();
			vet.setName(request.getParameter("name"));
//			遍历专业并存入专业
			for (int i = 0; i < specIds.length; i++) {
				Speciality spec = new Speciality();
				spec.setId(Integer.valueOf(specIds[i]));
				
				vet.getSpecs().add(spec);
			}
			
			System.out.println("spec: " + vet.getSpecs().size());
			
			VetDao vetDao = new VetDao();
			vetDao.save(vet);
			request.setAttribute("msg", "操作成功");
			request.getRequestDispatcher("/vetsearch.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			toAdd(request,response);
		}
	}

	private void search(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
		try {
			String vetName = request.getParameter("vetName");
			String specName= request.getParameter("specName");
			VetDao vetDao = new VetDao();
			List<Vet> vets = vetDao.search(vetName, specName);
//			未找到修理师傅信息转发到vetsearch.jsp
			if(vets.size()==0) {
				request.setAttribute("msg", "未找到相关修理师傅的信息");
				request.getRequestDispatcher("/vetsearch.jsp").forward(request, response);
			}else {
//				找到则转发到vetsearch_result.jsp
				request.setAttribute("vets", vets);
				request.getRequestDispatcher("/vetsearch_result.jsp").forward(request, response);
			}
			
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/vetsearch.jsp").forward(request, response);
		}
		
	}

}
