/**
 * @author Kumar.Kunal
 */
package com.org.kunal.parametrejdbc.purchaserequest;

import org.mapstruct.Mapper;



/**
 * @author Kumar.Kunal
 *
 */
@Mapper(componentModel = "spring")
public interface PurchaseRequestMapper {
	
	PurchaseRequestEntity toEntity(PurchaseResponseDto purchaseResponseDto);

	PurchaseResponseDto toDTO(PurchaseRequestEntity purchaseRequestEntity);

}
