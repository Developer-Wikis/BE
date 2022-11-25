package com.developer.wiki.oauth.util;


import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.developer.wiki.config.S3Config;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
@Service
@RequiredArgsConstructor
public class AwsService {
//    @Value("${cloud.aws.defaultUrl}")
    private final String defaultUrl="https://kr.object.ncloudstorage.com/devwiki/";
//
//    @Value("${cloud.aws.s3.bucket}")
    private final String bucketName="devwiki";
    private final S3Config s3Config;


    public void S3Test(){


        List<Bucket> buckets = s3Config.amazonS3Client().listBuckets();
        System.out.println("Bucket List: ");
        for (Bucket bucket : buckets) {
            System.out.println("    name=" + bucket.getName() + ", creation_date=" + bucket.getCreationDate() + ", owner=" + bucket.getOwner().getId());
        }

    }

    public String upload(String saveFileName,MultipartFile multipartFile) throws IOException {
        File file = new File(System.getProperty("user.dir") + saveFileName);
        multipartFile.transferTo(file);
        final TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(s3Config.amazonS3Client()).build();
        System.out.println(saveFileName);
        final PutObjectRequest request = new PutObjectRequest(bucketName, saveFileName, file);
        final Upload upload =  transferManager.upload(request);
        try {
            upload.waitForCompletion();
            AccessControlList accessControlList = s3Config.amazonS3Client().getObjectAcl(bucketName, saveFileName);
            accessControlList.grantPermission(GroupGrantee.AllUsers, Permission.Read);
            s3Config.amazonS3Client().setObjectAcl(bucketName, saveFileName, accessControlList);
            return defaultUrl+saveFileName;
        } catch (AmazonClientException | InterruptedException amazonClientException) {
            amazonClientException.printStackTrace();
            return null;
        }finally {
            file.delete();
        }
    }
}
