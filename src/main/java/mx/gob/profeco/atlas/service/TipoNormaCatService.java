package mx.gob.profeco.atlas.service;

import mx.gob.profeco.atlas.domain.TipoNormaCat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TipoNormaCat.
 */
public interface TipoNormaCatService {

    /**
     * Save a tipoNormaCat.
     *
     * @param tipoNormaCat the entity to save
     * @return the persisted entity
     */
    TipoNormaCat save(TipoNormaCat tipoNormaCat);

    /**
     *  Get all the tipoNormaCats.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TipoNormaCat> findAll(Pageable pageable);

    /**
     *  Get the "id" tipoNormaCat.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TipoNormaCat findOne(Long id);

    /**
     *  Delete the "id" tipoNormaCat.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tipoNormaCat corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TipoNormaCat> search(String query, Pageable pageable);
}
