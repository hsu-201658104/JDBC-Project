package com.hanshin.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class addressbook_Update {

	public static String[] name = { "kim", "lee", "park", "choi", "kang" };
	public static String[] tel = { "010-1111-2222", "010-2222-3333", "010-3333-4444", "010-4444-5555",
			"010-5555-6666" };
	public static String[] email = { "kim", "lee", "park", "choi", "kang" };
	public static String[] address = { "kim", "lee", "park", "choi", "kang" };

	public static void main(String[] args) {
		String jdbc_driver = "com.mysql.cj.jdbc.Driver";
		String jdbc_url = "jdbc:mysql://127.0.0.1:3306/databasetest?serverTimezone=UTC";
		try {
			Class.forName(jdbc_driver).newInstance();
			Connection con = DriverManager.getConnection(jdbc_url, "root", "123456");

			for (int i = 0; i < name.length; i++) { // 행 입력
				String stSql = "INSERT INTO databasetest.addressbook VALUES (?, ?, ?, ?, ?)";
				PreparedStatement st = con.prepareStatement(stSql);
				st.setInt(1, i);
				st.setString(2, name[i]);
				st.setString(3, tel[i]);
				st.setString(4, email[i]);
				st.setString(5, address[i]);
				st.executeUpdate();
				st.close();

			}
			System.out.println("\n행 입력 완료\n");
			Statement sts = con.createStatement();
			String sql = "SELECT * FROM databasetest.addressbook";
			ResultSet rs = sts.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String tel = rs.getString("tel");
				String email = rs.getString("email");
				String address = rs.getString("address");
				System.out.printf("id:  %d, name: %s, tel: %s, email: %s, address: %s" + "\n", id, name, tel, email,
						address);
			}

			for (int i = 0; i < name.length; i++) { // 행 수정(email 에 도메인 @naver.com 붙이기.
				String stSql = "Update databasetest.addressbook set email = ? where id = ?";
				PreparedStatement st = con.prepareStatement(stSql);
				String e_m = email[i] + "@naver.com";
				st.setInt(2, i);
				st.setString(1, e_m);
				st.executeUpdate();
				st.close();

			}

			System.out.println("\n행 수정 완료\n");
			sts = con.createStatement();
			sql = "SELECT * FROM databasetest.addressbook";
			rs = sts.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String tel = rs.getString("tel");
				String email = rs.getString("email");
				String address = rs.getString("address");
				System.out.printf("id:  %d, name: %s, tel: %s, email: %s, address: %s" + "\n", id, name, tel, email,
						address);
			}

			for (int i = name.length; i > name.length-3; i--) { // 하위 2개 행 삭제
				String stSql = "delete from databasetest.addressbook  where id = ?";
				PreparedStatement st = con.prepareStatement(stSql);
				st.setInt(1, i);
				st.executeUpdate();
				st.close();

			}

			System.out.println("\n행 삭제 완료\n");
			sts = con.createStatement();
			sql = "SELECT * FROM databasetest.addressbook";
			rs = sts.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String tel = rs.getString("tel");
				String email = rs.getString("email");
				String address = rs.getString("address");
				System.out.printf("id:  %d, name: %s, tel: %s, email: %s, address: %s" + "\n", id, name, tel, email,
						address);
			}

			rs.close();
			sts.close();

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
