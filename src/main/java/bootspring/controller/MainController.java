package bootspring.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import bootspring.load.UploadFileResponse;
import bootspring.model.Task;
import bootspring.service.TaskService;


@Controller
public class MainController {
	
	@Autowired
	private TaskService taskService;


	@GetMapping("/")
	public String home(HttpServletRequest request){
		request.setAttribute("mode", "MODE_HOME");
		return "index";
	}
	
	@GetMapping("/all-tasks")
	public String allTasks(HttpServletRequest request){
		request.setAttribute("tasks", taskService.findAll());
		request.setAttribute("mode", "MODE_TASKS");
		return "index";
	}
	
	@GetMapping("/new-task")
	public String newTask(HttpServletRequest request){
		request.setAttribute("mode", "MODE_NEW");
		return "index";
	}
	
	@PostMapping("/save-task")
	public String saveTask(@ModelAttribute Task task, BindingResult bindingResult, HttpServletRequest request){
		task.setDateCreated(new Date());
		taskService.save(task);
		request.setAttribute("tasks", taskService.findAll());
		request.setAttribute("mode", "MODE_TASKS");
		return "index";
	}
	
	@GetMapping("/update-task")
	public String updateTask(@RequestParam int id, HttpServletRequest request){
		request.setAttribute("task", taskService.findTask(id));
		request.setAttribute("mode", "MODE_UPDATE");
		return "index";
	}
	
	@GetMapping("/delete-task")
	public String deleteTask(@RequestParam int id, HttpServletRequest request){
		taskService.delete(id);
		request.setAttribute("tasks", taskService.findAll());
		request.setAttribute("mode", "MODE_TASKS");
		return "index";
	}
	
	@GetMapping("/showFiles")
    public String showFile(HttpServletRequest request) {
		
    	System.out.println(UploadFileResponse.listFile());
    	request.setAttribute("files", UploadFileResponse.listFile());
    	request.setAttribute("mode", "MODE_FILES");
    	return "index";
    	
    }
	
	@GetMapping("/createRaport")
	public String createRaport(HttpServletRequest request, HttpServletResponse response) {
		
		Date actualyDate = new Date();
		
		List<Task> actuallyTasks = new ArrayList<>();
		List<String> actuallyFiles = new ArrayList<>();
		

		for (Task task : taskService.findAll()) {
			actuallyTasks.add(task);
		}
		
		for (String f : UploadFileResponse.listFile()) {
			actuallyFiles.add(f);
		}
		
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream("C:/Users/karro/eclipse-workspace/bootspring/uploads/Raport.pdf"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		document.open();
		Font font = FontFactory.getFont(FontFactory.COURIER, 12, BaseColor.BLACK);
		try {
			Chunk chunk = new Chunk("Raport", font);
			document.add(new Paragraph("Raport"));
			document.add(chunk.NEWLINE);
			document.add(new Paragraph(actualyDate.toString()));
			document.add(chunk.NEWLINE);
			document.add(chunk.NEWLINE);
			document.add(new Paragraph("List Task:"));
			document.add(chunk.NEWLINE);
			for (int i = 0; i < actuallyTasks.size(); i++) {
				document.add(new Paragraph((i + 1) + ". " + actuallyTasks.get(i)));
				document.add(chunk.NEWLINE);
			}
			
			document.add(chunk.NEWLINE);
			document.add(chunk.NEWLINE);
			document.add(new Paragraph("List Files:"));
			document.add(chunk.NEWLINE);
			for (int i = 0; i < actuallyFiles.size(); i++) {
				document.add(new Paragraph((i + 1) + ". " + actuallyFiles.get(i)));
				document.add(chunk.NEWLINE);
			}
			document.close();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try
        {
            // get your file as InputStream
            InputStream is = new FileInputStream("C:/Users/karro/eclipse-workspace/bootspring/uploads/Raport.pdf");
            // copy it to response's OutputStream
            IOUtils.copy( is, response.getOutputStream() );
            response.flushBuffer();
        }
        catch( IOException ex )
        {
            throw new RuntimeException( "IOError writing file to output stream" );
        }
		
		request.setAttribute("files", UploadFileResponse.listFile());
    	request.setAttribute("mode", "MODE_FILES");
    	return "index";
	}
	
}
