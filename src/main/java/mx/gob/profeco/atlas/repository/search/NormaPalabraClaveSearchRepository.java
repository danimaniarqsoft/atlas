package mx.gob.profeco.atlas.repository.search;

import mx.gob.profeco.atlas.domain.NormaPalabraClave;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the NormaPalabraClave entity.
 */
public interface NormaPalabraClaveSearchRepository extends ElasticsearchRepository<NormaPalabraClave, Long> {
}
