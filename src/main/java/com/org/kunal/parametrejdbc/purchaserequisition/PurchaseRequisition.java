package com.org.kunal.parametrejdbc.purchaserequisition;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;
/**
 * Kumar.Kunal
 */
@Data
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_DEFAULT)
public class PurchaseRequisition {
	private Long id;
	private Long userId;
	private Date date;
	private int departmentCode;
	private String reason;
	private int itemNumber;
	private String itemDescription;
	private int unitPrice;
	private int quantity;
	private int estimatedValue;
	private String receiverEmail;
	private String signature;
	//private String name;
	//private String type;

	//@Lob
	//private byte[] profileImage;
}