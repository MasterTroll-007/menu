package cz.client.service;

import cz.client.dto.ClientDto;
import cz.client.entity.Client;
import cz.client.exception.ClientException;
import cz.client.dto.BMRDto;
import cz.menu.exception.MenuException;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

public interface IClientService {

    Client saveClientForm(Model model, ClientDto clientDto);

    void saveBmr(Model model, BMRDto bmrDto, Long menuId) throws MenuException, ClientException;

    Page<ClientDto> findPaginated(int currentPage, int pageSize, String sortField, String sortDir);

    void calculateBMR(Model model, Long menuId) throws MenuException, ClientException;

    void deleteClient(Long id);

    void mealPlan(Model model, Long menuId);

}
