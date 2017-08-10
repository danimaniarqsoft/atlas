package mx.gob.profeco.atlas.repository.search;

import mx.gob.profeco.atlas.domain.Norma;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Norma entity.
 */
public interface NormaSearchRepository extends ElasticsearchRepository<Norma, Long> {
}
