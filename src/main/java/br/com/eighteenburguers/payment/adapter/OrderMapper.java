package br.com.eighteenburguers.payment.adapter;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.ReportingPolicy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.eighteenburguers.payment.adapter.request.OrderRequest;
import br.com.eighteenburguers.payment.core.entity.Order;

@Mapper(unmappedTargetPolicy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface OrderMapper {

	@Mapping(target = "id", source = "orderId")
	Order toEntity(OrderRequest request);
}
