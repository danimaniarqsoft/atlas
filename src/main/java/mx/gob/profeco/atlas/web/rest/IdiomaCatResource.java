package mx.gob.profeco.atlas.web.rest;

import com.codahale.metrics.annotation.Timed;
import mx.gob.profeco.atlas.domain.IdiomaCat;
import mx.gob.profeco.atlas.service.IdiomaCatService;
import mx.gob.profeco.atlas.web.rest.util.HeaderUtil;
import mx.gob.profeco.atlas.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing IdiomaCat.
 */
@RestController
@RequestMapping("/api")
public class IdiomaCatResource {

    private final Logger log = LoggerFactory.getLogger(IdiomaCatResource.class);

    private static final String ENTITY_NAME = "idiomaCat";

    private final IdiomaCatService idiomaCatService;

    public IdiomaCatResource(IdiomaCatService idiomaCatService) {
        this.idiomaCatService = idiomaCatService;
    }

    /**
     * POST  /idioma-cats : Create a new idiomaCat.
     *
     * @param idiomaCat the idiomaCat to create
     * @return the ResponseEntity with status 201 (Created) and with body the new idiomaCat, or with status 400 (Bad Request) if the idiomaCat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/idioma-cats")
    @Timed
    public ResponseEntity<IdiomaCat> createIdiomaCat(@RequestBody IdiomaCat idiomaCat) throws URISyntaxException {
        log.debug("REST request to save IdiomaCat : {}", idiomaCat);
        if (idiomaCat.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new idiomaCat cannot already have an ID")).body(null);
        }
        IdiomaCat result = idiomaCatService.save(idiomaCat);
        return ResponseEntity.created(new URI("/api/idioma-cats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /idioma-cats : Updates an existing idiomaCat.
     *
     * @param idiomaCat the idiomaCat to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated idiomaCat,
     * or with status 400 (Bad Request) if the idiomaCat is not valid,
     * or with status 500 (Internal Server Error) if the idiomaCat couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/idioma-cats")
    @Timed
    public ResponseEntity<IdiomaCat> updateIdiomaCat(@RequestBody IdiomaCat idiomaCat) throws URISyntaxException {
        log.debug("REST request to update IdiomaCat : {}", idiomaCat);
        if (idiomaCat.getId() == null) {
            return createIdiomaCat(idiomaCat);
        }
        IdiomaCat result = idiomaCatService.save(idiomaCat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, idiomaCat.getId().toString()))
            .body(result);
    }

    /**
     * GET  /idioma-cats : get all the idiomaCats.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of idiomaCats in body
     */
    @GetMapping("/idioma-cats")
    @Timed
    public ResponseEntity<List<IdiomaCat>> getAllIdiomaCats(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of IdiomaCats");
        Page<IdiomaCat> page = idiomaCatService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/idioma-cats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /idioma-cats/:id : get the "id" idiomaCat.
     *
     * @param id the id of the idiomaCat to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the idiomaCat, or with status 404 (Not Found)
     */
    @GetMapping("/idioma-cats/{id}")
    @Timed
    public ResponseEntity<IdiomaCat> getIdiomaCat(@PathVariable Long id) {
        log.debug("REST request to get IdiomaCat : {}", id);
        IdiomaCat idiomaCat = idiomaCatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(idiomaCat));
    }

    /**
     * DELETE  /idioma-cats/:id : delete the "id" idiomaCat.
     *
     * @param id the id of the idiomaCat to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/idioma-cats/{id}")
    @Timed
    public ResponseEntity<Void> deleteIdiomaCat(@PathVariable Long id) {
        log.debug("REST request to delete IdiomaCat : {}", id);
        idiomaCatService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/idioma-cats?query=:query : search for the idiomaCat corresponding
     * to the query.
     *
     * @param query the query of the idiomaCat search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/idioma-cats")
    @Timed
    public ResponseEntity<List<IdiomaCat>> searchIdiomaCats(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of IdiomaCats for query {}", query);
        Page<IdiomaCat> page = idiomaCatService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/idioma-cats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
