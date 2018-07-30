package com.web.cloud.common.response;

import java.io.Serializable;

public interface Response<T> extends Serializable {

    public T getModule();

    public int getCode();

    public String getMessage();

    public long getTotalCount();

    public boolean isSuccess();
}
