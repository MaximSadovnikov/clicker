package ru.maxim.clicker.domain;

import org.springframework.stereotype.Service;
import ru.maxim.clicker.repo.CounterRepository;

@Service
public class CounterService {

    private final CounterRepository counterRepository;

    public CounterService(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    public Count getNumOfClicks() {
        return new Count(counterRepository.getCount().getCount());
    }

    public Count makeClick() {
        return new Count(counterRepository.incrementAndGet().getCount());
    }
}
