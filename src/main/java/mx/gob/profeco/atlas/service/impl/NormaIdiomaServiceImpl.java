package mx.gob.profeco.atlas.service.impl;

import mx.gob.profeco.atlas.service.NormaIdiomaService;
import mx.gob.profeco.atlas.domain.NormaIdioma;
import mx.gob.profeco.atlas.repository.NormaIdiomaRepository;
import mx.gob.profeco.atlas.repository.search.NormaIdiomaSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing NormaIdioma.
 */
@Service
@Transactional
public class NormaIdiomaServiceImpl implements NormaIdiomaService{

    private final Logger log = LoggerFactory.getLogger(NormaIdiomaServiceImpl.class);

    private final NormaIdiomaRepository normaIdiomaRepository;

    private final NormaIdiomaSearchRepository normaIdiomaSearchRepository;

    public NormaIdiomaServiceImpl(NormaIdiomaRepository normaIdiomaRepository, NormaIdiomaSearchRepository normaIdiomaSearchRepository) {
        this.normaIdiomaRepository = normaIdiomaRepository;
        this.normaIdiomaSearchRepository = normaIdiomaSearchRepository;
    }

    /**
     * Save a normaIdioma.
     *
     * @param normaIdioma the entity to save
     * @return the persisted entity
     */
    @Override
    public NormaIdioma save(NormaIdioma normaIdioma) {
        log.debug("Request to save NormaIdioma : {}", normaIdioma);
        NormaIdioma result = normaIdiomaRepository.save(normaIdioma);
        normaIdiomaSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the normaIdiomas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NormaIdioma> findAll(Pageable pageable) {
        log.debug("Request to get all NormaIdiomas");
        return normaIdiomaRepository.findAll(pageable);
    }

    /**
     *  Get one normaIdioma by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public NormaIdioma findOne(Long id) {
        log.debug("Request to get NormaIdioma : {}", id);
        return normaIdiomaRepository.findOne(id);
    }

    /**
     *  Delete the  normaIdioma by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete NormaIdioma : {}", id);
        normaIdiomaRepository.delete(id);
        normaIdiomaSearchRepository.delete(id);
    }

    /**
     * Search for the normaIdioma corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NormaIdioma> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of NormaIdiomas for query {}", query);
        Page<NormaIdioma> result = normaIdiomaSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
