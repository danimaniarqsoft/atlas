package mx.gob.profeco.atlas.repository.search;

import mx.gob.profeco.atlas.domain.EstatusCat;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the EstatusCat entity.
 */
public interface EstatusCatSearchRepository extends ElasticsearchRepository<EstatusCat, Long> {
}
