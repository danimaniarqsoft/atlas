package mx.gob.profeco.atlas.service.impl;

import mx.gob.profeco.atlas.service.NormaTemaService;
import mx.gob.profeco.atlas.domain.NormaTema;
import mx.gob.profeco.atlas.repository.NormaTemaRepository;
import mx.gob.profeco.atlas.repository.search.NormaTemaSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing NormaTema.
 */
@Service
@Transactional
public class NormaTemaServiceImpl implements NormaTemaService{

    private final Logger log = LoggerFactory.getLogger(NormaTemaServiceImpl.class);

    private final NormaTemaRepository normaTemaRepository;

    private final NormaTemaSearchRepository normaTemaSearchRepository;

    public NormaTemaServiceImpl(NormaTemaRepository normaTemaRepository, NormaTemaSearchRepository normaTemaSearchRepository) {
        this.normaTemaRepository = normaTemaRepository;
        this.normaTemaSearchRepository = normaTemaSearchRepository;
    }

    /**
     * Save a normaTema.
     *
     * @param normaTema the entity to save
     * @return the persisted entity
     */
    @Override
    public NormaTema save(NormaTema normaTema) {
        log.debug("Request to save NormaTema : {}", normaTema);
        NormaTema result = normaTemaRepository.save(normaTema);
        normaTemaSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the normaTemas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NormaTema> findAll(Pageable pageable) {
        log.debug("Request to get all NormaTemas");
        return normaTemaRepository.findAll(pageable);
    }

    /**
     *  Get one normaTema by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public NormaTema findOne(Long id) {
        log.debug("Request to get NormaTema : {}", id);
        return normaTemaRepository.findOne(id);
    }

    /**
     *  Delete the  normaTema by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete NormaTema : {}", id);
        normaTemaRepository.delete(id);
        normaTemaSearchRepository.delete(id);
    }

    /**
     * Search for the normaTema corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NormaTema> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of NormaTemas for query {}", query);
        Page<NormaTema> result = normaTemaSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
