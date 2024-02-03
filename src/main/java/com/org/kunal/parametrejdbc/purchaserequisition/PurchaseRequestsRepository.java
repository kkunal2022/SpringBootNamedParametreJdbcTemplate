package com.org.kunal.parametrejdbc.purchaserequisition;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
/**
 * Kumar.Kunal
 */
public interface PurchaseRequestsRepository<T extends PurchaseRequisition> {
	List<T> list();

	//T create(T data, Long userId);

	T create(T data);

	T get(Long id);

	// List<PurchaseRequestEntity> saveFile(List<MultipartFile> files,
	// List<PurchaseRequestEntity> pEntity) throws IOException;

	boolean delete(Long id);

	/* More Complex */
	T findOneWithUser(Long id);

	void update(T data, Long id);// is this correct , yes correct

	// mehtod to get purchase requests belonging to user
	List<T> getUserAllPurchaseRequests(Long userId);

	void updateSignature(PurchaseRequisitionDTO purchaseRequisitionDTO, MultipartFile image);

	List<PurchaseRequisition> getAllProducts();

	List<PurchaseRequisition> findPurchaseRequisitionsByUser(String username);
}
