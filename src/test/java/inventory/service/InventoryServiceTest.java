package inventory.service;

import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.model.Product;
import inventory.repository.InventoryFileRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;

import java.util.Collections;
import java.util.Comparator;

class InventoryServiceTest {

    InventoryService inventoryService;
    InventoryFileRepository inventoryFileRepository;

    @BeforeEach
    void setUp() {
        inventoryFileRepository = new InventoryFileRepository();
        inventoryService = new InventoryService(inventoryFileRepository);
    }

    @AfterEach
    @Disabled
    void tearDown() {
    }

    @Test
    @DisplayName("ECP_1 - OK")
    void addProduct_validData_TC1_ECP() {
        try {
            //arrange
            Part part = new InhousePart(1000, "creier", 1, 200, 5, 1000, 1);
            inventoryFileRepository.addPart(part);
            ObservableList<Part> parts = FXCollections.observableArrayList(
                    inventoryFileRepository.getAllParts().stream()
                            .sorted(Comparator.comparingInt(Part::getPartId).reversed())
                            .limit(1)
                            .toList());
            Product product = new Product(-1, "IQ", 500000000, 100, 0, 1000000, parts);

            //act
            inventoryService.addProduct("IQ", 500000000, 100, 0, 1000000, parts);

            //assert
            ObservableList<Product> allSavedProducts = inventoryFileRepository.getAllProducts();
            assert (!allSavedProducts.isEmpty());
            Product saved_product = Collections.max(allSavedProducts, Comparator.comparingInt(Product::getProductId));
            assert (saved_product.equals(product));
        } catch (Exception err) {
            System.out.println(err.getMessage());
            assert (false);
        }
    }

    @Test
    @DisplayName("ECP_2 - pret invalid")
    @Tag("Invalid")
    void addProduct_pretInvalid_TC2_ECP() {
        try {
            //arrange
            Part part = new InhousePart(1001, "cioburi de oglinda", 5000, 200, 5, 1000, 1);
            inventoryFileRepository.addPart(part);
            ObservableList<Part> parts = FXCollections.observableArrayList(
                    inventoryFileRepository.getAllParts().stream()
                            .sorted(Comparator.comparingInt(Part::getPartId).reversed())
                            .limit(1)
                            .toList());

            //act
            inventoryService.addProduct("ghinion", -2000, 10, 0, 100, parts);

            //assert
            assert (false);
        } catch (Exception err) {
            assert (err instanceof RuntimeException);
            assert (err.getMessage().contains("Product price must be greater than cost of parts. "));
        }
    }

    @Test
    @DisplayName("ECP_3 - pret invalid")
    @Tag("Invalid")
    void addProduct_pretInvalid_TC3_ECP() {
        try {
            //arrange
            Part part1 = new InhousePart(1002, "bani", 500000000, 200, 5, 1000, 1);
            Part part2 = new InhousePart(1003, "prieteni", 500000000, 200, 5, 1000, 1);
            inventoryFileRepository.addPart(part1);
            inventoryFileRepository.addPart(part2);
            ObservableList<Part> parts = FXCollections.observableArrayList(
                    inventoryFileRepository.getAllParts().stream()
                            .sorted(Comparator.comparingInt(Part::getPartId).reversed())
                            .limit(2)
                            .toList());

            //act
            inventoryService.addProduct("fericire", 1000000000, 0, 10, 100, parts);

            //assert
            assert (false);
        } catch (Exception err) {
            assert (err instanceof RuntimeException);
            assert (err.getMessage().contains("Inventory level is lower than minimum value."));
        }
    }

