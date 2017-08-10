package mx.gob.profeco.atlas.service.impl;

import mx.gob.profeco.atlas.service.NormaPalabraClaveService;
import mx.gob.profeco.atlas.domain.NormaPalabraClave;
import mx.gob.profeco.atlas.repository.NormaPalabraClaveRepository;
import mx.gob.profeco.atlas.repository.search.NormaPalabraClaveSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing NormaPalabraClave.
 */
@Service
@Transactional
public class NormaPalabraClaveServiceImpl implements NormaPalabraClaveService{

    private final Logger log = LoggerFactory.getLogger(NormaPalabraClaveServiceImpl.class);

    private final NormaPalabraClaveRepository normaPalabraClaveRepository;

    private final NormaPalabraClaveSearchRepository normaPalabraClaveSearchRepository;

    public NormaPalabraClaveServiceImpl(NormaPalabraClaveRepository normaPalabraClaveRepository, NormaPalabraClaveSearchRepository normaPalabraClaveSearchRepository) {
        this.normaPalabraClaveRepository = normaPalabraClaveRepository;
        this.normaPalabraClaveSearchRepository = normaPalabraClaveSearchRepository;
    }

    /**
     * Save a normaPalabraClave.
     *
     * @param normaPalabraClave the entity to save
     * @return the persisted entity
     */
    @Override
    public NormaPalabraClave save(NormaPalabraClave normaPalabraClave) {
        log.debug("Request to save NormaPalabraClave : {}", normaPalabraClave);
        NormaPalabraClave result = normaPalabraClaveRepository.save(normaPalabraClave);
        normaPalabraClaveSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the normaPalabraClaves.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NormaPalabraClave> findAll(Pageable pageable) {
        log.debug("Request to get all NormaPalabraClaves");
        return normaPalabraClaveRepository.findAll(pageable);
    }

    /**
     *  Get one normaPalabraClave by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public NormaPalabraClave findOne(Long id) {
        log.debug("Request to get NormaPalabraClave : {}", id);
        return normaPalabraClaveRepository.findOne(id);
    }

    /**
     *  Delete the  normaPalabraClave by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete NormaPalabraClave : {}", id);
        normaPalabraClaveRepository.delete(id);
        normaPalabraClaveSearchRepository.delete(id);
    }

    /**
     * Search for the normaPalabraClave corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NormaPalabraClave> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of NormaPalabraClaves for query {}", query);
        Page<NormaPalabraClave> result = normaPalabraClaveSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
