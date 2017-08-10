package mx.gob.profeco.atlas.service.impl;

import mx.gob.profeco.atlas.service.ArchivoService;
import mx.gob.profeco.atlas.domain.Archivo;
import mx.gob.profeco.atlas.repository.ArchivoRepository;
import mx.gob.profeco.atlas.repository.search.ArchivoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Archivo.
 */
@Service
@Transactional
public class ArchivoServiceImpl implements ArchivoService{

    private final Logger log = LoggerFactory.getLogger(ArchivoServiceImpl.class);

    private final ArchivoRepository archivoRepository;

    private final ArchivoSearchRepository archivoSearchRepository;

    public ArchivoServiceImpl(ArchivoRepository archivoRepository, ArchivoSearchRepository archivoSearchRepository) {
        this.archivoRepository = archivoRepository;
        this.archivoSearchRepository = archivoSearchRepository;
    }

    /**
     * Save a archivo.
     *
     * @param archivo the entity to save
     * @return the persisted entity
     */
    @Override
    public Archivo save(Archivo archivo) {
        log.debug("Request to save Archivo : {}", archivo);
        Archivo result = archivoRepository.save(archivo);
        archivoSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the archivos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Archivo> findAll(Pageable pageable) {
        log.debug("Request to get all Archivos");
        return archivoRepository.findAll(pageable);
    }

    /**
     *  Get one archivo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Archivo findOne(Long id) {
        log.debug("Request to get Archivo : {}", id);
        return archivoRepository.findOne(id);
    }

    /**
     *  Delete the  archivo by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Archivo : {}", id);
        archivoRepository.delete(id);
        archivoSearchRepository.delete(id);
    }

    /**
     * Search for the archivo corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Archivo> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Archivos for query {}", query);
        Page<Archivo> result = archivoSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
