package com.sms.sb.common.util;

import org.apache.maven.surefire.shade.org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Objects;

import static com.sms.sb.common.constant.ApplicationConstant.VALUE_ONE;
import static com.sms.sb.common.constant.ApplicationConstant.VALUE_ZERO;

@Component
public class CaseConverter {
    public static String capitalizeFirstCharacter(String readableString) {
        if (readableString == null || readableString.length() == VALUE_ZERO) return readableString;
        return readableString.substring(VALUE_ZERO, VALUE_ONE).toUpperCase() + readableString.substring(VALUE_ONE);
    }

    public static String uncapitalizeAllCharacter(String uncapitalizedString) {
        if (Objects.isNull(uncapitalizedString)) return null;
        uncapitalizedString = StringUtils.normalizeSpace(uncapitalizedString);
        return uncapitalizedString.toLowerCase();
    }

    public static String capitalizeAllCharacter(String capitalizedString) {
        if (Objects.isNull(capitalizedString)) return null;
        capitalizedString = StringUtils.normalizeSpace(capitalizedString);
        return capitalizedString.toUpperCase(Locale.ROOT);
    }
}

