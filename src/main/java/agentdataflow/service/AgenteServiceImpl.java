package agentdataflow.service;

import agentdataflow.entity.AgenteEntity;
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
import java.util.*;

@Service
public class AgenteServiceImpl implements AgenteService {

    @Autowired
    private AgenteRepository agenteRepository;


    @Override
    public List<AgenteEntity> getAllAgentes() {
        return agenteRepository.findAll();
    }

        public void processFile(MultipartFile file) {
            try {
                InputStream is = file.getInputStream();
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(is);
                doc.getDocumentElement().normalize();

                NodeList nList = doc.getElementsByTagName("agente");

                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Element nNode = (Element) nList.item(temp);
                    String codigo = nNode.getElementsByTagName("codigo").item(0).getTextContent();
                    String data = nNode.getElementsByTagName("data").item(0).getTextContent();

                    // Imprimir código de agente na saída padrão
                    System.out.println("Agente recebido com código: " + codigo);

                    AgenteEntity agente = new AgenteEntity();
                    agente.setCodigo(codigo);
                    agente.setData(data);

                    List<RegiaoEntity> regioes = new ArrayList<>();
                    NodeList regiaoList = nNode.getElementsByTagName("submercado"); // Ajuste conforme sua estrutura XML

                    for (int j = 0; j < regiaoList.getLength(); j++) {
                        Element regiaoNode = (Element) regiaoList.item(j);
                        String sigla = regiaoNode.getAttribute("sigla");

                        // Suponhamos que você pode extrair múltiplos valores de geracao, compra, e precoMedio
                        List<Double> geracaoList = extractListFromNode(regiaoNode, "geracao");
                        List<Double> compraList = extractListFromNode(regiaoNode, "compra");

                        RegiaoEntity regiao = new RegiaoEntity();
                        regiao.setSigla(sigla);
                        regiao.setGeracao(geracaoList);
                        regiao.setCompra(compraList);
                        regiao.setPrecoMedio(null); // Manter a confidencialidade

                        regioes.add(regiao);
                    }

                    agente.setRegioes(regioes);
                    agenteRepository.save(agente); // Isso irá salvar a entidade no banco de dados
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private List<Double> extractListFromNode(Element parentNode, String tagName) {
            NodeList nodeList = parentNode.getElementsByTagName(tagName);
            List<Double> values = new ArrayList<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                values.add(Double.parseDouble(nodeList.item(i).getTextContent()));
            }
            return values;
        }

    @Override
    public Object getConsolidatedData() {
        // Uma forma de fazer isso é criar um mapa para armazenar os dados consolidados
        Map<String, Map<String, Double>> consolidatedData = new HashMap<>();

        // Recupere todos os agentes e suas respectivas regiões do banco de dados
        List<AgenteEntity> allAgentes = agenteRepository.findAll();

        for (AgenteEntity agente : allAgentes) {
            for (RegiaoEntity regiao : agente.getRegioes()) {
                if (!consolidatedData.containsKey(regiao.getSigla())) {
                    consolidatedData.put(regiao.getSigla(), new HashMap<>());
                }

                Map<String, Double> dataByRegion = consolidatedData.get(regiao.getSigla());

                // Supondo que você queira a média para os campos geracao, compra e precoMedio
                dataByRegion.put("geracaoMedia", regiao.getGeracao().stream().mapToDouble(Double::doubleValue).average().orElse(0));
                dataByRegion.put("compraMedia", regiao.getCompra().stream().mapToDouble(Double::doubleValue).average().orElse(0));
                dataByRegion.put("precoMedioMedia", regiao.getPrecoMedio().stream().mapToDouble(Double::doubleValue).average().orElse(0));

                consolidatedData.put(regiao.getSigla(), dataByRegion);
            }
        }

        return consolidatedData;
    }
}
