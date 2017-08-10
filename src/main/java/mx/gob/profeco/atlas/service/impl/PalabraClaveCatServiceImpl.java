package mx.gob.profeco.atlas.service.impl;

import mx.gob.profeco.atlas.service.PalabraClaveCatService;
import mx.gob.profeco.atlas.domain.PalabraClaveCat;
import mx.gob.profeco.atlas.repository.PalabraClaveCatRepository;
import mx.gob.profeco.atlas.repository.search.PalabraClaveCatSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PalabraClaveCat.
 */
@Service
@Transactional
public class PalabraClaveCatServiceImpl implements PalabraClaveCatService{

    private final Logger log = LoggerFactory.getLogger(PalabraClaveCatServiceImpl.class);

    private final PalabraClaveCatRepository palabraClaveCatRepository;

    private final PalabraClaveCatSearchRepository palabraClaveCatSearchRepository;

    public PalabraClaveCatServiceImpl(PalabraClaveCatRepository palabraClaveCatRepository, PalabraClaveCatSearchRepository palabraClaveCatSearchRepository) {
        this.palabraClaveCatRepository = palabraClaveCatRepository;
        this.palabraClaveCatSearchRepository = palabraClaveCatSearchRepository;
    }

    /**
     * Save a palabraClaveCat.
     *
     * @param palabraClaveCat the entity to save
     * @return the persisted entity
     */
    @Override
    public PalabraClaveCat save(PalabraClaveCat palabraClaveCat) {
        log.debug("Request to save PalabraClaveCat : {}", palabraClaveCat);
        PalabraClaveCat result = palabraClaveCatRepository.save(palabraClaveCat);
        palabraClaveCatSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the palabraClaveCats.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PalabraClaveCat> findAll(Pageable pageable) {
        log.debug("Request to get all PalabraClaveCats");
        return palabraClaveCatRepository.findAll(pageable);
    }

    /**
     *  Get one palabraClaveCat by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PalabraClaveCat findOne(Long id) {
        log.debug("Request to get PalabraClaveCat : {}", id);
        return palabraClaveCatRepository.findOne(id);
    }

    /**
     *  Delete the  palabraClaveCat by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PalabraClaveCat : {}", id);
        palabraClaveCatRepository.delete(id);
        palabraClaveCatSearchRepository.delete(id);
    }

    /**
     * Search for the palabraClaveCat corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PalabraClaveCat> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PalabraClaveCats for query {}", query);
        Page<PalabraClaveCat> result = palabraClaveCatSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
