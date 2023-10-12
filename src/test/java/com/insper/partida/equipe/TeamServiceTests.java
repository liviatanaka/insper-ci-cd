package com.insper.partida.equipe;

import com.insper.partida.equipe.dto.SaveTeamDTO;
import com.insper.partida.equipe.dto.TeamReturnDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTests {

    @InjectMocks
    TeamService teamService;

    @Mock
    TeamRepository teamRepository;


    @Test
    void test_listTeams() {
        Mockito.when(teamRepository.findAll()).thenReturn(new ArrayList<>());

        List<TeamReturnDTO> resp = teamService.listTeams();

        Assertions.assertEquals(0, resp.size());
    }

    @Test
    void test_listTeamsNotEmpty() {

        Team team = getTeam();

        List<Team> lista = new ArrayList<>();
        lista.add(team);

        Mockito.when(teamRepository.findAll()).thenReturn(lista);

        List<TeamReturnDTO> resp = teamService.listTeams();

        Assertions.assertEquals(1, resp.size());
    }

//    @Test
//    void test_saveTeam(){
//        Team team = getTeam();
//
//        SaveTeamDTO saveTeamDTO = new SaveTeamDTO();
//        saveTeamDTO.setIdentifier(team.getIdentifier());
//        saveTeamDTO.setName(team.getName());
//        saveTeamDTO.setStadium(team.getStadium());
//
//        Mockito.when(teamRepository.existsByIdentifier(team.getIdentifier())).thenReturn(false);
//        Mockito.when(teamRepository.save(team)).thenReturn(team);
//        TeamReturnDTO teamReturnDTO = teamService.saveTeam(saveTeamDTO);
//
//        Assertions.assertEquals("time-1", teamReturnDTO.getIdentifier());
//        Assertions.assertEquals("Time 1", teamReturnDTO.getName());
//
//
//    }

    @Test
    void test_getTeam(){
        Team team = getTeam();

        Mockito.when(teamRepository.findByIdentifier("time-1")).thenReturn(team);

        Team resp = teamService.getTeam("time-1");

        Assertions.assertEquals("1", resp.getId());
    }




    private static Team getTeam() {
        Team team = new Team();
        team.setId("1");
        team.setIdentifier("time-1");
        team.setName("Time 1");
        return team;
    }


}
