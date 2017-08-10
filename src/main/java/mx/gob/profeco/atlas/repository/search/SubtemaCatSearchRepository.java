package mx.gob.profeco.atlas.repository.search;

import mx.gob.profeco.atlas.domain.SubtemaCat;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SubtemaCat entity.
 */
public interface SubtemaCatSearchRepository extends ElasticsearchRepository<SubtemaCat, Long> {
}
