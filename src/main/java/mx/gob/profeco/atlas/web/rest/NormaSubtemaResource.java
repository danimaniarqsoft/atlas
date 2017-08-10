package mx.gob.profeco.atlas.web.rest;

import com.codahale.metrics.annotation.Timed;
import mx.gob.profeco.atlas.domain.NormaSubtema;
import mx.gob.profeco.atlas.service.NormaSubtemaService;
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
 * REST controller for managing NormaSubtema.
 */
@RestController
@RequestMapping("/api")
public class NormaSubtemaResource {

    private final Logger log = LoggerFactory.getLogger(NormaSubtemaResource.class);

    private static final String ENTITY_NAME = "normaSubtema";

    private final NormaSubtemaService normaSubtemaService;

    public NormaSubtemaResource(NormaSubtemaService normaSubtemaService) {
        this.normaSubtemaService = normaSubtemaService;
    }

    /**
     * POST  /norma-subtemas : Create a new normaSubtema.
     *
     * @param normaSubtema the normaSubtema to create
     * @return the ResponseEntity with status 201 (Created) and with body the new normaSubtema, or with status 400 (Bad Request) if the normaSubtema has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/norma-subtemas")
    @Timed
    public ResponseEntity<NormaSubtema> createNormaSubtema(@RequestBody NormaSubtema normaSubtema) throws URISyntaxException {
        log.debug("REST request to save NormaSubtema : {}", normaSubtema);
        if (normaSubtema.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new normaSubtema cannot already have an ID")).body(null);
        }
        NormaSubtema result = normaSubtemaService.save(normaSubtema);
        return ResponseEntity.created(new URI("/api/norma-subtemas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /norma-subtemas : Updates an existing normaSubtema.
     *
     * @param normaSubtema the normaSubtema to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated normaSubtema,
     * or with status 400 (Bad Request) if the normaSubtema is not valid,
     * or with status 500 (Internal Server Error) if the normaSubtema couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/norma-subtemas")
    @Timed
    public ResponseEntity<NormaSubtema> updateNormaSubtema(@RequestBody NormaSubtema normaSubtema) throws URISyntaxException {
        log.debug("REST request to update NormaSubtema : {}", normaSubtema);
        if (normaSubtema.getId() == null) {
            return createNormaSubtema(normaSubtema);
        }
        NormaSubtema result = normaSubtemaService.save(normaSubtema);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, normaSubtema.getId().toString()))
            .body(result);
    }

    /**
     * GET  /norma-subtemas : get all the normaSubtemas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of normaSubtemas in body
     */
    @GetMapping("/norma-subtemas")
    @Timed
    public ResponseEntity<List<NormaSubtema>> getAllNormaSubtemas(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of NormaSubtemas");
        Page<NormaSubtema> page = normaSubtemaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/norma-subtemas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /norma-subtemas/:id : get the "id" normaSubtema.
     *
     * @param id the id of the normaSubtema to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the normaSubtema, or with status 404 (Not Found)
     */
    @GetMapping("/norma-subtemas/{id}")
    @Timed
    public ResponseEntity<NormaSubtema> getNormaSubtema(@PathVariable Long id) {
        log.debug("REST request to get NormaSubtema : {}", id);
        NormaSubtema normaSubtema = normaSubtemaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(normaSubtema));
    }

    /**
     * DELETE  /norma-subtemas/:id : delete the "id" normaSubtema.
     *
     * @param id the id of the normaSubtema to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/norma-subtemas/{id}")
    @Timed
    public ResponseEntity<Void> deleteNormaSubtema(@PathVariable Long id) {
        log.debug("REST request to delete NormaSubtema : {}", id);
        normaSubtemaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/norma-subtemas?query=:query : search for the normaSubtema corresponding
     * to the query.
     *
     * @param query the query of the normaSubtema search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/norma-subtemas")
    @Timed
    public ResponseEntity<List<NormaSubtema>> searchNormaSubtemas(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of NormaSubtemas for query {}", query);
        Page<NormaSubtema> page = normaSubtemaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/norma-subtemas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
