package salema.servicobackup;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "status")
public class statushttp {

	
    @GetMapping(path = "/validar")
	public String listar() {
		return "200 - ok";
	}

}
