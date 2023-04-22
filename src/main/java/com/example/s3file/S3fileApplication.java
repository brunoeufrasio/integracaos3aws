package com.example.s3file;

import com.example.s3file.core.AttachmentService;
import com.example.s3file.dto.AttachmentDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class S3fileApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(S3fileApplication.class, args);

		AttachmentService service = new AttachmentService();
		AttachmentDTO dto = new AttachmentDTO();
		dto.setFilename("testes3");
		dto.setFileExtension("pdf");
		dto.setBase64File(Util.converterPDFByteArray("C:\\Users\\brunocris\\OneDrive\\Downloads\\pdf_sample_2.pdf"));

		service.uploadFile(dto);


	}

}
