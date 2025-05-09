package inventory;

import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.repository.InventoryFileRepository;
import inventory.repository.InventoryInMemoryRepository;
import inventory.service.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class InventoryServiceRepositoryIntegrationTest {

    InventoryService inventoryService;
    InventoryFileRepository inventoryFileRepository;
    InventoryInMemoryRepository inventoryInMemoryRepository;

    @BeforeEach
    void setUp() {
        inventoryInMemoryRepository = mock(InventoryInMemoryRepository.class);
        inventoryFileRepository = spy(new InventoryFileRepository(inventoryInMemoryRepository));
        inventoryService = new InventoryService(inventoryFileRepository);

        reset(inventoryInMemoryRepository);
        reset(inventoryFileRepository);
    }

    @Test
    void testLookupPart() {
        Part part = new InhousePart(888, "test", 10, 10, 0, 100, 1);
        when(inventoryInMemoryRepository.lookupPart(anyString())).thenReturn(part);

        assert inventoryService.lookupPart("test") == part;
        verify(inventoryFileRepository, times(1)).lookupPart(anyString());
        verify(inventoryInMemoryRepository, times(1)).lookupPart(anyString());
    }


    @Test
    void testGetAllParts() {
        Part part = new InhousePart(888, "test", 10, 10, 0, 100, 1);
        ObservableList<Part> allParts = FXCollections.observableArrayList();
        allParts.add(part);
        when(inventoryInMemoryRepository.getAllParts()).thenReturn(allParts);

        ObservableList<Part> resultedParts = inventoryService.getAllParts();

        assert resultedParts.size() == 1;
        assert resultedParts.get(0).equals(part);
        assert allParts.equals(resultedParts);
        verify(inventoryFileRepository, times(1)).getAllParts();
        verify(inventoryInMemoryRepository, times(1)).getAllParts();
    }
}