    @Test
    @DisplayName("ECP_4 - min invalid")
    @Tag("Invalid")
    void addProduct_minInvalid_TC4_ECP() {
        try {
            //arrange
            Part part1 = new InhousePart(1004, "fericire", 500000000, 200, 5, 1000, 1);
            Part part2 = new InhousePart(1005, "timp liber", 500000000, 200, 5, 1000, 1);
            inventoryFileRepository.addPart(part1);
            inventoryFileRepository.addPart(part2);
            ObservableList<Part> parts = FXCollections.observableArrayList(
                    inventoryFileRepository.getAllParts().stream()
                            .sorted(Comparator.comparingInt(Part::getPartId).reversed())
                            .limit(2)
                            .toList());

            //act
            inventoryService.addProduct("rabdare", 1000000000, 20, -1, 100, parts);

            //assert
            assert (false);
        } catch (Exception err) {
            assert (err instanceof RuntimeException);
            assert (err.getMessage().contains("The Min value must be greater than 0."));
        }
    }

    @RepeatedTest(2)
    @DisplayName("BVA_1 - min invalid")
    void addProduct_minInvalid_TC1_BVA() {
        try {
            //arrange
            ObservableList<Part> parts = FXCollections.observableArrayList();

            //act
            inventoryService.addProduct("IQ", 5000, 10, -1, 100, parts);

            //assert
        } catch (Exception err) {
            assert (err instanceof RuntimeException);
            assert (err.getMessage().contains("The Min value must be greater than 0."));
        }
    }

    @Test
    @DisplayName("BVA_2 - OK")
    void addProduct_validData_TC2_BVA() {
        try {
            //arrange
            Part part = new InhousePart(2000, "familie", 50, 200, 5, 1000, 1);
            inventoryFileRepository.addPart(part);
            ObservableList<Part> parts = FXCollections.observableArrayList(
                    inventoryFileRepository.getAllParts().stream()
                            .sorted(Comparator.comparingInt(Part::getPartId).reversed())
                            .limit(1)
                            .toList());
            Product product = new Product(-1, "fericire", 100000, 10, 0, 100, parts);

            //act
            inventoryService.addProduct("fericire", 100000, 10, 0, 100, parts);

            //assert
            ObservableList<Product> allSavedProducts = inventoryFileRepository.getAllProducts();
            assert (!allSavedProducts.isEmpty());
            Product saved_product = Collections.max(allSavedProducts, Comparator.comparingInt(Product::getProductId));
            assert (saved_product.equals(product));
        } catch (Exception err) {
            System.out.println(err.getMessage());
            assert (false);
        }
    }

    @Test
    @DisplayName("BVA_8 - OK")
    void addProduct_validData_TC8_BVA() {
        try {
            //arrange
            Part part = new InhousePart(3000, "pisica neagra", 50, 200, 5, 1000, 1);
            inventoryFileRepository.addPart(part);
            ObservableList<Part> parts = FXCollections.observableArrayList(
                    inventoryFileRepository.getAllParts().stream()
                            .sorted(Comparator.comparingInt(Part::getPartId).reversed())
                            .limit(1)
                            .toList());
            Product product = new Product(-1, "ghinion", 2000, 100, 100, 100, parts);

            //act
            inventoryService.addProduct("ghinion", 2000, 100, 100, 100, parts);

            //assert
            ObservableList<Product> allSavedProducts = inventoryFileRepository.getAllProducts();
            assert (!allSavedProducts.isEmpty());
            Product saved_product = Collections.max(allSavedProducts, Comparator.comparingInt(Product::getProductId));
            assert (saved_product.equals(product));
        } catch (Exception err) {
            System.out.println(err.getMessage());
            assert (false);
        }
    }

    @RepeatedTest(2)
    @DisplayName("BVA_10 - inStock invalid")
    @Timeout(60)
    void addProduct_inStockInvalid_TC10_BVA() {
        try {
            //arrange
            ObservableList<Part> parts = FXCollections.observableArrayList();

            //act
            inventoryService.addProduct("rabdare", 1000, 9, 10, 100, parts);

            //assert
        } catch (Exception err) {
            assert (err instanceof RuntimeException);
            assert (err.getMessage().contains("Inventory level is lower than minimum value."));
        }
    }
}