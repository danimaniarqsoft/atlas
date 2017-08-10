package mx.gob.profeco.atlas.service.impl;

import mx.gob.profeco.atlas.service.TipoNormaCatService;
import mx.gob.profeco.atlas.domain.TipoNormaCat;
import mx.gob.profeco.atlas.repository.TipoNormaCatRepository;
import mx.gob.profeco.atlas.repository.search.TipoNormaCatSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TipoNormaCat.
 */
@Service
@Transactional
public class TipoNormaCatServiceImpl implements TipoNormaCatService{

    private final Logger log = LoggerFactory.getLogger(TipoNormaCatServiceImpl.class);

    private final TipoNormaCatRepository tipoNormaCatRepository;

    private final TipoNormaCatSearchRepository tipoNormaCatSearchRepository;

    public TipoNormaCatServiceImpl(TipoNormaCatRepository tipoNormaCatRepository, TipoNormaCatSearchRepository tipoNormaCatSearchRepository) {
        this.tipoNormaCatRepository = tipoNormaCatRepository;
        this.tipoNormaCatSearchRepository = tipoNormaCatSearchRepository;
    }

    /**
     * Save a tipoNormaCat.
     *
     * @param tipoNormaCat the entity to save
     * @return the persisted entity
     */
    @Override
    public TipoNormaCat save(TipoNormaCat tipoNormaCat) {
        log.debug("Request to save TipoNormaCat : {}", tipoNormaCat);
        TipoNormaCat result = tipoNormaCatRepository.save(tipoNormaCat);
        tipoNormaCatSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tipoNormaCats.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoNormaCat> findAll(Pageable pageable) {
        log.debug("Request to get all TipoNormaCats");
        return tipoNormaCatRepository.findAll(pageable);
    }

    /**
     *  Get one tipoNormaCat by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TipoNormaCat findOne(Long id) {
        log.debug("Request to get TipoNormaCat : {}", id);
        return tipoNormaCatRepository.findOne(id);
    }

    /**
     *  Delete the  tipoNormaCat by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoNormaCat : {}", id);
        tipoNormaCatRepository.delete(id);
        tipoNormaCatSearchRepository.delete(id);
    }

    /**
     * Search for the tipoNormaCat corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoNormaCat> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoNormaCats for query {}", query);
        Page<TipoNormaCat> result = tipoNormaCatSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
