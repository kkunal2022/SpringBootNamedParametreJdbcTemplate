package com.org.kunal.parametrejdbc.purchaserequisition;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
/**
 * Kumar.Kunal
 */
public interface PurchaseRequisitionService {

    //PurchaseRequisition createPurchaseRequest(PurchaseRequisition purchaseRequisition, Long userId);
    PurchaseRequisition createPurchaseRequest(PurchaseRequisition purchaseRequisition);

    List<PurchaseRequisition> getAllPurchaseRequests();

    PurchaseRequisition getPurchaseRequestById(Long id);

    boolean deletePurchaseRequests(Long id);

    void updatePurchaseRequest(PurchaseRequisition purchaseRequisition, Long id);//is the return type here correct, yes correct consistent return value

    PurchaseRequisitionDTO findOneWithUser(Long userId, Long purchaseId);

    List<PurchaseRequisition> getUserAllPurchaseRequests(Long userId);

    void updateSignature(PurchaseRequisitionDTO purchaseRequisition, MultipartFile image);

    List<PurchaseResponseDto> getAllProducts();

    List<PurchaseRequisition> getPurchaseRequisitionsByUser(String username);
}