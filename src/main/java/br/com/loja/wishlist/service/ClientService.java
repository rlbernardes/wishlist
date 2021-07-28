package br.com.loja.wishlist.service;

import br.com.loja.wishlist.entity.Client;
import br.com.loja.wishlist.exception.BusinessRunTimeException;
import br.com.loja.wishlist.message.MessageError;
import br.com.loja.wishlist.repository.ClientRepository;
import br.com.loja.wishlist.util.Translator;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
@NoArgsConstructor
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public Client getClient(Long id_client){
        try {
            Optional<Client> client = clientRepository.findById(id_client);
            if(client.isPresent()){
                return client.get();
            }
        }catch (BusinessRunTimeException e){
            throw new BusinessRunTimeException(Translator.toLocale(MessageError.MSG_CLIENT_FINDFAIL.getMensagem()) + "\n Cliente: " + id_client, e);
        }

        return null;
    }
}
