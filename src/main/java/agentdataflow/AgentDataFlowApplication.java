package agentdataflow;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@OpenAPIDefinition(info = @Info(title = "Agent Data Flow", version = "1.0.0"))
@SpringBootApplication
public class AgentDataFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgentDataFlowApplication.class, args);
	}

}
