package com.axoulotl.alextheque.service;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.model.dto.input.SearchGameDTO;
import com.axoulotl.alextheque.model.dto.output.GamesOutputDTO;

/**
 * Search related service
 * <p>
 *     This interface defines jobs operations for searching games
 * </p>
 *
 * @version 1.0
 * @since 2026-01-01
 */
public interface SearchService {
    /**
     * Search all the game following the search filter
     *
     * @param searchGameDTO - The entry data with all the criteria to meet
     * @param page - The page to display
     * @param size - The number of result per page
     * @return a {@link GamesOutputDTO} - which check all the search specification and matching the current pages
     * @throws AlexthequeStandardError a {@link AlexthequeStandardError} - if any error happen during the proccess
     */
    GamesOutputDTO searchGameWithCriteria(SearchGameDTO searchGameDTO, int page, int size) throws AlexthequeStandardError;
}
