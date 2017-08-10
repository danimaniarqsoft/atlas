package mx.gob.profeco.atlas.web.rest;

import com.codahale.metrics.annotation.Timed;
import mx.gob.profeco.atlas.domain.NormaPalabraClave;
import mx.gob.profeco.atlas.service.NormaPalabraClaveService;
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
 * REST controller for managing NormaPalabraClave.
 */
@RestController
@RequestMapping("/api")
public class NormaPalabraClaveResource {

    private final Logger log = LoggerFactory.getLogger(NormaPalabraClaveResource.class);

    private static final String ENTITY_NAME = "normaPalabraClave";

    private final NormaPalabraClaveService normaPalabraClaveService;

    public NormaPalabraClaveResource(NormaPalabraClaveService normaPalabraClaveService) {
        this.normaPalabraClaveService = normaPalabraClaveService;
    }

    /**
     * POST  /norma-palabra-claves : Create a new normaPalabraClave.
     *
     * @param normaPalabraClave the normaPalabraClave to create
     * @return the ResponseEntity with status 201 (Created) and with body the new normaPalabraClave, or with status 400 (Bad Request) if the normaPalabraClave has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/norma-palabra-claves")
    @Timed
    public ResponseEntity<NormaPalabraClave> createNormaPalabraClave(@RequestBody NormaPalabraClave normaPalabraClave) throws URISyntaxException {
        log.debug("REST request to save NormaPalabraClave : {}", normaPalabraClave);
        if (normaPalabraClave.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new normaPalabraClave cannot already have an ID")).body(null);
        }
        NormaPalabraClave result = normaPalabraClaveService.save(normaPalabraClave);
        return ResponseEntity.created(new URI("/api/norma-palabra-claves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /norma-palabra-claves : Updates an existing normaPalabraClave.
     *
     * @param normaPalabraClave the normaPalabraClave to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated normaPalabraClave,
     * or with status 400 (Bad Request) if the normaPalabraClave is not valid,
     * or with status 500 (Internal Server Error) if the normaPalabraClave couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/norma-palabra-claves")
    @Timed
    public ResponseEntity<NormaPalabraClave> updateNormaPalabraClave(@RequestBody NormaPalabraClave normaPalabraClave) throws URISyntaxException {
        log.debug("REST request to update NormaPalabraClave : {}", normaPalabraClave);
        if (normaPalabraClave.getId() == null) {
            return createNormaPalabraClave(normaPalabraClave);
        }
        NormaPalabraClave result = normaPalabraClaveService.save(normaPalabraClave);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, normaPalabraClave.getId().toString()))
            .body(result);
    }

    /**
     * GET  /norma-palabra-claves : get all the normaPalabraClaves.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of normaPalabraClaves in body
     */
    @GetMapping("/norma-palabra-claves")
    @Timed
    public ResponseEntity<List<NormaPalabraClave>> getAllNormaPalabraClaves(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of NormaPalabraClaves");
        Page<NormaPalabraClave> page = normaPalabraClaveService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/norma-palabra-claves");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /norma-palabra-claves/:id : get the "id" normaPalabraClave.
     *
     * @param id the id of the normaPalabraClave to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the normaPalabraClave, or with status 404 (Not Found)
     */
    @GetMapping("/norma-palabra-claves/{id}")
    @Timed
    public ResponseEntity<NormaPalabraClave> getNormaPalabraClave(@PathVariable Long id) {
        log.debug("REST request to get NormaPalabraClave : {}", id);
        NormaPalabraClave normaPalabraClave = normaPalabraClaveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(normaPalabraClave));
    }

    /**
     * DELETE  /norma-palabra-claves/:id : delete the "id" normaPalabraClave.
     *
     * @param id the id of the normaPalabraClave to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/norma-palabra-claves/{id}")
    @Timed
    public ResponseEntity<Void> deleteNormaPalabraClave(@PathVariable Long id) {
        log.debug("REST request to delete NormaPalabraClave : {}", id);
        normaPalabraClaveService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/norma-palabra-claves?query=:query : search for the normaPalabraClave corresponding
     * to the query.
     *
     * @param query the query of the normaPalabraClave search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/norma-palabra-claves")
    @Timed
    public ResponseEntity<List<NormaPalabraClave>> searchNormaPalabraClaves(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of NormaPalabraClaves for query {}", query);
        Page<NormaPalabraClave> page = normaPalabraClaveService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/norma-palabra-claves");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
