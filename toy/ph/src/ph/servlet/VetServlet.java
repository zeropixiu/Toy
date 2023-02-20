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
//		���ܱ�����
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
//			�ж����������ʦ�������Ƿ�Ϊ��
			if(vetName==null || "".equals(vetName)) {
				throw new Exception("����������ʦ������");
			}
			String specIds[] = request.getParameterValues("specId");
//			�ж����������ʦ��רҵ�Ƿ�Ϊ��
			if(specIds==null || specIds.length==0) {
				throw new Exception("��ѡ������һ��רҵ�س�");
			}
//			����Vet������Ϣ
			Vet vet = new Vet();
			vet.setName(request.getParameter("name"));
//			����רҵ������רҵ
			for (int i = 0; i < specIds.length; i++) {
				Speciality spec = new Speciality();
				spec.setId(Integer.valueOf(specIds[i]));
				
				vet.getSpecs().add(spec);
			}
			
			System.out.println("spec: " + vet.getSpecs().size());
			
			VetDao vetDao = new VetDao();
			vetDao.save(vet);
			request.setAttribute("msg", "�����ɹ�");
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
//			δ�ҵ�����ʦ����Ϣת����vetsearch.jsp
			if(vets.size()==0) {
				request.setAttribute("msg", "δ�ҵ��������ʦ������Ϣ");
				request.getRequestDispatcher("/vetsearch.jsp").forward(request, response);
			}else {
//				�ҵ���ת����vetsearch_result.jsp
				request.setAttribute("vets", vets);
				request.getRequestDispatcher("/vetsearch_result.jsp").forward(request, response);
			}
			
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/vetsearch.jsp").forward(request, response);
		}
		
	}

}
