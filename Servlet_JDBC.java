package com.hanshin.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.ResultSet;

@WebServlet("/Servlet_JDBC")
public class Servlet_JDBC extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String jdbc_driver = "com.mysql.cj.jdbc.Driver";
		String jdbc_url = "jdbc:mysql://127.0.0.1:3306/databasetest?serverTimezone=UTC";
		try {
			Class.forName(jdbc_driver).newInstance();
			Connection con = DriverManager.getConnection(jdbc_url, "root", "123456");
			Statement st = con.createStatement();
			String sql = "SELECT * FROM databasetest.member";
			ResultSet rs = st.executeQuery(sql);
			response.setContentType("text/html;charset=UTF-8");
			request.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();

			HttpSession session = request.getSession();
			String op = request.getParameter("operator");

			String id = request.getParameter("id");
			String password = request.getParameter("pwd");
			String name = request.getParameter("name");
			String address = request.getParameter("tel");
			String email = request.getParameter("email");
			String major = request.getParameter("dept");
			String gender = request.getParameter("gender");
			String birth = request.getParameter("birth");
			String word = request.getParameter("introduction");
			int k = 0;
			while (rs.next()) {
				k++;
			}
			String[] id_ = new String[k];
			String[] password_ = new String[k];
			String[] name_ = new String[k];
			String[] address_ = new String[k];
			String[] email_ = new String[k];
			String[] major_ = new String[k];
			String[] gender_ = new String[k];
			String[] birth_ = new String[k];
			String[] word_ = new String[k];

			rs = st.executeQuery(sql);
			int t = 0;
			while (rs.next()) {
				id_[t] = rs.getString("id");
				password_[t] = rs.getString("password");
				name_[t] = rs.getString("name");
				address_[t] = rs.getString("address");
				email_[t] = rs.getString("email");
				major_[t] = rs.getString("major");
				gender_[t] = rs.getString("gender");
				birth_[t] = rs.getString("birth");
				word_[t] = rs.getString("word");
				t++;
			}
			// id, name 이 같고, pass 같을때
			String CorrectP = "";
			// id, name 이 같고, pass 다를때
			String Correct = "";
			for (int i = 0; i < id_.length; i++) {
				if (id.equals(id_[i])) {
					if (name.equals(name_[i])) {
						if (password.equals(password_[i])) {
							CorrectP = "A";
							Correct = null;
							break;
						} else {
							Correct = "A";
							CorrectP = null;
							break;
						}
					} else {
						Correct = null;
						CorrectP = null;
					}
				} else {
					Correct = null;
					CorrectP = null;
				}

			}

			session.setAttribute("operator", op);
			String Sop = (String) session.getAttribute("operator");
			// 전송
			if (Sop.equals("전송")) {
				out.print("<html><head><title>201658104 박영창</title></head>");
				out.print("<body>");
				out.print("<h1>Servlet + JDBC</h1>");
				out.print("\n id=" + id + ", 이름 : " + name + ", 주소 : " + address + ", email : " + email + ", 전공 : "
						+ major + ", 성별 : " + gender + ", 태어난 계절 : " + birth + ", 소개 : " + word);

				// 비번 다를때
				if (Correct == "A") {
					out.print("<br>비밀번호가 다릅니다.");

				}
				// 비밀번호 같을 때
				else if (CorrectP == "A") {
					String stSql = "Update databasetest.member set address ='" + address + "',email ='" + email
							+ "',major ='" + major + "',gender ='" + gender + "',birth ='" + birth + "',word ='" + word
							+ "' where id ='" + id + "'";
					PreparedStatement st2 = con.prepareStatement(stSql);
					st2.executeUpdate();
					st2.close();
					out.print("<br>업데이트 되었습니다.");

				}

				// 같은 것이 없을때
				else {
					String sqlI = "INSERT INTO databasetest.member VALUES('" + id + "','" + password + "','" + name
							+ "','" + address + "', '" + email + "','" + major + "','" + gender + "','" + birth + "','"
							+ word + "')";
					st.executeUpdate(sqlI);
					out.print("<br>내용이 추가 되었습니다.");
				}

				out.println("</body></html>");
			}

			if (Sop.equals("DB보기")) {
				out.print("<html><head><title>201658104 박영창</title></head>");
				out.print("<body>");
				out.print("<h1>Servlet + JDBC</h1>");

				for (int i = 0; i < id_.length; i++) {
					out.print("id=" + id_[i] + ", 이름 : " + name_[i] + ", 주소 : " + address_[i] + ", email : " + email_[i]
							+ ", 전공 : " + major_[i] + ", 성별 : " + gender_[i] + ", 태어난 계절 : " + birth_[i] + ", 소개 : "
							+ word_[i] + "<br>");

				}
				out.print("</body></html>");

			}

			if (Sop.equals("DB전체삭제")) {
				out.print("<html><head><title>201658104 박영창</title></head>");
				out.print("<body>");
				out.print("<h1>Servlet + JDBC</h1>");
				String stSql = "delete from databasetest.member";
				PreparedStatement st2 = con.prepareStatement(stSql);
				st2.executeUpdate();
				st2.close();
				out.print("DB에 모든 정보를 삭제하였습니다.");
				out.print("</body></html>");

			}

			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
