package enjoytrip.article.controller;

import enjoytrip.article.domain.Type;
import enjoytrip.article.dto.Base64Image;
import enjoytrip.article.dto.form.ArticleSaveForm;
import enjoytrip.article.dto.form.ArticleUpdateForm;
import enjoytrip.article.dto.request.ArticleFindRequest;
import enjoytrip.article.dto.request.ArticleSaveRequest;
import enjoytrip.article.dto.request.ArticleUpdateRequest;
import enjoytrip.article.dto.response.ArticleFindResponse;
import enjoytrip.article.dto.response.ArticleSaveResponse;
import enjoytrip.article.dto.response.ArticleUpdateResponse;
import enjoytrip.article.exception.ArticleNotFoundException;
import enjoytrip.article.exception.ArticleSaveException;
import enjoytrip.article.exception.ArticleTypeMismatchException;
import enjoytrip.article.exception.ArticleUpdateException;
import enjoytrip.article.service.ArticleService;
import enjoytrip.article.util.file.FileStore;
import enjoytrip.global.ErrorResult;
import java.net.MalformedURLException;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true", allowedHeaders = "*", methods = {
    RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT,
    RequestMethod.HEAD, RequestMethod.OPTIONS})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final FileStore fileStore;

    @ExceptionHandler //TODO : 예외 공통 처리 대상
    public ResponseEntity<ErrorResult> articleNotFoundExceptionHandler(ArticleNotFoundException e) {
        log.error("[exceptionHandle] ex", e);
        ErrorResult errorResult = new ErrorResult(400, "ARTICLE-FIND-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler //TODO : 예외 공통 처리 대상
    public ResponseEntity<ErrorResult> articleTypeMismatchExceptionHandler(
        ArticleTypeMismatchException e) {
        log.error("[exceptionHandle] ex", e);
        ErrorResult errorResult = new ErrorResult(400, "ARTICLE-TYPE-MISMATCH-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler //TODO : 예외 공통 처리 대상
    public ResponseEntity<ErrorResult> articleSaveExceptionHandler(ArticleSaveException e) {
        log.error("[exceptionHandle] ex", e);
        ErrorResult errorResult = new ErrorResult(400, "ARTICLE-SAVE-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler //TODO : 예외 공통 처리 대상
    public ResponseEntity<ErrorResult> articleUpdateExceptionHandler(ArticleUpdateException e) {
        log.error("[exceptionHandle] ex", e);
        ErrorResult errorResult = new ErrorResult(400, "ARTICLE-UPDATE-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<?> findByPage(@RequestBody ArticleFindRequest request,
        @PageableDefault(size = 5, page = 0) Pageable pageable) {
        if (!isValidType(request.getType())) {
            throw new ArticleTypeMismatchException();
        }
        return ResponseEntity.ok(articleService.findByPage(request, pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ArticleFindResponse> findById(@PathVariable("id") Long id) {
        ArticleFindResponse findArticleResponse = articleService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(findArticleResponse);
    }

    @GetMapping("/{fileName}/images")
    public Resource file(@PathVariable String fileName) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(fileName));
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ArticleSaveResponse> save(@RequestBody ArticleSaveRequest request)
        throws Exception {
        ArticleSaveForm saveForm = new ArticleSaveForm(request);
        if (request.getBase64Image() != null) {
            MultipartFile image = getMultipartFile(request.getBase64Image());
            saveForm.addImage(image);
        }
        ArticleSaveResponse articleSaveResponse = articleService.save(saveForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(articleSaveResponse);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ArticleUpdateResponse> update(@RequestBody ArticleUpdateRequest request)
        throws Exception {
        ArticleUpdateForm updateForm = new ArticleUpdateForm(request);
        if (request.getBase64Image() != null) {
            MultipartFile image = getMultipartFile(request.getBase64Image());
            updateForm.addImage(image);
        }

        ArticleUpdateResponse articleUpdateResponse = articleService.update(updateForm);

        return ResponseEntity.status(HttpStatus.OK).body(articleUpdateResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        articleService.delete(id);
        //204(No Content) : 작업이 수행되었으며 별도로 내용을 반환할게 없을 때.
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public static boolean isValidType(Type type) {
        return Type.TOUR == type || Type.BOARD == type;
    }

    private MultipartFile getMultipartFile(Base64Image base64Image) {
        String fileName = base64Image.getOriginalName();
        String base64File = base64Image.getBase64File();
        String extension = base64Image.getExtension();

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedByte = decoder.decode(base64File.getBytes());

        return new MockMultipartFile("image", fileName, extension, decodedByte);
    }
}
