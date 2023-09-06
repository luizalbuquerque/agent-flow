package agentdataflow.controller;

import agentdataflow.entity.AgenteEntity;
import agentdataflow.service.impl.AgenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UploadController{
    @Autowired
    private AgenteService agenteService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            agenteService.processFile(file);
            return ResponseEntity.ok().body("Arquivo processado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Falha ao processar arquivo: " + e.getMessage());
        }
    }

    @GetMapping("/consolidated")
    public ResponseEntity<Object> getConsolidatedDataByRegion() {
        try {
            Object data = agenteService.getConsolidatedData();
            return ResponseEntity.ok().body(data);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Falha ao recuperar dados: " + e.getMessage());
        }
    }
}
