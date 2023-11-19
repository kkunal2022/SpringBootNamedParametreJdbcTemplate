/**
 * kunal
 * parametrejdbc
 * com.org.kunal.parametrejdbc.stockitem
 */
package com.org.kunal.parametrejdbc.stockitem;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

/**
 * @author Kumar.Kunal 
 * parametrejdbc 
 * 2023
 */
@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee {

	@Id
	private Long id;
	private String username;
    private String password;
    
    private String departmentRequesting;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private Date stockRequestDate;
    private Integer departmentCode;
    private String purposeOfIssue;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private Date stockDate;
    private Integer itemNo;
    private String itemReferenceNo;
    private String itemDescription;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private Date dateOfPreviousIssue;
    private Integer previousIssueQuantity;
    private Integer quantityRequested;
    private String departmentInitiatedBy;
    private String departmentAuthorisedBy;
    private String departmentConfirmedBy;
    private String departmentReceivedBy;
    private String designatedPersonApprovalName;
    private String signature;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private Date dateOfConfirmation;
    private String role;

    private List<LeaveRequest> leaveRequests;
}
