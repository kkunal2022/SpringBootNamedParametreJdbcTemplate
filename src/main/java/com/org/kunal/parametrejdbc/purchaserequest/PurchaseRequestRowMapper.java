/**
 * @author Kumar.Kunal
 */
package com.org.kunal.parametrejdbc.purchaserequest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.org.kunal.parametrejdbc.purchaserequest.PurchaseRequestEntity;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Kumar.Kunal
 *
 */
@Slf4j
public class PurchaseRequestRowMapper implements RowMapper<PurchaseRequestEntity> {

	@Override
	public PurchaseRequestEntity mapRow(ResultSet resultSet, int rowNum) throws SQLException {

		log.info("Entering Row Mapper - /purchase request/{} -> request");

		Long id = resultSet.getLong("id");
		Date purchaseDate = resultSet.getDate("purchase_date");
		String requestingDepartment = resultSet.getString("requesting_department");
		int departmentCode = resultSet.getInt("department_code");
		String requestReason = resultSet.getString("request_reason");
		int itemNumber = resultSet.getInt("item_number");
		String itemDescription = resultSet.getString("item_description");
		int unitPrice = resultSet.getInt("unit_price");
		int quantity = resultSet.getInt("quantity");
		int estimatedValue = resultSet.getInt("estimated_value");
		String emailAddress = resultSet.getString("email_address");
		String name = resultSet.getString("name");
		String type = resultSet.getString("type");
		byte[] profileImage = resultSet.getBytes("profile_image");

		return PurchaseRequestEntity.builder()
				.id(id)
				.purchaseDate(purchaseDate)
				.requestingDepartment(requestingDepartment)
				.departmentCode(departmentCode)
				.requestReason(requestReason)
				.itemNumber(itemNumber)
				.itemDescription(itemDescription)
				.unitPrice(unitPrice)
				.quantity(quantity)
				.estimatedValue(estimatedValue)
				.emailAddress(emailAddress)
				.name(name)
				.type(type)
				.profileImage(profileImage).build();
	}

}
