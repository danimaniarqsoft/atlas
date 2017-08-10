package mx.gob.profeco.atlas.web.rest;

import com.codahale.metrics.annotation.Timed;
import mx.gob.profeco.atlas.domain.TipoNormaCat;
import mx.gob.profeco.atlas.service.TipoNormaCatService;
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
 * REST controller for managing TipoNormaCat.
 */
@RestController
@RequestMapping("/api")
public class TipoNormaCatResource {

    private final Logger log = LoggerFactory.getLogger(TipoNormaCatResource.class);

    private static final String ENTITY_NAME = "tipoNormaCat";

    private final TipoNormaCatService tipoNormaCatService;

    public TipoNormaCatResource(TipoNormaCatService tipoNormaCatService) {
        this.tipoNormaCatService = tipoNormaCatService;
    }

    /**
     * POST  /tipo-norma-cats : Create a new tipoNormaCat.
     *
     * @param tipoNormaCat the tipoNormaCat to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoNormaCat, or with status 400 (Bad Request) if the tipoNormaCat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-norma-cats")
    @Timed
    public ResponseEntity<TipoNormaCat> createTipoNormaCat(@RequestBody TipoNormaCat tipoNormaCat) throws URISyntaxException {
        log.debug("REST request to save TipoNormaCat : {}", tipoNormaCat);
        if (tipoNormaCat.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tipoNormaCat cannot already have an ID")).body(null);
        }
        TipoNormaCat result = tipoNormaCatService.save(tipoNormaCat);
        return ResponseEntity.created(new URI("/api/tipo-norma-cats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-norma-cats : Updates an existing tipoNormaCat.
     *
     * @param tipoNormaCat the tipoNormaCat to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoNormaCat,
     * or with status 400 (Bad Request) if the tipoNormaCat is not valid,
     * or with status 500 (Internal Server Error) if the tipoNormaCat couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-norma-cats")
    @Timed
    public ResponseEntity<TipoNormaCat> updateTipoNormaCat(@RequestBody TipoNormaCat tipoNormaCat) throws URISyntaxException {
        log.debug("REST request to update TipoNormaCat : {}", tipoNormaCat);
        if (tipoNormaCat.getId() == null) {
            return createTipoNormaCat(tipoNormaCat);
        }
        TipoNormaCat result = tipoNormaCatService.save(tipoNormaCat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoNormaCat.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-norma-cats : get all the tipoNormaCats.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tipoNormaCats in body
     */
    @GetMapping("/tipo-norma-cats")
    @Timed
    public ResponseEntity<List<TipoNormaCat>> getAllTipoNormaCats(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of TipoNormaCats");
        Page<TipoNormaCat> page = tipoNormaCatService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tipo-norma-cats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tipo-norma-cats/:id : get the "id" tipoNormaCat.
     *
     * @param id the id of the tipoNormaCat to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoNormaCat, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-norma-cats/{id}")
    @Timed
    public ResponseEntity<TipoNormaCat> getTipoNormaCat(@PathVariable Long id) {
        log.debug("REST request to get TipoNormaCat : {}", id);
        TipoNormaCat tipoNormaCat = tipoNormaCatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tipoNormaCat));
    }

    /**
     * DELETE  /tipo-norma-cats/:id : delete the "id" tipoNormaCat.
     *
     * @param id the id of the tipoNormaCat to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-norma-cats/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoNormaCat(@PathVariable Long id) {
        log.debug("REST request to delete TipoNormaCat : {}", id);
        tipoNormaCatService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/tipo-norma-cats?query=:query : search for the tipoNormaCat corresponding
     * to the query.
     *
     * @param query the query of the tipoNormaCat search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/tipo-norma-cats")
    @Timed
    public ResponseEntity<List<TipoNormaCat>> searchTipoNormaCats(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of TipoNormaCats for query {}", query);
        Page<TipoNormaCat> page = tipoNormaCatService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tipo-norma-cats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
