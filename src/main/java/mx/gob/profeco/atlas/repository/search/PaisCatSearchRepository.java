package mx.gob.profeco.atlas.repository.search;

import mx.gob.profeco.atlas.domain.PaisCat;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PaisCat entity.
 */
public interface PaisCatSearchRepository extends ElasticsearchRepository<PaisCat, Long> {
}
