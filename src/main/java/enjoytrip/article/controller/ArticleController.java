package enjoytrip.article.controller;

import enjoytrip.article.domain.ArticleType;
import enjoytrip.article.dto.request.ArticleSaveRequest;
import enjoytrip.article.dto.request.ArticleUpdateRequest;
import enjoytrip.article.dto.response.ArticleFindResponse;
import enjoytrip.article.dto.response.ArticleSaveResponse;
import enjoytrip.article.dto.response.ArticleUpdateResponse;
import enjoytrip.article.service.ArticleService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true", allowedHeaders = "*", methods = {
    RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT,
    RequestMethod.HEAD, RequestMethod.OPTIONS})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<ArticleSaveResponse> save(@RequestBody ArticleSaveRequest request)
        throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(articleService.save(request));
    }

    @GetMapping
    public ResponseEntity<Page<ArticleFindResponse>> findByPage(
        @RequestParam("articleType") ArticleType articleType, @RequestParam("title") String title,
        @PageableDefault(size = 5, page = 0) Pageable pageable) {
        return ResponseEntity.ok(articleService.findByPage(articleType, title, pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ArticleFindResponse> findById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.findById(id));
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<ArticleUpdateResponse> update(@RequestBody ArticleUpdateRequest request)
        throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.update(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) throws IOException {
        articleService.delete(id);
        //204(No Content) : 작업이 수행되었으며 별도로 내용을 반환할게 없을 때.
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/views")
    public ResponseEntity<Long> updateViews(@PathVariable("id") Long id) {
        Long views = articleService.updateViews(id);
        return ResponseEntity.status(HttpStatus.OK).body(views);
    }
}
