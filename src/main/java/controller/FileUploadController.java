package controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {
	public static String uploadDirectory = System.getProperty("user.dir")+"/uploads";
	String folderPath = "C:\\Users\\Ajit\\Documents\\Test";
	
	@RequestMapping("/")
	 
	public String uploadPage(Model model) {
		File folder = new File(folderPath);
		String Path = System.getProperty("user.dir");
		File[] ListOfFiles = folder.listFiles();
		model.addAttribute("downloadableFiles", ListOfFiles);			
		model.addAttribute("projectPath",Path );
		return "uploadView";
	}
	
	
	@RequestMapping("/file/{fileName}")
	@ResponseBody
	public void show(@PathVariable("fileName") String fileName, HttpServletResponse response) {
		if(fileName.indexOf(".xls")>-1) response.setContentType("application/vnd.ms-excel");		
		if(fileName.indexOf(".xml")>-1) response.setContentType("application/xml");		
		if(fileName.indexOf(".properties")>-1) response.setContentType("application/Properties");
		
		response.setHeader("Content-Disposition", "attachment; filename="+fileName);
		response.setHeader("Content-Transfer-Encoding","binary");
		
		try {
			BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
			FileInputStream fis = new FileInputStream(folderPath+fileName);
			int len;
			byte[] buf = new byte[1024];
			while((len = fis.read(buf))>0) {
				bos.write(buf,0,len);
			}
			bos.close();
			response.flushBuffer();
		}catch(IOException e) {
			System.out.println(e);
		}
	}
	
	@RequestMapping("/upload")
	public String upload(Model model,@RequestParam("files") MultipartFile[] files) {
		StringBuilder fileNames = new StringBuilder();
		for(MultipartFile file:files) {
			Path filenameAndPath = Paths.get(uploadDirectory,file.getOriginalFilename());
			fileNames.append(file.getOriginalFilename());
			fileNames.append('\n');
			try {
				Files.write(filenameAndPath,file.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		model.addAttribute("msg", "Successfully uploaded files -> "+fileNames.toString());
		return "uploadStatusView";
	}
	

}
