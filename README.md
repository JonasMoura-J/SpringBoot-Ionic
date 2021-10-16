# eVendas API

O [eVendas](https://rest-api-ionic.herokuapp.com/) disponibiliza uma API REST que permite o acesso aos produtos, e categorias do sistema de forma publica.

Docunebtação feita no Swagger: (https://rest-api-ionic.herokuapp.com/swagger-ui.html)

## Métodos
Requisições para a API devem seguir os padrões:
| Método | Descrição |
|---|---|
| `GET` | Retorna informações de um ou mais registros. |
| `POST` | Utilizado para criar um novo registro. |
| `PUT` | Atualiza dados de um registro ou altera sua situação. |
| `DELETE` | Remove um registro do sistema. |


## Respostas

| Código | Descrição |
|---|---|
| `200` | Requisição executada com sucesso (success).|
| `400` | Erros de validação ou os campos informados não existem no sistema.|
| `401` | Dados de acesso inválidos.|
| `404` | Registro pesquisado não encontrado (Not found).|
| `405` | Método não implementado.|
| `410` | Registro pesquisado foi apagado do sistema e não esta mais disponível.|
| `422` | Dados informados estão fora do escopo definido para o campo.|
| `429` | Número máximo de requisições atingido. (*aguarde alguns segundos e tente novamente*)|


## Listar
As ações de `listar` permitem o envio dos seguintes parâmetros:

| Parâmetro | Descrição |
|---|---|
| `filtro` | Filtra dados pelo valor informado. |
| `page` | Informa qual página deve ser retornada. |

# Group Recursos


# Categorias [/categorias]

Os contatos podem ser clientes, fornecedores e transportadoras.


### Listar (List) [GET]

+ Request (application/json)

+ Response 200 (application/json)

          {
              "total": 1,
              "per_page": 50,
              "current_page": 1,
              "last_page": 4,
              "next_page_url": null,
              "prev_page_url": null,
              "from": 1,
              "to": 50,
              "data": [
                {
                  "codigo": "1",
                  "tipo": ["cliente", "fornecedor"],
                  "nome": "Nome do contato",
                  "emails": [],
                  "fones": [],
                  "tags": [],
                  "cidade": "Nome da cidade",
                  "uf": "UF"
                }
              ]
          }

+ Response 401 (application/json)

          {
              "errCode": 401,
              "errMsg": "Não foi possível acessar o sistema. Verifique seu \"access_token\".",
              "errObs": "access_denied",
              "errFields": null,
              "errUrl": "/v1/contatos"
          }

### Novo (Create) [POST]

+ Attributes (object)

    + nome: nome do contato (string, required) - limite 60 caracteres
    + fantasia (string, optional) - limite de 60 caracteres
    + tipo (array, required) - Tipo
        + cliente
        + fornecedor
        + transportadora
    + nomeParaContato (string, optional) - limite de 60 caracteres
    + cpfcnpj (string, optional)
    + dtNasc (string, optional) - formato: YYYY-MM-DD
    + emails (array)
    + fones (array)
    + cep (number, optional)
    + logradouro (string, optional)
    + numero (string, optional)
    + complemento (string, optional)
    + bairro (string, optional)
    + cidade (string, optional)
    + codIBGE (string, optional)
    + uf (string, optional)
    + pais (string, optional)
    + clienteFinal (optional)
    + indicadorIE (enum[number], optional)
        + Members
          + 1 - Contribuinte
          + 2 - Isento de IE
          + 9 - Não contribuinte
    + inscricaoMunicipal (string, optional)
    + inscricaoEstadual (string, optional)
    + inscricaoEstadualST (string, optional)
    + suframa (string, optional)
    + obs (string, optional)
    + tags (array, optional)

+ Request (application/json)

    + Headers

            Authorization: Bearer [access_token]

    + Body

            {
              "codigo": 324,
              "nome": "Kaya Labadie",
              "fantasia": "",
              "nomeParaContato": "Elfrieda Labadie",
              "cpfcnpj": "83294489654",
              "tipo": [
                "cliente"
              ],
              "dtNasc": "1992-02-13",
              "emails": [
                "exemplo@example.com.br"
              ],
              "fones": [],
              "cep": 4320040,
              "logradouro": "Rua Exemplo lado ímpar",
              "numero": "999",
              "complemento": "",
              "bairro": "",
              "codIBGE": "355030",
              "uf": "SP",
              "pais": "",
              "clienteFinal": true,
              "indicadorIE": 1,
              "inscricaoMunicipal": "",
              "inscricaoEstadual": "",
              "inscricaoEstadualST": "",
              "suframa": "",
              "obs": "",
              "tags": []
            }

+ Response 200 (application/json)

    + Headers

            X-RateLimit-Limit: 60
            X-RateLimit-Remaining: 59

    + Body

            {
                "codigo": 1,
                "nome": "Nome do contato"
            }

### Detalhar (Read) [GET /categorias/{codigo}]

+ Parameters
    + codigo (required, number, `1`) ... Código do contato

+ Request (application/json)

    + Headers

            Authorization: Bearer [access_token]

+ Response 200 (application/json)
  Todos os dados do contato
    + Headers

            X-RateLimit-Limit: 60
            X-RateLimit-Remaining: 58

    + Body

            {
              "codigo": "1",
              "tipo": ["cliente", "fornecedor", "transportadora"],
              "nome": "Nome do novo contato",
              "fantasia": "",
              "nomeContato": "",
              "cpfcnpj": "",
              "clienteFinal": true,
              "indicadorIE": 9,
              "inscricaoEstadual": "",
              "inscricaoEstadualST": "",
              "inscricaoMunicipal": "",
              "suframa": "",
              "emails": [],
              "logradouro": "",
              "numero": "",
              "complemento": "",
              "bairro": "",
              "cidade": "",
              "codIBGE": "",
              "uf": "",
              "cep": "",
              "pais": "",
              "fones": [],
              "tags": [],
              "obs": "",
              "dtNasc": "1990-05-12",
              "dtCad": "2017-01-15 11:20:15"
            }

+ Response 404 (application/json)
  Quando registro não for encontrado.
    + Headers

            X-RateLimit-Limit: 60
            X-RateLimit-Remaining: 59

    + Body

            {
              "errCode": 404,
              "errMsg": "Nenhum registro com código 1 econtrado",
              "errObs": null,
              "errFields": null
            }

+ Response 410 (application/json)
  Quando registro foi apagado do sistema, o código de retorno é 410.
    + Headers

            X-RateLimit-Limit: 60
            X-RateLimit-Remaining: 59
    + Body

            {
              "errCode": 410,
              "errMsg": "Registro com código 1 não existe.",
              "errObs": null,
              "errFields": null
            }

### Editar (Update) [PUT  /categorias/{codigo}]

+ Request (application/json)

    + Headers

            Authorization: Bearer [access_token]

    + Body

            {
              "nome": "Exemplo Company LTDA",
              "fantasia": "",
              "nomeParaContato": "Elfrieda",
              "cpfcnpj": "83294489654",
              "tipo": [
                "cliente"
              ],
              "dtNasc": "1992-02-13",
              "emails": [
                "exemplo@example.com.br"
              ],
              "fones": [],
              "cep": 4320040,
              "logradouro": "Rua Exemplo lado ímpar",
              "numero": "999",
              "complemento": "",
              "bairro": "",
              "codIBGE": "355030",
              "uf": "SP",
              "pais": "",
              "clienteFinal": true,
              "indicadorIE": 1,
              "inscricaoMunicipal": "",
              "inscricaoEstadual": "",
              "inscricaoEstadualST": "",
              "suframa": "",
              "obs": "",
              "tags": []
            }

+ Response 200 (application/json)
  Todos os dados do contato
    + Headers

            X-RateLimit-Limit: 60
            X-RateLimit-Remaining: 58

    + Body

            {
              "codigo": "1",
              "tipo": ["cliente", "fornecedor", "transportadora"],
              "nome": "Nome do novo contato",
              "fantasia": "",
              "nomeContato": "",
              "cpfcnpj": "",
              "clienteFinal": true,
              "indicadorIE": 9,
              "inscricaoEstadual": "",
              "inscricaoEstadualST": "",
              "inscricaoMunicipal": "",
              "suframa": "",
              "emails": [],
              "logradouro": "",
              "numero": "",
              "complemento": "",
              "bairro": "",
              "cidade": "",
              "codIBGE": "",
              "uf": "",
              "cep": "",
              "pais": "",
              "fones": [],
              "tags": [],
              "obs": "",
              "dtNasc": "1990-05-12",
              "dtCad": "2017-01-15 11:20:15"
            }

### Remover (Delete) [DELETE  /categortias/{codigo}]

+ Request (application/json)

    + Headers

            Authorization: Bearer [access_token]

+ Response 200 (application/json)

    + Headers

            X-RateLimit-Limit: 60
            X-RateLimit-Remaining: 59

    + Body

            {
                "code": "200",
                "msg": "Contato com código 317 excluído com sucesso!",
                "obs": null,
                "fields": null
            }

# Produtos [/produtos]

Produtos são utilizados nas vendas e controle de estoque.


### Listar (List) [GET]

+ Request (application/json)

  + Headers

            Authorization: Bearer [access_token]

+ Response 200 (application/json)

            {
                "total": 1,
                "per_page": 50,
                "current_page": 1,
                "last_page": 4,
                "next_page_url": null,
                "prev_page_url": null,
                "from": 1,
                "to": 50,
                "data": [
                  {
                      "codigo": 1,
                      "descricao": "Caneca Preta",
                      "codigoProprio": "",
                      "estoque": 60,
                      "estoqueMinimo": 10,
                      "controlarEstoque": false,
                      "margemLucro": 0,
                      "precoCusto": 100,
                      "precoVenda": 200,
                      "origemFiscal": 0,
                      "unidadeTributada": "UN",
                      "refEanGtin": "",
                      "ncm": "",
                      "excecaoIPI": 1,
                      "codigoCEST": "",
                      "pesoBruto": 0,
                      "pesoLiquido": 0,
                      "codigoGrupoTributos": 1,
                      "anotacoesNFE": "",
                      "anotacoesInternas": "",
                      "tags": [ 'café', 'utilidades' ],
                      "dtCad": "2017-03-10 14:10:37"
                  }
                ]
            }

### Novo (Create) [POST]

+ Request (application/json)

    + Headers

            Authorization: Bearer [access_token]

    + Body

                {
                  "descricao": "Novo produto",
                  "codigoProprio": "001",
                  "estoque": 100,
                  "estoqueMinimo": 0,
                  "controlarEstoque": true,
                  "margemLucro": 0.00,
                  "precoCusto": 5.00,
                  "precoVenda": 10.37,
                  "origemFiscal": 0,
                  "unidadeTributada": "UN",
                  "refEanGtin": "",
                  "ncm": "",
                  "codigoCEST": "0107400",
                  "excecaoIPI": 7,
                  "codigoGrupoTributos": 0,
                  "anotacoesNFE": "",
                  "anotacoesInternas": "",
                  "pesoBruto": 0,
                  "pesoLiquido": 0,
                  "tags": ['examplo', 'modelo']
                }

+ Response 200 (application/json)

    + Headers

            X-RateLimit-Limit: 60
            X-RateLimit-Remaining: 59

    + Body

            {
                "codigo": 1,
                "descricao": "Novo produto"
            }

### Detalhar (Read) [GET /produtos/{codigo}]

+ Request (application/json)

  + Headers

            Authorization: Bearer [access_token]

  + Parameters
      + codigo (required, number, `1`) ... Código do contato

+ Response 200 (application/json)

    + Headers

            X-RateLimit-Limit: 60
            X-RateLimit-Remaining: 58

    + Body

            {
              "codigo": "1",
              "descricao": "Novo produto",
              "codigoProprio": "",
              "estoque": 100,
              "estoqueMinimo": 0,
              "controlarEstoque": true,
              "margemLucro": 0.00,
              "precoCusto": 0.0000,
              "precoVenda": 0.0000,
              "origemFiscal": 0,
              "unidadeTributada": "UN",
              "refEanGtin": "",
              "ncm": "",
              "codigoCEST": "",
              "excecaoIPI": "",
              "codigoGrupoTributos": 0,
              "anotacoesNFE": "",
              "anotacoesInternas": "",
              "pesoBruto": "0.000",
              "pesoLiquido": "0.000",
              "tags": [],
              "dtCad": "2017-05-24 17:32:18"
            }

+ Response 410 (application/json)
 Quando o registro foi apagado do sistema.

    + Body

            {
              "errCode": 410,
              "errMsg": "Registro com código 1 não existe.",
              "errObs": null,
              "errFields": null
            }

+ Response 404 (application/json)
 Quando o registro não foi encontrado.

    + Body

            {
              "errCode": 404,
              "errMsg": "Nenhum registro com código 1 econtrado",
              "errObs": null,
              "errFields": null
            }

### Editar (Update) [PUT /produtos/{codigo}]

+ Request (application/json)

  + Headers

            Authorization: Bearer [access_token]

  + Parameters
      + codigo (required, number, `1`) ... Código do contato

+ Response 200 (application/json)

    + Headers

            X-RateLimit-Limit: 60
            X-RateLimit-Remaining: 58

    + Body

            {
              "descricao": "Novo produto",
              "codigoProprio": "",
              "estoque": 100,
              "estoqueMinimo": 0,
              "controlarEstoque": true,
              "margemLucro": 0.00,
              "precoCusto": 0.0000,
              "precoVenda": 0.0000,
              "origemFiscal": 0,
              "unidadeTributada": "UN",
              "refEanGtin": "",
              "ncm": "",
              "codigoCEST": "",
              "excecaoIPI": "",
              "codigoGrupoTributos": 0,
              "anotacoesNFE": "",
              "anotacoesInternas": "",
              "pesoBruto": "0.000",
              "pesoLiquido": "0.000",
              "tags": []
            }

+ Response 410 (application/json)
 Quando o registro foi apagado do sistema.

    + Body

            {
              "errCode": 410,
              "errMsg": "Registro com código 1 não existe.",
              "errObs": null,
              "errFields": null
            }

+ Response 404 (application/json)
 Quando registro não foi encontrado.

    + Body

            {
              "errCode": 404,
              "errMsg": "Nenhum registro com código 1 econtrado",
              "errObs": null,
              "errFields": null
            }

### Remover (Delete) [DELETE  /produtos/{codigo}]

+ Request (application/json)

    + Headers

            Authorization: Bearer [access_token]

+ Response 200 (application/json)

    + Headers

            X-RateLimit-Limit: 60
            X-RateLimit-Remaining: 59

    + Body

            {
              "code": "200",
              "msg": "Produto com código 1 excluído com sucesso.",
              "obs": null,
              "fields": null
            }

# Serviços [/cliente]


### Listar (List) [GET]

+ Request (application/json)

  + Headers

            Authorization: Bearer [access_token]

+ Response 200 (application/json)

            {
                "total": 1,
                "per_page": 50,
                "current_page": 1,
                "last_page": 1,
                "next_page_url": null,
                "prev_page_url": null,
                "from": 1,
                "to": 50,
                "data": [
                    {
                        "codigo": 3,
                        "descricao": "",
                        "precoVenda": 0,
                        "codigoGrupoTributos": 0,
                        "tags": [],
                        "dtCad": "2017-04-24 11:44:35",
                        "itemListaServico": ""
                    }
                ]
            }

### Novo (Create) [POST]

+ Request (application/json)

    + Headers

            Authorization: Bearer [access_token]

    + Body

            {
              "descricao": "Manuteção agendada",
              "precoVenda": 10,
              "codigoGrupoTributos": 0,
              "dtCad": "2017-04-24 11:44:35",
              "tags": [],
              "itemListaServico": ""
            }

+ Response 200 (application/json)

    + Headers

            X-RateLimit-Limit: 60
            X-RateLimit-Remaining: 59

    + Body



### Detalhar (Read) [GET /cliente/{codigo}]

+ Request (application/json)

  + Headers

            Authorization: Bearer [access_token]

  + Parameters
      + codigo (required, number, `1`) ... Código do contato

+ Response 200 (application/json)

    + Headers

            X-RateLimit-Limit: 60
            X-RateLimit-Remaining: 58

    + Body

            {
              "descricao": "Manuteção agendada",
              "precoVenda": 10,
              "codigoGrupoTributos": 0,
              "dtCad": "2017-04-24 11:44:35",
              "tags": [],
              "itemListaServico": ""
            }

+ Response 410 (application/json)
 Quando o registro foi apagado do sistema.

    + Body

            {
              "errCode": 410,
              "errMsg": "Registro com código 1 não existe.",
              "errObs": null,
              "errFields": null
            }

+ Response 404 (application/json)
 Quando o registro não foi encontrado.

    + Body

            {
              "errCode": 404,
              "errMsg": "Nenhum registro com código 1 econtrado",
              "errObs": null,
              "errFields": null
            }

### Editar (Update) [PUT /cliente/{codigo}]

+ Request (application/json)

  + Headers

            Authorization: Bearer [access_token]

  + Parameters
      + codigo (required, number, `1`) ... Código do contato

+ Response 200 (application/json)

    + Headers

            X-RateLimit-Limit: 60
            X-RateLimit-Remaining: 58

    + Body

            {
              "codigo": 434,
              "descricao": "Manutenção"
            }

+ Response 410 (application/json)
 Quando o registro foi apagado do sistema.

    + Body

            {
              "errCode": 410,
              "errMsg": "Registro com código 1 não existe.",
              "errObs": null,
              "errFields": null
            }

+ Response 404 (application/json)
 Quando o registro não foi encontrado.

    + Body

            {
              "errCode": 404,
              "errMsg": "Nenhum registro com código 1 econtrado",
              "errObs": null,
              "errFields": null
            }

### Remover (Delete) [DELETE  /cliente/{codigo}]

+ Request (application/json)

    + Headers

            Authorization: Bearer [access_token]

+ Response 200 (application/json)

    + Headers

            X-RateLimit-Limit: 60
            X-RateLimit-Remaining: 59

    + Body

            {
              "code": "200",
              "msg": "Serviço com código 1 excluído com sucesso.",
              "obs": null,
              "fields": null
            }


# Vendas [/pedido]

As vendas não podem ser editadas. Caso seja necessário, apague-a e crie uma nova.

### Listar (List) [GET]

+ Request (application/json)

    + Headers

            Authorization: Bearer [access_token]

+ Response 200 (application/json)

          {
              "total": 1,
              "per_page": 50,
              "current_page": 1,
              "last_page": 4,
              "next_page_url": null,
              "prev_page_url": null,
              "from": 1,
              "to": 50,
              "data": [
                {
                    "codigo": 4,
                    "codContato": 7,
                    "nomeContato": "Cliente",
                    "codVendedor": 1,
                    "dtVenda": "2017-04-01",
                    "dtEntrega": "2017-04-10",
                    "valorTotal": 500,
                    "valorFinanc": 500,
                    "codsNFe": [],
                    "situacao": 50,
                    "tags": []
                }
              ]
          }

+ Response 401 (application/json)

          {
              "errCode": 401,
              "errMsg": "Não foi possível acessar o sistema. Verifique seu \"access_token\".",
              "errObs": "access_denied",
              "errFields": null,
              "errUrl": "/v1/vendas"
          }

### Novo (Create) [POST]

+ Request (application/json)

    + Headers

            Authorization: Bearer [access_token]

    + Body

            {
              "dtVenda":"2017-07-18",
              "codVendedor":1,
              "tags": [],
              "customizado": {
                "xCampo1": "Observações gerais"
              },
              "produtos": [
                {
                "codProduto":17,
                "quant":1,
                "vDesc":0,
                "preco":3438,
                "obs":""
                }
              ],
              "financeiros": [
                { "dtVenc":"2017-05-18" }
              ]
            }

+ Response 200 (application/json)

    + Headers

            X-RateLimit-Limit: 60
            X-RateLimit-Remaining: 59

    + Body

            {
                "codigo": 1,
                "nome": "Nome do contato"
            }

### Detalhar (Read) [GET /pedido/{codigo}]

+ Parameters
    + codigo (required, number, `1`) ... Código da venda

+ Request (application/json)

    + Headers

            Authorization: Bearer [access_token]

+ Response 200 (application/json)
  Todos os dados do contato
    + Headers

            X-RateLimit-Limit: 60
            X-RateLimit-Remaining: 58

    + Body

            {
              "codigo": 1,
              "codContato": 1,
              "nomeContato": "Marcelo",
              "codVendedor": 2,
              "nomeVendedor": "João",
              "dtVenda": "2017-07-11",
              "dtEntrega": "2017-07-12",
              "dtCad": "2017-06-11 14:15:20",
              "valorTotal": 500,
              "valorFinanc": 500,
              "valorEntrada": 0,
              "numParcelas": 0,
              "codsNFe": [],
              "customizado": {
                "xCampo1": "Venda direta."
              },
              "clienteFinal": true,
              "situacao": 50,
              "tags": [],
              "produtos": [
                {
                    "codigo": 26,
                    "codProduto": 17,
                    "tipoProd": "produto",
                    "preco": 0,
                    "custo": 110,
                    "custoCadProd": 110,
                    "quant": 1,
                    "vDesc": 0,
                    "valorIPI": 0,
                    "valorST": 0,
                    "obs": "",
                    "descricao": "Caneca preta",
                    "codigoProprio": "204"
                },
                {
                    "codigo": 267,
                    "codProduto": 17,
                    "tipoProd": "produto",
                    "preco": 500,
                    "custo": 110,
                    "custoCadProd": 110,
                    "quant": 1,
                    "vDesc": 0,
                    "valorIPI": 5,
                    "valorST": 0,
                    "obs": "",
                    "descricao": "Caneca preta",
                    "codigoProprio": "204"
                }
              ],
              "financeiros": [
                {
                    "codigo": 337,
                    "codFormaPgto": 0,
                    "codCaixa": 1,
                    "codRecibo": 0,
                    "situacao": 20,
                    "dtComp": "2017-07-11",
                    "dtVenc": "2017-07-11",
                    "valor": 505
                }
              ]
            }

+ Response 404 (application/json)
  Quando o registro não foi encontrado.
    + Headers

            X-RateLimit-Limit: 60
            X-RateLimit-Remaining: 59

    + Body

            {
              "errCode": 404,
              "errMsg": "Nenhum registro com código 1 econtrado",
              "errObs": null,
              "errFields": null
            }

+ Response 410 (application/json)
  Quando o registro foi apagado do sistema.
    + Headers

            X-RateLimit-Limit: 60
            X-RateLimit-Remaining: 59
    + Body

            {
              "errCode": 410,
              "errMsg": "Registro com código 1 não existe.",
              "errObs": null,
              "errFields": null
            }


### Remover (Delete) [DELETE  /pedido/{codigo}]

+ Request (application/json)

    + Headers

            Authorization: Bearer [access_token]

+ Response 200 (application/json)

    + Headers

            X-RateLimit-Limit: 60
            X-RateLimit-Remaining: 59

    + Body

            {
                "code": "200",
                "msg": "Venda com código 317 excluída com sucesso!",
                "obs": null,
                "fields": null
            }