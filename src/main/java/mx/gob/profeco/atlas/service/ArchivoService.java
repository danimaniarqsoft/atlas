package mx.gob.profeco.atlas.service;

import mx.gob.profeco.atlas.domain.Archivo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Archivo.
 */
public interface ArchivoService {

    /**
     * Save a archivo.
     *
     * @param archivo the entity to save
     * @return the persisted entity
     */
    Archivo save(Archivo archivo);

    /**
     *  Get all the archivos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Archivo> findAll(Pageable pageable);

    /**
     *  Get the "id" archivo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Archivo findOne(Long id);

    /**
     *  Delete the "id" archivo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the archivo corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Archivo> search(String query, Pageable pageable);
}
