package com.example.s3file.core;

import java.util.Base64;

import javax.annotation.PostConstruct;

import com.example.s3file.dto.AttachmentDTO;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import lombok.Getter;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
public class AttachmentService {


    @Getter
    //@Value("${cloud.aws.credentials.accessKey}")
    private String key = "key";

    @Getter
   // @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey = "secretKey";

    @Getter
    //@Value("${application.files.baseUrl}")
    private String baseUrl = "https://s3.amazonaws.com/";

    @Getter
    //@Value("${application.files.bucket}")
    private String bucket = "bucket.cis.medwise.com.br";

    @Getter
    //@Value("${application.files.folder}")
    private String folder = "cis-ms-api/clinical";

    @Getter
    private S3Client s3Client;

    @PostConstruct
    public void initialize() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(key, secretKey);
        s3Client = S3Client.builder().credentialsProvider(StaticCredentialsProvider
                .create(awsCreds)).region(Region.SA_EAST_1).build();
    }

    public AttachmentDTO uploadFile(AttachmentDTO attachment) {

        try {
            initialize();
            byte[] fileByteArray = attachment.getBase64File();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(fileByteArray);

            attachment.setFilename(attachment.getFilename() + "-" + "sds884c5vc5v54vc");

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket).key(folder + "/" + attachment.getFilename() + "." + attachment.getFileExtension())
                    .acl(ObjectCannedACL.BUCKET_OWNER_FULL_CONTROL).build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(outputStream.toByteArray()));
            outputStream.close();

            return attachment;

        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Unsuccessful upload!");
        }
    }

    public byte[] downloadFile(AttachmentDTO attachment) {

        try {

            GetObjectRequest objectRequest = GetObjectRequest
                    .builder()
                    .key(folder + "/" + attachment.getFilename() + "." + attachment.getFileExtension())
                    .bucket(bucket)
                    .build();

            ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(objectRequest);

            return objectBytes.asByteArray();

        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Unsuccessful download!");
        }
    }
}
