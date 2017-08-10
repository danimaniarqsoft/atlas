package mx.gob.profeco.atlas.web.rest;

import com.codahale.metrics.annotation.Timed;
import mx.gob.profeco.atlas.domain.PalabraClaveCat;
import mx.gob.profeco.atlas.service.PalabraClaveCatService;
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
 * REST controller for managing PalabraClaveCat.
 */
@RestController
@RequestMapping("/api")
public class PalabraClaveCatResource {

    private final Logger log = LoggerFactory.getLogger(PalabraClaveCatResource.class);

    private static final String ENTITY_NAME = "palabraClaveCat";

    private final PalabraClaveCatService palabraClaveCatService;

    public PalabraClaveCatResource(PalabraClaveCatService palabraClaveCatService) {
        this.palabraClaveCatService = palabraClaveCatService;
    }

    /**
     * POST  /palabra-clave-cats : Create a new palabraClaveCat.
     *
     * @param palabraClaveCat the palabraClaveCat to create
     * @return the ResponseEntity with status 201 (Created) and with body the new palabraClaveCat, or with status 400 (Bad Request) if the palabraClaveCat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/palabra-clave-cats")
    @Timed
    public ResponseEntity<PalabraClaveCat> createPalabraClaveCat(@RequestBody PalabraClaveCat palabraClaveCat) throws URISyntaxException {
        log.debug("REST request to save PalabraClaveCat : {}", palabraClaveCat);
        if (palabraClaveCat.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new palabraClaveCat cannot already have an ID")).body(null);
        }
        PalabraClaveCat result = palabraClaveCatService.save(palabraClaveCat);
        return ResponseEntity.created(new URI("/api/palabra-clave-cats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /palabra-clave-cats : Updates an existing palabraClaveCat.
     *
     * @param palabraClaveCat the palabraClaveCat to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated palabraClaveCat,
     * or with status 400 (Bad Request) if the palabraClaveCat is not valid,
     * or with status 500 (Internal Server Error) if the palabraClaveCat couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/palabra-clave-cats")
    @Timed
    public ResponseEntity<PalabraClaveCat> updatePalabraClaveCat(@RequestBody PalabraClaveCat palabraClaveCat) throws URISyntaxException {
        log.debug("REST request to update PalabraClaveCat : {}", palabraClaveCat);
        if (palabraClaveCat.getId() == null) {
            return createPalabraClaveCat(palabraClaveCat);
        }
        PalabraClaveCat result = palabraClaveCatService.save(palabraClaveCat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, palabraClaveCat.getId().toString()))
            .body(result);
    }

    /**
     * GET  /palabra-clave-cats : get all the palabraClaveCats.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of palabraClaveCats in body
     */
    @GetMapping("/palabra-clave-cats")
    @Timed
    public ResponseEntity<List<PalabraClaveCat>> getAllPalabraClaveCats(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of PalabraClaveCats");
        Page<PalabraClaveCat> page = palabraClaveCatService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/palabra-clave-cats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /palabra-clave-cats/:id : get the "id" palabraClaveCat.
     *
     * @param id the id of the palabraClaveCat to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the palabraClaveCat, or with status 404 (Not Found)
     */
    @GetMapping("/palabra-clave-cats/{id}")
    @Timed
    public ResponseEntity<PalabraClaveCat> getPalabraClaveCat(@PathVariable Long id) {
        log.debug("REST request to get PalabraClaveCat : {}", id);
        PalabraClaveCat palabraClaveCat = palabraClaveCatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(palabraClaveCat));
    }

    /**
     * DELETE  /palabra-clave-cats/:id : delete the "id" palabraClaveCat.
     *
     * @param id the id of the palabraClaveCat to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/palabra-clave-cats/{id}")
    @Timed
    public ResponseEntity<Void> deletePalabraClaveCat(@PathVariable Long id) {
        log.debug("REST request to delete PalabraClaveCat : {}", id);
        palabraClaveCatService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/palabra-clave-cats?query=:query : search for the palabraClaveCat corresponding
     * to the query.
     *
     * @param query the query of the palabraClaveCat search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/palabra-clave-cats")
    @Timed
    public ResponseEntity<List<PalabraClaveCat>> searchPalabraClaveCats(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of PalabraClaveCats for query {}", query);
        Page<PalabraClaveCat> page = palabraClaveCatService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/palabra-clave-cats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
