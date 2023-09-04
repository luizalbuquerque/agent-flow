package agentdataflow.service.impl;

import agentdataflow.entity.AgenteEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface AgenteService {

    List<AgenteEntity> getAllAgentes();
    void processFile(MultipartFile file);

    Object getConsolidatedData();

}