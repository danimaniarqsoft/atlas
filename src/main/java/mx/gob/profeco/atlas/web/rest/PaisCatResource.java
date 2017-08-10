package mx.gob.profeco.atlas.web.rest;

import com.codahale.metrics.annotation.Timed;
import mx.gob.profeco.atlas.domain.PaisCat;
import mx.gob.profeco.atlas.service.PaisCatService;
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
 * REST controller for managing PaisCat.
 */
@RestController
@RequestMapping("/api")
public class PaisCatResource {

    private final Logger log = LoggerFactory.getLogger(PaisCatResource.class);

    private static final String ENTITY_NAME = "paisCat";

    private final PaisCatService paisCatService;

    public PaisCatResource(PaisCatService paisCatService) {
        this.paisCatService = paisCatService;
    }

    /**
     * POST  /pais-cats : Create a new paisCat.
     *
     * @param paisCat the paisCat to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paisCat, or with status 400 (Bad Request) if the paisCat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pais-cats")
    @Timed
    public ResponseEntity<PaisCat> createPaisCat(@RequestBody PaisCat paisCat) throws URISyntaxException {
        log.debug("REST request to save PaisCat : {}", paisCat);
        if (paisCat.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new paisCat cannot already have an ID")).body(null);
        }
        PaisCat result = paisCatService.save(paisCat);
        return ResponseEntity.created(new URI("/api/pais-cats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pais-cats : Updates an existing paisCat.
     *
     * @param paisCat the paisCat to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paisCat,
     * or with status 400 (Bad Request) if the paisCat is not valid,
     * or with status 500 (Internal Server Error) if the paisCat couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pais-cats")
    @Timed
    public ResponseEntity<PaisCat> updatePaisCat(@RequestBody PaisCat paisCat) throws URISyntaxException {
        log.debug("REST request to update PaisCat : {}", paisCat);
        if (paisCat.getId() == null) {
            return createPaisCat(paisCat);
        }
        PaisCat result = paisCatService.save(paisCat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paisCat.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pais-cats : get all the paisCats.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of paisCats in body
     */
    @GetMapping("/pais-cats")
    @Timed
    public ResponseEntity<List<PaisCat>> getAllPaisCats(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of PaisCats");
        Page<PaisCat> page = paisCatService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pais-cats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pais-cats/:id : get the "id" paisCat.
     *
     * @param id the id of the paisCat to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paisCat, or with status 404 (Not Found)
     */
    @GetMapping("/pais-cats/{id}")
    @Timed
    public ResponseEntity<PaisCat> getPaisCat(@PathVariable Long id) {
        log.debug("REST request to get PaisCat : {}", id);
        PaisCat paisCat = paisCatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(paisCat));
    }

    /**
     * DELETE  /pais-cats/:id : delete the "id" paisCat.
     *
     * @param id the id of the paisCat to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pais-cats/{id}")
    @Timed
    public ResponseEntity<Void> deletePaisCat(@PathVariable Long id) {
        log.debug("REST request to delete PaisCat : {}", id);
        paisCatService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/pais-cats?query=:query : search for the paisCat corresponding
     * to the query.
     *
     * @param query the query of the paisCat search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/pais-cats")
    @Timed
    public ResponseEntity<List<PaisCat>> searchPaisCats(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of PaisCats for query {}", query);
        Page<PaisCat> page = paisCatService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/pais-cats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
