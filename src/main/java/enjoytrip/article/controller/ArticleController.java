package enjoytrip.article.controller;

import enjoytrip.article.domain.Article;
import enjoytrip.article.dto.*;
import enjoytrip.article.exception.ArticleNotFoundException;
import enjoytrip.article.service.ArticleService;
import enjoytrip.article.util.file.FileStore;
import enjoytrip.article.util.file.UploadFile;
import enjoytrip.global.ErrorResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;

@CrossOrigin(
        origins = "http://127.0.0.1:5500",
        allowCredentials = "true",
        allowedHeaders = "*",
        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}
)
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {
    public static final String TOUR = "tour";
    public static final String BOARD = "board";

    private final ArticleService articleService;
    private final FileStore fileStore;

    @Value("${file.dir")
    private String fileDir;

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(ArticleNotFoundException e) {
        log.error("[exceptionHandle] ex", e);
        ErrorResult errorResult = new ErrorResult(400, "ARTICLE-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    /**
     * GET      /            find       :query-string ?type={}&title={}?page={}?size={}?sort={}
     * GET      /{id}        findById
     * POST     /            save
     * PUT      /{id}        update     : json과 file을 동시에 받기 위해 @RequestPart 사용
     * DELETE   /{id}        delete
     */

    @GetMapping
    public ResponseEntity<?> find(@ModelAttribute ArticleFindRequest articleFindRequest, @PageableDefault(size = 5, page = 0) Pageable pageable) throws BadRequestException {
        if (!TOUR.equals(articleFindRequest.getType()) && !BOARD.equals(articleFindRequest.getType())) throw new BadRequestException();
        return ResponseEntity.ok(articleService.find(articleFindRequest, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleFindResponse> findById(@PathVariable("id") Long id) {
        ArticleFindResponse findArticleResponse = articleService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(findArticleResponse);
    }

    @GetMapping("/{fileName}/images")
    public Resource file(@PathVariable String fileName) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(fileName));
    }

    @PostMapping
    public ResponseEntity<ArticleSaveResponse> save(@ModelAttribute ArticleSaveRequest articleSaveRequest) throws IOException {
        ArticleSaveResponse articleSaveResponse = articleService.save(articleSaveRequest);
        return ResponseEntity.status(HttpStatus.OK).body(articleSaveResponse);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ArticleUpdateResponse> update(@PathVariable Long id,
                                                        @RequestPart("data") ArticleUpdateRequest articleUpdateRequest,
                                                        @RequestPart(value = "updateImage", required = false) MultipartFile updateImage
                                                        ) throws IOException {
        ArticleUpdateResponse articleUpdateResponse
                = articleService.update(articleUpdateRequest, updateImage);

        return ResponseEntity.status(HttpStatus.OK).body(articleUpdateResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        articleService.delete(id);
        //204(No Content) : 작업이 수행되었으며 별도로 내용을 반환할게 없을 때.
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
