package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Component
@Service
public class IncentiveComponent {
    private final RestTemplate restTemplate = new RestTemplate();

    public float getIncentive(Transaction transaction) {
        String resourceUrl = "http://localhost:8080/incentive";
        Incentive res = restTemplate.postForObject(resourceUrl, transaction, Incentive.class);
        if (res != null) {
            return res.getAmount();
        } else {
            return 0;
        }
    }
}
