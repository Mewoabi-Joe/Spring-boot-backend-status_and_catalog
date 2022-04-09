package reseau.project.status.centralCatalogs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reseau.project.status.businesses.Business;
import reseau.project.status.businesses.BusinessService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/central_catalog")
public class CentralCatalogController {

}
