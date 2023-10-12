package com.insper.partida.game;


import com.insper.partida.equipe.Team;
import com.insper.partida.equipe.TeamService;
import com.insper.partida.game.dto.GameReturnDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GameServiceTests {

    @InjectMocks
    GameService gameService;

    @Mock
    TeamService teamService;

    @Mock
    GameRepository gameRepository;

    @Test
    void test_listGamesWhenHomeAndAwayNotNull(){
        Team team = new Team();
        team.setId("1");
        team.setIdentifier("time-1");

        Team team2 = new Team();
        team2.setId("2");
        team2.setIdentifier("time-2");

        Game game = new Game();
        game.setHome(team.getIdentifier());
        game.setAway(team2.getIdentifier());


        Pageable pageable = PageRequest.of(0, 8);

        List<Game> games = new ArrayList<>();
        games.add(game);

        Page<Game> pagedResponse = new PageImpl<>(games);

        Mockito.when(teamService.getTeam("time-1")).thenReturn(team);
        Mockito.when(teamService.getTeam("time-2")).thenReturn(team2);
        Mockito.when(gameRepository.findByHomeAndAway("time-1", "time-2", pageable))
                .thenReturn(pagedResponse);

        Page<GameReturnDTO> resp = gameService.listGames("time-1", "time-2", null, pageable);

        Assertions.assertEquals(game.getIdentifier(), resp.getContent().get(0).getIdentifier());
        Assertions.assertEquals(team2.getIdentifier(), resp.getContent().get(0).getAway());

    }

    @Test
    void test_listAllGames(){
        Team team = new Team();
        team.setId("1");
        team.setIdentifier("time-1");

        Team team2 = new Team();
        team2.setId("2");
        team2.setIdentifier("time-2");

        Game game = new Game();
        game.setHome(team.getIdentifier());
        game.setAway(team2.getIdentifier());


        Pageable pageable = PageRequest.of(0, 8);

        List<Game> games = new ArrayList<>();
        games.add(game);

        Page<Game> pagedResponse = new PageImpl<>(games);


        Mockito.when(gameRepository.findAll(pageable)).thenReturn(pagedResponse);

        Page<GameReturnDTO> resp = gameService.listGames(null, null, null, pageable);

        Assertions.assertEquals(game.getIdentifier(), resp.getContent().get(0).getIdentifier());
        Assertions.assertEquals(team.getIdentifier(), resp.getContent().get(0).getHome());

    }
}
