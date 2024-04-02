// package ro.unibuc.triplea.application.games.steam.web;

// import org.junit.jupiter.api.Tag;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.MvcResult;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import ro.unibuc.triplea.application.games.steam.dto.response.SteamGameResponse;
// import ro.unibuc.triplea.domain.games.steam.service.SteamGameService;
// import java.util.Optional;
// import static org.hamcrest.Matchers.is;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.mockito.Mockito.when;

// @SpringBootTest
// @Tag("IT")
// public class SteamGameControllerTestIT {

//     @Autowired
//     private MockMvc mockMvc;

//     @Autowired
//     private ObjectMapper objectMapper;

//     @Autowired
//     private SteamGameService steamGameService;

//     @Test
//     public void testGetGameByIdentifier() throws Exception {
//         // Given
//         SteamGameResponse expectedGame = SteamGameResponse.builder()
//                 .gameSteamId(1)
//                 .gameName("test")
//                 .build();

//         when(steamGameService.getGameByIdentifier("test")).thenReturn(Optional.of(expectedGame));

//         // When
//         MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/steam/games/test"))
//                 .andExpect(MockMvcResultMatchers.status().isOk())
//                 .andExpect(MockMvcResultMatchers.jsonPath("$.gameSteamId", is(1)))
//                 .andExpect(MockMvcResultMatchers.jsonPath("$.gameName", is("test")))
//                 .andReturn();

//         // Then
//         String responseBody = result.getResponse().getContentAsString();
//         assertNotNull(responseBody);

//         SteamGameResponse actualGame = objectMapper.readValue(responseBody,
//                 SteamGameResponse.class);
//         assertEquals(expectedGame, actualGame);
//     }
// }
