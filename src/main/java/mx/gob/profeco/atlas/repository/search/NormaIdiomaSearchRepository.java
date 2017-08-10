package mx.gob.profeco.atlas.repository.search;

import mx.gob.profeco.atlas.domain.NormaIdioma;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the NormaIdioma entity.
 */
public interface NormaIdiomaSearchRepository extends ElasticsearchRepository<NormaIdioma, Long> {
}
