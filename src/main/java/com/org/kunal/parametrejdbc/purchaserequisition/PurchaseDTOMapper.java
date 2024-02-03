package com.org.kunal.parametrejdbc.purchaserequisition;

import org.springframework.beans.BeanUtils;
/**
 * Kumar.Kunal
 */
public class PurchaseDTOMapper {
	public static PurchaseRequisitionDTO fromPurchaseRequisition(PurchaseRequisition purchaseRequisition/*, Users user*/) {
		PurchaseRequisitionDTO purchaseRequisitionDTO = new PurchaseRequisitionDTO();
		BeanUtils.copyProperties(purchaseRequisition, purchaseRequisitionDTO);
		//purchaseRequisitionDTO.setDepartment(user.getDepartment());
		return purchaseRequisitionDTO;
	}
}
