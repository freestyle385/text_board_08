package com.sbs.example.textBoard_controller;

import java.util.List;

import com.sbs.example.textBoard.Container;
import com.sbs.example.textBoard_dto.Article;
import com.sbs.example.textBoard_service.ArticleService;

public class ArticleController extends Controller {

	private ArticleService articleService;

	public ArticleController() {
		articleService = Container.articleService;
	}

	public void doWrite(String command) {

		if (Container.session.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}

		System.out.println("---게시글 생성---");
		System.out.print("제목 : ");
		String title = sc.nextLine();
		System.out.print("내용 : ");
		String body = sc.nextLine();
		int memberId = Container.session.loginedMemberId;

		int id = articleService.write(memberId, title, body);

		System.out.printf("%d번 게시물이 생성되었습니다.\n", id);
	}

	public void doModify(String command) {

		if (Container.session.isLogined() == false) {
			System.out.println("로그인 후 이용해 주세요.");
			return;
		}

		int id = Integer.parseInt(command.split(" ")[2]);
		System.out.printf("---%d번 게시글 수정---\n", id);

		boolean articleExists = articleService.articleExists(id);

		if (articleExists == false) {
			System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
			return;
		}

		System.out.print("새 제목 : ");
		String title = sc.nextLine();
		System.out.print("새 내용 : ");
		String body = sc.nextLine();

		articleService.update(title, body, id);

		System.out.printf("%d번 게시글 수정이 완료되었습니다.\n", id);
	}

	public void showList(String command) {
		System.out.println("---게시글 리스트---");
		List<Article> articles = articleService.getArticles();

		if (articles.size() == 0) {
			System.out.println("게시물이 존재하지 않습니다.");
			return;
		}

		System.out.println("번호 / 		작성날짜 		/	작성자	/   제목");

		for (Article article : articles) {
			System.out.printf("%d   /   %s   /		%s   	/   %s\n", article.id, article.regDate,
					article.extra_writer, article.title);
		}
	}

	public void showDetail(String command) {
		int id = Integer.parseInt(command.split(" ")[2]);
		System.out.printf("---%d번 게시글 상세정보---\n", id);

		articleService.increaseHit(id);
		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
			return;
		}

		System.out.printf("번호 : %d\n", foundArticle.id);
		System.out.printf("생성일 : %s\n", foundArticle.regDate);
		System.out.printf("수정일 : %s\n", foundArticle.updateDate);
		System.out.printf("작성자 : %s\n", foundArticle.extra_writer);
		System.out.printf("제목 : %s\n", foundArticle.title);
		System.out.printf("내용 : %s\n", foundArticle.body);
		System.out.printf("조회수 : %d\n", foundArticle.hit);

	}

	public void doDelete(String command) {

		if (Container.session.isLogined() == false) {
			System.out.println("로그인 후 이용해 주세요.");
			return;
		}

		int id = Integer.parseInt(command.split(" ")[2]);
		System.out.printf("---%d번 게시글 삭제---\n", id);

		boolean articleExists = articleService.articleExists(id);

		if (articleExists == false) {
			System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
			return;
		}

		articleService.delete(id);

		System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
	}

}
