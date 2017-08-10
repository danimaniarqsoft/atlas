package mx.gob.profeco.atlas.repository.search;

import mx.gob.profeco.atlas.domain.NormaTema;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the NormaTema entity.
 */
public interface NormaTemaSearchRepository extends ElasticsearchRepository<NormaTema, Long> {
}
