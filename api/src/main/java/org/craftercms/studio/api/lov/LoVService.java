package org.craftercms.studio.api.lov;

import java.util.List;

/**
 * @author Sumer Jabri
 */
public interface LoVService {
    /**
     * Retrieve the List of Values associated with the lovKey provided.
     *
     * @param lovKey List of Value identifier
     * @return List of Values
     * @throws LoVNotFoundException
     */
    List<String> getLoV(String lovKey) throws LoVNotFoundException;
}
