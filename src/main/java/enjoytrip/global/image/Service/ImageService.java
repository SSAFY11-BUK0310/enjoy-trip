package enjoytrip.global.image.Service;

import enjoytrip.global.image.FileStore;
import enjoytrip.global.image.UploadFile;
import enjoytrip.global.image.domain.Image;
import enjoytrip.global.image.dto.request.ImageSave2ImageStorageRequest;
import enjoytrip.global.image.dto.response.ImageSaveResponseDto;
import enjoytrip.global.image.repository.ImageRepository;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final FileStore fileStore;

    // 이미지를 이미지 저장소까지만 저장하고, 데이터베이스에는 저장하지 않는다.
    public ImageSaveResponseDto save2ImageStorage(ImageSave2ImageStorageRequest request)
        throws IOException {
        MultipartFile multipartFile = getMultipartFile(request);
        UploadFile uploadFile = fileStore.storeFile(multipartFile, request.getDirectoryUUID());
        return new ImageSaveResponseDto(uploadFile.getUploadFileUUID());
    }

    // 이미지 스토리지에 있는 이미지 단건에 대한 정보 DB 저장
    public void saveToDB(Image image) {
        Long imageSaveResult = imageRepository.save(image);
        if (imageSaveResult == 0) {
            throw new RuntimeException();
        }
    }

    public List<Image> findByArticleId(Long articleId) {
        return imageRepository.findByArticleId(articleId);
    }

    public Image findByImageUUID(String imageUUID) {
        return imageRepository.findByImageUUID(imageUUID);
    }

    public void deleteByImageUUID(String imageUUID) {
        Long result = imageRepository.deleteByImageUUID(imageUUID);
        if (result == 0) {
            throw new RuntimeException();
        }
    }

    public Long deleteByArticleId(Long articleId) {
        return imageRepository.deleteByArticleId(articleId);
    }

    public Long deleteByDirectoryUUID(String directoryUUID) {
        return imageRepository.deleteByDirectoryUUID(directoryUUID);
    }

    private MultipartFile getMultipartFile(ImageSave2ImageStorageRequest request) {

        String fileName = request.getOriginalName();
        String base64File = request.getBase64File();
        String mimeType = request.getMimeType();

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedByte = decoder.decode(base64File.getBytes());
        return new MockMultipartFile("file", fileName, mimeType, decodedByte);
    }
}
