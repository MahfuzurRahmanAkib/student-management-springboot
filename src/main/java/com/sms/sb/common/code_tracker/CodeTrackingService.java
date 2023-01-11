package com.sms.sb.common.code_tracker;

import com.sms.sb.common.constant.ApplicationConstant;
import com.sms.sb.common.exception.StudentManagementException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

import static com.sms.sb.common.constant.ApplicationConstant.DASH;
import static com.sms.sb.common.constant.ApplicationConstant.MESSAGE_SEPARATOR;
import static com.sms.sb.common.constant.ErrorId.VOUCHER_ERROR;
import static com.sms.sb.common.constant.ErrorId.VOUCHER_NO_ERROR;

@Service
public class CodeTrackingService {
    private final CodeTrackingRepository codeTrackingRepository;

    public CodeTrackingService(CodeTrackingRepository codeTrackingRepository) {
        this.codeTrackingRepository = codeTrackingRepository;
    }

    public String generateUniqueCodeNo(CodeType key) {
        return key.toString() + DASH + uniqueCode(key);
    }

    public String uniqueCode(CodeType key) {
        return String.format(ApplicationConstant.FOUR_DIGIT_FORMAT, getCodeSequence(key));
    }

    private Long getCodeSequence(CodeType codeType) {
        CodeTracking codeTracking = codeTrackingRepository.findByCodeType(codeType).orElseThrow(() ->
                StudentManagementException.internalServerException(createDynamicCode(VOUCHER_ERROR, codeType.name())));
        Long incrementedSeq = codeTracking.getCodeLastSequence() + 1;
        codeTracking.setCodeLastSequence(incrementedSeq);

        synchronized (codeType) {
            try {
                codeTrackingRepository.saveAndFlush(codeTracking);
            } catch (Exception e) {
                throw StudentManagementException.dataSaveException(VOUCHER_NO_ERROR);
            }
            return codeTracking.getCodeLastSequence();
        }
    }

    public static String createDynamicCode(String errorCode, String... placeHolders) {
        StringBuilder builder = new StringBuilder(errorCode);
        if (Objects.isNull(placeHolders)) {
            return errorCode;
        }
        Arrays.stream(placeHolders).forEach(placeHolder -> builder.append(MESSAGE_SEPARATOR).append(placeHolder));
        return builder.toString();
    }
}
