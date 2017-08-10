package mx.gob.profeco.atlas.service.impl;

import mx.gob.profeco.atlas.service.TemaCatService;
import mx.gob.profeco.atlas.domain.TemaCat;
import mx.gob.profeco.atlas.repository.TemaCatRepository;
import mx.gob.profeco.atlas.repository.search.TemaCatSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TemaCat.
 */
@Service
@Transactional
public class TemaCatServiceImpl implements TemaCatService{

    private final Logger log = LoggerFactory.getLogger(TemaCatServiceImpl.class);

    private final TemaCatRepository temaCatRepository;

    private final TemaCatSearchRepository temaCatSearchRepository;

    public TemaCatServiceImpl(TemaCatRepository temaCatRepository, TemaCatSearchRepository temaCatSearchRepository) {
        this.temaCatRepository = temaCatRepository;
        this.temaCatSearchRepository = temaCatSearchRepository;
    }

    /**
     * Save a temaCat.
     *
     * @param temaCat the entity to save
     * @return the persisted entity
     */
    @Override
    public TemaCat save(TemaCat temaCat) {
        log.debug("Request to save TemaCat : {}", temaCat);
        TemaCat result = temaCatRepository.save(temaCat);
        temaCatSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the temaCats.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TemaCat> findAll(Pageable pageable) {
        log.debug("Request to get all TemaCats");
        return temaCatRepository.findAll(pageable);
    }

    /**
     *  Get one temaCat by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TemaCat findOne(Long id) {
        log.debug("Request to get TemaCat : {}", id);
        return temaCatRepository.findOne(id);
    }

    /**
     *  Delete the  temaCat by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TemaCat : {}", id);
        temaCatRepository.delete(id);
        temaCatSearchRepository.delete(id);
    }

    /**
     * Search for the temaCat corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TemaCat> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TemaCats for query {}", query);
        Page<TemaCat> result = temaCatSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
