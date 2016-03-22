package br.cin.ufpe.contribua.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.cin.ufpe.contribua.model.Usuario;

public class AuthFilter implements Filter {

	private static final boolean debug = true;
	private FilterConfig filterConfig = null;
	private final static String FILTER_APPLIED = "_security_filter_applied";

	public AuthFilter() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest hreq = (HttpServletRequest) request;
		HttpServletResponse hresp = (HttpServletResponse) response;
		HttpSession session = hreq.getSession();

		hreq.getPathInfo();
		String paginaAtual = new String(hreq.getRequestURL());

		if ((request.getAttribute(FILTER_APPLIED) == null) && paginaAtual != null
				&& (!paginaAtual.endsWith("index.xhtml")) && (!paginaAtual.contains("javax.faces.resource")) // TODO:
																												// Verificar
																												// solução
																												// melhor
				&& ((paginaAtual.contains("/pages/private/") || paginaAtual.contains("/pages/admin/"))) // TODO:
																										// Verificar
				// solução
				// melhor
				&& (paginaAtual.endsWith(".xhtml"))) {
			request.setAttribute(FILTER_APPLIED, Boolean.TRUE);

			Object logged = null;
			if ((session.getAttribute("autenticado")) != null) {
				logged = (session.getAttribute("autenticado"));
				Usuario usuario = (Usuario) session.getAttribute("usuario");

				if (usuario.getLogin() != "admin") {

					if (paginaAtual.contains("/pages/admin/")) {
						String contextPath = ((HttpServletRequest) request).getContextPath();
						((HttpServletResponse) response).sendRedirect(contextPath + "/index.xhtml");

						return;
					}
				} else {
					session.setAttribute("isAdmin", true);
				}

			}
			if ((logged == null) || ((Boolean) logged) == false) {
				String contextPath = ((HttpServletRequest) request).getContextPath();
				((HttpServletResponse) response).sendRedirect(contextPath + "/index.xhtml");

				return;
			}

		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
		if (filterConfig != null) {
			if (debug) {
				log("AuthFilter:Initializing filter");
			}
		}
	}

	@Override
	public String toString() {
		if (filterConfig == null) {
			return ("AuthFilter()");
		}
		StringBuffer sb = new StringBuffer("AuthFilter(");
		sb.append(filterConfig);
		sb.append(")");
		return (sb.toString());
	}

	public void log(String msg) {
		filterConfig.getServletContext().log(msg);
	}

}
