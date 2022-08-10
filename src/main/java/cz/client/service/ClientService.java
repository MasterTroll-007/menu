package cz.client.service;

import cz.client.dto.ClientDto;
import cz.client.entity.BMR;
import cz.client.entity.Client;
import cz.client.exception.ClientException;
import cz.client.dto.BMRDto;
import cz.menu.model.Sex;
import cz.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.util.Precision;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService implements IClientService {

    private final ClientRepository clientRepository;

    @Override
    @Transactional
    public Client saveClientForm(Model model, ClientDto clientDto) {
        Client clientEntity = clientEntityMapper(clientDto);
        Client savedEntity = clientRepository.save(clientEntity);
        model.addAttribute("model", model);
        model.addAttribute("client", clientDto);

        log.info("Ulozene data: {}", savedEntity);

        return savedEntity;
    }

    @Override
    public Page<ClientDto> findPaginated(int currentPage, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, sort);

        Page<Client> clients = clientRepository.findAll(pageable);
        List<ClientDto> clientsDto = new ArrayList<>();
        clients.forEach(client ->
                clientsDto.add(new ClientDto(client)));

        return new PageImpl<>(clientsDto, pageable, clients.getTotalElements());
    }

    @Override
    @Transactional
    public void saveBmr(Model model, BMRDto bmrDto, Long clientId) throws ClientException {
        Client clientEntity = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientException("Jídelníček nenalezen."));

        BMR bmrEntity = bmrEntityMapper(bmrDto);

        if (clientEntity.getBmr() == null) {
            clientEntity.setBmr(new BMR());
        } else {
            bmrEntity.setId(clientEntity.getBmr().getId());
        }

        clientEntity.getBmr().setProteins(bmrEntity.getProteins());
        clientEntity.getBmr().setCarbohydrates(bmrEntity.getCarbohydrates());
        clientEntity.getBmr().setFats(bmrEntity.getFats());
        clientEntity.getBmr().setFibres(bmrEntity.getFibres());
        clientEntity.getBmr().setKJ(bmrEntity.getKJ());

        Client save = clientRepository.save(clientEntity);
        log.info(save.getBmr().toString());
    }

    @Override
    public void calculateBMR(Model model, Long clientId) throws ClientException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientException("Klient nenalezen."));

        BMR bmr = getBmr(client);

        model.addAttribute("model", model);
        model.addAttribute("sex", client.getSex());
        model.addAttribute("fullname", client.getFullName());
        model.addAttribute("bmr", bmr);
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public void mealPlan(Model model, Long clientId) {
        Client clientEntity = clientRepository.getReferenceById(clientId);

    }

    private BMR getBmr(Client client) {
        BMR bmr = new BMR();
        final long age = client.getAge();
        final long height = client.getHeight();
        final long weight = client.getWeight();
        long kj;

        if(Sex.FEMALE.equals(client.getSex())) {
            kj = (long)(((10 * weight) + (6.25 * height) - (5 * age) - 161) * 4.184) * (long)client.getBmrCoef();
        } else {
            kj = (long)(((10 * weight) + (6.25 * height) - (5 * age) + 5) * 4.184) * (long)client.getBmrCoef();
        }
        bmr.setKJ(Precision.round(kj,1));
        // P/C/F = 25%/45%/30%
        long proteins = (long)Precision.round(bmr.getKJ() / 17 * 0.25, 0);
        long carbs = (long)Precision.round(bmr.getKJ() / 17 * 0.45, 0);
        long fats = (long)Precision.round(bmr.getKJ() / 38 * 0.30, 0);
        bmr.setFats(fats);
        bmr.setCarbohydrates(carbs);
        bmr.setProteins(proteins);
        return bmr;
    }

    private Client clientEntityMapper(ClientDto dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Client.class);
    }

    private BMR bmrEntityMapper(BMRDto dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, BMR.class);
    }

}
