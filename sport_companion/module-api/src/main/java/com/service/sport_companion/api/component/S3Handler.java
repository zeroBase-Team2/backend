package com.service.sport_companion.api.component;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Handler {

  // Config에서 s3Client라는 이름으로 등록된 bean을 사용한다.
  private final AmazonS3 s3Client;

  @Value("${cloud.S3.bucket}")
  private String bucket;

  public String upload(MultipartFile multipartFile) throws IOException {
    // MultipartFile을 File로 변환
    File uploadFile = convert(multipartFile)
        .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환에 실패했습니다."));

    String fileName =  "image/" + UUID.randomUUID() + uploadFile.getName();

    // S3에 파일을 업로드. 업로드 완료 여부와 관계 없이 임시 경로에 생성된 파일을 삭제
    try {
      return putS3(uploadFile, fileName);
    } finally {
      removeNewFile(uploadFile);
    }
  }

  private Optional<File> convert(MultipartFile file) throws IOException {
    // 임시 경로에 file을 생성한다.
    String fileOriginalName = file.getOriginalFilename();
    File convertFile = new File(Objects.requireNonNull(fileOriginalName));

    // MultipartFile의 내용을 convertFile에 작성한다.
    if (convertFile.createNewFile()) {
      try (FileOutputStream fos = new FileOutputStream(convertFile)) {
        fos.write(file.getBytes());
      }
      return Optional.of(convertFile);
    }
    return Optional.empty();
  }

  private String putS3(File uploadFile, String fileName) {
    // S3에 파일을 업로드한다.
    s3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
        .withCannedAcl(CannedAccessControlList.PublicRead));

    // 업로드된 파일의 경로를 가져온다.
    return s3Client.getUrl(bucket, fileName).toString();
  }

  private void removeNewFile(File targetFile) {
    if (targetFile.delete()) {
      log.info("파일이 삭제되었습니다.");
    } else {
      log.info("파일이 삭제되지 못했습니다.");
    }
  }

}

