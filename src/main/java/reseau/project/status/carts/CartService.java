package reseau.project.status.carts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reseau.project.status.businesses.Business;
import reseau.project.status.catalogs.CatalogRepository;
import reseau.project.status.exceptions.BadRequestException;
import reseau.project.status.exceptions.NotFoundException;
import reseau.project.status.items.ItemRepository;
import reseau.project.status.users.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class CartService implements CartInterface {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;


    public void validatePhoneNumber(int userNumber) {
        if (userNumber < 600000000 || userNumber > 700000000)
            throw new BadRequestException("The userNumber is not a valid phone number");
        if (!userRepository.findById(userNumber).isPresent())
            throw new NotFoundException("The user with that Number is not registered to this platform");
    }

    public Cart validateExistence(int userNumber, int ownerNumber, UUID categoryId) {
        Optional<Cart> optionalCart = cartRepository.findByKey_UserNumberAndKey_OwnerNumberAndKey_categoryId(userNumber, ownerNumber, categoryId);
        if (!optionalCart.isPresent())
            throw new NotFoundException("No category with that id exit for that user and owner");
        return optionalCart.get();
    }

    public Cart validateRowExistence(int userNumber, int ownerNumber, UUID categoryId, UUID itemId) {
        Optional<Cart> optionalCart = cartRepository.findByKey_UserNumberAndKey_OwnerNumberAndKey_categoryIdAndKey_ItemId(userNumber, ownerNumber, categoryId, itemId);
        if (!optionalCart.isPresent())
            throw new NotFoundException("No item with that Id exist for that category , owner number and user number");
        return optionalCart.get();
    }

//    public Cart validatePhoneNumberAndRowExistence(int userNumber, String cartId){
//        validatePhoneNumber(userNumber);
//
//        return validateRowExistence(userNumber, cartId);
//    }

    //    @Override
//    public Cart addUpdateCartItem(Cart cart) {
//        validatePhoneNumber(cart.getKey().getUserNumber());
//        validatePhoneNumber(cart.getKey().getOwnerNumber());
//        int quantity =  cart.getQuantity();
//
//        if(cart.getKey().getItemId() == null){
//            Optional<Cart> optionalCart = cartRepository.findByKey_UserNumberAndKey_OwnerNumberAndKey_categoryId(cart.getKey().getUserNumber(),cart.getKey().getOwnerNumber(),cart.getKey().getCategoryId());
//            if(optionalCart.isPresent()){
//                optionalCart.get().get
//                cartRepository.save()
//            };
//        }
//
//        if()
//        if(quantity == 0){
//            Cart cart1 = validateRowExistence(cart.getKey().getUserNumber(), cart.getKey().getOwnerNumber(), cart.getKey().getItemOrCategoryId());
//        }
//    }
//
    public void validateCatologId(UUID catalogId) {
        if (itemRepository.findByCatalogId(catalogId).size() == 0)
            throw new BadRequestException("No catalog with such an id exist");
    }

    public void validateCatalogAndItemId(UUID catalogId, UUID itemId) {
        if (!itemRepository.findByCatalogIdAndItemId(catalogId, itemId).isPresent())
            throw new NotFoundException("that Catalog does not have an item with that id");
    }

    public void validateSequenceCatalogAndItemId(UUID catalogId, UUID itemId) {
        validateCatologId(catalogId);
        validateCatalogAndItemId(catalogId, itemId);
    }

    @Override
    public Cart addUpdateCartItem(Cart cart) {
        return null;
    }
}
