package CouponRedeemSystem.System.File.model;

public class TextFile {
	String fileName;
	String fileContent;
	
	public TextFile(String fileName, String fileContent) {
		this.fileName = fileName;
		this.fileContent = fileContent;
	}
	
	public String getFileName() {return fileName;}
	public void setFiName(String fileName) {this.fileName = fileName;}
	public String getFileContent() {return fileContent;}
	public void setFileContent(String fileContent) {this.fileContent = fileContent;}
}
