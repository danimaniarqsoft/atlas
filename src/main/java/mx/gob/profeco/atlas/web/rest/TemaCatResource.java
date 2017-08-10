package mx.gob.profeco.atlas.web.rest;

import com.codahale.metrics.annotation.Timed;
import mx.gob.profeco.atlas.domain.TemaCat;
import mx.gob.profeco.atlas.service.TemaCatService;
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
 * REST controller for managing TemaCat.
 */
@RestController
@RequestMapping("/api")
public class TemaCatResource {

    private final Logger log = LoggerFactory.getLogger(TemaCatResource.class);

    private static final String ENTITY_NAME = "temaCat";

    private final TemaCatService temaCatService;

    public TemaCatResource(TemaCatService temaCatService) {
        this.temaCatService = temaCatService;
    }

    /**
     * POST  /tema-cats : Create a new temaCat.
     *
     * @param temaCat the temaCat to create
     * @return the ResponseEntity with status 201 (Created) and with body the new temaCat, or with status 400 (Bad Request) if the temaCat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tema-cats")
    @Timed
    public ResponseEntity<TemaCat> createTemaCat(@RequestBody TemaCat temaCat) throws URISyntaxException {
        log.debug("REST request to save TemaCat : {}", temaCat);
        if (temaCat.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new temaCat cannot already have an ID")).body(null);
        }
        TemaCat result = temaCatService.save(temaCat);
        return ResponseEntity.created(new URI("/api/tema-cats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tema-cats : Updates an existing temaCat.
     *
     * @param temaCat the temaCat to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated temaCat,
     * or with status 400 (Bad Request) if the temaCat is not valid,
     * or with status 500 (Internal Server Error) if the temaCat couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tema-cats")
    @Timed
    public ResponseEntity<TemaCat> updateTemaCat(@RequestBody TemaCat temaCat) throws URISyntaxException {
        log.debug("REST request to update TemaCat : {}", temaCat);
        if (temaCat.getId() == null) {
            return createTemaCat(temaCat);
        }
        TemaCat result = temaCatService.save(temaCat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, temaCat.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tema-cats : get all the temaCats.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of temaCats in body
     */
    @GetMapping("/tema-cats")
    @Timed
    public ResponseEntity<List<TemaCat>> getAllTemaCats(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of TemaCats");
        Page<TemaCat> page = temaCatService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tema-cats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tema-cats/:id : get the "id" temaCat.
     *
     * @param id the id of the temaCat to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the temaCat, or with status 404 (Not Found)
     */
    @GetMapping("/tema-cats/{id}")
    @Timed
    public ResponseEntity<TemaCat> getTemaCat(@PathVariable Long id) {
        log.debug("REST request to get TemaCat : {}", id);
        TemaCat temaCat = temaCatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(temaCat));
    }

    /**
     * DELETE  /tema-cats/:id : delete the "id" temaCat.
     *
     * @param id the id of the temaCat to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tema-cats/{id}")
    @Timed
    public ResponseEntity<Void> deleteTemaCat(@PathVariable Long id) {
        log.debug("REST request to delete TemaCat : {}", id);
        temaCatService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/tema-cats?query=:query : search for the temaCat corresponding
     * to the query.
     *
     * @param query the query of the temaCat search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/tema-cats")
    @Timed
    public ResponseEntity<List<TemaCat>> searchTemaCats(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of TemaCats for query {}", query);
        Page<TemaCat> page = temaCatService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tema-cats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
