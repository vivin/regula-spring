package net.vivin.validation;

import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class StarshipNameService {

    Map<String, Boolean> nameMap;

    public StarshipNameService() {
        nameMap = new LinkedHashMap<String, Boolean>();
        nameMap.put("enterprise", true);
        nameMap.put("excelsior", true);
        nameMap.put("endeavor", true);
    }

    public boolean exists(String name) {
        return nameMap.get(name) != null;
    }
}
