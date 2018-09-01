package Http.Request;

public class MultipartFile {
    private String fileName;
    private byte[] fileBytes;

    public MultipartFile(String a_fileName, byte[] a_fileBytes){
        this.fileName = a_fileName;
        this.fileBytes = a_fileBytes;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }
}
