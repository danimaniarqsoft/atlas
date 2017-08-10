package mx.gob.profeco.atlas.service;

import mx.gob.profeco.atlas.domain.NormaPalabraClave;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing NormaPalabraClave.
 */
public interface NormaPalabraClaveService {

    /**
     * Save a normaPalabraClave.
     *
     * @param normaPalabraClave the entity to save
     * @return the persisted entity
     */
    NormaPalabraClave save(NormaPalabraClave normaPalabraClave);

    /**
     *  Get all the normaPalabraClaves.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<NormaPalabraClave> findAll(Pageable pageable);

    /**
     *  Get the "id" normaPalabraClave.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    NormaPalabraClave findOne(Long id);

    /**
     *  Delete the "id" normaPalabraClave.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the normaPalabraClave corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<NormaPalabraClave> search(String query, Pageable pageable);
}
