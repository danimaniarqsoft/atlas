package mx.gob.profeco.atlas.web.rest;

import com.codahale.metrics.annotation.Timed;
import mx.gob.profeco.atlas.domain.NormaIdioma;
import mx.gob.profeco.atlas.service.NormaIdiomaService;
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
 * REST controller for managing NormaIdioma.
 */
@RestController
@RequestMapping("/api")
public class NormaIdiomaResource {

    private final Logger log = LoggerFactory.getLogger(NormaIdiomaResource.class);

    private static final String ENTITY_NAME = "normaIdioma";

    private final NormaIdiomaService normaIdiomaService;

    public NormaIdiomaResource(NormaIdiomaService normaIdiomaService) {
        this.normaIdiomaService = normaIdiomaService;
    }

    /**
     * POST  /norma-idiomas : Create a new normaIdioma.
     *
     * @param normaIdioma the normaIdioma to create
     * @return the ResponseEntity with status 201 (Created) and with body the new normaIdioma, or with status 400 (Bad Request) if the normaIdioma has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/norma-idiomas")
    @Timed
    public ResponseEntity<NormaIdioma> createNormaIdioma(@RequestBody NormaIdioma normaIdioma) throws URISyntaxException {
        log.debug("REST request to save NormaIdioma : {}", normaIdioma);
        if (normaIdioma.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new normaIdioma cannot already have an ID")).body(null);
        }
        NormaIdioma result = normaIdiomaService.save(normaIdioma);
        return ResponseEntity.created(new URI("/api/norma-idiomas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /norma-idiomas : Updates an existing normaIdioma.
     *
     * @param normaIdioma the normaIdioma to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated normaIdioma,
     * or with status 400 (Bad Request) if the normaIdioma is not valid,
     * or with status 500 (Internal Server Error) if the normaIdioma couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/norma-idiomas")
    @Timed
    public ResponseEntity<NormaIdioma> updateNormaIdioma(@RequestBody NormaIdioma normaIdioma) throws URISyntaxException {
        log.debug("REST request to update NormaIdioma : {}", normaIdioma);
        if (normaIdioma.getId() == null) {
            return createNormaIdioma(normaIdioma);
        }
        NormaIdioma result = normaIdiomaService.save(normaIdioma);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, normaIdioma.getId().toString()))
            .body(result);
    }

    /**
     * GET  /norma-idiomas : get all the normaIdiomas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of normaIdiomas in body
     */
    @GetMapping("/norma-idiomas")
    @Timed
    public ResponseEntity<List<NormaIdioma>> getAllNormaIdiomas(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of NormaIdiomas");
        Page<NormaIdioma> page = normaIdiomaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/norma-idiomas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /norma-idiomas/:id : get the "id" normaIdioma.
     *
     * @param id the id of the normaIdioma to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the normaIdioma, or with status 404 (Not Found)
     */
    @GetMapping("/norma-idiomas/{id}")
    @Timed
    public ResponseEntity<NormaIdioma> getNormaIdioma(@PathVariable Long id) {
        log.debug("REST request to get NormaIdioma : {}", id);
        NormaIdioma normaIdioma = normaIdiomaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(normaIdioma));
    }

    /**
     * DELETE  /norma-idiomas/:id : delete the "id" normaIdioma.
     *
     * @param id the id of the normaIdioma to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/norma-idiomas/{id}")
    @Timed
    public ResponseEntity<Void> deleteNormaIdioma(@PathVariable Long id) {
        log.debug("REST request to delete NormaIdioma : {}", id);
        normaIdiomaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/norma-idiomas?query=:query : search for the normaIdioma corresponding
     * to the query.
     *
     * @param query the query of the normaIdioma search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/norma-idiomas")
    @Timed
    public ResponseEntity<List<NormaIdioma>> searchNormaIdiomas(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of NormaIdiomas for query {}", query);
        Page<NormaIdioma> page = normaIdiomaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/norma-idiomas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
