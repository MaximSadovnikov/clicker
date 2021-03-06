package ru.maxim.clicker.domain;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.maxim.clicker.repo.CounterRepository;
import ru.maxim.clicker.repo.model.CountEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class CounterServiceTest {

    private final CounterRepository counterRepository = Mockito.mock(CounterRepository.class);

    CounterService service = new CounterService(counterRepository);

    @Test
    void successGetNumOfClicks() {
        when(counterRepository.getCount()).thenReturn(new CountEntity(1));
        Count numOfClicks = service.getNumOfClicks();
        assertThat(numOfClicks.getValue()).isEqualTo(1);
    }

    @Test
    void successMakeClick() {
        when(counterRepository.incrementAndGet()).thenReturn(new CountEntity(1));
        Count numOfClicks = service.makeClick();
        assertThat(numOfClicks.getValue()).isEqualTo(1);
    }
}