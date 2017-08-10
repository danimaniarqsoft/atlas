package mx.gob.profeco.atlas.web.rest;

import com.codahale.metrics.annotation.Timed;
import mx.gob.profeco.atlas.domain.NormaTema;
import mx.gob.profeco.atlas.service.NormaTemaService;
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
 * REST controller for managing NormaTema.
 */
@RestController
@RequestMapping("/api")
public class NormaTemaResource {

    private final Logger log = LoggerFactory.getLogger(NormaTemaResource.class);

    private static final String ENTITY_NAME = "normaTema";

    private final NormaTemaService normaTemaService;

    public NormaTemaResource(NormaTemaService normaTemaService) {
        this.normaTemaService = normaTemaService;
    }

    /**
     * POST  /norma-temas : Create a new normaTema.
     *
     * @param normaTema the normaTema to create
     * @return the ResponseEntity with status 201 (Created) and with body the new normaTema, or with status 400 (Bad Request) if the normaTema has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/norma-temas")
    @Timed
    public ResponseEntity<NormaTema> createNormaTema(@RequestBody NormaTema normaTema) throws URISyntaxException {
        log.debug("REST request to save NormaTema : {}", normaTema);
        if (normaTema.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new normaTema cannot already have an ID")).body(null);
        }
        NormaTema result = normaTemaService.save(normaTema);
        return ResponseEntity.created(new URI("/api/norma-temas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /norma-temas : Updates an existing normaTema.
     *
     * @param normaTema the normaTema to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated normaTema,
     * or with status 400 (Bad Request) if the normaTema is not valid,
     * or with status 500 (Internal Server Error) if the normaTema couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/norma-temas")
    @Timed
    public ResponseEntity<NormaTema> updateNormaTema(@RequestBody NormaTema normaTema) throws URISyntaxException {
        log.debug("REST request to update NormaTema : {}", normaTema);
        if (normaTema.getId() == null) {
            return createNormaTema(normaTema);
        }
        NormaTema result = normaTemaService.save(normaTema);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, normaTema.getId().toString()))
            .body(result);
    }

    /**
     * GET  /norma-temas : get all the normaTemas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of normaTemas in body
     */
    @GetMapping("/norma-temas")
    @Timed
    public ResponseEntity<List<NormaTema>> getAllNormaTemas(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of NormaTemas");
        Page<NormaTema> page = normaTemaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/norma-temas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /norma-temas/:id : get the "id" normaTema.
     *
     * @param id the id of the normaTema to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the normaTema, or with status 404 (Not Found)
     */
    @GetMapping("/norma-temas/{id}")
    @Timed
    public ResponseEntity<NormaTema> getNormaTema(@PathVariable Long id) {
        log.debug("REST request to get NormaTema : {}", id);
        NormaTema normaTema = normaTemaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(normaTema));
    }

    /**
     * DELETE  /norma-temas/:id : delete the "id" normaTema.
     *
     * @param id the id of the normaTema to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/norma-temas/{id}")
    @Timed
    public ResponseEntity<Void> deleteNormaTema(@PathVariable Long id) {
        log.debug("REST request to delete NormaTema : {}", id);
        normaTemaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/norma-temas?query=:query : search for the normaTema corresponding
     * to the query.
     *
     * @param query the query of the normaTema search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/norma-temas")
    @Timed
    public ResponseEntity<List<NormaTema>> searchNormaTemas(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of NormaTemas for query {}", query);
        Page<NormaTema> page = normaTemaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/norma-temas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
