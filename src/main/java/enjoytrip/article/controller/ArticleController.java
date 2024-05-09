package enjoytrip.article.controller;

import enjoytrip.article.dto.ArticleFindRequest;
import enjoytrip.article.dto.ArticleFindResponse;
import enjoytrip.article.dto.ArticleSaveRequest;
import enjoytrip.article.dto.ArticleSaveResponse;
import enjoytrip.article.dto.ArticleUpdateRequest;
import enjoytrip.article.dto.ArticleUpdateResponse;
import enjoytrip.article.exception.ArticleNotFoundException;
import enjoytrip.article.service.ArticleService;
import enjoytrip.article.util.file.FileStore;
import enjoytrip.global.ErrorResult;
import java.io.IOException;
import java.net.MalformedURLException;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(
    origins = "http://127.0.0.1:5500",
    allowCredentials = "true",
    allowedHeaders = "*",
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT,
        RequestMethod.HEAD, RequestMethod.OPTIONS}
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

    @Value("${file.dir}")
    private String fileDir;

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(ArticleNotFoundException e) {
        log.error("[exceptionHandle] ex", e);
        ErrorResult errorResult = new ErrorResult(400, "ARTICLE-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

//      GET    articles/            find       :query-string ?type={}&title={}?page={}?size={}?sort={}
//      GET    articles/{id}        findById
//      POST   articles/            save
//      PUT    articles/{id}        update     : json과 file을 동시에 받기 위해 @RequestPart 사용
//      DELETE articles/{id}        delete


    @GetMapping
    public ResponseEntity<?> find(@ModelAttribute ArticleFindRequest articleFindRequest,
        @PageableDefault(size = 5, page = 0) Pageable pageable) throws BadRequestException {
        if (!TOUR.equals(articleFindRequest.getType()) && !BOARD.equals(
            articleFindRequest.getType())) {
            throw new BadRequestException();
        }
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
    public ResponseEntity<ArticleSaveResponse> save(
        @ModelAttribute ArticleSaveRequest articleSaveRequest) throws IOException {
        ArticleSaveResponse articleSaveResponse = articleService.save(articleSaveRequest);
        return ResponseEntity.status(HttpStatus.OK).body(articleSaveResponse);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ArticleUpdateResponse> update(
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
