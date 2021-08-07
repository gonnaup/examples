package org.gonnaup.examples.springs.core.placeholdconfigurer;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author gonnaup
 * @version created at 2021/8/7 18:36
 */
@Data
public class Information {
    @Value("${applicationname}")
    private String applicationname;
    @Value("${password}")
    private String password;
}
