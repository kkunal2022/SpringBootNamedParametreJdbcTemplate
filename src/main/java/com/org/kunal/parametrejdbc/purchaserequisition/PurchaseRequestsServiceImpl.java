package com.org.kunal.parametrejdbc.purchaserequisition;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Kumar.Kunal
 */
@Service
@RequiredArgsConstructor
public class PurchaseRequestsServiceImpl implements PurchaseRequisitionService {

    private final PurchaseRequestsRepository<PurchaseRequisition> purchaseRequestsRepository;
    //private final UserRepository<Users> userRepository;

    private final ModelMapper modelMapper;

    public boolean deletePurchaseRequest(Long id) {
        return purchaseRequestsRepository.delete(id);
    }

    /*@Override
    public PurchaseRequisition createPurchaseRequest(PurchaseRequisition purchaseRequests, Long userId) {
        return purchaseRequestsRepository.create(purchaseRequests, userId);
    }*/

    @Override
    public PurchaseRequisition createPurchaseRequest(PurchaseRequisition purchaseRequests) {
        return purchaseRequestsRepository.create(purchaseRequests);
    }

    @Override
    public List<PurchaseRequisition> getAllPurchaseRequests() {
        return purchaseRequestsRepository.list();
    }

    @Override
    public PurchaseRequisition getPurchaseRequestById(Long id) {
        return purchaseRequestsRepository.get(id);
    }

    @Override
    public boolean deletePurchaseRequests(Long id) {
        return purchaseRequestsRepository.delete(id);
    }

    @Override
    public PurchaseRequisitionDTO findOneWithUser(Long userId, Long purchaseId) {
        return mapToPurchaseRequisitionDTO(purchaseRequestsRepository.findOneWithUser(purchaseId)/*, userId*/);
    }

    @Override
    public void updatePurchaseRequest(PurchaseRequisition purchaseRequisition, Long id) {
        purchaseRequestsRepository.update(purchaseRequisition, id);
    }

    private PurchaseRequisitionDTO mapToPurchaseRequisitionDTO(PurchaseRequisition purchaseRequisition/*, Long userId*/) {
        //return PurchaseDTOMapper
        //        .fromPurchaseRequisition(purchaseRequisition, userRepository.get(userId));

        return PurchaseDTOMapper
                .fromPurchaseRequisition(purchaseRequisition);
    }


    @Override
    public List<PurchaseRequisition> getUserAllPurchaseRequests(Long userId) {
        return purchaseRequestsRepository.getUserAllPurchaseRequests(userId);
    }


    @Override
    public void updateSignature(PurchaseRequisitionDTO purchaseRequisition, MultipartFile image) {
        purchaseRequestsRepository.updateSignature(purchaseRequisition, image);
           // userRepository.updateImage(user, image);
    }

    @Override
    public List<PurchaseResponseDto> getAllProducts() {
        return purchaseRequestsRepository.getAllProducts().stream()
                .map(purchase -> modelMapper.map(purchase, PurchaseResponseDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PurchaseRequisition> getPurchaseRequisitionsByUser(String username) {
        return purchaseRequestsRepository.findPurchaseRequisitionsByUser(username);
    }

}
