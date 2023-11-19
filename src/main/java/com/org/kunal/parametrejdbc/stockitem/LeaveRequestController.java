/**
 * kunal
 * parametrejdbc
 * com.org.kunal.parametrejdbc.stockitem
 */
package com.org.kunal.parametrejdbc.stockitem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * kunal
 * parametrejdbc
 * 2023
*/
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/leave-requests")
@Slf4j
public class LeaveRequestController {

	@Autowired
	private LeaveRequestService leaveRequestService;

	@GetMapping("/{employeeId}")
	public List<LeaveRequest> getLeaveRequestsByEmployeeId(@PathVariable Long employeeId) {
		return leaveRequestService.getLeaveRequestsByEmployeeId(employeeId);
	}

	@PostMapping
	public Long requestLeave(@RequestBody LeaveRequest leaveRequest) {
		return leaveRequestService.requestLeave(leaveRequest);
	}

	@PutMapping("/{requestId}")
	public void updateLeaveRequestApproval(@PathVariable Long requestId, @RequestParam boolean approved) {
		leaveRequestService.updateLeaveRequestApproval(requestId, approved);
	}

}
