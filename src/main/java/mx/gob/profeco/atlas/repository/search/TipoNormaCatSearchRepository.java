package mx.gob.profeco.atlas.repository.search;

import mx.gob.profeco.atlas.domain.TipoNormaCat;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TipoNormaCat entity.
 */
public interface TipoNormaCatSearchRepository extends ElasticsearchRepository<TipoNormaCat, Long> {
}
