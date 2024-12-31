package br.com.zup.consumer.controllerTest;

public class TestConrollerConsumer {
    @Mock
    private ConsumerService consumerService;

    @InjectMocks
    private ConsumerController consumerController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(consumerController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateConsumer() throws Exception {
        ConsumerRequestDTO requestDTO = new ConsumerRequestDTO("John Doe", 30, "john.doe@example.com");
        ConsumerResponseDTO responseDTO = new ConsumerResponseDTO("1", "John Doe", 30, "john.doe@example.com");

        when(consumerService.createConsumer(any())).thenReturn(responseDTO);

        mockMvc.perform(post("/consumers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(consumerService, times(1)).createConsumer(any());
    }

    @Test
    void testGetAllConsumers() throws Exception {
        List<ConsumerResponseDTO> responseDTOs = Arrays.asList(
                new ConsumerResponseDTO("1", "John Doe", 30, "john.doe@example.com"),
                new ConsumerResponseDTO("2", "Jane Doe", 25, "jane.doe@example.com")
        );

        when(consumerService.getAllConsumers()).thenReturn(responseDTOs);

        mockMvc.perform(get("/consumers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"));

        verify(consumerService, times(1)).getAllConsumers();
    }

    @Test
    void testGetConsumerById() throws Exception {
        ConsumerResponseDTO responseDTO = new ConsumerResponseDTO("1", "John Doe", 30, "john.doe@example.com");

        when(consumerService.getConsumerById("1")).thenReturn(responseDTO);

        mockMvc.perform(get("/consumers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(consumerService, times(1)).getConsumerById("1");
    }

    @Test
    void testUpdateConsumer() throws Exception {
        ConsumerRequestDTO requestDTO = new ConsumerRequestDTO("John Smith", 35, "john.smith@example.com");
        ConsumerResponseDTO responseDTO = new ConsumerResponseDTO("1", "John Smith", 35, "john.smith@example.com");

        when(consumerService.updateConsumer(eq("1"), any())).thenReturn(responseDTO);

        mockMvc.perform(put("/consumers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("John Smith"));

        verify(consumerService, times(1)).updateConsumer(eq("1"), any());
    }

    @Test
    void testDeleteConsumer() throws Exception {
        doNothing().when(consumerService).deleteConsumer("1");

        mockMvc.perform(delete("/consumers/1"))
                .andExpect(status().isNoContent());

        verify(consumerService, times(1)).deleteConsumer("1");
    }
}
