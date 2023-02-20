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
//   需要“admin”角色登陆成功才能访问的页面
   static {
   	adminAuthPages.add("/vetsearch.jsp");
   	adminAuthPages.add("/customer.jsp");
   	adminAuthPages.add("/vetsearch_result.jsp");
   	adminAuthPages.add("/addPet.jsp");
   	adminAuthPages.add("/petsearch.jsp");
//   	后续可增加
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
			if(user==null) {//没登录则拦截
				req.setAttribute("msg", "请先登录！");
				req.getRequestDispatcher("/index.jsp").forward(req, response);
			}else if("admin".equals(user.getRole())) {
				//登录为admin角色放行
				chain.doFilter(request, response);
			}else {
				//登陆了但非admin角色则拦截
				req.setAttribute("msg", "该页面只有管理员才能访问！");
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
