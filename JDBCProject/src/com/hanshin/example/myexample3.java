package com.hanshin.example;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/my3")
public class myexample3 extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		String v_ = request.getParameter("value");
		int v = Integer.parseInt(v_);
		String op = request.getParameter("operator");

		ServletContext application = request.getServletContext();
		HttpSession session = request.getSession();
		Cookie[] cookies = request.getCookies();

		int result = 0;
		int result1 = 0;
		int result2 = 0;
		if (op.equals("=")) {
			// 저장되어있는 값과 연산자 가지고 와서 현재값이랑 연산 후 출력 = result
			int prev1 = (int) application.getAttribute("value");
			String prev_op1 = (String) application.getAttribute("operator");

			int prev2 = (int) session.getAttribute("value");
			String prev_op2 = (String) session.getAttribute("operator");

			int prev = 0;
			String prev_op = "";
			for (Cookie c : cookies) {
				if (c.getName().equals("value")) {
					prev = Integer.parseInt(c.getValue());
				}
				if (c.getName().equals("operator")) {
					prev_op = c.getValue();
				}
			}

			if (prev_op.equals("+")) {
				result = prev + v;
			} else if (prev_op.equals("-")) {
				result = prev - v;
			}
			if (prev_op1.equals("+")) {
				result1 = prev1 + v;
			} else if (prev_op.equals("-")) {
				result1 = prev1 - v;
			}
			if (prev_op2.equals("+")) {
				result2 = prev2 + v;
			} else if (prev_op.equals("-")) {
				result2 = prev2 - v;
			}

			response.getWriter().printf("Cookie is %d\n", result);
			response.getWriter().printf("Attribute result %d\n", result1);
			response.getWriter().printf("Session result %d\n", result2);

		} else {
			// 값과 연산자를 저장
			application.setAttribute("value", v);
			application.setAttribute("operator", op);

			session.setAttribute("value", v);
			session.setAttribute("operator", op);

			Cookie valueCookie = new Cookie("value", String.valueOf(v));
			Cookie operatorCookie = new Cookie("operator", op);
			valueCookie.setMaxAge(60 * 60 * 2);

			response.addCookie(valueCookie);
			response.addCookie(operatorCookie);

			response.sendRedirect("Calculator2.html");

		}
	}

}
