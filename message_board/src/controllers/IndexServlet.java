package controllers;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Message;
import utils.DBUtil;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// EntityManager取得
		EntityManager em = DBUtil.createEntityManager();
		// 現在のページを取得する
		int page = 1;
		try {
			page = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		// 現在のページ内での投稿内容を取得する
		List<Message> messages = em.createNamedQuery("getAllMessages", Message.class)
															 .setFirstResult(8 * (page-  1)).setMaxResults(8).getResultList();
		// 現在の投稿件数を取得する
		long messages_count = (long)em.createNamedQuery("getMessagesCount", Long.class)
				                                                .getSingleResult();
		em.close();
		// 投稿内容とフラッシュを送る
		request.setAttribute("messages", messages);	// 現在のページ内での投稿内容
		request.setAttribute("messages_count", messages_count); // 現在の投稿件数
		request.setAttribute("page", page);	// 現在のページ
		HttpSession session = request.getSession();
		if (session.getAttribute("flush") != null) { // フラッシュ
			request.setAttribute("flush", session.getAttribute("flush"));
			session.removeAttribute("flush");
		}
		// index.jspへディスパッチ
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/messages/index.jsp");
		rd.forward(request, response);
	}

}
