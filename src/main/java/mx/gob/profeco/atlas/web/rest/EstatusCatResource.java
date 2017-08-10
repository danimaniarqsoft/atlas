package mx.gob.profeco.atlas.web.rest;

import com.codahale.metrics.annotation.Timed;
import mx.gob.profeco.atlas.domain.EstatusCat;
import mx.gob.profeco.atlas.service.EstatusCatService;
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
 * REST controller for managing EstatusCat.
 */
@RestController
@RequestMapping("/api")
public class EstatusCatResource {

    private final Logger log = LoggerFactory.getLogger(EstatusCatResource.class);

    private static final String ENTITY_NAME = "estatusCat";

    private final EstatusCatService estatusCatService;

    public EstatusCatResource(EstatusCatService estatusCatService) {
        this.estatusCatService = estatusCatService;
    }

    /**
     * POST  /estatus-cats : Create a new estatusCat.
     *
     * @param estatusCat the estatusCat to create
     * @return the ResponseEntity with status 201 (Created) and with body the new estatusCat, or with status 400 (Bad Request) if the estatusCat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/estatus-cats")
    @Timed
    public ResponseEntity<EstatusCat> createEstatusCat(@RequestBody EstatusCat estatusCat) throws URISyntaxException {
        log.debug("REST request to save EstatusCat : {}", estatusCat);
        if (estatusCat.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new estatusCat cannot already have an ID")).body(null);
        }
        EstatusCat result = estatusCatService.save(estatusCat);
        return ResponseEntity.created(new URI("/api/estatus-cats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /estatus-cats : Updates an existing estatusCat.
     *
     * @param estatusCat the estatusCat to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated estatusCat,
     * or with status 400 (Bad Request) if the estatusCat is not valid,
     * or with status 500 (Internal Server Error) if the estatusCat couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/estatus-cats")
    @Timed
    public ResponseEntity<EstatusCat> updateEstatusCat(@RequestBody EstatusCat estatusCat) throws URISyntaxException {
        log.debug("REST request to update EstatusCat : {}", estatusCat);
        if (estatusCat.getId() == null) {
            return createEstatusCat(estatusCat);
        }
        EstatusCat result = estatusCatService.save(estatusCat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, estatusCat.getId().toString()))
            .body(result);
    }

    /**
     * GET  /estatus-cats : get all the estatusCats.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of estatusCats in body
     */
    @GetMapping("/estatus-cats")
    @Timed
    public ResponseEntity<List<EstatusCat>> getAllEstatusCats(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of EstatusCats");
        Page<EstatusCat> page = estatusCatService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/estatus-cats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /estatus-cats/:id : get the "id" estatusCat.
     *
     * @param id the id of the estatusCat to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the estatusCat, or with status 404 (Not Found)
     */
    @GetMapping("/estatus-cats/{id}")
    @Timed
    public ResponseEntity<EstatusCat> getEstatusCat(@PathVariable Long id) {
        log.debug("REST request to get EstatusCat : {}", id);
        EstatusCat estatusCat = estatusCatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(estatusCat));
    }

    /**
     * DELETE  /estatus-cats/:id : delete the "id" estatusCat.
     *
     * @param id the id of the estatusCat to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/estatus-cats/{id}")
    @Timed
    public ResponseEntity<Void> deleteEstatusCat(@PathVariable Long id) {
        log.debug("REST request to delete EstatusCat : {}", id);
        estatusCatService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/estatus-cats?query=:query : search for the estatusCat corresponding
     * to the query.
     *
     * @param query the query of the estatusCat search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/estatus-cats")
    @Timed
    public ResponseEntity<List<EstatusCat>> searchEstatusCats(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of EstatusCats for query {}", query);
        Page<EstatusCat> page = estatusCatService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/estatus-cats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
