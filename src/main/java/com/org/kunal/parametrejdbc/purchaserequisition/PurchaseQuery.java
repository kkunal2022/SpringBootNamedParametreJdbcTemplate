package com.org.kunal.parametrejdbc.purchaserequisition;

public class PurchaseQuery {
    public static final String SELECT_PURCHASE_REQUESTS_BY_ID_QUERY ="SELECT * FROM PurchaseRequisition WHERE id = :id";
    public static final String INSERT_PURCHASE_REQUISITION_REQUEST_QUERY = "INSERT INTO PurchaseRequisition (user_id, date, department_code,receiver_email, reason, item_number, Item_description, unit_price, quantity, estimated_value,  signature) VALUES (:userId, :date, :departmentCode,:receiverEmail, :reason, :itemNumber, :itemDescription, :unitPrice, :quantity, :estimatedValue, :signature)";

    public static final String GET_PURCHASE_REQUISITION_TO_USER_REQUEST_QUERY="SELECT * FROM purchaseRequests";
    public static final  String  UPDATE_PURCHASE_SIGNATURE_IMAGE_QUERY="";
}
