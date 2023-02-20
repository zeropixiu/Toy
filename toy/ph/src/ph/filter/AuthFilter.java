package ph.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ph.entity.User;

/**
 * Servlet Filter implementation class AuthFilter
 */
//@WebFilter("*.jsp")
public class AuthFilter implements Filter {

	 private static List<String> adminAuthPages = new ArrayList<String>();
//   ��Ҫ��admin����ɫ��½�ɹ����ܷ��ʵ�ҳ��
   static {
   	adminAuthPages.add("/vetsearch.jsp");
   	adminAuthPages.add("/customer.jsp");
   	adminAuthPages.add("/vetsearch_result.jsp");
   	adminAuthPages.add("/addPet.jsp");
   	adminAuthPages.add("/petsearch.jsp");
//   	����������
   }
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(true);
		String reqURI= req.getRequestURI();
		reqURI = reqURI.substring(reqURI.lastIndexOf("/"));
		if(adminAuthPages.contains(reqURI)) {
			User user = (User) session.getAttribute("user");
			if(user==null) {//û��¼������
				req.setAttribute("msg", "���ȵ�¼��");
				req.getRequestDispatcher("/index.jsp").forward(req, response);
			}else if("admin".equals(user.getRole())) {
				//��¼Ϊadmin��ɫ����
				chain.doFilter(request, response);
			}else {
				//��½�˵���admin��ɫ������
				req.setAttribute("msg", "��ҳ��ֻ�й���Ա���ܷ��ʣ�");
				req.getRequestDispatcher("/index.jsp").forward(request, response);
				
			}
		}else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
