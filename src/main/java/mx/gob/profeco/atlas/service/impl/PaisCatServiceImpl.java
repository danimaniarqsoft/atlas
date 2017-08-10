package mx.gob.profeco.atlas.service.impl;

import mx.gob.profeco.atlas.service.PaisCatService;
import mx.gob.profeco.atlas.domain.PaisCat;
import mx.gob.profeco.atlas.repository.PaisCatRepository;
import mx.gob.profeco.atlas.repository.search.PaisCatSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PaisCat.
 */
@Service
@Transactional
public class PaisCatServiceImpl implements PaisCatService{

    private final Logger log = LoggerFactory.getLogger(PaisCatServiceImpl.class);

    private final PaisCatRepository paisCatRepository;

    private final PaisCatSearchRepository paisCatSearchRepository;

    public PaisCatServiceImpl(PaisCatRepository paisCatRepository, PaisCatSearchRepository paisCatSearchRepository) {
        this.paisCatRepository = paisCatRepository;
        this.paisCatSearchRepository = paisCatSearchRepository;
    }

    /**
     * Save a paisCat.
     *
     * @param paisCat the entity to save
     * @return the persisted entity
     */
    @Override
    public PaisCat save(PaisCat paisCat) {
        log.debug("Request to save PaisCat : {}", paisCat);
        PaisCat result = paisCatRepository.save(paisCat);
        paisCatSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the paisCats.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PaisCat> findAll(Pageable pageable) {
        log.debug("Request to get all PaisCats");
        return paisCatRepository.findAll(pageable);
    }

    /**
     *  Get one paisCat by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PaisCat findOne(Long id) {
        log.debug("Request to get PaisCat : {}", id);
        return paisCatRepository.findOne(id);
    }

    /**
     *  Delete the  paisCat by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaisCat : {}", id);
        paisCatRepository.delete(id);
        paisCatSearchRepository.delete(id);
    }

    /**
     * Search for the paisCat corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PaisCat> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PaisCats for query {}", query);
        Page<PaisCat> result = paisCatSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
