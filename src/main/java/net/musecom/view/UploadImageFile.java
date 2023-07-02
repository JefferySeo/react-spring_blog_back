package net.musecom.view;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.JsonObject;

import net.musecom.database.BlogImpl;
import net.musecom.database.FileDto;


@WebServlet("/uploadImageFile")
public class UploadImageFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	// 캐릭터셋, 경로, 이미지 최대용량, 새 파일이름
	private static final String CHARSET = "utf-8";
	private static final String ATTACHES_DIR = "C:/www/react/myblog/public/data/img";
	private static final int LIMIT_SIZE = 1024 * 1024 * 100;
	private static String nfilename = "ts";
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BlogImpl blogfile = new BlogImpl();
		FileDto fDto = new FileDto();
		PrintWriter out = response.getWriter();
		JsonObject obj = new JsonObject();
		String Filetype = (String) request.getSession().getAttribute("fileType");
		File attacheDir = new File(ATTACHES_DIR);
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		
		fileItemFactory.setRepository(attacheDir);
		fileItemFactory.setSizeThreshold(LIMIT_SIZE);
		ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
		HttpSession session = request.getSession(true);
		if(session.getAttribute("imname") == null ) {
			session.setAttribute("imname", Integer.toString((int) (System.currentTimeMillis() / 1000)));
		}
		
		response.setContentType("text/html; charset=UTF-8");
		
		try {
			List<FileItem> items = fileUpload.parseRequest(request);
			for(FileItem item : items) {
				// 원래 파일 이름
				String oname = item.getName();
				// 확장자
				String ext = oname.substring(oname.indexOf("."));
				// 새 파일이름
				nfilename = "ts" + Integer.toString((int) (System.currentTimeMillis() / 1000));
				String nname = nfilename + ext;
				
				File uploadedFile = new File(ATTACHES_DIR + File.separator + nname);
				item.write(uploadedFile);
				int filesize = (int) item.getSize();
				
				System.out.printf("파라미터명 : %s, 파일명 : %s, 파일크기 : %s byte, 새 파일명 : %s \n", item.getFieldName(), item.getName(), item.getSize(), nname);
				
				int imname = Integer.parseInt((String)session.getAttribute("imname"));
				fDto.setNewname(nname);
				fDto.setOldname(oname);
				fDto.setFilesize(filesize);
				fDto.setExt(ext);
				fDto.setImname(imname);
				
				int result = blogfile.fileInsert(fDto);
				System.out.println("업데이트 된 글 번호 : " + result);
				
				
				
				// json 타입으로 리턴
				obj.addProperty("url", "http://localhost:3000/data/img/" + nname);
				
			}
			
			out.println(obj);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
