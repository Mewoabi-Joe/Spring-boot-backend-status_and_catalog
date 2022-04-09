package reseau.project.status.centralBusinesses;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reseau.project.status.businesses.Business;
import reseau.project.status.businesses.BusinessInterface;
import reseau.project.status.businesses.BusinessRepository;
import reseau.project.status.exceptions.BadRequestException;
import reseau.project.status.users.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class CentralBusinessService implements CentralBusinessInterface {

}
