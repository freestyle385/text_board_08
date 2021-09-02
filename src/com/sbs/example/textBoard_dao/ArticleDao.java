package com.sbs.example.textBoard_dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.textBoard.Article;
import com.sbs.example.textBoard.util.DBUtil;
import com.sbs.example.textBoard.util.SecSql;

public class ArticleDao {

	private Connection conn;

	public ArticleDao(Connection conn) {
		this.conn = conn;
	}

	public int write(String title, String body) {

		SecSql sql = new SecSql();

		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);

		int id = DBUtil.insert(conn, sql); // DB에 새로 생성된 데이터의 id를 조회해 가져옴

		return id;
	}

	public boolean articleExists(int id) {
		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(*) > 0");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);

		return DBUtil.selectRowBooleanValue(conn, sql);
		// 입력된 id의 게시물이 있다면 'SELECT COUNT(*) > 0' 식이 참이므로 1(true), 없다면 0(false)
	}

	public void update(String title, String body, int id) {
		SecSql sql = new SecSql();

		sql.append("UPDATE article");
		sql.append("SET updateDate = NOW()");
		sql.append(", title= ?", title);
		sql.append(", `body` = ?", body);
		sql.append("WHERE id = ?", id);

		DBUtil.update(conn, sql);
	}

	public List<Article> getArticles() {
		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("ORDER BY id DESC");

		List<Map<String, Object>> articlesListMap = DBUtil.selectRows(conn, sql);
		// DB에서 조회해 불러온 데이터의 구조를 자바에서는 배열로 바로 인식할 수 없음 => 우선 맵(key,value) 리스트로 불러오기

		List<Article> articles = new ArrayList<>();

		for (Map<String, Object> articleMap : articlesListMap) {
			articles.add(new Article(articleMap));
		}
		// 맵 리스트의 데이터 articleMap을 for문을 통해 articles 배열에 하나씩 넣어줌

		return articles;
	}

	public Article getArticleById(int id) {
		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);
		Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

		if (articleMap.isEmpty()) {
			return null;
		}

		return new Article(articleMap);
	}

	public void delete(int id) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM article");
		sql.append("WHERE id = ?", id);

		DBUtil.delete(conn, sql);
	}

}
