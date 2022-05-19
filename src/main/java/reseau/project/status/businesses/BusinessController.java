package reseau.project.status.businesses;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reseau.project.status.contacts.Contact;
import reseau.project.status.contacts.ContactService;
import reseau.project.status.status.Status;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.datastax.oss.driver.shaded.guava.common.net.HttpHeaders.CONTENT_LENGTH;
import static com.datastax.oss.driver.shaded.guava.common.net.HttpHeaders.CONTENT_TYPE;

@RestController
@Slf4j
@RequestMapping("/business")
public class BusinessController {
    @Autowired
    private BusinessService businessService;

    @GetMapping
    public ResponseEntity<List<Business>> getAllUsersAndTheirBusinesses(){
        return new ResponseEntity<>(businessService.getAllUsersAndTheirBusinesses(), HttpStatus.OK);
    }

    @GetMapping("/{userNumber}")
    public ResponseEntity<List<Business>> getBusinessesOfAUser(@PathVariable int userNumber){
        List<Business> businesses = businessService.getAllBusinessesOfAUser(userNumber);

        return new ResponseEntity<>(businesses, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Business> addOrUpdateUserOneBusiness(@Valid @RequestBody Business business){
//        log.info("IN BUSINESS SERVICE ADDING BUSINESS:");
//        log.info("userNumber: "+ business.getUserNumber() );
//        log.info("businessName: "+ business.getBusinessName() );
//        log.info("businessDescription: "+ business.getDescription() );
//        log.info("location: "+ business.getLocation() );
//        log.info("itemCategorie: "+ business.getItemCategories() );
//        log.info("openHours: "+ business.getOpenHours() );

        return new ResponseEntity<>(businessService.addOrUpdateUserOneBusiness(business), HttpStatus.CREATED);
    }

    @DeleteMapping("/{userNumber}")
    public ResponseEntity<Business> deleteAUsersBusiness(@PathVariable int userNumber,
                                                         @RequestParam String businessId){
        return new ResponseEntity<>(businessService.deleteAUsersBusiness(userNumber, businessId), HttpStatus.OK);
    }

    @PostMapping("/add_image")
    public ResponseEntity<Business> addImageToBusiness(@RequestBody AddImageRequest imageRequest){
        Business business = businessService.addImageToBusiness(imageRequest);
        return new ResponseEntity<>(business,HttpStatus.OK);
    }

    @DeleteMapping("/delete_image")
    public ResponseEntity<Business> deleteImageFromBusiness(@RequestBody DeleteImageRequest imageRequest){
        Business business = businessService.deleteImageFromBusiness(imageRequest);
        return new ResponseEntity<>(business,HttpStatus.OK);
    }

    public String getImageUrl(String userNumber, String businessId, String imageNumber){
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("business/view_image/")
                .path(userNumber)
                .toUriString()+"?businessId=" + businessId + "&imageNumber=" + imageNumber;
    }



}
