package br.com.flaviohblima.orders_report.infra.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemsRepositoryImplTest {

    @InjectMocks
    private ItemsRepositoryImpl itemsRepository;

    @Mock
    private ItemsJpaRepository jpaRepository;

    @Test
    void calcTotalByCodigoPedido() {
        when(jpaRepository.calcTotalByCodigoPedido(1L)).thenReturn(2f);
        Float actual = itemsRepository.calcTotalByCodigoPedido(1L);
        assertEquals(2f, actual);
    }
}