package reseau.project.status.businesses;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import reseau.project.status.exceptions.BadRequestException;
import reseau.project.status.exceptions.NotFoundException;
import reseau.project.status.users.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class BusinessService implements BusinessInterface {

    @Autowired
    private BusinessRepository businessRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<Business> getAllUsersAndTheirBusinesses() {
        return businessRepository.findAll();
    }

    @Override
    public Business addOrUpdateUserOneBusiness(Business business) {

        if(business.getBusinessId() == null){
            UUID businessId = Uuids.timeBased();
            business.setBusinessId(businessId);
            int userNumber = business.getUserNumber();
            if (!(userRepository.findById(userNumber).isPresent())) throw new BadRequestException("The user with number: " + userNumber + " does not have an account on our platform.");
            return businessRepository.save(business);
        }else{
            if (!(userRepository.findById(business.getUserNumber()).isPresent())) throw new BadRequestException("The user with number: " + business.getUserNumber() + " does not have an account on our platform.");
            Optional<Business> optionalBusiness = businessRepository.findByUserNumberAndBusinessId(business.getUserNumber(), business.getBusinessId());
            if(!optionalBusiness.isPresent()) new NotFoundException("That user does not have a business with that id");
            return businessRepository.save(business);
        }

    }

    @Override
    public List<Business> getAllBusinessesOfAUser(int userNumber) {
        return businessRepository.findAllByUserNumber(userNumber);
    }

    @Override
    public Business getBusinessByUserNumberAndBusinessId(int userNumber, String businessId) {
        validatePhoneNumber(userNumber);
        Optional<Business> optionalBusiness = businessRepository.findByUserNumberAndBusinessId(userNumber, UUID.fromString(businessId));
        if (optionalBusiness.isPresent()) return optionalBusiness.get();
        throw  new NotFoundException("That user does not have a business with that id");
    }

    public void validatePhoneNumber(int userNumber){
        if(userNumber < 600000000 || userNumber > 700000000) throw new BadRequestException("The userNumber is not a valid phone number");
        if(!userRepository.findById(userNumber).isPresent()) throw new NotFoundException("The user with that Number is not registered to this platform");
    }

    public Business validateRowExistence(int userNumber, String businessId){
        Optional<Business> optionalBusiness = businessRepository.findByUserNumberAndBusinessId(userNumber, UUID.fromString(businessId));
        if(!optionalBusiness.isPresent()) throw new NotFoundException("That user does not have a business with that id");
        return optionalBusiness.get();
    }

    public Business validatePhoneNumberAndRowExistence(int userNumber, String businessId){
        validatePhoneNumber(userNumber);
       return validateRowExistence(userNumber, businessId);
    }

    public Business deleteAUsersBusiness(int userNumber, String businessId) {
        validatePhoneNumber(userNumber);
        Optional<Business> optionalBusiness = businessRepository.findByUserNumberAndBusinessId(userNumber, UUID.fromString(businessId));
        if(!optionalBusiness.isPresent()) throw new NotFoundException("That user does not have that business id");
        businessRepository.deleteByUserNumberAndBusinessId(userNumber, UUID.fromString(businessId));
        return optionalBusiness.get();
    }

    @Override
    public Business addImageToBusiness(AddImageRequest imageRequest) {
        Integer index = imageRequest.getArrayIndex();
        Business business = validateRowExistence(imageRequest.getUserNumber(), imageRequest.getBusinessId().toString());
        if(index == null) index = (int) Math.floor(Math.random() * 3);
        business.getThreeImageUrls().add(index, imageRequest.getImageUrl());
        return  businessRepository.save(business);

    }
}
