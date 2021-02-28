package com.vdx.jobService.service;

import com.vdx.jobService.exception.BaseException;
import com.vdx.jobService.response.BaseResponse;

public interface ExceptionHelperService {
    BaseResponse getBaseResponseFromBaseException(BaseException exception);

    BaseResponse getBaseResponseFromException(Exception exception);
}
