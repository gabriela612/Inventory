package inventory.service;

import inventory.model.Part;
import inventory.repository.InventoryFileRepository;
import inventory.repository.InventoryInMemoryRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

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
        try (MockedStatic<Part> mocked = mockStatic(Part.class)) {

            mocked.when(() -> Part.validateName(anyString(), anyString())).thenReturn("");
            assert Part.validateName("anyName", "").isEmpty();

            Part part = mock(Part.class);
            when(inventoryInMemoryRepository.lookupPart(anyString())).thenReturn(part);

            assert inventoryService.lookupPart("anything") == part;
            verify(inventoryFileRepository, times(1)).lookupPart(anyString());
            verify(inventoryInMemoryRepository, times(1)).lookupPart(anyString());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void testGetAllParts() {
        Part part = mock(Part.class);
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