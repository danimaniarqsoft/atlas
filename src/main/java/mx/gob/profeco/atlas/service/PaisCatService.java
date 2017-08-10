package mx.gob.profeco.atlas.service;

import mx.gob.profeco.atlas.domain.PaisCat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PaisCat.
 */
public interface PaisCatService {

    /**
     * Save a paisCat.
     *
     * @param paisCat the entity to save
     * @return the persisted entity
     */
    PaisCat save(PaisCat paisCat);

    /**
     *  Get all the paisCats.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PaisCat> findAll(Pageable pageable);

    /**
     *  Get the "id" paisCat.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PaisCat findOne(Long id);

    /**
     *  Delete the "id" paisCat.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the paisCat corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PaisCat> search(String query, Pageable pageable);
}
