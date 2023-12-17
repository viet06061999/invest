package com.vn.investion.mapper;

import com.vn.investion.dto.auth.PayslipHisResponse;
import com.vn.investion.model.PayslipHis;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Entity2PayslipResponse extends BeanMapper<PayslipHis, PayslipHisResponse> {
    Entity2PayslipResponse INSTANCE = Mappers.getMapper(Entity2PayslipResponse.class);
}
