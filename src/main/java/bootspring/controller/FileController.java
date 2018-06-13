package bootspring.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import bootspring.load.UploadFileResponse;
import bootspring.service.FileStorageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@RestController
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;
    
    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        
        String dirSource = "C:/Users/karro/eclipse-workspace/bootspring/uploads/";
        File fileOnThisDir = new File(dirSource + fileName);
        
        if (fileOnThisDir.exists()) System.out.println("istnieje");
        else System.out.println("nie istnieje");
        
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        
        
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
        
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    
    @RequestMapping(value = "/uploads/{file_name}", method = RequestMethod.GET)
    public void getFile(@PathVariable("file_name") String fileName, HttpServletResponse response) {
        try {
          // get your file as InputStream
          InputStream is = new FileInputStream("C:/Users/karro/eclipse-workspace/bootspring/uploads/" + fileName + ".k");
          File file = new File("C:/Users/karro/eclipse-workspace/bootspring/uploads/" + fileName + ".k");
          if (file.exists()) {
        	  System.out.println("Plik o takiej nazwie istnieje");
          }
          else {
        	  response.flushBuffer();
          }
        } catch (IOException ex) {
          throw new RuntimeException("IOError writing file to output stream");
        }

    }
    
    @RequestMapping(value = "/generateModel/{file_name}", method = RequestMethod.GET)
    public JSONObject generateModel(@PathVariable("file_name") String fileName) {
    	
    	JSONObject entity = new JSONObject();
    	JSONArray positionX = new JSONArray();
    	JSONArray positionY = new JSONArray();
    	JSONArray positionZ = new JSONArray();
    	JSONArray temperature = new JSONArray();
    	
    	File file = new File("C:/Users/karro/eclipse-workspace/bootspring/uploads/" + fileName + ".k");
    	
    	try {
			Scanner scanner = new Scanner(file);
			
			String actualLine = "";
			int counter = 1;
			while (scanner.hasNext()) {
				
				actualLine = scanner.nextLine();
				
				if (actualLine.equals("*NODE")) continue;
				else if (counter < 1405) {
					String[] data = actualLine.split(",");
					positionX.add(data[1].replaceAll(" ", ""));
					positionY.add(data[2].replaceAll(" ", ""));
					positionZ.add(data[3].replaceAll(" ", ""));
					temperature.add(data[4].replaceAll(" ", ""));
				}
				counter++;
				
			}
			
			entity.put("positionX", positionX);
			entity.put("positionY", positionY);
			entity.put("positionZ", positionZ);
			entity.put("temperature", temperature);
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    	return entity;
    }
    
}
