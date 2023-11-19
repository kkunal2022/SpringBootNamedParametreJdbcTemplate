/**
 * @author Kumar.Kunal
 */
package com.org.kunal.parametrejdbc.exception;

import java.io.Serial;

import com.org.kunal.parametrejdbc.purchaserequest.PurchaseRequestExceptionEnum;
/**
 * Kumar.Kunal
 */
public class PurchaseRequestNotFoundException extends RuntimeException {
	
	@Serial
    private static final long serialVersionUID = -7541208107922250009L;

	public PurchaseRequestNotFoundException () {
        super(PurchaseRequestExceptionEnum.PURCHASE_REQUEST_NOT_FOUND_EXCEPTION.getMessage());
    }
}
