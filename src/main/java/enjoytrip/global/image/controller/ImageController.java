package enjoytrip.global.image.controller;

import enjoytrip.global.image.FileStore;
import enjoytrip.global.image.service.ImageService;
import enjoytrip.global.image.domain.Image;
import enjoytrip.global.image.dto.request.ImageSave2ImageStorageRequest;
import enjoytrip.global.image.dto.response.ImageSaveResponseDto;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true", allowedHeaders = "*", methods = {
    RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT,
    RequestMethod.HEAD, RequestMethod.OPTIONS})
@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
@Slf4j
public class ImageController {

    @Value("${file.dir}")
    private String fileDir;

    private final ImageService imageService;
    private final FileStore fileStore;

    // 저장된 이미지를 반환한다.
    @GetMapping("/{directoryUUID}/{imageUUID}")
    public Resource find(@PathVariable("directoryUUID") String directoryUUID,
        @PathVariable("imageUUID") String imageUUID)
        throws IOException {

        System.out.println("directoryUUID = " + directoryUUID);

        String fullPath = fileStore.getFullPath(directoryUUID + "/" + imageUUID);
        fullPath = fullPath.substring(2);
        System.out.println("fullPath = " + fullPath);
        return new UrlResource("file:" + fullPath);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<List<Image>> findByArticleId(@PathVariable("articleId") Long articleId) {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.findByArticleId(articleId));
    }

    // 게시물 작성 페이지에 들어오는 순간 요청이 들어오고 해당 게시물에 대한 directory를 만들어 반환한다.
    @PostMapping("/mkdir")
    public ResponseEntity<Map<String, String>> mkdir() {
        return ResponseEntity.status(HttpStatus.CREATED).body(fileStore.mkdir());
    }

    // 게시물 작성을 완료하지 않고 취소한 경우, 만들었던 directory를 제거한다.
    @DeleteMapping("/{directoryUUID}")
    public ResponseEntity<String> rmDir(@PathVariable("directoryUUID") String directoryUUID)
        throws IOException {
        fileStore.rmdir(directoryUUID);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // image를 이미지 저장소에 저장한다.
    @PostMapping
    public ResponseEntity<ImageSaveResponseDto> save(
        @RequestBody ImageSave2ImageStorageRequest request)
        throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(imageService.save2ImageStorage(request));
    }
}
