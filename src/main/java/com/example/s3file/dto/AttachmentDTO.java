package com.example.s3file.dto;

public class AttachmentDTO {

    private  byte[] base64File;
    private String filename;
    private String fileExtension;

    public byte[] getBase64File() {
        return base64File;
    }

    public void setBase64File(byte[] base64File) {
        this.base64File = base64File;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }
}
