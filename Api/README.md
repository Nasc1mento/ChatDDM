# API REST for Chatlamp

## Endpoints


### 1. Salvar uma mensagem

#### Requisição

- **Método HTTP:** `POST`
- **URL:** `/api/messages`

#### Corpo da Requisição (JSON):

```json
{
    "name": "Fulano de tal",
    "message": "Late Edonaluf"
}
```

#### Resposta
- **Código:** `200 OK`
- **Exemplo de resposta:** 

```json
{
    "name": "Fulano de tal",
    "message": "Late Edonaluf"
}
```

### 2. Listar todos as mensagens

#### Requisição
- **Método HTTP:** GET
- **URL:** `/api/messages`

#### Parâmetros de Query
Nenhum.

#### Resposta

- **Código:** `200 OK`
- **Exemplo de resposta:**
```json
[
    {
        "name": "Maito Gai",
        "message": "Olha só pra você, nem está consciente e está empenhado para mostrar ao mundo o que pode fazer"
    },
    {
        "name": "Kakashi",
        "message": "Ele está nocauteado"
    }
]
```
