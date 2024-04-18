package ro.unibuc.triplea.domain.games.steam.service;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import ro.unibuc.triplea.domain.games.steam.exception.SteamGameNotFoundException;
import ro.unibuc.triplea.domain.games.steam.repository.SteamGameRepository;
import ro.unibuc.triplea.application.games.steam.dto.response.SteamGameResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mockStatic;


public class SteamGameServiceTest {

    private final SteamGameRepository steamGameRepository = mock(SteamGameRepository.class);

    private final MeterRegistry meterRegistry = mock(MeterRegistry.class);

    private final Counter counter = mock(Counter.class);

    private final Timer.Sample sample = mock(Timer.Sample.class);

    private SteamGameService steamGameService = new SteamGameService(steamGameRepository, meterRegistry);
    @Test
    public void testGetAllGames() {
        try (MockedStatic<Timer> timerMock = mockStatic(Timer.class)) {
            timerMock.when(() -> Timer.start(any(MeterRegistry.class))).thenReturn(sample);
            
            List<SteamGameResponse> expectedGames = new ArrayList<>();

            when(meterRegistry.counter(anyString(), anyString(), anyString())).thenReturn(counter);
            doNothing().when(counter).increment();
            
            expectedGames.add(SteamGameResponse.builder().gameSteamId(1).gameName("Game 1").build());
            expectedGames.add(SteamGameResponse.builder().gameSteamId(2).gameName("Game 2").build());

            when(steamGameRepository.findGames(any())).thenReturn(expectedGames);

            List<SteamGameResponse> resultGames = steamGameService.getAllGames(Optional.empty());

            assertEquals(expectedGames, resultGames);
            verify(steamGameRepository, times(1)).findGames(Optional.empty());
        }
    }

    @Test
    public void testGetAllGames_NegativeCount() {
        Optional<Integer> count = Optional.of(-1);

        try (MockedStatic<Timer> timerMock = mockStatic(Timer.class)) {
            timerMock.when(() -> Timer.start(any(MeterRegistry.class))).thenReturn(sample);

            when(meterRegistry.counter(anyString(), anyString(), anyString())).thenReturn(counter);
            doNothing().when(counter).increment();
            
            assertThrows(IllegalArgumentException.class, () -> steamGameService.getAllGames(count));
        }
    }

    @Test
    public void testGetAllGames_WithCount() {
        Optional<Integer> count = Optional.of(2);
        try (MockedStatic<Timer> timerMock = mockStatic(Timer.class)) {
            timerMock.when(() -> Timer.start(any(MeterRegistry.class))).thenReturn(sample);
            when(meterRegistry.counter(anyString(), anyString(), anyString())).thenReturn(counter);
            doNothing().when(counter).increment();
            
            List<SteamGameResponse> expectedGames = new ArrayList<>();
            expectedGames.add(SteamGameResponse.builder().gameSteamId(1).gameName("Game 1").build());
            expectedGames.add(SteamGameResponse.builder().gameSteamId(2).gameName("Game 2").build());
            when(steamGameRepository.findGames(any())).thenReturn(expectedGames);
            
            List<SteamGameResponse> resultGames = steamGameService.getAllGames(count);
            assertEquals(expectedGames, resultGames);
            verify(steamGameRepository, times(1)).findGames(count);
        }
    }

    @Test
    public void testGetAllGames_NoGamesFound() {
        Optional<Integer> count = Optional.of(10);

        try (MockedStatic<Timer> timerMock = mockStatic(Timer.class)) {
            timerMock.when(() -> Timer.start(any(MeterRegistry.class))).thenReturn(sample);

            when(meterRegistry.counter(anyString(), anyString(), anyString())).thenReturn(counter);
            doNothing().when(counter).increment();

            when(steamGameRepository.findGames(any())).thenReturn(Collections.emptyList());

            assertThrows(SteamGameNotFoundException.class, () -> steamGameService.getAllGames(count));
        }
    }

    @Test
    public void testGetGameByName() {
        String gameName = "Test Game";
        Optional<SteamGameResponse> expectedGame = Optional
                .of(SteamGameResponse.builder().gameSteamId(123).gameName(gameName).build());

        when(steamGameRepository.findByGameName(gameName)).thenReturn(expectedGame);

        Optional<SteamGameResponse> actualGame = steamGameService.getGameByName(gameName);

        assertEquals(expectedGame, actualGame);
        verify(steamGameRepository, times(1)).findByGameName(gameName);
    }

    @Test
    public void testGetGameByIdentifier_SteamId() {
        Integer steamId = 123;
        String gameIdentifier = steamId.toString();
        Optional<SteamGameResponse> expectedGame = Optional
                .of(SteamGameResponse.builder().gameSteamId(steamId).gameName("Test Game").build());

        when(steamGameRepository.findByGameSteamId(steamId)).thenReturn(expectedGame);

        Optional<SteamGameResponse> actualGame = steamGameService.getGameByIdentifier(gameIdentifier);

        assertEquals(expectedGame, actualGame);
        verify(steamGameRepository, times(1)).findByGameSteamId(steamId);
    }

    @Test
    public void testGetGameByIdentifier_Name() {
        String identifier = "Test Game";
        Optional<SteamGameResponse> expectedGame = Optional
                .of(SteamGameResponse.builder().gameSteamId(123).gameName(identifier).build());

        when(steamGameRepository.findByGameName(identifier)).thenReturn(expectedGame);

        Optional<SteamGameResponse> actualGame = steamGameService.getGameByIdentifier(identifier);

        assertEquals(expectedGame, actualGame);
        verify(steamGameRepository, times(1)).findByGameName(identifier);
    }

    @Test
    public void testGetGameByIdentifier_Id_GameNotFound() {
        String identifier = "123";
        when(steamGameRepository.findByGameSteamId(anyInt())).thenReturn(Optional.empty());
        when(steamGameRepository.findByGameName(anyString())).thenReturn(Optional.empty());

        assertThrows(SteamGameNotFoundException.class, () -> steamGameService.getGameByIdentifier(identifier));
    }

    @Test
    public void testGetGameByIdentifier_Name_GameNotFound() {
        String identifier = "Non-existent Game";
        when(steamGameRepository.findByGameSteamId(anyInt())).thenReturn(Optional.empty());
        when(steamGameRepository.findByGameName(anyString())).thenReturn(Optional.empty());

        assertThrows(SteamGameNotFoundException.class, () -> steamGameService.getGameByIdentifier(identifier));
    }
}