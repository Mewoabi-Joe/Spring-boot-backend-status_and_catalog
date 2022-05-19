package reseau.project.status.businesses;

import java.util.List;

public interface BusinessInterface {
    public List<Business> getAllUsersAndTheirBusinesses();
    public Business addOrUpdateUserOneBusiness(Business business);
    public List<Business> getAllBusinessesOfAUser(int userNumber);
    public Business getBusinessByUserNumberAndBusinessId(int userNumber, String businessId);

    public Business deleteAUsersBusiness(int userNumber, String businessId);

    Business addImageToBusiness(AddImageRequest imageRequest);
    Business deleteImageFromBusiness(DeleteImageRequest imageRequest);
}
