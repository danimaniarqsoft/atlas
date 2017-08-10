package mx.gob.profeco.atlas.repository.search;

import mx.gob.profeco.atlas.domain.TemaCat;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TemaCat entity.
 */
public interface TemaCatSearchRepository extends ElasticsearchRepository<TemaCat, Long> {
}
