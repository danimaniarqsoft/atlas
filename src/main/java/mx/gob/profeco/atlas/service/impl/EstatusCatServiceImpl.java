package mx.gob.profeco.atlas.service.impl;

import mx.gob.profeco.atlas.service.EstatusCatService;
import mx.gob.profeco.atlas.domain.EstatusCat;
import mx.gob.profeco.atlas.repository.EstatusCatRepository;
import mx.gob.profeco.atlas.repository.search.EstatusCatSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing EstatusCat.
 */
@Service
@Transactional
public class EstatusCatServiceImpl implements EstatusCatService{

    private final Logger log = LoggerFactory.getLogger(EstatusCatServiceImpl.class);

    private final EstatusCatRepository estatusCatRepository;

    private final EstatusCatSearchRepository estatusCatSearchRepository;

    public EstatusCatServiceImpl(EstatusCatRepository estatusCatRepository, EstatusCatSearchRepository estatusCatSearchRepository) {
        this.estatusCatRepository = estatusCatRepository;
        this.estatusCatSearchRepository = estatusCatSearchRepository;
    }

    /**
     * Save a estatusCat.
     *
     * @param estatusCat the entity to save
     * @return the persisted entity
     */
    @Override
    public EstatusCat save(EstatusCat estatusCat) {
        log.debug("Request to save EstatusCat : {}", estatusCat);
        EstatusCat result = estatusCatRepository.save(estatusCat);
        estatusCatSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the estatusCats.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EstatusCat> findAll(Pageable pageable) {
        log.debug("Request to get all EstatusCats");
        return estatusCatRepository.findAll(pageable);
    }

    /**
     *  Get one estatusCat by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EstatusCat findOne(Long id) {
        log.debug("Request to get EstatusCat : {}", id);
        return estatusCatRepository.findOne(id);
    }

    /**
     *  Delete the  estatusCat by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EstatusCat : {}", id);
        estatusCatRepository.delete(id);
        estatusCatSearchRepository.delete(id);
    }

    /**
     * Search for the estatusCat corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EstatusCat> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EstatusCats for query {}", query);
        Page<EstatusCat> result = estatusCatSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
