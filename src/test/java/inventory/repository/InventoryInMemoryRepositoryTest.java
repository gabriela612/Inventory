package inventory.repository;

import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class InventoryInMemoryRepositoryTest {
    InventoryInMemoryRepository inventoryInMemoryRepository;

    @BeforeEach
    void setUp() {
        inventoryInMemoryRepository = new InventoryInMemoryRepository();
    }

    @Test
    void TC01() {
        try {
            inventoryInMemoryRepository.lookupProduct("");
            assert (false);
        } catch (Exception err) {
            assert (err instanceof IllegalArgumentException);
            assert (err.getMessage().contains("The name or ID was not entered"));
        }
    }

    @Test
    void TC02() {
        try {
            addProducts();
            Product product = inventoryInMemoryRepository.lookupProduct("1");
            assertEquals (product.getProductId(), 1);
        } catch (Exception err) {
            assert (false);
        }
    }

    @Test
    void TC03() {
        try {
            addProducts();
            Product product = inventoryInMemoryRepository.lookupProduct("og");
            assertEquals (product.getName(), "Cog");
        } catch (Exception err) {
            assert (false);
        }
    }

    @Test
    void TC04() {
        try {
            addProducts();
            Product product = inventoryInMemoryRepository.lookupProduct("aaa");
            assertNull(product);
        } catch (Exception err) {
            assert (false);
        }
    }

    @Test
    void TC05() {
        try {
            Product product = inventoryInMemoryRepository.lookupProduct("aaa");
            assertNull(product);
        } catch (Exception err) {
            assert (false);
        }
    }

    public void addProducts() {
        inventoryInMemoryRepository.addProduct(new Product(1, "Cog", 1,1,1,1, null));
        inventoryInMemoryRepository.addProduct(new Product(2, "Spring", 2,2,2,2, null));
        inventoryInMemoryRepository.addProduct(new Product(3, "Screw", 3,3,3,3, null));
    }
}