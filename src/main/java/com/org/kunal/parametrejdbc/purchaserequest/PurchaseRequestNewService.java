/**
 * @author Kumar.Kunal
 */
package com.org.kunal.parametrejdbc.purchaserequest;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.org.kunal.parametrejdbc.purchaserequest.PurchaseRequestDto;
import com.org.kunal.parametrejdbc.purchaserequest.PurchaseRequestDtoId;
import com.org.kunal.parametrejdbc.purchaserequest.PurchaseResponseDto;
import com.org.kunal.parametrejdbc.purchaserequest.ResponseDto;

/**
 * Kumar.Kunal
 */
public interface PurchaseRequestNewService {
	
	List<PurchaseRequestDto> saveFile(List<MultipartFile> files, List<PurchaseRequestDto> pEntity) throws IOException;

	List<PurchaseResponseDto> findAll();

	PurchaseResponseDto findById(Long id);

	ResponseDto insert(PurchaseRequestDto purchaseRequest);

	ResponseDto update(PurchaseRequestDtoId purchaseRequest);

	ResponseDto delete(Long id);

	List<PurchaseResponseDto> findAll(Page page);

}