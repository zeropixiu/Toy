package ph.servlet;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import ph.dao.PetDao;
import ph.entity.Pet;

/**
 * Servlet implementation class PetServlet
 */
@MultipartConfig//表示此Servlet支持文件上传
@WebServlet("/PetServlet")
public class PetServlet extends HttpServlet {
	
    public PetServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String m = request.getParameter("m");
		if ("delete".equals(m)) {
			delete(request, response);
		}else if ("toAdd".equals(m)) {
			toAdd(request,response);
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
	private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			Part part = request.getPart("photo");
			String filename = getFileName(part);
			String photo;
			String real_photo;
			if(filename != null) {
				long currentTimeMillis = System.currentTimeMillis();
				photo="photo/"+currentTimeMillis + filename.substring(filename.lastIndexOf("."));
				real_photo=getServletContext().getRealPath("/")+"/"+ photo;
				part.write(real_photo);
			}else {
				photo = "photo/default.jsp";
			}
			Pet pet = new Pet();
			pet.setName(request.getParameter("name"));
			pet.setBirthdate(request.getParameter("birthdate"));
			pet.setPhoto(photo);
			pet.setOwnerId(Integer.parseInt(request.getParameter("cid")));
			new PetDao().save(pet);
			request.setAttribute("msg", "添加成功");
			response.sendRedirect(request.getContextPath() +"/CustomerServlet?m=showDetail&cid="+ pet.getOwnerId());
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			response.sendRedirect(request.getServletContext()+"/CustomerServlet?m=search&customerName=");
		}
		
	}

	private String getFileName(Part part) {
		String contentDesc = part.getHeader("content-disposition");
		String fileName = null;
		Pattern pattern = Pattern.compile("filename=\".+\"");
		Matcher matcher = pattern.matcher(contentDesc);
		if(matcher.find()) {
			fileName = matcher.group();
			fileName = fileName.substring(10,fileName.length() - 1);
		}
		return fileName;
	}

	private void toAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/petadd.jsp").forward(request, response);
//		添加新玩具超链接请求中带的cid、cname参数会随请求对象传递到petadd.jsp
		
	}
	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int petId = Integer.parseInt(request.getParameter("petId"));
			new PetDao().delete(petId);
			request.setAttribute("msg", "删除成功");
			request.getRequestDispatcher("/CustomerServlet?m=showDetail&cid=" + request.getParameter("cid")).forward(request, response);
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/CustomerServlet?m=showDetail&cid=" + request.getParameter("cid")).forward(request, response);
		}
	}

}
