package inventory;

import inventory.model.Part;
import inventory.repository.InventoryFileRepository;
import inventory.repository.InventoryInMemoryRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class InventoryFileRepositoryUnitTest {
    InventoryFileRepository inventoryFileRepository;
    InventoryInMemoryRepository inventoryInMemoryRepository;

    @BeforeEach
    void setUp() {
        inventoryInMemoryRepository = mock(InventoryInMemoryRepository.class);
        inventoryFileRepository = new InventoryFileRepository(inventoryInMemoryRepository);
        reset(inventoryInMemoryRepository);
    }

    @Test
    void testLookupPart() {
        Part part = mock(Part.class);
        when(inventoryInMemoryRepository.lookupPart(anyString())).thenReturn(part);

        assert inventoryFileRepository.lookupPart("anything") == part;
        verify(inventoryInMemoryRepository, times(1)).lookupPart(anyString());
    }

    @Test
    void testGetAllParts() {
        Part part = mock(Part.class);
        ObservableList<Part> allParts= FXCollections.observableArrayList();
        allParts.add(part);
        when(inventoryInMemoryRepository.getAllParts()).thenReturn(allParts);

        ObservableList<Part> resultedParts = inventoryFileRepository.getAllParts();

        assert resultedParts.size() == 1;
        assert resultedParts.get(0).equals(part);
        assert allParts.equals(resultedParts);
        verify(inventoryInMemoryRepository, times(1)).getAllParts();
    }
}