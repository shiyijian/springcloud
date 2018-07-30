package com.web.cloud.common.model.dto;

import java.io.Serializable;

public interface BaseDTO extends Serializable {
    String[] getLimitField();

    String[] getSecretField();
}
