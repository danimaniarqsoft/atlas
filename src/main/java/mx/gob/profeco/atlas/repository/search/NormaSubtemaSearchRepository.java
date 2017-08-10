package mx.gob.profeco.atlas.repository.search;

import mx.gob.profeco.atlas.domain.NormaSubtema;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the NormaSubtema entity.
 */
public interface NormaSubtemaSearchRepository extends ElasticsearchRepository<NormaSubtema, Long> {
}
