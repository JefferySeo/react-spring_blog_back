package net.musecom.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import net.musecom.database.BlogDto;
import net.musecom.database.BlogImpl;
import net.musecom.database.FileDto;
import net.musecom.util.Pagination;

@WebServlet("/blist")
public class BlogList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public BlogList() {
        super();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String page = req.getParameter("page");
          int pg = 1;
          if(page != null) {
            pg = Integer.parseInt(page);   
          }      
           
        res.setContentType("text/plan;charset=UTF-8");
        PrintWriter out = res.getWriter();
        
        BlogImpl blog = new BlogImpl();
        Pagination pagination = new Pagination();
        
        List<BlogDto> lists = blog.bList(pg);
        String content = null;
        
        for(BlogDto list: lists) {
           
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
        
        //int totalCnt = blog.bListCount();
        //pagination.setPageInfo(pg, 12, 15, totalCnt);
        
        String gson = new Gson().toJson(lists);  //gson을 이용해 json 타입으로 변환
        out.println(gson);
        out.close();
     }


     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
     }

     
     public String removeHtmlTag(String html) throws Exception {
        return html.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
     }
}
