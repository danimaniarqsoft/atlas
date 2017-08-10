package mx.gob.profeco.atlas.web.rest;

import com.codahale.metrics.annotation.Timed;
import mx.gob.profeco.atlas.domain.SubtemaCat;
import mx.gob.profeco.atlas.service.SubtemaCatService;
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
 * REST controller for managing SubtemaCat.
 */
@RestController
@RequestMapping("/api")
public class SubtemaCatResource {

    private final Logger log = LoggerFactory.getLogger(SubtemaCatResource.class);

    private static final String ENTITY_NAME = "subtemaCat";

    private final SubtemaCatService subtemaCatService;

    public SubtemaCatResource(SubtemaCatService subtemaCatService) {
        this.subtemaCatService = subtemaCatService;
    }

    /**
     * POST  /subtema-cats : Create a new subtemaCat.
     *
     * @param subtemaCat the subtemaCat to create
     * @return the ResponseEntity with status 201 (Created) and with body the new subtemaCat, or with status 400 (Bad Request) if the subtemaCat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/subtema-cats")
    @Timed
    public ResponseEntity<SubtemaCat> createSubtemaCat(@RequestBody SubtemaCat subtemaCat) throws URISyntaxException {
        log.debug("REST request to save SubtemaCat : {}", subtemaCat);
        if (subtemaCat.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new subtemaCat cannot already have an ID")).body(null);
        }
        SubtemaCat result = subtemaCatService.save(subtemaCat);
        return ResponseEntity.created(new URI("/api/subtema-cats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /subtema-cats : Updates an existing subtemaCat.
     *
     * @param subtemaCat the subtemaCat to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated subtemaCat,
     * or with status 400 (Bad Request) if the subtemaCat is not valid,
     * or with status 500 (Internal Server Error) if the subtemaCat couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/subtema-cats")
    @Timed
    public ResponseEntity<SubtemaCat> updateSubtemaCat(@RequestBody SubtemaCat subtemaCat) throws URISyntaxException {
        log.debug("REST request to update SubtemaCat : {}", subtemaCat);
        if (subtemaCat.getId() == null) {
            return createSubtemaCat(subtemaCat);
        }
        SubtemaCat result = subtemaCatService.save(subtemaCat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, subtemaCat.getId().toString()))
            .body(result);
    }

    /**
     * GET  /subtema-cats : get all the subtemaCats.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of subtemaCats in body
     */
    @GetMapping("/subtema-cats")
    @Timed
    public ResponseEntity<List<SubtemaCat>> getAllSubtemaCats(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of SubtemaCats");
        Page<SubtemaCat> page = subtemaCatService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/subtema-cats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /subtema-cats/:id : get the "id" subtemaCat.
     *
     * @param id the id of the subtemaCat to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subtemaCat, or with status 404 (Not Found)
     */
    @GetMapping("/subtema-cats/{id}")
    @Timed
    public ResponseEntity<SubtemaCat> getSubtemaCat(@PathVariable Long id) {
        log.debug("REST request to get SubtemaCat : {}", id);
        SubtemaCat subtemaCat = subtemaCatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(subtemaCat));
    }

    /**
     * DELETE  /subtema-cats/:id : delete the "id" subtemaCat.
     *
     * @param id the id of the subtemaCat to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/subtema-cats/{id}")
    @Timed
    public ResponseEntity<Void> deleteSubtemaCat(@PathVariable Long id) {
        log.debug("REST request to delete SubtemaCat : {}", id);
        subtemaCatService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/subtema-cats?query=:query : search for the subtemaCat corresponding
     * to the query.
     *
     * @param query the query of the subtemaCat search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/subtema-cats")
    @Timed
    public ResponseEntity<List<SubtemaCat>> searchSubtemaCats(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of SubtemaCats for query {}", query);
        Page<SubtemaCat> page = subtemaCatService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/subtema-cats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
