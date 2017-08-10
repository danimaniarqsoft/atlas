package mx.gob.profeco.atlas.repository.search;

import mx.gob.profeco.atlas.domain.IdiomaCat;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the IdiomaCat entity.
 */
public interface IdiomaCatSearchRepository extends ElasticsearchRepository<IdiomaCat, Long> {
}
