package controller;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FileDownloadController {

//	String folderPath = "C:\\Users\\Ajit\\Documents";
	@RequestMapping("/Run")
	public void showFiles() {
		try {
			System.out.println("ok");
			Process p = Runtime.getRuntime().exec("cmd /c start \"C:\\Users\\Ajit\\Desktop\\test.cmd\"") ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    
		
	}
}
