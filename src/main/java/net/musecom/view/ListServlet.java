package net.musecom.view;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.musecom.database.BlogDto;
import net.musecom.database.BlogImpl;
import net.musecom.database.FileDto;
import net.musecom.util.Pagination;


@WebServlet("/list")
public class ListServlet extends HttpServlet {
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		// file db에서 blog_num 이 0 인 컬럼을 검색
		// 해당 파일을 삭제
		// db도 삭제
		// blog 목록을 검색
		// 목록에 맞는 파일을 검색
		
		String links = "C:\\www\\react\\myblog\\public\\data\\img\\"; // 실제 파일이 있는 경로
		String fData;
		String fLink;
		String page = req.getParameter("page");
		
		int pg = 1;
		
		if(page != null) {
			pg = Integer.parseInt(page);
		}
		
		res.setContentType("text/plan; charset=UTF-8");
		PrintWriter out = res.getWriter();
		
		BlogImpl blog = new BlogImpl();
		
		Pagination pagination = new Pagination();
		int totalCnt = blog.bListCount();
		pagination.setPageInfo(pg, 12, 15, totalCnt);
		
		List<FileDto> fLists = blog.fileList(0);
		if(fLists.size() > 0) {
			for(FileDto fList : fLists) {
				fData = fList.getNewname();
				fLink = links + fData;
				
				File file = new File(fLink);
				if(file.exists()) {
					file.delete();
					System.out.println(fLink + " - 삭제 성공");
				}
				blog.fileDelete(fList.getNum());
				System.out.println("db 삭제 성공");
			}
		}  // 쓰레기 파일 삭제하는 부분
		
		List<BlogDto> lists = blog.bList(pg);
		
		String content = null;
		for(BlogDto list : lists) {
			try {
				content = removeHtmlTag(list.getContent());
				list.setContent(content);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			List<FileDto> fdto = blog.fileList(list.getNum());
			if(fdto.size() > 0) {
				String filename = fdto.get(0).getNewname();
				String fileExt = fdto.get(0).getExt();
				int fileSize = (int) fdto.get(0).getFilesize();
				
				list.setFileName(filename);
				list.setFileSize(fileSize);
				list.setFileExit(fileExt);
			}
		}
		
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("/list.jsp");
		req.setAttribute("list", lists);
		req.setAttribute("pagination", pagination);
		requestDispatcher.forward(req, res);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		doGet(request, response);
	}
	
	public String removeHtmlTag(String html) throws Exception {
	    return html.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
	}

}
