package it.sevenbits.spring_boot.task_manager.web.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * For saving validation size page
 */
@Component
public class ValidatorSizePage {
    private Integer minSize;
    private Integer maxSize;

    /**
     * Constructor
     *
     * @param minSize minimum size
     * @param maxSize maximum size
     */
    public ValidatorSizePage(@Value("${configuration.meta.min-page-size}") final Integer minSize,
                             @Value("${configuration.meta.max-page-size}") final Integer maxSize) {
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    /**
     * Get minimum
     *
     * @return minSize
     */
    public int getMinSize() {
        return minSize;
    }

    /**
     * Get maximum
     *
     * @return maxSize
     */
    public int getMaxSize() {
        return maxSize;
    }

}
