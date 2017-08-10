package mx.gob.profeco.atlas.repository.search;

import mx.gob.profeco.atlas.domain.PalabraClaveCat;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PalabraClaveCat entity.
 */
public interface PalabraClaveCatSearchRepository extends ElasticsearchRepository<PalabraClaveCat, Long> {
}
