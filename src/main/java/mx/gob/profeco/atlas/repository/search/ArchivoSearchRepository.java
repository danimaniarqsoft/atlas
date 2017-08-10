package mx.gob.profeco.atlas.repository.search;

import mx.gob.profeco.atlas.domain.Archivo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Archivo entity.
 */
public interface ArchivoSearchRepository extends ElasticsearchRepository<Archivo, Long> {
}
