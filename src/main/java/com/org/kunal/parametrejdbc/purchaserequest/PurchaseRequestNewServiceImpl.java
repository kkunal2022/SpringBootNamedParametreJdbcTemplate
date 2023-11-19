/**
 * @author Kumar.Kunal
 */
package com.org.kunal.parametrejdbc.purchaserequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.org.kunal.parametrejdbc.purchaserequest.PurchaseRequestEntity;
import com.org.kunal.parametrejdbc.exception.PurchaseRequestNotFoundException;
import com.org.kunal.parametrejdbc.purchaserequest.PurchaseRequestDto;
import com.org.kunal.parametrejdbc.purchaserequest.PurchaseRequestDtoId;
import com.org.kunal.parametrejdbc.purchaserequest.PurchaseResponseDto;
import com.org.kunal.parametrejdbc.purchaserequest.ResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Kumar.Kunal
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseRequestNewServiceImpl implements PurchaseRequestNewService {

	private final PurchaseRequestNewRepository purchaseRequestRepository;
	private final ModelMapper modelMapper;
	private final PurchaseRequestMapper mapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<PurchaseRequestDto> saveFile(List<MultipartFile> files, List<PurchaseRequestDto> pEntity)
			throws IOException {
		log.info("Entering saveFile method from Service class '{}' ");
		purchaseRequestRepository.saveFile(files, Arrays.asList(modelMapper.map(pEntity, PurchaseRequestEntity[].class)));
		log.info("Processing saveFile method value in Service class ---- '{}'", pEntity);
		return pEntity;

	}

	/*
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<PurchaseRequestDto> saveFile(List<MultipartFile> files, List<PurchaseRequestDto> pEntity)
			throws IOException {
		log.info("Entering saveFile method in Service class ---- ");
		List<PurchaseRequestEntity> entityList = new ArrayList<>();
		for (int i = 0; i < files.size(); i++) {
			MultipartFile file = files.get(i);
			PurchaseRequestDto dto = pEntity.get(i);

			PurchaseRequestEntity entity = modelMapper.map(dto, PurchaseRequestEntity.class);
			log.info("Started processing saveFile entity method for mapper in Service class ---- '{}'", entity);

			entity.setId(dto.getId());
			entity.setPurchaseDate(dto.getPurchaseDate());
			entity.setRequestingDepartment(dto.getRequestingDepartment());
			entity.setDepartmentCode(dto.getDepartmentCode());
			entity.setRequestReason(dto.getRequestReason());
			entity.setItemNumber(dto.getItemNumber());
			entity.setItemDescription(dto.getItemDescription());
			entity.setUnitPrice(dto.getUnitPrice());
			entity.setQuantity(dto.getQuantity());
			entity.setEstimatedValue(dto.getEstimatedValue());
			entity.setEmailAddress(dto.getEmailAddress());
			entity.setName(file.getOriginalFilename());
			entity.setType(file.getContentType());
			entity.setProfileImage(file.getBytes());
			entityList.add(entity);
		}

		List<PurchaseRequestEntity> savedEntities = purchaseRequestRepository.saveFile(files, entityList);
		log.info("Processing savedEntities entity method in Service class ---- '{}'", savedEntities);
		return modelMapper.map(savedEntities, new TypeToken<List<PurchaseRequestEntity>>(){}.getType());
	}
	*/
	
	@Override
	public List<PurchaseResponseDto> findAll() {

		return purchaseRequestRepository.findAll().stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseResponseDto.class)).collect(Collectors.toList());
	}

	@Override
	public PurchaseResponseDto findById(Long id) {

		return modelMapper.map(
				purchaseRequestRepository.findById(id).orElseThrow(PurchaseRequestNotFoundException::new),
				PurchaseResponseDto.class);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseDto insert(PurchaseRequestDto purchaseRequest) {

		return purchaseRequestRepository.save(modelMapper.map(purchaseRequest, PurchaseRequestEntity.class)) > 0
				? new ResponseDto("Purchase Request Created Successfully!")
				: new ResponseDto("Purchase Request Created Failed!");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseDto update(PurchaseRequestDtoId purchaseRequest) {

		return purchaseRequestRepository.update(modelMapper.map(purchaseRequest, PurchaseRequestEntity.class)) > 0
				? new ResponseDto("Purchase Request Updated Successfully!")
				: new ResponseDto("Purchase Request Updated Failed!");
	}

	@Override
	public ResponseDto delete(Long id) {

		return purchaseRequestRepository.deleteById(id) > 0 ? new ResponseDto("Purchase Request Deleted Successfully!")
				: new ResponseDto("Purchase Request Deleted Failed!");
	}

	@Override
	public List<PurchaseResponseDto> findAll(com.org.kunal.parametrejdbc.purchaserequest.Page page) {
		Stream<PurchaseRequestEntity> stream = purchaseRequestRepository.findAll(page);
		return stream.map(mapper::toDTO).collect(Collectors.toList());
	}

}
