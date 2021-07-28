package wishlist.service;


import br.com.loja.wishlist.entity.Client;
import br.com.loja.wishlist.repository.ClientRepository;
import br.com.loja.wishlist.service.ClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import wishlist.DadosTeste;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {
    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    ClientService clientService;

    @Test
    public void should_return_a_client(){
        DadosTeste dadosTeste = new DadosTeste();
        Optional<Client> client = Optional.ofNullable(dadosTeste.creatClient());

        when(clientRepository.findById(1l)).thenReturn(client);

        Client c = clientService.getClient(1l);

        assertThat(client.get().getId()).isSameAs(c.getId());

    }

}
