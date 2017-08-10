package mx.gob.profeco.atlas.service.impl;

import mx.gob.profeco.atlas.service.SubtemaCatService;
import mx.gob.profeco.atlas.domain.SubtemaCat;
import mx.gob.profeco.atlas.repository.SubtemaCatRepository;
import mx.gob.profeco.atlas.repository.search.SubtemaCatSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SubtemaCat.
 */
@Service
@Transactional
public class SubtemaCatServiceImpl implements SubtemaCatService{

    private final Logger log = LoggerFactory.getLogger(SubtemaCatServiceImpl.class);

    private final SubtemaCatRepository subtemaCatRepository;

    private final SubtemaCatSearchRepository subtemaCatSearchRepository;

    public SubtemaCatServiceImpl(SubtemaCatRepository subtemaCatRepository, SubtemaCatSearchRepository subtemaCatSearchRepository) {
        this.subtemaCatRepository = subtemaCatRepository;
        this.subtemaCatSearchRepository = subtemaCatSearchRepository;
    }

    /**
     * Save a subtemaCat.
     *
     * @param subtemaCat the entity to save
     * @return the persisted entity
     */
    @Override
    public SubtemaCat save(SubtemaCat subtemaCat) {
        log.debug("Request to save SubtemaCat : {}", subtemaCat);
        SubtemaCat result = subtemaCatRepository.save(subtemaCat);
        subtemaCatSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the subtemaCats.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SubtemaCat> findAll(Pageable pageable) {
        log.debug("Request to get all SubtemaCats");
        return subtemaCatRepository.findAll(pageable);
    }

    /**
     *  Get one subtemaCat by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SubtemaCat findOne(Long id) {
        log.debug("Request to get SubtemaCat : {}", id);
        return subtemaCatRepository.findOne(id);
    }

    /**
     *  Delete the  subtemaCat by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SubtemaCat : {}", id);
        subtemaCatRepository.delete(id);
        subtemaCatSearchRepository.delete(id);
    }

    /**
     * Search for the subtemaCat corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SubtemaCat> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SubtemaCats for query {}", query);
        Page<SubtemaCat> result = subtemaCatSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
