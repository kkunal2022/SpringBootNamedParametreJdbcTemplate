/**
 * @author Kumar.Kunal
 */
package com.org.kunal.parametrejdbc.purchaserequisition;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Kumar.Kunal
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseResponseDto {
	
	private long id;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
	
	private  String requestingDepartment;
	private int departmentCode;
	private String reason;
	private int itemNumber;
	private String itemDescription;
	private int unitPrice;
	private int quantity;
	private int estimatedValue;
	private String receiverEmail;
	private String signature;
}
