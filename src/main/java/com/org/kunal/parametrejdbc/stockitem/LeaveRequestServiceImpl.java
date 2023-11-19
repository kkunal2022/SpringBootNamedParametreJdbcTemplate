/**
 * kunal
 * parametrejdbc
 * com.org.kunal.parametrejdbc.stockitem
 */
package com.org.kunal.parametrejdbc.stockitem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * kunal
 * parametrejdbc
 * 2023
*/
@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

	@Autowired
	private LeaveRequestRepository leaveRequestRepository;

	@Override
	public List<LeaveRequest> getLeaveRequestsByEmployeeId(Long employeeId) {
		return leaveRequestRepository.getLeaveRequestsByEmployeeId(employeeId);
	}

	@Override
	public Long requestLeave(LeaveRequest leaveRequest) {
		return leaveRequestRepository.requestLeave(leaveRequest);
	}

	@Override
	public void updateLeaveRequestApproval(Long requestId, boolean approved) {
		leaveRequestRepository.updateLeaveRequestApproval(requestId, approved);
	}

}
