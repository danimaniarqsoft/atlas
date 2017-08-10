package mx.gob.profeco.atlas.service.impl;

import mx.gob.profeco.atlas.service.IdiomaCatService;
import mx.gob.profeco.atlas.domain.IdiomaCat;
import mx.gob.profeco.atlas.repository.IdiomaCatRepository;
import mx.gob.profeco.atlas.repository.search.IdiomaCatSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing IdiomaCat.
 */
@Service
@Transactional
public class IdiomaCatServiceImpl implements IdiomaCatService{

    private final Logger log = LoggerFactory.getLogger(IdiomaCatServiceImpl.class);

    private final IdiomaCatRepository idiomaCatRepository;

    private final IdiomaCatSearchRepository idiomaCatSearchRepository;

    public IdiomaCatServiceImpl(IdiomaCatRepository idiomaCatRepository, IdiomaCatSearchRepository idiomaCatSearchRepository) {
        this.idiomaCatRepository = idiomaCatRepository;
        this.idiomaCatSearchRepository = idiomaCatSearchRepository;
    }

    /**
     * Save a idiomaCat.
     *
     * @param idiomaCat the entity to save
     * @return the persisted entity
     */
    @Override
    public IdiomaCat save(IdiomaCat idiomaCat) {
        log.debug("Request to save IdiomaCat : {}", idiomaCat);
        IdiomaCat result = idiomaCatRepository.save(idiomaCat);
        idiomaCatSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the idiomaCats.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IdiomaCat> findAll(Pageable pageable) {
        log.debug("Request to get all IdiomaCats");
        return idiomaCatRepository.findAll(pageable);
    }

    /**
     *  Get one idiomaCat by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public IdiomaCat findOne(Long id) {
        log.debug("Request to get IdiomaCat : {}", id);
        return idiomaCatRepository.findOne(id);
    }

    /**
     *  Delete the  idiomaCat by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete IdiomaCat : {}", id);
        idiomaCatRepository.delete(id);
        idiomaCatSearchRepository.delete(id);
    }

    /**
     * Search for the idiomaCat corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IdiomaCat> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of IdiomaCats for query {}", query);
        Page<IdiomaCat> result = idiomaCatSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
