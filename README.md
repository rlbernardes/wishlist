# Wishlist

Ao iniciar o projeto no modo test são criadas as tabelas
no banco de dados.

Foi criado o arquivo data.sql que insere
3 produtos e 3 clientes.

Para utilizar os serviços a url base é
http://localhost:8090/wishlist

OBS: O delete possui duas urls, na primeira é possível deletar um produto
do carrinho de compra, na segunda é possíve deletar todo o carrinho de compras 
do cliente.

Os serviços são:

POST:

     URL: http://localhost:8090/wishlist
Request Body Exemplo:

    {
        "client": "1",
        "product": "1",
        "quantity": "1"
    }

Response Body Exemplo:

    [
        {
            "product": {
                "id": 2,
                "descrption": "Nunchaku",
                "price": 100.0
            },
            "quantity": 2,
            "total": 200.0
        }
    ]


GET:

    URL: http://localhost:8090/wishlist/{clientID}

Response Body Exemplo:

    [
        {
            "product": {
                "id": 2,
                "descrption": "Nunchaku",
                "price": 100.0
            },
            "quantity": 2,
            "total": 200.0
        }
    ]

DELETE:

    URL1: http://localhost:8090/wishlist/product

Request Body Exemplo:

    {
        "product":1,
        "client":1
    }
Response Body Exemplo caso 1 (onde só exisitia um produto no carrinho):
    
    []

Response Body Exemplo caso 2 (onde existe mais de 1 produto no carrinho):

    [
        {
            "product": {
                "id": 1,
                "descrption": "Nonatsu no taizai",
                "price": 100.0
            },
            "quantity": 2,
            "total": 200.0
        }
    ]

DELETE:

    URL2: http://localhost:8090/wishlist/{clientId}


Response Body Exemplo:

    []

A aplicação foi desenvolvida de forma que os produtos e os carrinhos de compra não são excluídos
eles são apenas inativados. Essa abordagem foi adotada basendo-se no principio de que o carrinho
de compra de um usuário diz muito sobre o perfil dele, e manter esses dados serve para análises 
onde seria possivel por exemplo ofertar determinado segmento de produtos ao cliente.

Implementações futuras: Criar um job que a partir de determinado tempo de inatividade da sessão do
usuário inativa o carrinho de compra.
