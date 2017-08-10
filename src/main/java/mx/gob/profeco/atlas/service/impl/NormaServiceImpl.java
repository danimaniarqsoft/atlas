package mx.gob.profeco.atlas.service.impl;

import mx.gob.profeco.atlas.service.NormaService;
import mx.gob.profeco.atlas.domain.Norma;
import mx.gob.profeco.atlas.repository.NormaRepository;
import mx.gob.profeco.atlas.repository.search.NormaSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Norma.
 */
@Service
@Transactional
public class NormaServiceImpl implements NormaService{

    private final Logger log = LoggerFactory.getLogger(NormaServiceImpl.class);

    private final NormaRepository normaRepository;

    private final NormaSearchRepository normaSearchRepository;

    public NormaServiceImpl(NormaRepository normaRepository, NormaSearchRepository normaSearchRepository) {
        this.normaRepository = normaRepository;
        this.normaSearchRepository = normaSearchRepository;
    }

    /**
     * Save a norma.
     *
     * @param norma the entity to save
     * @return the persisted entity
     */
    @Override
    public Norma save(Norma norma) {
        log.debug("Request to save Norma : {}", norma);
        Norma result = normaRepository.save(norma);
        normaSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the normas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Norma> findAll(Pageable pageable) {
        log.debug("Request to get all Normas");
        return normaRepository.findAll(pageable);
    }

    /**
     *  Get one norma by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Norma findOne(Long id) {
        log.debug("Request to get Norma : {}", id);
        return normaRepository.findOne(id);
    }

    /**
     *  Delete the  norma by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Norma : {}", id);
        normaRepository.delete(id);
        normaSearchRepository.delete(id);
    }

    /**
     * Search for the norma corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Norma> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Normas for query {}", query);
        Page<Norma> result = normaSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
