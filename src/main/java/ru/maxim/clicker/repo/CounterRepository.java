package ru.maxim.clicker.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.maxim.clicker.repo.model.CountEntity;
import ru.maxim.clicker.repo.repository.HibernateCountRepository;

@Component
public class CounterRepository {

     Logger logger = LoggerFactory.getLogger(CounterRepository.class);
     private final HibernateCountRepository repository;

     public CounterRepository(HibernateCountRepository repository) {
          this.repository = repository;
     }

     @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
     public CountEntity getCount() {
          CountEntity result = null;
          try {
               result = repository.getCounter();
               if (result == null) {
                    result = repository.createCounter();
               }
          } catch (Exception e) {
               return handleException("Problem with getting counter ", e);
          }
          return result;
     }

     @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
     public CountEntity incrementAndGet() {
          CountEntity result = null;
          try {
               getCount();
               result = repository.updateCounter();
               assert result != null;
          } catch (Exception e){
               return handleException("Problem with updating counter ", e);
          }
          logger.info("Counter have been updated to " + result.getCount().toString());
          return result;
     }

     private CountEntity handleException(String s, Exception e) {
          logger.error(s + e, e);
          throw new IllegalStateException(e);
     }
}
