package ch.wiss.motoforumapi.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void testSetGetMotorcycle() {
        String desc = "Yamaha R6";
        User user = new User();
        user.setMotorcycle(desc);
        assertEquals(desc, user.getMotorcycle());
    }

    @Test
    public void testSetGetId() {
        long id = 10;
        User user = new User();
        user.setId(id);
        assertEquals(id, user.getId());
    }
}
