package enjoytrip.global.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public Map<String, String> mkdir() {
        String directoryUUID = UUID.randomUUID().toString();
        File file = new File(fileDir + directoryUUID);
        file.mkdir();
        Map<String, String> map = new HashMap<>();
        map.put("directoryUUID", directoryUUID);
        return map;
    }

    public void rmdir(String directoryUUID) throws IOException {
        File file = new File(fileDir + directoryUUID);
        if (file.exists()) {
            FileUtils.cleanDirectory(file); // 폴더 하위 내용을 삭제
            file.delete(); // 해당 폴더 삭제
        }
    }

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile, ""));
            }
        }
        return storeFileResult;
    }

    public UploadFile storeFile(MultipartFile multipartFile, String directoryUUID)
        throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = getStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(directoryUUID + "/" + storeFileName)));
        return UploadFile.builder()
            .uploadFileName(originalFilename)
            .uploadFileUUID(storeFileName)
            .build();
    }

    public void deleteFile(String directoryUUID, String fileName) {
        String fullPath = getFullPath(directoryUUID + "/" + fileName);
        try {
            File file = new File(fullPath);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private String getStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    public MultipartFile retrieveFile(String directoryUUID, String imageUUID)
        throws IOException {
        String fullPath = getFullPath(directoryUUID + "/" + imageUUID);
        File file = new File(fullPath);
        FileInputStream data = new FileInputStream(file);

        return new MockMultipartFile(
            "image",
            file.getName(),
            "image/*",
            data
        );
    }
}
