package bootspring.load;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UploadFileResponse {

	private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

    public UploadFileResponse(String fileName, String fileDownloadUri, String fileType, long size) {
        this.setFileName(fileName);
        this.setFileDownloadUri(fileDownloadUri);
        this.setFileType(fileType);
        this.setSize(size);
    }

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileDownloadUri() {
		return fileDownloadUri;
	}

	public void setFileDownloadUri(String fileDownloadUri) {
		this.fileDownloadUri = fileDownloadUri;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
	
	public static boolean checkExistFile(String filename) {
		
		File localFile = new File("C:\\Users\\karro\\eclipse-workspace\\bootspring\\uploads\\" + filename);
		
		if (localFile.exists()) {
			return true;
		} 
		else {
			return false;
		}
		
	}
	
	public static List<String> listFile() {
		
		File folder = new File("C:/Users/karro/eclipse-workspace/bootspring/uploads/");
		File[] listOfFiles = folder.listFiles();
		
		List<String> filesNames = new ArrayList<>();
		
		for (int i = 0; i < listOfFiles.length; i++) {
			filesNames.add(listOfFiles[i].getName());
		}
		
		return filesNames;
	}
        
}
