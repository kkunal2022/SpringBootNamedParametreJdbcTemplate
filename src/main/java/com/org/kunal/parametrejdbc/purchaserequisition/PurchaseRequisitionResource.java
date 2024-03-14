package com.org.kunal.parametrejdbc.purchaserequisition;

import com.org.kunal.parametrejdbc.users.JwtRequest;
import com.org.kunal.parametrejdbc.users.UserAuthService;
import com.org.kunal.parametrejdbc.util.EmailService;
import com.org.kunal.parametrejdbc.util.HttpResponse;
import com.org.kunal.parametrejdbc.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.apache.tomcat.util.http.fileupload.FileUploadBase.CONTENT_DISPOSITION;
import static org.springframework.http.MediaType.parseMediaType;
/**
 * Kumar.Kunal
 */
@RestController
@RequestMapping(path = "/purchase-requisition")
@RequiredArgsConstructor
@Slf4j
public class PurchaseRequisitionResource {

    private final PurchaseRequisitionService purchaseRequisitionService;

    @Autowired
    private EmailService emailService;

    //@Autowired
    //private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private AuthenticationManager authenticationManager;
//

    @GetMapping("/by-user")
    public ResponseEntity<HttpResponse> getPurchaseRequisitionsByUser(@AuthenticationPrincipal JwtRequest currentUser) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(currentUser.getEmail(), currentUser.getUserpwd()));
            UserDetails userDetails = userAuthService.loadUserByUsername(currentUser.getEmail());
            String username = userDetails.getUsername();
            if (username == null || currentUser.getEmail() == null || currentUser.getEmail().isEmpty()) {
                return ResponseEntity.badRequest().body(
                        HttpResponse.builder()
                                .timeStamp(now().toString())
                                .message("Invalid user authentication data")
                                .status(HttpStatus.BAD_REQUEST)
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .build()
                );
            }

            List<PurchaseRequisition> purchaseRequisitions = purchaseRequisitionService.getPurchaseRequisitionsByUser(currentUser.getEmail());

            return ResponseEntity.ok(
                    HttpResponse.builder()
                            .timeStamp(now().toString())
                            .data(of("user", currentUser, "purchaseRequisitions", purchaseRequisitions))
                            .message("Purchase requisitions retrieved for the user")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            // Log the exception details for debugging purposes
            log.error("Error retrieving purchase requisitions: ", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    HttpResponse.builder()
                            .timeStamp(now().toString())
                            .message("An error occurred while retrieving purchase requisitions")
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            // Optionally include more details about the error
                            // .developerMessage(e.getMessage())
                            .build()
            );
        }
    }

    //@GetMapping
    @GetMapping(value = "/getAllPurchases")
    public List<PurchaseResponseDto> getAllProducts() {
        log.info("GET - /purchase request -> request none");
        List<PurchaseResponseDto> purchaseResponse = purchaseRequisitionService.getAllProducts();
        log.info("GET - /purchase request - response -> {}" + purchaseResponse);
        return purchaseResponse;
    }

    @PostMapping(value = "/createlist", consumes = {"multipart/form-data"})
    public ResponseEntity<List<PurchaseRequisition>> createPurchases(@RequestParam("files") MultipartFile photo,@RequestBody  List<PurchaseRequisition> purchaseRequisitions, Authentication authentication) {
        List<PurchaseRequisition> createdPurchases = new ArrayList<>();

        for (PurchaseRequisition purchaseRequisition : purchaseRequisitions) {
            String uniqueFile = null;
            try {
                uniqueFile = UUID.randomUUID().toString() + "_" + photo.getOriginalFilename();
                Path rootPath = Path.of("uploads").resolve(uniqueFile).toAbsolutePath();
                Files.copy(photo.getInputStream(), rootPath);
                purchaseRequisition.setSignature(uniqueFile);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
            log.info("Signature file: {}", purchaseRequisition.toString());
            PurchaseRequisition createdPurchase =
                    purchaseRequisitionService.createPurchaseRequest(purchaseRequisition);
            /*String email = purchaseRequisition.getReceiverEmail();
            String subject = "Purchase Request Send has been sent login Verification From Administrator";
            String message = "Hello " + purchaseRequisition.getId() + " for Product Name" + purchaseRequisition.getItemDescription() + ", " +
                    "\n A Purchase Request Email Verification Was Sent To \n" + purchaseRequisition.getReceiverEmail();
            emailService.sendEmail(email, subject, message);*///email sent a notification
            createdPurchases.add(createdPurchase);
        }

        return ResponseEntity.ok(createdPurchases);

    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PurchaseRequisition> findById(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseRequisitionService.getPurchaseRequestById(id));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<PurchaseRequisition>> findAll() {
        return ResponseEntity.ok(purchaseRequisitionService.getAllPurchaseRequests());
    }

    @GetMapping("/download/report")
    public ResponseEntity<Resource> downloadReport() {
        List<PurchaseRequisition> purchaseRequisitions = new ArrayList<>();
        // customerService.getCustomers().iterator().forEachRemaining(customers::add);

        purchaseRequisitionService.getAllPurchaseRequests().iterator().forEachRemaining(purchaseRequisitions::add);
        PurchaseRequisitionReport report = new PurchaseRequisitionReport(purchaseRequisitions);
        HttpHeaders headers = new HttpHeaders();
        headers.add("File-Name", "PurchaseRequisition-report.xlsx");
        headers.add(CONTENT_DISPOSITION, "attachment;File-Name=PurchaseRequisition-report.xlsx");
        return ResponseEntity.ok().contentType(parseMediaType("application/vnd.ms-excel"))
                .headers(headers).body(report.export());
    }
    @PutMapping(path = "/{id}")
    public ResponseEntity<PurchaseRequisition> updatePurchaseRequest(@PathVariable Long id, @RequestBody PurchaseRequisition purchaseRequisition) {
        purchaseRequisitionService.updatePurchaseRequest(purchaseRequisition, id);
        return ResponseEntity.ok(purchaseRequisitionService.getPurchaseRequestById(id)); //maybe that will return latest value,ok lets try
    }

    @GetMapping(path = "/user/{id}/{userId}")
    public ResponseEntity<PurchaseRequisitionDTO> findOneWithUser(@PathVariable Long id, @PathVariable Long userId) {
        return ResponseEntity.ok(purchaseRequisitionService.findOneWithUser(userId, id));
    }


    @GetMapping("/user/{userId}/purchase-requisition")
    public ResponseEntity<List<PurchaseRequisition>> getUserAllPurchaseRequests(@PathVariable Long userId) {
        List<PurchaseRequisition> purchaseRequests = purchaseRequisitionService.getUserAllPurchaseRequests(userId);
        return ResponseEntity.ok(purchaseRequests);
    }


    @PatchMapping("/update/signature")
    public ResponseEntity<HttpResponse> updateSignature(PurchaseRequisitionDTO purchaseRequisition, @RequestParam("image") MultipartFile image) throws InterruptedException {
        purchaseRequisitionService.updateSignature(purchaseRequisition, image);

        Long purchaseRequestId = purchaseRequisition.getId(); // Assuming PurchaseRequisitionDTO has a method to get the ID
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .data(Map.of("purchaseRequisition", purchaseRequisitionService.getPurchaseRequestById(purchaseRequestId)))
                        .timeStamp(now().toString())
                        .message("signature updated")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


}
