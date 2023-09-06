package agentdataflow.service;

import agentdataflow.entity.AgenteEntity;
import agentdataflow.entity.MedicaoEntity;
import agentdataflow.entity.RegiaoEntity;
import agentdataflow.repositoty.AgenteRepository;
import agentdataflow.service.impl.AgenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.*;

@Service
public class AgenteServiceImpl implements AgenteService {

    @Autowired
    private AgenteRepository agenteRepository;

    @Override
    public List<AgenteEntity> getAllAgentes() {
        return agenteRepository.findAll();
    }

    @Override
    public void processFile(MultipartFile file) {
        try {
            InputStream is = file.getInputStream();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);
            doc.getDocumentElement().normalize();

            NodeList agentNodeList = doc.getElementsByTagName("agente");

            for (int agentIndex = 0; agentIndex < agentNodeList.getLength(); agentIndex++) {
                Element agentNode = (Element) agentNodeList.item(agentIndex);
                String codigo = agentNode.getElementsByTagName("codigo").item(0).getTextContent();
                String dataString = agentNode.getElementsByTagName("data").item(0).getTextContent();

                System.out.println("Agente recebido com código: " + codigo);

                AgenteEntity agente = new AgenteEntity();
                agente.setCodigo(Integer.valueOf(codigo));
                agente.setData(OffsetDateTime.parse(dataString));

                List<RegiaoEntity> regioes = new ArrayList<>();
                NodeList regiaoNodeList = agentNode.getElementsByTagName("regiao");

                for (int regiaoIndex = 0; regiaoIndex < regiaoNodeList.getLength(); regiaoIndex++) {
                    Element regiaoNode = (Element) regiaoNodeList.item(regiaoIndex);
                    String sigla = regiaoNode.getAttribute("sigla");

                    List<Double> geracaoList = extractListFromNode(regiaoNode, "geracao");
                    List<Double> compraList = extractListFromNode(regiaoNode, "compra");

                    RegiaoEntity regiao = new RegiaoEntity();
                    regiao.setSigla(sigla);
                    // Associar a RegiaoEntity ao AgenteEntity correspondente
                    regiao.setAgente(agente);

                    List<MedicaoEntity> geracaoMedicoes = createMedicaoEntities(geracaoList, regiao);
                    List<MedicaoEntity> compraMedicoes = createMedicaoEntities(compraList, regiao);

                    regiao.setGeracao(geracaoMedicoes);
                    regiao.setCompra(compraMedicoes);
                    regiao.setPrecoMedio(Collections.emptyList());

                    regioes.add(regiao);
                }

                agente.setRegioes(regioes);
                agenteRepository.save(agente);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public Map<String, Map<String, Double>> getConsolidatedData() {
        Map<String, Map<String, Double>> consolidatedData = new HashMap<>();
        List<AgenteEntity> allAgentes = agenteRepository.findAll();

        for (AgenteEntity agente : allAgentes) {
            for (RegiaoEntity regiao : agente.getRegioes()) {
                String regiaoSigla = regiao.getSigla();

                if (!consolidatedData.containsKey(regiaoSigla)) {
                    consolidatedData.put(regiaoSigla, new HashMap<>());
                }

                Map<String, Double> dataByRegion = consolidatedData.get(regiaoSigla);

                double geracaoMedia = calculateAverageMedicoes(regiao.getGeracao());
                double compraMedia = calculateAverageMedicoes(regiao.getCompra());

                dataByRegion.put("geracaoMedia", geracaoMedia);
                dataByRegion.put("compraMedia", compraMedia);

                consolidatedData.put(regiaoSigla, dataByRegion);
            }
        }

        return consolidatedData;
    }

    private double calculateAverageMedicoes(List<MedicaoEntity> medicoes) {
        if (medicoes.isEmpty()) {
            return 0.0; // Retorna 0 se a lista de medicoes estiver vazia para evitar divisão por zero
        }

        double sum = 0.0;
        for (MedicaoEntity medicao : medicoes) {
            sum += medicao.getValor();
        }
        return sum / medicoes.size();
    }




    private List<Double> extractListFromNode(Element parentNode, String tagName) {
        NodeList nodeList = parentNode.getElementsByTagName(tagName);
        List<Double> values = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            String[] lines = nodeList.item(i).getTextContent().split("\n");
            for (String line : lines) {
                line = line.trim();
                if (!line.isEmpty()) {
                    values.add(Double.parseDouble(line));
                }
            }
        }

        return values;
    }


    private List<MedicaoEntity> createMedicaoEntities(List<Double> valores, RegiaoEntity regiao) {
        List<MedicaoEntity> medicoes = new ArrayList<>();
        for (Double valor : valores) {
            MedicaoEntity medicao = new MedicaoEntity();
            medicao.setValor(valor);
            medicao.setRegiao(regiao);
            medicoes.add(medicao);
        }
        return medicoes;
    }
}
