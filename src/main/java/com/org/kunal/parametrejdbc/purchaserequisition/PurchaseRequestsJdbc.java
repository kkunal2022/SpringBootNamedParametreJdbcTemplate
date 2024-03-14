package com.org.kunal.parametrejdbc.purchaserequisition;

import com.org.kunal.parametrejdbc.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.org.kunal.parametrejdbc.purchaserequisition.PurchaseQuery.SELECT_PURCHASE_REQUESTS_BY_ID_QUERY;
import static com.org.kunal.parametrejdbc.purchaserequisition.PurchaseQuery.UPDATE_PURCHASE_SIGNATURE_IMAGE_QUERY;
import static java.util.Map.of;

/**
 * Kumar.Kunal
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class PurchaseRequestsJdbc implements PurchaseRequestsRepository<PurchaseRequisition> {
    public static final String AN_ERROR_OCCURRED_PLEASE_TRY_AGAIN = "An error occurred. Please try again.";
    private static int lastGeneratedNumber = 99;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public static final String PURCHASE_REQUEST_LIST = "select * from purchaserequisition";

    private static final String SELECT_BY_USERNAME =
            "SELECT * FROM purchaserequisition WHERE receiver_email = :receiverEmail";
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    RowMapper<PurchaseRequisition> rowMapper = (resultSet, rowNum) -> {
        PurchaseRequisition purchaseRequest = new PurchaseRequisition();
        purchaseRequest.setId(resultSet.getLong("id"));
        purchaseRequest.setDate(resultSet.getDate("date"));
        purchaseRequest.setDepartmentCode(resultSet.getInt("department_code"));
        purchaseRequest.setReceiverEmail(resultSet.getString("receiver_email"));
        purchaseRequest.setReason(resultSet.getString("reason"));
        purchaseRequest.setItemNumber(resultSet.getInt("item_number"));
        purchaseRequest.setItemDescription(resultSet.getString("item_description"));
        purchaseRequest.setUnitPrice(resultSet.getInt("unit_price"));
        purchaseRequest.setQuantity(resultSet.getInt("quantity"));
        purchaseRequest.setEstimatedValue(resultSet.getInt("estimated_value"));
        purchaseRequest.setSignature(resultSet.getString("signature"));
        //purchaseRequest.setUserId(resultSet.getLong("user_id"));
        return purchaseRequest;
    };

    /*@Override
    public PurchaseRequisition create(PurchaseRequisition purchaseRequests, Long userId) {
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameters = getSqlParameterSource(purchaseRequests, userId);
        namedParameterJdbcTemplate.update(PurchaseQuery.INSERT_PURCHASE_REQUISITION_REQUEST_QUERY, parameters, holder);
        return purchaseRequests;
    }*/

    @Override
    public PurchaseRequisition create(PurchaseRequisition purchaseRequests) {
        KeyHolder holder = new GeneratedKeyHolder();
        //SqlParameterSource parameters = getSqlParameterSource(purchaseRequests, userId);
        SqlParameterSource parameters = getSqlParameterSource(purchaseRequests);
        namedParameterJdbcTemplate.update(PurchaseQuery.INSERT_PURCHASE_REQUISITION_REQUEST_QUERY, parameters, holder);
        return purchaseRequests;
    }

    @Override
    public List<PurchaseRequisition> list() {
        try {
            String listPurchaseRequisitionQuery = "SELECT * FROM purchaserequisition;";
            List<PurchaseRequisition> purchaseRequests =
                    namedParameterJdbcTemplate.query(listPurchaseRequisitionQuery, rowMapper);
            return purchaseRequests;
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException(
                    "An error occurred while retrieving the list of purchase request. Please try again.");
        }

    }

    //private SqlParameterSource getSqlParameterSource(PurchaseRequisition purchaseRequisition, Long userId) {
    private SqlParameterSource getSqlParameterSource(PurchaseRequisition purchaseRequisition) {
        return new MapSqlParameterSource()
                //.addValue("userId", userId).addValue("id", purchaseRequisition.getId())
                .addValue("date", purchaseRequisition.getDate())
                .addValue("departmentCode", purchaseRequisition.getDepartmentCode())
                .addValue("department_code", generateDepartmentCode(""))
                .addValue("receiverEmail", purchaseRequisition.getReceiverEmail())
                .addValue("reason", purchaseRequisition.getReason())
                .addValue("itemNumber", purchaseRequisition.getItemNumber())
                .addValue("itemDescription", purchaseRequisition.getItemDescription())
                .addValue("unitPrice", purchaseRequisition.getUnitPrice())
                .addValue("quantity", purchaseRequisition.getQuantity())
                .addValue("estimatedValue", purchaseRequisition.getEstimatedValue())
                .addValue("signature", purchaseRequisition.getSignature());
    }

    @Override
    public PurchaseRequisition get(Long id) {
        try {

            return namedParameterJdbcTemplate.queryForObject(SELECT_PURCHASE_REQUESTS_BY_ID_QUERY, of("id", id), rowMapper);

        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("No PURCHASE REQUESTS found by id: " + id);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException(AN_ERROR_OCCURRED_PLEASE_TRY_AGAIN);
        }
    }

    @Override
    public void update(PurchaseRequisition data, Long id) {
        try {
            String updateQuery = "UPDATE purchaserequisition SET " + "date = :date, "
                    + "department_code = :departmentCode, " + "receiver_email = :receiverEmail, " + "reason = :reason, "
                    + "item_number = :itemNumber, " + "Item_description = :itemDescription, "
                    + "unit_price = :unitPrice, " + "quantity = :quantity, " + "estimated_value = :estimatedValue, "
                    + "user_id = :userId, " + "signature = :signature " + "WHERE id = :id";
            MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("date", data.getDate())
                    .addValue("departmentCode", data.getDepartmentCode())
                    .addValue("receiverEmail", data.getReceiverEmail()).addValue("reason", data.getReason())
                    .addValue("itemNumber", data.getItemNumber()).addValue("itemDescription", data.getItemDescription())
                    .addValue("unitPrice", data.getUnitPrice()).addValue("quantity", data.getQuantity())
                    .addValue("estimatedValue", data.getEstimatedValue()).addValue("signature", data.getSignature());
                    //.addValue("userId", data.getUserId()).addValue("id", id);

            namedParameterJdbcTemplate.update(updateQuery, parameters);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException(AN_ERROR_OCCURRED_PLEASE_TRY_AGAIN);
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            String DELETE_FROM_PURCHASEREQUESTS_BY_PURCHASEREQUEST_ID = "DELETE FROM PURCHASEREQUEST WHERE id = :purchaserequestwId";
            namedParameterJdbcTemplate.update(DELETE_FROM_PURCHASEREQUESTS_BY_PURCHASEREQUEST_ID,
                    Collections.singletonMap("purchaserequestsId", id));
            return true;
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException(AN_ERROR_OCCURRED_PLEASE_TRY_AGAIN);
        }
    }

    @Override
    public PurchaseRequisition findOneWithUser(Long id) {
        try {
            return namedParameterJdbcTemplate.queryForObject(SELECT_PURCHASE_REQUESTS_BY_ID_QUERY, of("id", id),
                    new PurchaseRequisitionRowMapper());
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException(AN_ERROR_OCCURRED_PLEASE_TRY_AGAIN);
        }
    }

    @Override
    public void updateSignature(PurchaseRequisitionDTO purchaseRequisition, MultipartFile image) {
        String purchaseRequisitionSignatureUrl = setPurchaseRequisitionSignatureUrl(purchaseRequisition.getId());
        saveSignature(purchaseRequisition.getId(), image);
        namedParameterJdbcTemplate.update(UPDATE_PURCHASE_SIGNATURE_IMAGE_QUERY,
                Map.of("imageUrl", purchaseRequisitionSignatureUrl, "id",
                        purchaseRequisition.getId()));
    }

    private String setPurchaseRequisitionSignatureUrl(Long id) {
        String idString = String.valueOf(id);
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/image/" + idString + ".png")
                .toUriString();
    }

    private void saveSignature(Long id, MultipartFile image) {
        String idString = String.valueOf(id);
        Path fileStorageLocation = Paths.get(System.getProperty("user.home"), "Downloads", "images").toAbsolutePath()
                .normalize();
        try {
            if (!Files.exists(fileStorageLocation)) {
                Files.createDirectories(fileStorageLocation);
                log.info("Created directories: {}", fileStorageLocation);
            }
            Files.copy(image.getInputStream(), fileStorageLocation.resolve(idString + ".png"),
                    StandardCopyOption.REPLACE_EXISTING);
            log.info("File saved in: {} folder", fileStorageLocation);
        } catch (IOException exception) {
            log.error(exception.getMessage());
            throw new ApiException("Unable to save image: " + exception.getMessage());
        }
    }

    @Override
    public List<PurchaseRequisition> getUserAllPurchaseRequests(Long userId) {
        try {
            String query = "SELECT * FROM purchase_requests WHERE user_id = :userId";
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("userId", userId);
            List<PurchaseRequisition> purchaseRequisitionList = namedParameterJdbcTemplate.query(query, parameters, rowMapper);
            return purchaseRequisitionList;
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    private String generateDepartmentCode(String departmentCode) {
        String prefix = getDepartmentCodePrefix(departmentCode);
        String generatedNumbers = generateUniqueNumbers();
        return prefix + generatedNumbers;
    }

    private String getDepartmentCodePrefix(String departmentCode) {
        int departmentCodeHyphenIndex = departmentCode.indexOf("-");
        return departmentCodeHyphenIndex != -1 ? departmentCode.substring(0, departmentCodeHyphenIndex) : departmentCode;
    }

    private String generateUniqueNumbers() {
        int maxNumber = 999;
        String generatedNumbers;

        while (true) {
            lastGeneratedNumber++;
            if (lastGeneratedNumber > maxNumber) {
                lastGeneratedNumber = 100; // Reset to the minimum number if it exceeds the maximum
            }
            generatedNumbers = String.format("%03d", lastGeneratedNumber);
            if (isDepartmentCodeUnique(generatedNumbers)) {
                return generatedNumbers;
            }
        }
    }

    private boolean isDepartmentCodeUnique(String generatedNumbers) {
        try {
            try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
                String query = "SELECT COUNT(*) FROM stocks WHERE LOWER(department_code) = LOWER(?)";

                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, getDepartmentCodePrefix(generatedNumbers));
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            int count = resultSet.getInt(1);
                            return count == 0;
                        }
                    }
                }
            }
        } catch (SQLException sqlException) {
            log.error("Error while generating unique department code with exception - '{}' "
                    + "and exceptionMessage - '{}'", sqlException, sqlException.getMessage());
        }

        return false;
    }
    @Override
    public List<PurchaseRequisition> getAllProducts() {
        log.info("Fetching Purchase Request All Details from DB with ID ");
        return namedParameterJdbcTemplate.query(PURCHASE_REQUEST_LIST,
                (resultSet, row) -> PurchaseRequisition.builder().id(resultSet.getLong("id"))
                        .date(resultSet.getDate("date"))
                        .departmentCode(resultSet.getInt("departmentCode"))
                        .reason(resultSet.getString("reason"))
                        .itemNumber(resultSet.getInt("itemNumber"))
                        .itemDescription(resultSet.getString("itemDescription"))
                        .unitPrice(resultSet.getInt("unitPrice"))
                        .quantity(resultSet.getInt("quantity"))
                        .estimatedValue(resultSet.getInt("estimatedValue"))
                        .receiverEmail(resultSet.getString("receiverEmail"))
                        .signature(resultSet.getString("signature"))
                        .build());
    }

    @Override
    public List<PurchaseRequisition> findPurchaseRequisitionsByUser(String receiverEmail) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("receiver_email", receiverEmail);

        return namedParameterJdbcTemplate.query(SELECT_BY_USERNAME,
                parameters, new BeanPropertyRowMapper<>(PurchaseRequisition.class));
    }


}
