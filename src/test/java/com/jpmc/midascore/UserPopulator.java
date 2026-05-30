package com.jpmc.midascore;

import com.jpmc.midascore.component.DatabaseConduit;
import com.jpmc.midascore.entity.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class UserPopulator {
    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private DatabaseConduit databaseConduit;

    public void populate() {
        String[] allUserLines = fileLoader.loadStrings("/test_data/lkjhgfdsa.hjkl");
        String[] userLines = allUserLines[0].split("\\r?\\n");
        for (String userLine : userLines) {
            //String userLine = Arrays.toString(userLines.split("\n"));
            if (userLine == null || userLine.trim().isEmpty()) {
                continue;
            }
            String[] userData = userLine.split(", ");
            UserRecord user = new UserRecord(userData[0], Float.parseFloat(userData[1]));
            databaseConduit.save(user);
            System.out.println("POPULATED USER: " + user);
        }
    }
}
