package com.vdx.jobService.service.impl;


import com.vdx.jobService.constant.Status;
import com.vdx.jobService.exception.BaseException;
import com.vdx.jobService.response.BaseResponse;
import com.vdx.jobService.service.ExceptionHelperService;
import org.springframework.stereotype.Service;

@Service("ExceptionHelperServiceImpl")
public class ExceptionHelperServiceImpl implements ExceptionHelperService {

    @Override
    public BaseResponse getBaseResponseFromBaseException(BaseException exception) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(Status.FAILURE);
        baseResponse.setReason(exception.getMessage());

        return baseResponse;
    }

    @Override
    public BaseResponse getBaseResponseFromException(Exception exception) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(Status.FAILURE);
        baseResponse.setReason(exception.getMessage());
        return baseResponse;
    }
}
