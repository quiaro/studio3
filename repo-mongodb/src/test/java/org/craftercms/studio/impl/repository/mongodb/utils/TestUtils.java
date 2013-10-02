package org.craftercms.studio.impl.repository.mongodb.utils;

import java.util.UUID;

import static org.junit.Assert.fail;

/**
 * Extra assertions.
 */
public final class TestUtils {
    /**
     * Since is a utils class hide its CTOR
     */
    private  TestUtils(){

    }
    public static void isUUIDValid(String uuid) {
        if (uuid == null) {
            fail("Given UUID is null");
        }
        try {
            // we have to convert to object and back to string because the built in fromString does not have
            // good validation logic.
            UUID fromStringUUID = UUID.fromString(uuid);
            String toStringUUID = fromStringUUID.toString();
            if(!toStringUUID.equals(uuid)){
                fail(String.format("UUID {} is not valid", uuid));
            }
        } catch (IllegalArgumentException e) {
            fail(String.format("UUID {} is not valid", uuid));
        }
    }
}
