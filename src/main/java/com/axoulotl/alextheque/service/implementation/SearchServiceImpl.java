package com.axoulotl.alextheque.service.implementation;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.exception.StandardErrorEnum;
import com.axoulotl.alextheque.model.dto.input.SearchGameDTO;
import com.axoulotl.alextheque.model.dto.output.GamesOutputDTO;
import com.axoulotl.alextheque.model.entity.Game;
import com.axoulotl.alextheque.repository.GameRepository;
import com.axoulotl.alextheque.repository.specification.GameSpecifications;
import com.axoulotl.alextheque.service.SearchService;
import com.axoulotl.alextheque.service.converter.GameToGameDTOConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final GameRepository gameRepository;
    private final GameToGameDTOConverter converter;


    public GamesOutputDTO searchGameWithCriteria(SearchGameDTO searchGameDTO, int page, int size) throws AlexthequeStandardError {
        Pageable pageable = PageRequest.of(page, size);
        Page<Game> games = null;
        try{
            games = getGamesByCriteria(searchGameDTO, pageable);
        } catch (Exception e){
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_DATABASE, "Error while getting the games.");
        }

        return GamesOutputDTO.builder()
                .nbGames(games.getTotalElements())
                .games(converter.gamesToListOfGames(games.getContent()))
                .totalPages(games.getTotalPages())
                .currentPage(page)
                .build();
    }

    /**
     *  Get all the game matching the criteria from the Search DTO
     *
     * @param searchGameDTO - The criteria to look for
     * @param pageable - The number of game and the current page to look for
     * @return the page of the game matching the criteria
     */
    private Page<Game> getGamesByCriteria(SearchGameDTO searchGameDTO, Pageable pageable) {
        Specification<Game> specifications = GameSpecifications.getSpecification(searchGameDTO);
        return gameRepository.findAll(specifications, pageable);
    }
}
