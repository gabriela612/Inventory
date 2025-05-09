package inventory;

import inventory.model.InhousePart;
import inventory.model.Part;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PartUnitTest {
    Part part;

    @BeforeEach
    void setUp() {
        part = new InhousePart(888, "test", 10, 10, 0, 100, 1);
    }

    @Test
    void testGetPartId() {
        assert part.getPartId() == 888;
    }

    @Test
    void testGetName() {
        assert part.getName().equals("test");
    }

    @Test
    void testValidateName() {
        assert Part.validateName("Ana", "").isEmpty();
    }

    @Test
    void testIsValidPart() {
        assert Part.isValidPart("Ana", 10, 10, 0, 100, "").isEmpty();
    }
}