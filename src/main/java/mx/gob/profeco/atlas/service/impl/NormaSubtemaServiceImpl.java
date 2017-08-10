package mx.gob.profeco.atlas.service.impl;

import mx.gob.profeco.atlas.service.NormaSubtemaService;
import mx.gob.profeco.atlas.domain.NormaSubtema;
import mx.gob.profeco.atlas.repository.NormaSubtemaRepository;
import mx.gob.profeco.atlas.repository.search.NormaSubtemaSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing NormaSubtema.
 */
@Service
@Transactional
public class NormaSubtemaServiceImpl implements NormaSubtemaService{

    private final Logger log = LoggerFactory.getLogger(NormaSubtemaServiceImpl.class);

    private final NormaSubtemaRepository normaSubtemaRepository;

    private final NormaSubtemaSearchRepository normaSubtemaSearchRepository;

    public NormaSubtemaServiceImpl(NormaSubtemaRepository normaSubtemaRepository, NormaSubtemaSearchRepository normaSubtemaSearchRepository) {
        this.normaSubtemaRepository = normaSubtemaRepository;
        this.normaSubtemaSearchRepository = normaSubtemaSearchRepository;
    }

    /**
     * Save a normaSubtema.
     *
     * @param normaSubtema the entity to save
     * @return the persisted entity
     */
    @Override
    public NormaSubtema save(NormaSubtema normaSubtema) {
        log.debug("Request to save NormaSubtema : {}", normaSubtema);
        NormaSubtema result = normaSubtemaRepository.save(normaSubtema);
        normaSubtemaSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the normaSubtemas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NormaSubtema> findAll(Pageable pageable) {
        log.debug("Request to get all NormaSubtemas");
        return normaSubtemaRepository.findAll(pageable);
    }

    /**
     *  Get one normaSubtema by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public NormaSubtema findOne(Long id) {
        log.debug("Request to get NormaSubtema : {}", id);
        return normaSubtemaRepository.findOne(id);
    }

    /**
     *  Delete the  normaSubtema by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete NormaSubtema : {}", id);
        normaSubtemaRepository.delete(id);
        normaSubtemaSearchRepository.delete(id);
    }

    /**
     * Search for the normaSubtema corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NormaSubtema> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of NormaSubtemas for query {}", query);
        Page<NormaSubtema> result = normaSubtemaSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
