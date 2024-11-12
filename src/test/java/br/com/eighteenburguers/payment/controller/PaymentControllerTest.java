package br.com.eighteenburguers.payment.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.eighteenburguers.payment.adapter.OrderMapper;
import br.com.eighteenburguers.payment.adapter.request.OrderRequest;
import br.com.eighteenburguers.payment.core.entity.PaymentInformation;
import br.com.eighteenburguers.payment.core.exception.BusinessException;
import br.com.eighteenburguers.payment.core.usecase.CreatePaymentMethod;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

	@Mock
	CreatePaymentMethod createPaymentMethod;
	
	OrderMapper mapper;
	
	@BeforeEach
	void setup() {
		this.mapper = Mappers.getMapper(OrderMapper.class);
	}
	
	@Test
	void shouldBeCreate() throws BusinessException {
		Mockito.when(createPaymentMethod.execute(Mockito.any())).thenReturn(new PaymentInformation());
		
		PaymentController controller = new PaymentController(createPaymentMethod, mapper);
		PaymentInformation response = controller.create(new OrderRequest());
		assertNotNull(response);
	}
}