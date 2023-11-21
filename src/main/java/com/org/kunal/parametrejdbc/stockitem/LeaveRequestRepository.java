/**
 * kunal
 * parametrejdbc
 * com.org.kunal.parametrejdbc.stockitem
 */
package com.org.kunal.parametrejdbc.stockitem;

import java.util.List;

/**
 * kunal parametrejdbc 2023
 */
public interface LeaveRequestRepository {
	
	//void saveLeaveRequest(StockRequest leaveRequest);

	List<LeaveRequest> getLeaveRequestsByEmployeeId(Long employeeId);

	Long requestLeave(LeaveRequest leaveRequest);

	void updateLeaveRequestApproval(Long requestId, boolean approved);

}
